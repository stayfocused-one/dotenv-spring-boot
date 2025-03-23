package one.stayfocused.spring.dotenv.environment;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.*;


import java.util.Map;

/**
 * A custom {@link PropertySource} that holds environment variables from a {@code .env} file.
 * <p>
 * Integrates key-value pairs into Spring's {@link Environment}.
 * </p>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
public class DotenvPropertySource extends PropertySource<Map<String, String>> {

    /**
     * Creates a new {@code DotenvPropertySource} with the given name and source.
     *
     * @param name   the name of the property source
     * @param source the map of environment variables loaded from the {@code .env} file
     */
    public DotenvPropertySource(String name, Map<String, String> source) {
        super(name, source);
    }

    /**
     * Retrieves the value of the specified environment variable.
     *
     * @param name the name of the environment variable
     * @return the value, or {@code null} if not found
     */
    @Override
    @Nullable
    public Object getProperty(@NonNull String name) {
        return source.get(name);
    }
}
