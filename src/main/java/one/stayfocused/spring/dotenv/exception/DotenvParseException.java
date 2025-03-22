package one.stayfocused.spring.dotenv.exception;

/**
 * Thrown when an error occurs while parsing a .env file.
 */
public class DotenvParseException extends RuntimeException {

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the error message
     */
    public DotenvParseException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message the error message
     * @param cause the cause of the error
     */
    public DotenvParseException(String message, Throwable cause) {
        super(message, cause);
    }
}