package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "spring.profiles.active=dev",
        "dotenv.path=.env.${spring.profiles.active}"
})
class DotenvProfileBasedTest {

    @Autowired
    private Environment environment;

    private static final Path DOTENV_DEV = Path.of(".env.dev");
    private static final Path DOTENV_PROD = Path.of(".env.prod");
    private static final String DEV_CONTENT = "TEST_ENV_VAR=LoadedFromDevEnv";
    private static final String PROD_CONTENT = "TEST_ENV_VAR=LoadedFromProdEnv";

    @BeforeAll
    static void setup() throws IOException {
        Files.writeString(DOTENV_DEV, DEV_CONTENT);
        Files.writeString(DOTENV_PROD, PROD_CONTENT);
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(DOTENV_DEV);
        Files.deleteIfExists(DOTENV_PROD);
    }

    @Test
    void shouldLoadCorrectDotenvForActiveProfile() {
        assertEquals("LoadedFromDevEnv", environment.getProperty("TEST_ENV_VAR"));
    }
}
