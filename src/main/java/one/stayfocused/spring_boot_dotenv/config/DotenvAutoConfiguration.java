package one.stayfocused.spring_boot_dotenv.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EnableConfigurationProperties(DotenvProperties.class)
@ComponentScan(basePackages = "one.stayfocused.spring_boot_dotenv")
public class DotenvAutoConfiguration {
}
