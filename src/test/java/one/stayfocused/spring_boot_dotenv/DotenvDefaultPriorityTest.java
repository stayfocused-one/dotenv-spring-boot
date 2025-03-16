package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "TEST_ENV_VAR=from_properties")
class DotenvDefaultPriorityTest extends DotenvTestBase{

    @Autowired
    private Environment environment;

    @Test
    void shouldPrioritizeApplicationPropertiesOverDotenv() {
        assertEquals("from_properties",  environment.getProperty("TEST_ENV_VAR"));
    }
}
