package testcontainers.java.reproduce.issue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
open class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
