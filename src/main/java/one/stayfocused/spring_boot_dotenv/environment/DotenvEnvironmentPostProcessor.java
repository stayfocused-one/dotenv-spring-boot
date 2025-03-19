package one.stayfocused.spring_boot_dotenv.environment;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring_boot_dotenv.core.DotenvLoader;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;

import static one.stayfocused.spring_boot_dotenv.core.DotenvUtils.*;

/**
 * Custom {@link EnvironmentPostProcessor} that loads environment variables from a `.env` file
 * before Spring Boot initializes its configuration.
 * <p>
 * - Checks if dotenv loading is enabled via `dotenv.enabled` property.
 * - Uses {@link DotenvLoader} to load variables from the specified `.env` file.
 * - Registers the loaded variables as a {@link DotenvPropertySource} in the Spring environment.
 * - Supports priority configuration (`dotenv.priority=high|low`).
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Slf4j
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    static {
        System.out.println("\nstayfocused.one :: Dotenv for Spring Boot (v1.0.0)\n");
    }

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