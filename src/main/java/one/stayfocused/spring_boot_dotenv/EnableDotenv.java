package one.stayfocused.spring_boot_dotenv;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(DotenvAutoConfiguration.class)
public @interface EnableDotenv {
}
