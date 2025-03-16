package one.stayfocused.spring_boot_dotenv;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.core.env.PropertySource;

import java.util.Map;

public class DotenvPropertySource extends PropertySource<Map<String, String>> {

    public DotenvPropertySource(String name, Map<String, String> source) {
        super(name, source);
    }

    @Override
    @Nullable
    public Object getProperty(@Nonnull String name) {
        return source.get(name);
    }
}
