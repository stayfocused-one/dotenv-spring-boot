package one.stayfocused.spring_boot_dotenv.exception;

public class DotenvParseException extends RuntimeException {
    public DotenvParseException(String message) {
        super(message);
    }

    public DotenvParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
