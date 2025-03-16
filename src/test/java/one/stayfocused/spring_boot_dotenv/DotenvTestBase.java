package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

abstract class DotenvTestBase {

    protected static final Path DOTENV_PATH = Path.of(".env");
    protected static final String DEFAULT_TEST_PROPERTY = "TEST_ENV_VAR=BeforeReload";

    @BeforeAll
    static void createDotenv() throws IOException {
        Files.writeString(DOTENV_PATH, DEFAULT_TEST_PROPERTY);
    }

    @AfterAll
    static void deleteDotenv() throws IOException {
        Files.deleteIfExists(DOTENV_PATH);
    }
}
