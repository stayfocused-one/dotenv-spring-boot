package one.stayfocused.spring.dotenv.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Autoconfiguration for Dotenv support in Spring Boot.
 * <p>
 * Enables loading of {@code .env} files and registers related components.
 * </p>
 */
@AutoConfiguration
@EnableConfigurationProperties(DotenvProperties.class)
@ComponentScan(basePackages = "one.stayfocused.spring.dotenv")
public class DotenvAutoConfiguration {

    /**
     * Constructs a new {@code DotenvAutoConfiguration}.
     */
    public DotenvAutoConfiguration() {
        // No initialization needed
    }
}
