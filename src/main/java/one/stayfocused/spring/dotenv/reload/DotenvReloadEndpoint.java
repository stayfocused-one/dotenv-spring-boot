package one.stayfocused.spring.dotenv.reload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Actuator endpoint for reloading the {@code .env} file at runtime.
 * <p>
 * Triggers a reload of environment variables without restarting the application.
 * Requires {@code dotenv.reload.enabled=true} in application properties.
 * Expose this endpoint via {@code management.endpoints.web.exposure.include=dotenvReload}.
 * </p>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Component
@Endpoint(id = "dotenvReload")
public class DotenvReloadEndpoint {

    private final ReloadService reloadService;

    /**
     * Constructs a {@code DotenvReloadEndpoint} with the specified reload service.
     *
     * @param reloadService the service to handle reloading of environment variables
     */
    @Autowired
    public DotenvReloadEndpoint(ReloadService reloadService) {
        this.reloadService = reloadService;
    }

    /**
     * Triggers a reload of the {@code .env} file into the Spring Environment.
     *
     * @return a map with a message indicating success or failure (if reloading is disabled)
     */
    @WriteOperation
    public Map<String, String> reloadDotenv() {
        boolean reloaded = reloadService.reload();
        return Map.of("message", reloaded
                ? "Dotenv successfully reloaded!"
                : "Dotenv reload is disabled. Enable it with 'dotenv.reload.enabled=true'.");
    }
}
