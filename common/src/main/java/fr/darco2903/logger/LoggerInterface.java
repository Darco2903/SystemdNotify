package fr.darco2903.logger;

public interface LoggerInterface {
    void info(String message);

    void warn(String message);

    void error(String message);

    void debug(String message);
}
