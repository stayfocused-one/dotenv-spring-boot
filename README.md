# Spring Boot Dotenv

Spring Boot Dotenv provides a **simple and flexible** way to load `.env` files into Spring Boot applications. It requires **no manual configuration**, automatically integrating environment variables from `.env` files into the application's `Environment`. It also supports **profile-based `.env` files**, allowing different `.env` configurations based on the active Spring profile. The library is **fault-tolerant**, meaning if a `.env` file is missing, the application will still run without issues, ensuring maximum **flexibility**.
## Features

- **Flexible loading** of `.env` from different locations: project root, `resources/`, or a custom path (`dotenv.path`).
- **Profile-based .env support** (`.env.{profile}` for different environments).
- **Autoconfiguration** without requiring `@EnableDotenv`.
- **Configurable settings** via `application.properties`.
- **Environment reload** without restarting the application via Spring Actuator or a custom REST endpoint.
- **Priority handling** for `.env` (high/low).
- **Compatibility** with Spring Boot 2.7+ and 3.x, Java 17+.

---

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>one.stayfocused</groupId>
    <artifactId>spring-boot-dotenv</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("one.stayfocused:spring-boot-dotenv:1.0.0")
}
```

---

## Quick Start

Create a `.env` file in the project root:

```env
DATABASE_URL=jdbc:postgresql://localhost:5432/mydb
SECRET_KEY=mysecret
```

### Configuration via `application.properties`:

```properties
# Enable dotenv (default: true)
dotenv.enabled=true

# Custom path to .env (project root by default)
dotenv.path=config/.env

# Priority (high - above application.properties, low - below)
dotenv.priority=high

# Allow reloading without restart
dotenv.reload.enabled=true

# Allow application run in case .env is missing
dotenv.fail-on-missing=false
```

Now, environment variables are available in the `Environment`:

```java
@Value("${DATABASE_URL}")
private String databaseUrl;
```

### Using Different `.env` Files for Different Profiles

You can automatically load different `.env` files based on the active Spring profile:

```properties
spring.profiles.active=dev
dotenv.path=.env.${spring.profiles.active}
```

#### Example Setup:

- `.env.dev`
  ```env
  TEST_ENV_VAR=LoadedFromDevEnv
  ```
- `.env.prod`
  ```env
  TEST_ENV_VAR=LoadedFromProdEnv
  ```

When running with `spring.profiles.active=dev`, the library will load `.env.dev`.

---

## Reloading `.env` Without Restart

### Using Actuator

Add Actuator to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Enable the endpoint in `application.properties`:

```properties
management.endpoints.web.exposure.include=dotenvReload
```

Reload `.env` using:

```bash
curl -X POST http://localhost:8080/actuator/dotenvReload
```

### Using a Custom REST Controller

If you are not using Spring Actuator, you can expose a simple `RestController` for reloading `.env` variables:

```java
@RestController
@RequestMapping("/dotenv")
public class DotenvReloadController {
    private final DotenvReloadService dotenvReloadService;

    public DotenvReloadController(DotenvReloadService dotenvReloadService) {
        this.dotenvReloadService = dotenvReloadService;
    }

    @PostMapping("/reload")
    public ResponseEntity<String> reload() {
        boolean success = dotenvReloadService.reload();
        return success ? ResponseEntity.ok("Dotenv reloaded successfully")
                       : ResponseEntity.badRequest().body("Dotenv reload is disabled");
    }
}
```

Now, you can trigger a reload with:

```bash
curl -X POST http://localhost:8080/dotenv/reload
```

---

## Configuration Options

| Property                 | Description                                       | Default Value |
| ------------------------ | ------------------------------------------------- | ------------- |
| `dotenv.enabled`         | Enables/disables `.env` loading                   | `true`        |
| `dotenv.path`            | Path to the `.env` file                           | `.env`        |
| `dotenv.priority`        | Load priority (`high` or `low`)                   | `low`         |
| `dotenv.fail-on-missing` | Fails if `.env` is missing                        | `false`       |
| `dotenv.reload.enabled`  | Enables reloading without restart                 | `false`       |

---

## Build & Test

### Build Locally

For Maven:

```bash
mvn clean package
```

For Gradle:

```bash
gradle build
```

### Running Tests

```bash
mvn test
```

or

```bash
gradle test
```

---

## License

[MIT License](https://opensource.org/licenses/MIT)

---

## Contact & Support

For questions or suggestions, open an [Issue](https://github.com/stayfocused-one/spring-boot-dotenv/issues) in the repository.

