package one.stayfocused.spring_boot_dotenv;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DotenvProperties.class)
public class DotenvConfig {
}
