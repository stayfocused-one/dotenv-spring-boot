package one.stayfocused.spring_boot_dotenv.reload;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * Exposes a custom Spring Boot Actuator endpoint for reloading the `.env` file at runtime.
 *
 * <p>This endpoint allows triggering a reload of environment variables from the `.env` file
 * without restarting the application. It requires the property {@code dotenv.reload.enabled=true}
 * to be set in `application.properties` or `application.yml`.
 *
 * <p>By default, this endpoint is disabled unless explicitly exposed via:
 * <pre>{@code
 * management.endpoints.web.exposure.include=dotenvReload
 * }</pre>
 *
 * <p>Usage example:
 * <pre>{@code
 * curl -X POST http://localhost:8080/actuator/dotenvReload
 * }</pre>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Endpoint(id = "dotenvReload")
public class DotenvReloadEndpoint {

    private final DotenvReloadService dotenvReloadService;

    /**
     * Triggers a reload of the `.env` file.
     *
     * <p>This method reloads environment variables and updates the Spring Environment.
     * If reloading is disabled, it returns a message indicating that the operation is not allowed.
     *
     * @return a success message if reload is enabled, otherwise an error message
     */
    @ReadOperation
    public String reloadDotenv() {
        boolean reloaded = dotenvReloadService.reload();
        return reloaded
                ? "Dotenv successfully reloaded!"
                : "Dotenv reload is disabled in configuration.";
    }
}
