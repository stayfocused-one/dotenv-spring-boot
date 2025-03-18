package one.stayfocused.spring_boot_dotenv.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Registers {@link DotenvProperties} as a Spring component to enable
 * configuration via `application.properties` and generate metadata for IDE autocompletion.
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(DotenvProperties.class)
public class DotenvConfig {
}
