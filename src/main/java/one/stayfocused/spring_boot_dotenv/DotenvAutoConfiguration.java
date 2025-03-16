package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

@Slf4j
@Configuration
public class DotenvAutoConfiguration {

    @Bean
    public DotenvPropertySource dotenvPropertySource(Environment environment) {
        log.info("Initializing DotenvPropertySource...");
        Map<String, String> dotenvVariables = DotenvPropertySourceLoader.loadDotenv(environment);
        log.info("DotenvPropertySource initialized. Loaded {} variables.", dotenvVariables.size());
        return new DotenvPropertySource("dotenv", dotenvVariables);
    }
}
