package testcontainers.java.reproduce.issue

import io.r2dbc.spi.ConnectionFactoryOptions
import org.testcontainers.containers.MSSQLR2DBCDatabaseContainer
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.containers.Network

class CustomDatabaseContainer(
    dbContainer: MSSQLServerContainer<*>,
) : MSSQLR2DBCDatabaseContainer(dbContainer) {
    private val dbNetwork = Network.newNetwork()

    init {
        dbContainer.apply {
            withNetwork(dbNetwork)
            withNetworkAliases("db")
        }
    }

    override fun configure(options: ConnectionFactoryOptions): ConnectionFactoryOptions {
        return super.configure(options.removeCustom())
    }

    override fun start() {
        super.start()
        startMigrations()
    }

    private fun startMigrations() {
        CustomMigrationsContainer(
            dbNetwork = dbNetwork,
            dbHost = "db",
            dbPort = 1433
        ).start()
    }
}
