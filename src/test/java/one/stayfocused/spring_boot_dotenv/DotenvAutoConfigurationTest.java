package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DotenvAutoConfigurationTest {

    @Autowired
    private Environment environment;

    @Test
    void shouldLoadDotenvProperties() {
        System.out.println("TEST_ENV_VAR=" + environment.getProperty("TEST_ENV_VAR"));

        String testValue = environment.getProperty("TEST_ENV_VAR");
        assertNotNull(testValue, "TEST_ENV_VAR should be loaded from .env file");
    }
}
