package one.stayfocused.spring.dotenv.exception;

/**
 * Exception thrown when a required {@code .env} file is not found at the specified path.
 */
public class DotenvFileNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception for a missing {@code .env} file at the given path.
     *
     * @param path the path where the {@code .env} file was expected
     */
    public DotenvFileNotFoundException(String path) {
        super("[Dotenv] Required .env file is missing at: " + path);
    }
}