package one.stayfocused.spring_boot_dotenv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DotenvFailOnMissingTest {

    @Test
    void shouldFailWhenDotenvIsMissing() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(DotenvAutoConfiguration.class))
                .withPropertyValues("dotenv.fail-on-missing=true")
                .run(context -> assertThatThrownBy(context::refresh)
                        .isInstanceOf(IllegalStateException.class));
    }
}
