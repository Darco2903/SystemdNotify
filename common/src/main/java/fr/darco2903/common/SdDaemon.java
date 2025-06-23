/*

Permission to use, copy, modify, and/or distribute this software for
any purpose with or without fee is hereby granted.

THE SOFTWARE IS PROVIDED “AS IS” AND THE AUTHOR DISCLAIMS ALL
WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE
FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY
DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN
AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT
OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

*/

package fr.darco2903.common;

import fr.darco2903.logger.LoggerInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class SdDaemon {

    private final LoggerInterface logger;

    public SdDaemon(LoggerInterface logger) {
        this.logger = logger;
    }

    public void status(String status) {
        notify("--status=" + status);
    }

    public void ready() {
        notify("--ready");
    }

    public boolean booted() {
        return Files.isDirectory(Paths.get("/run/systemd/system"), LinkOption.NOFOLLOW_LINKS);
    }

    private void notify(String arg) {
        if (!booted() || System.getenv("NOTIFY_SOCKET") == null)
            return;

        try {
            Process p = new ProcessBuilder("systemd-notify", arg).redirectErrorStream(true).start();
            if (ignoreInterruptedException(p::waitFor) == 0)
                return;

            String msg = String.format("Failed to notify systemd of/that %s; systemd-notify exited with status %d", arg,
                    p.exitValue());
            logger.error(msg);

            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                r.lines().forEach(l -> logger.error("systemd-notify: " + l));
            }
        } catch (Exception e) {
            logger.error("Failed to notify systemd of service readiness" + e);
        }
    }

    private <T> T ignoreInterruptedException(ThrowingSupplier<T, InterruptedException> r) {
        Supplier<Object> s;
        for (;;) {
            try {
                return r.get();
            } catch (InterruptedException e) {
                continue;
            }
        }
    }

    private interface ThrowingSupplier<T, E extends Throwable> {
        T get() throws E;
    }
}
