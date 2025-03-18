package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

/**
 * Utility class for managing `.env` file configuration in Spring Boot applications.
 *
 * <p>This class provides helper methods for retrieving configuration properties
 * and handling environment-specific settings related to `.env` file loading.</p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>Retrieves properties from the Spring {@link Environment}.</li>
 *     <li>Handles default values for various dotenv settings.</li>
 *     <li>Determines if dotenv should be enabled, reloaded, or prioritized.</li>
 *     <li>Logs warnings for missing `.env` files when required.</li>
 * </ul>
 *
 * <p>This is a final utility class and cannot be instantiated.</p>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Slf4j
public final class DotenvUtils {

    /** The name of the PropertySource for dotenv variables. */
    public static final String PROPERTY_SOURCE_NAME = "dotenv";

    /** Key for specifying the dotenv file path. */
    public static final String DOTENV_PATH_KEY = "dotenv.path";

    /** Key for enabling or disabling dotenv support. */
    public static final String DOTENV_ENABLED_KEY = "dotenv.enabled";

    /** Key for enforcing failure when the `.env` file is missing. */
    public static final String DOTENV_FAIL_ON_MISSING_KEY = "dotenv.fail-on-missing";

    /** Key for enabling dotenv reload functionality. */
    public static final String DOTENV_RELOAD_ENABLED_KEY = "dotenv.reload.enabled";

    /** Key for determining dotenv property priority. */
    public static final String DOTENV_PRIORITY_KEY = "dotenv.priority";

    /** Default priority level for dotenv properties. */
    public static final String DEFAULT_DOTENV_PRIORITY = "low";

    /** Default value for enabling dotenv support. */
    public static final boolean DEFAULT_DOTENV_ENABLED = true;

    /** Default value for enabling dotenv reload functionality. */
    public static final boolean DEFAULT_RELOAD_ENABLED = false;

    /** Default value for enforcing failure on missing dotenv file. */
    public static final boolean DEFAULT_FAIL_ON_MISSING = false;

    /** Default path for the `.env` file. */
    public static final String DEFAULT_ENV_PATH = ".env";


    /**
     * Private constructor to prevent instantiation.
     *
     * <p>This class is designed to be a static utility class.</p>
     */
    private DotenvUtils() {
        throw new UnsupportedOperationException("DotenvUtils is a utility class and cannot be instantiated.");
    }

    /**
     * Retrieves the dotenv file path from the environment properties.
     *
     * @param environment the Spring environment
     * @return the configured dotenv file path or the default path
     */
    public static String getDotenvPath(Environment environment) {
        return getStringProperty(environment, DOTENV_PATH_KEY, DEFAULT_ENV_PATH);
    }

    /**
     * Retrieves a boolean property from the environment.
     *
     * @param environment the Spring environment
     * @param propertyName the property key
     * @param defaultValue the default value if the property is missing
     * @return the boolean value of the property
     */
    public static boolean getBooleanProperty(Environment environment, String propertyName, boolean defaultValue) {
        return Boolean.parseBoolean(environment.getProperty(propertyName, String.valueOf(defaultValue)));
    }

    /**
     * Retrieves a string property from the environment.
     *
     * @param environment the Spring environment
     * @param propertyName the property key
     * @param defaultValue the default value if the property is missing
     * @return the string value of the property
     */
    public static String getStringProperty(Environment environment, String propertyName, String defaultValue) {
        return environment.getProperty(propertyName, defaultValue);
    }

    /**
     * Determines if the dotenv properties should have high priority in the Spring environment.
     *
     * @param environment the Spring environment
     * @return {@code true} if dotenv should be prioritized, {@code false} otherwise
     */
    public static boolean isHighPriority(Environment environment) {
        return "high".equals(getStringProperty(environment, DOTENV_PRIORITY_KEY, DEFAULT_DOTENV_PRIORITY));
    }

    /**
     * Checks if dotenv reload functionality is enabled.
     *
     * @param environment the Spring environment
     * @return {@code true} if dotenv reload is enabled, {@code false} otherwise
     */
    public static boolean isReloadEnabled(Environment environment) {
        return Boolean.parseBoolean(environment.getProperty(
                DOTENV_RELOAD_ENABLED_KEY, String.valueOf(DEFAULT_RELOAD_ENABLED)
        ));
    }

    /**
     * Checks if the application should fail when the `.env` file is missing.
     *
     * @param environment the Spring environment
     * @return {@code true} if the application should fail on missing `.env`, {@code false} otherwise
     */
    public static boolean isFailOnMissing(Environment environment) {
        return getBooleanProperty(environment, DOTENV_FAIL_ON_MISSING_KEY, DEFAULT_FAIL_ON_MISSING);
    }

    /**
     * Checks if .env file loading is enabled.
     * <p>
     * Retrieves the {@code dotenv.enabled} property from {@link Environment}.
     * Defaults to {@code false} if not set. Returns {@code true} if the property
     * is explicitly set to "true".
     *
     * @param environment the {@link Environment} containing application properties.
     * @return {@code true} if .env loading is enabled, otherwise {@code false}.
     */
    public static boolean isEnable(Environment environment) {
        return environment.getProperty(DOTENV_ENABLED_KEY, String.valueOf(DEFAULT_DOTENV_ENABLED)).equals("true");
    }
}
