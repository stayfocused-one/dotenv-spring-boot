package one.stayfocused.spring_boot_dotenv;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Provides metadata for dotenv settings in application.properties / application.yml.
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "dotenv")
public class DotenvProperties {

    /**
     * Enable or disable loading of the .env file.
     */
    private boolean enabled = true;

    /**
     * The path to the .env file.
     */
    private String path = ".env";

    /**
     * Define whether dotenv variables should have high or low priority.
     */
    private String priority = "low";

    /**
     * Whether the application should fail if the .env file is missing.
     */
    private boolean failOnMissing = false;

    /**
     * Enable or disable the ability to reload the .env file at runtime.
     */
    private boolean reloadEnabled = false;
}
