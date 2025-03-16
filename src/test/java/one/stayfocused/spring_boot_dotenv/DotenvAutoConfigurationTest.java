package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DotenvAutoConfigurationTest extends DotenvTestBase{

    @Autowired
    private Environment environment;

    @Test
    void shouldLoadDotenvProperties() {
        System.out.println("TEST_ENV_VAR=" + environment.getProperty("TEST_ENV_VAR"));

        String testValue = environment.getProperty("TEST_ENV_VAR");
        assertNotNull(testValue, "TEST_ENV_VAR should be loaded from .env file");
    }
}
