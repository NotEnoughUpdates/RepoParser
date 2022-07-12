package io.github.moulberry.repo;

public class NEURepositoryException extends Exception {
    public NEURepositoryException(String path, String message, Throwable cause) {
        super("NEU Repository | Failure loading " + path + ": " + message, cause);
    }

}
