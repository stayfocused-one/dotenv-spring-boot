package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "dotenv.path=src/test/resources/.env")
class DotenvCustomPathTest {

    @Autowired
    private Environment environment;

    protected static final Path DOTENV_PATH = Path.of("src/test/resources/.env");
    protected static final String DEFAULT_TEST_PROPERTY = "TEST_ENV_VAR=LoadedDotenvWithCustomPath";

    @BeforeAll
    static void createDotenv() throws IOException {
        Files.writeString(DOTENV_PATH, DEFAULT_TEST_PROPERTY);
    }

    @AfterAll
    static void deleteDotenv() throws IOException {
        Files.deleteIfExists(DOTENV_PATH);
    }

    @Test
    void shouldLoadDotenvFromCustomPath() {
        assertEquals("LoadedDotenvWithCustomPath", environment.getProperty("TEST_ENV_VAR"));
    }
}
