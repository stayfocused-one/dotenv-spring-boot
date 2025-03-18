package one.stayfocused.spring_boot_dotenv;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;
import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

@Slf4j
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    static {
        log.info("DotenvEnvironmentPostProcessor LOADED!");
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, org.springframework.boot.SpringApplication application) {
        log.info("DotenvEnvironmentPostProcessor started!");
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load(); // Загружаем .env
        Map<String, Object> envVariables = new HashMap<>();

        dotenv.entries().forEach(entry -> envVariables.put(entry.getKey(), entry.getValue()));

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(new MapPropertySource("dotenv", envVariables));
        } else {
            environment.getPropertySources().addLast(new MapPropertySource("dotenv", envVariables));
        }

        log.info(".env variables are loaded before Spring Boot is initialized!");
    }
}