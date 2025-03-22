package one.stayfocused.spring.dotenv.environment;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.*;


import java.util.Map;

/**
 * A custom {@link PropertySource} implementation that holds environment variables
 * loaded from a {@code .env} file.
 *
 * <p>This property source provides access to key-value pairs from the loaded
 * environment variables and integrates them into Spring's {@link Environment}.
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
public class DotenvPropertySource extends PropertySource<Map<String, String>> {

    /**
     * Creates a new {@code DotenvPropertySource} with the given name and source.
     *
     * @param name   the name of the property source
     * @param source the map containing environment variables loaded from the .env file
     */
    public DotenvPropertySource(String name, Map<String, String> source) {
        super(name, source);
    }

    /**
     * Retrieves the property value for the given key.
     *
     * @param name the name of the environment variable to retrieve
     * @return the corresponding value, or {@code null} if not found
     */
    @Override
    @Nullable
    public Object getProperty(@NonNull String name) {
        return source.get(name);
    }
}
