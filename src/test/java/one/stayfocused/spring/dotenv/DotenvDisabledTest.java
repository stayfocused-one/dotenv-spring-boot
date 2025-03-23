package one.stayfocused.spring.dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(properties = "dotenv.enabled=false")
class DotenvDisabledTest {

    @Autowired
    private Environment environment;

    @Test
    void shouldNotLoadDotenvWhenDisabled() {
        assertNull(environment.getProperty("TEST_ENV_VAR"));
    }
}
