package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

@Slf4j
@Service
public class DotenvReloadService implements ApplicationContextAware {

    private final ConfigurableEnvironment environment;
    private final Map<String, String> dotenvCache = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;

    public DotenvReloadService(ConfigurableEnvironment environment) {
        this.environment = environment;
        reloadDotenvCache();
    }

    public boolean reload() {
        if (!isReloadEnabled(environment)) {
            log.warn("Dotenv reload is disabled. Enable it with `dotenv.reload.enabled=true`");
            return false;
        }

        log.info("Reloading .env file...");
        reloadDotenvCache();

        environment.getPropertySources().remove(DOTENV_KEY);
        DotenvPropertySource newPropertySource = new DotenvPropertySource(DOTENV_KEY, dotenvCache);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(newPropertySource);
        } else {
            environment.getPropertySources().addLast(newPropertySource);
        }

        log.info(".env file successfully reloaded ({} variables)",  dotenvCache.size());
        log.debug("Reloaded variables: {}", dotenvCache.keySet());

        if (applicationContext != null) {
            log.info("Triggering Spring Context refresh...");
            applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
        } else {
            log.warn("ApplicationContext is null, skipping refresh event.");
        }
        return true;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private void reloadDotenvCache() {
        dotenvCache.clear();
        dotenvCache.putAll(DotenvPropertySourceLoader.loadDotenvFromFile(environment));
    }
}
