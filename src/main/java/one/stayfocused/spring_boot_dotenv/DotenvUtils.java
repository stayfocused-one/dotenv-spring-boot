package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public final class DotenvUtils {

    public static final String DOTENV_KEY = "dotenv";
    public static final String DOTENV_PATH_KEY = "dotenv.path";
    public static final String DOTENV_ENABLED_KEY = "dotenv.path";
    public static final String DOTENV_FAIL_ON_MISSING_KEY = "dotenv.fail-on-missing";
    public static final String DOTENV_RELOAD_ENABLED_KEY = "dotenv.reload.enabled";
    public static final String DOTENV_PRIORITY_KEY = "dotenv.priority";

    public static final String DEFAULT_DOTENV_PRIORITY = "low";
    public static final boolean DEFAULT_DOTENV_ENABLED = true;
    public static final boolean DEFAULT_RELOAD_ENABLED = false;
    public static final boolean DEFAULT_FAIL_ON_MISSING = false;
    public static final String DEFAULT_ENV_PATH = ".env";

    private DotenvUtils() {
        throw new UnsupportedOperationException("DotenvUtils is a utility class and cannot be instantiated.");
    }

    public static String getDotenvPath(Environment environment) {
        return getStringProperty(environment, DOTENV_PATH_KEY, DEFAULT_ENV_PATH);
    }

    public static boolean getBooleanProperty(Environment environment, String propertyName, boolean defaultValue) {
        return Boolean.parseBoolean(environment.getProperty(propertyName, String.valueOf(defaultValue)));
    }

    public static String getStringProperty(Environment environment, String propertyName, String defaultValue) {
        return environment.getProperty(propertyName, defaultValue);
    }

    public static boolean isHighPriority(Environment environment) {
        return "high".equals(getStringProperty(environment, DOTENV_PRIORITY_KEY, DEFAULT_DOTENV_PRIORITY));
    }

    public static boolean isReloadEnabled(Environment environment) {
        return Boolean.parseBoolean(environment.getProperty(
                DOTENV_RELOAD_ENABLED_KEY, String.valueOf(DEFAULT_RELOAD_ENABLED)
        ));
    }

    public static boolean isFailOnMissing(Environment environment) {
        return getBooleanProperty(environment, DOTENV_FAIL_ON_MISSING_KEY, DEFAULT_FAIL_ON_MISSING);
    }

    public static void handleMissingFile(String dotenvPath, boolean failOnMissing) {
        if (failOnMissing) {
            throw new IllegalStateException(".env file not found at: " + dotenvPath);
        } else {
            log.warn(".env file not found at: {}", dotenvPath);
        }
    }
}
