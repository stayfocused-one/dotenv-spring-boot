package one.stayfocused.spring.dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Boot Dotenv library.
 * <p>
 * This class serves as the entry point for running a Spring Boot application
 * with Dotenv support enabled. It is primarily used for testing and demonstration purposes.
 * </p>
 */
@SpringBootApplication
public class SpringBootDotenvApplication {

	/**
	 * Constructs a new {@code SpringBootDotenvApplication}.
	 */
	public SpringBootDotenvApplication() {
		// No initialization needed
	}

	/**
	 * Main method to start the Spring Boot application with Dotenv support.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDotenvApplication.class, args);
	}

}
