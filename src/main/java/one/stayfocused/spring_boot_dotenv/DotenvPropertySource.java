package one.stayfocused.spring_boot_dotenv;

import org.springframework.core.env.PropertySource;
import org.springframework.lang.*;


import java.util.Map;

public class DotenvPropertySource extends PropertySource<Map<String, String>> {

    public DotenvPropertySource(String name, Map<String, String> source) {
        super(name, source);
    }

    @Override
    @Nullable
    public Object getProperty(@NonNull String name) {
        return source.get(name);
    }
}
