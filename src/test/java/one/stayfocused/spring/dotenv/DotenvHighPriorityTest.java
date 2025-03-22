package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"dotenv.priority=high", "TEST_ENV_VAR=from_properties"})
class DotenvHighPriorityTest extends DotenvTestBase{

    @Autowired
    private Environment environment;

    @Test
    void shouldPrioritizeDotenvOverApplicationProperties() {
        assertEquals("BeforeReload",  environment.getProperty("TEST_ENV_VAR"));
    }
}
