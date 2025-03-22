package one.stayfocused.spring.dotenv.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Dotenv settings under the {@code dotenv} prefix.
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "dotenv")
public class DotenvProperties {

    /**
     * Constructs a new {@code DotenvProperties} with default values.
     */
    public DotenvProperties() {
        // Defaults are set via field initializers
    }

    /**
     * Whether to enable loading of the {@code .env} file. Defaults to {@code true}.
     */
    private boolean enabled = true;

    /**
     * Path to the {@code .env} file. Defaults to {@code .env}.
     */
    private String path = ".env";

    /**
     * Priority of dotenv variables ({@code high} or {@code low}). Defaults to {@code low}.
     */
    private String priority = "low";

    /**
     * Whether to fail if the {@code .env} file is missing. Defaults to {@code false}.
     */
    private boolean failOnMissing = false;

    /**
     * Settings for runtime reloading of the {@code .env} file.
     */
    private Reload reload = new Reload();

    /**
     * Settings for runtime reloading of the {@code .env} file.
     */
    @Getter
    @Setter
    public static class Reload {
        /**
         * Constructs a new {@code Reload} with default settings.
         */
        public Reload() {
            // Defaults are set via field initializers
        }

        /**
         * Whether runtime reloading is enabled. Defaults to {@code false}.
         */
        private boolean enabled;
    }
}
