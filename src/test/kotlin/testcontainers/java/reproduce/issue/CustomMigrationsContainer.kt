package testcontainers.java.reproduce.issue

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy
import java.io.File
import java.time.Duration

class CustomMigrationsContainer(
    dbNetwork: Network,
    dbHost: String,
    dbPort: Int,
) : GenericContainer<CustomMigrationsContainer>("busybox:latest") {

    init {
        withFileSystemBind(HOST_WORK_DIR, CONTAINER_WORK_DIR)
        withWorkingDirectory(CONTAINER_WORK_DIR)
//        withNetwork(dbNetwork)
        withEnv("NETWORK", dbNetwork.id)
        withEnv("HOST", dbHost)
        withEnv("PORT", "$dbPort")
//        withCreateContainerCmdModifier {
//            it.withEntrypoint("wait-and-migrate")
//        }
        withCommand("cat schema.sql")
        withStartupCheckStrategy(OneShotStartupCheckStrategy().withTimeout(STARTUP_TIMEOUT))
        withLogConsumer(Slf4jLogConsumer(logger()))

        // DEBUG
        logger().debug(binds.toString())
        if (logger().isDebugEnabled) {
            File(HOST_WORK_DIR)
                .walk()
                .map { it.absolutePath }
                .forEach { logger().debug(it) }
        }
    }

    companion object {
        private val STARTUP_TIMEOUT = Duration.ofMinutes(10)
        private val HOST_WORK_DIR = File("").absolutePath
        private const val CONTAINER_WORK_DIR = "/app"
    }
}
