package one.stayfocused.spring.dotenv.environment;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring.dotenv.core.DotenvLoader;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

import static one.stayfocused.spring.dotenv.core.DotenvUtils.*;

/**
 * Custom {@link EnvironmentPostProcessor} that loads variables from a {@code .env} file into Spring's {@link Environment}.
 * <p>
 * Loads variables before Spring Boot configuration initialization. Supports enabling/disabling via {@code dotenv.enabled}
 * and priority configuration via {@code dotenv.priority=high|low}.
 * </p>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Slf4j
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     * Constructs a new {@code DotenvEnvironmentPostProcessor}.
     */
    public DotenvEnvironmentPostProcessor() {
        // No initialization needed
    }

    static {
        final String BLUE = "\u001B[34m";
        final String RESET = "\u001B[0m";
        // Using System.out because logger is not yet initialized at this early stage
        System.out.println("\n" + BLUE + "stayfocused.one" + RESET + " :: Dotenv for Spring Boot (v1.0.0)\n");
    }

    /**
     * Loads environment variables from a {@code .env} file into the Spring {@link Environment}.
     * <p>
     * Skips loading if {@code dotenv.enabled=false}. Adds variables with high or low priority based on {@code dotenv.priority}.
     * </p>
     *
     * @param environment the Spring {@link ConfigurableEnvironment} to modify
     * @param application the Spring Boot application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                       org.springframework.boot.SpringApplication application) {
        if (!isEnable(environment)) return;

        Map<String, String> envVariableMap = new HashMap<>(DotenvLoader.load(environment));

        DotenvPropertySource newPropertySource = new DotenvPropertySource(PROPERTY_SOURCE_NAME, envVariableMap);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(newPropertySource);
        } else {
            environment.getPropertySources().addLast(newPropertySource);
        }
    }
}