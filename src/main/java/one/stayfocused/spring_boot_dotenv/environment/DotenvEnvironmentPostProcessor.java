package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

@Slf4j
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    static {
        System.out.println("[Dotenv] DotenvEnvironmentPostProcessor LOADED!");
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                       org.springframework.boot.SpringApplication application) {
        if (!isEnable(environment)) {
            System.out.println("[Dotenv] Loading disabled via 'dotenv.enabled=false'");
            return;
        }

        Map<String, String> envVariableMap = new HashMap<>(DotenvLoader.load(environment));

        DotenvPropertySource newPropertySource = new DotenvPropertySource(PROPERTY_SOURCE_NAME, envVariableMap);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(newPropertySource);
        } else {
            environment.getPropertySources().addLast(newPropertySource);
        }
        System.out.println("[Dotenv] .env variables are loaded before Spring Boot is initialized!");
    }
}