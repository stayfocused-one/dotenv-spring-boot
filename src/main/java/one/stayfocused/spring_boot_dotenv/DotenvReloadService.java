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

@Slf4j
@Service
public class DotenvReloadService implements ApplicationContextAware {

    private static final String DOTENV_RELOAD_ENABLED_KEY = "dotenv.reload.enabled";
    private static final boolean DEFAULT_RELOAD_ENABLED = false;

    private final ConfigurableEnvironment environment;
    private final Map<String, String> dotenvCache = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;

    public DotenvReloadService(ConfigurableEnvironment environment) {
        this.environment = environment;
        this.dotenvCache.putAll(DotenvPropertySourceLoader.loadDotenvFromFile(environment));
    }

    public boolean reload() {
        if (!isReloadEnabled(environment)) {
            log.warn("Dotenv reload is disabled. Enable it with `dotenv.reload.enabled=true`");
            return false;
        }

        log.info("Reloading .env file...");
        dotenvCache.clear();
        dotenvCache.putAll(DotenvPropertySourceLoader.loadDotenvFromFile(environment));

        environment.getPropertySources().remove("dotenv");
        environment.getPropertySources().addFirst(new DotenvPropertySource("dotenv", dotenvCache));
        log.info(".env file successfully reloaded ({} variables)",  dotenvCache.size());

        log.info("Triggering Spring Context refresh...");
        applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
        return true;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static boolean isReloadEnabled(Environment environment) {
        return Boolean.parseBoolean(environment.getProperty(
                DOTENV_RELOAD_ENABLED_KEY, String.valueOf(DEFAULT_RELOAD_ENABLED)
        ));
    }
}
