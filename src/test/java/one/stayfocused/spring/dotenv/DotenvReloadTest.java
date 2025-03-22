package one.stayfocused.spring.dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "dotenv.reload.enabled=true",
        "management.endpoints.web.exposure.include=dotenvReload"
})
class DotenvReloadTest extends DotenvTestBase {

    @LocalServerPort
    private int port;

    @Autowired
    private ConfigurableEnvironment environment;

    private static final String PROPERTY_KEY = "TEST_ENV_VAR";
    private static final String PROPERTY_VALUE_BEFORE_RELOAD = "BeforeReload";
    private static final String PROPERTY_VALUE_AFTER_RELOAD = "AfterReload";
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void shouldLoadDotenvProperties() throws IOException, InterruptedException {
        final String reloadDotenvUrl = "http://localhost:" + port + "/actuator/dotenvReload";

        assertEquals(PROPERTY_VALUE_BEFORE_RELOAD, environment.getProperty(PROPERTY_KEY),
                "Initial value should be BeforeReload");

        Files.writeString(DOTENV_PATH, PROPERTY_KEY + "=" + PROPERTY_VALUE_AFTER_RELOAD);
        Thread.sleep(1000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(reloadDotenvUrl, request, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("{\"message\":\"Dotenv successfully reloaded!\"}", response.getBody());

        assertEquals(PROPERTY_VALUE_AFTER_RELOAD, environment.getProperty(PROPERTY_KEY),
                "After reload, value should be AfterReload");
    }
}
