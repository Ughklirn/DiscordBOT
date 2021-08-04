package net.ughklirn.exceptions;

public class DriverFailedException extends Exception {
    public DriverFailedException() {
    }

    public DriverFailedException(String message) {
        super(message);
    }

    public DriverFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverFailedException(Throwable cause) {
        super(cause);
    }

    public DriverFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
