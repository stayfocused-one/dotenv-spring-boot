package one.stayfocused.spring_boot_dotenv.exception;

/**
 * Thrown when a required .env file is not found at the specified path.
 */
public class DotenvFileNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception for the missing .env file at the given path.
     *
     * @param path the path where the .env file was expected
     */
    public DotenvFileNotFoundException(String path) {
        super("[Dotenv] Required .env file is missing at: " + path);
    }
}