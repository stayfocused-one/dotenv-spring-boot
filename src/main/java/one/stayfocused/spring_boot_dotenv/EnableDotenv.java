package one.stayfocused.spring_boot_dotenv;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables automatic loading of `.env` files in a Spring Boot application.
 *
 * <p>When this annotation is added to a Spring Boot application's main class (or any
 * {@code @Configuration} class), it automatically imports {@link DotenvAutoConfiguration},
 * which loads environment variables from a `.env` file and integrates them into
 * the Spring {@code Environment}.</p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * &#64;SpringBootApplication
 * &#64;EnableDotenv
 * public class MyApplication {
 *     public static void main(String[] args) {
 *         SpringApplication.run(MyApplication.class, args);
 *     }
 * }
 * </pre>
 *
 * <h3>Configuration Properties:</h3>
 * <ul>
 *     <li>{@code dotenv.enabled} - Enables or disables dotenv loading (default: {@code true}).</li>
 *     <li>{@code dotenv.path} - Specifies the location of the `.env` file (default: {@code .env}).</li>
 *     <li>{@code dotenv.priority} - Sets priority for `.env` variables (`high` or `low`, default: {@code low}).</li>
 *     <li>{@code dotenv.fail-on-missing} - Fails the application startup if the `.env` file is missing (`true` or `false`, default: {@code false}).</li>
 *     <li>{@code dotenv.reload.enabled} - Enables runtime reloading of dotenv variables (`true` or `false`, default: {@code false}).</li>
 * </ul>
 *
 * <h3>Notes:</h3>
 * <ul>
 *     <li>Dotenv support is automatically enabled if the library is present in the classpath.</li>
 *     <li>This annotation is optional and can be used explicitly if autoconfiguration needs to be controlled.</li>
 * </ul>
 *
 * @author Augustin (StayFocused)
 * @since 0.0.1
 * @see DotenvAutoConfiguration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(DotenvAutoConfiguration.class)
public @interface EnableDotenv {
}