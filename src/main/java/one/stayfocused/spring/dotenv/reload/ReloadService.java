package one.stayfocused.spring.dotenv.reload;

/**
 * Interface for services that support reloading environment variables at runtime.
 */
public interface ReloadService {

    /**
     * Reloads environment variables into the Spring Environment.
     *
     * @return {@code true} if reloading succeeded, {@code false} otherwise
     */
    boolean reload();
}
