package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

/**
 * A service that enables reloading environment variables from a {@code .env} file
 * at runtime without restarting the application.
 *
 * <p>This service reloads the `.env` file and updates Spring's {@link Environment}
 * dynamically, allowing configuration changes to take effect immediately.
 * Optionally, it can trigger a {@link ContextRefreshedEvent} to refresh beans.
 *
 * <p>To enable reloading, set {@code dotenv.reload.enabled=true} in your application properties.
 *
 * @author Augustin (StayFocused)
 * @since 0.0.1
 */
@Slf4j
@Service
public class DotenvReloadService implements ApplicationContextAware {

    private final ConfigurableEnvironment environment;
    private final Map<String, String> dotenvCache = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    /**
     * Constructs a {@code DotenvReloadService} and preloads the environment variables.
     *
     * @param environment the Spring environment in which properties are managed
     */
    public DotenvReloadService(ConfigurableEnvironment environment) {
        this.environment = environment;
        reloadDotenvCache();
    }

    /**
     * Reloads the environment variables from the `.env` file.
     *
     * <p>If reloading is disabled via {@code dotenv.reload.enabled=false}, this method
     * will return {@code false} without making changes.
     *
     * <p>The method clears and repopulates the cached variables, updates the property sources,
     * and optionally triggers a Spring context refresh event.
     *
     * @return {@code true} if reloading was successful, {@code false} if reloading is disabled
     */
    public boolean reload() {
        if (!isReloadEnabled(environment)) {
            log.warn("[Dotenv] Dotenv reload is disabled. Enable it with `dotenv.reload.enabled=true`");
            return false;
        }

        log.info("[Dotenv] Reloading .env file...");
        reloadDotenvCache();

        environment.getPropertySources().remove(PROPERTY_SOURCE_NAME);
        DotenvPropertySource newPropertySource = new DotenvPropertySource(PROPERTY_SOURCE_NAME, dotenvCache);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(newPropertySource);
        } else {
            environment.getPropertySources().addLast(newPropertySource);
        }

        log.info("[Dotenv] .env file successfully reloaded ({} variables)",  dotenvCache.size());
        log.debug("[Dotenv] Reloaded variables: {}", dotenvCache.keySet());

        if (applicationContext != null) {
            log.info("[Dotenv] Triggering Spring Context refresh...");
            applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
        } else {
            log.warn("[Dotenv] ApplicationContext is null, skipping refresh event.");
        }
        return true;
    }

    /**
     * Sets the {@link ApplicationContext}, allowing this service to trigger
     * a context refresh after reloading environment variables.
     *
     * @param applicationContext the Spring application context
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Clears and reloads the cached environment variables from the `.env` file.
     */
    private void reloadDotenvCache() {
        dotenvCache.clear();
        dotenvCache.putAll(DotenvLoader.load(environment));
    }
}
