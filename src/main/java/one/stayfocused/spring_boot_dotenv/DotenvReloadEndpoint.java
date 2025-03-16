package one.stayfocused.spring_boot_dotenv;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Endpoint(id = "dotenv-reload")
public class DotenvReloadEndpoint {

    private final DotenvReloadService dotenvReloadService;

    @ReadOperation
    public String reloadDotenv() {
        boolean reloaded = dotenvReloadService.reload();
        return reloaded
                ? "Dotenv successfully reloaded!"
                : "Dotenv reload is disabled in configuration.";
    }
}
