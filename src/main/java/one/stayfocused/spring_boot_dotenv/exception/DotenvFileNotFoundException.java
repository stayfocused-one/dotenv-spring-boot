package one.stayfocused.spring_boot_dotenv.exception;

public class DotenvFileNotFoundException extends RuntimeException {
    public DotenvFileNotFoundException(String path) {
        super("[Dotenv] Required .env file is missing at: " + path);
    }
}
