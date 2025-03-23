# Dotenv for Spring Boot

Dotenv for Spring Boot provides a **simple and flexible** way to load `.env` files into Spring Boot applications. It requires **no manual configuration**, automatically integrating environment variables from `.env` files into the application's `Environment`. It also supports **profile-based `.env` files**, allowing different `.env` configurations based on the active Spring profile. The library is **fault-tolerant**, meaning if a `.env` file is missing, the application will still run without issues, ensuring maximum **flexibility**.
## Features

- **Plug-and-Start:** auto-loads `.env` files into Spring `Environment`.
- **Flexible loading** of `.env` from different locations: project root, `resources/`, or a custom path (`dotenv.path`).
- **Profile-based .env support** (`.env.{profile}` for different environments).
- **Configurable settings** via `application.properties`.
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

### **1. Create a `.env` file**

```env
DATABASE_URL=jdbc:postgresql://localhost:5432/mydb
SECRET_KEY=mysecret
```

### **2. Configuration via `application.properties (optional)`**

```properties
# Enable dotenv (default: true)
dotenv.enabled=true

# Custom path to .env (project root by default)
dotenv.path=src/main/resources/.env

# Priority (high - above application.properties, low - below)
dotenv.priority=high

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

## Configuration Options

| Property                 | Description                                       | Default Value |
| ------------------------ | ------------------------------------------------- | ------------- |
| `dotenv.enabled`         | Enables/disables `.env` loading                   | `true`        |
| `dotenv.path`            | Path to the `.env` file                           | `.env`        |
| `dotenv.priority`        | Load priority (`high` or `low`)                   | `low`         |
| `dotenv.fail-on-missing` | Fails if `.env` is missing                        | `false`       |

---

## Limitations
The current version of the library provides basic .env file parsing with the following limitations:
- Multi-line values are not supported.
- Variable substitution (e.g., `${VAR}`) is not supported.
- Escaped characters (e.g., `\n`, `\t`, `\ `) are not processed.
- Only basic key-value pairs are supported (e.g., `KEY=value` or `KEY="value"`).

These limitations will be addressed in future versions.

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

