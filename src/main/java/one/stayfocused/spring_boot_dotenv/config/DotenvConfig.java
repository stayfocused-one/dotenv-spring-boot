package one.stayfocused.spring_boot_dotenv.config;

import one.stayfocused.spring_boot_dotenv.reload.DotenvReloadEndpoint;
import one.stayfocused.spring_boot_dotenv.reload.DotenvReloadService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Registers {@link DotenvProperties} as a Spring component to enable
 * configuration via `application.properties` and generate metadata for IDE autocompletion.
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Configuration
@Import(DotenvReloadService.class)
@EnableConfigurationProperties(DotenvProperties.class)
public class DotenvConfig {

    @Bean
    @ConditionalOnMissingBean
    public DotenvReloadEndpoint dotenvReloadEndpoint(DotenvReloadService service) {
        return new DotenvReloadEndpoint(service);
    }
}
