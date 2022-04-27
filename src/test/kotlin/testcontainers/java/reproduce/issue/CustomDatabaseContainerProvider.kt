package testcontainers.java.reproduce.issue

import io.r2dbc.spi.ConnectionFactoryMetadata
import io.r2dbc.spi.ConnectionFactoryOptions
import org.testcontainers.containers.MSSQLR2DBCDatabaseContainerProvider
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.r2dbc.R2DBCDatabaseContainer
import org.testcontainers.utility.DockerImageName
import java.lang.Boolean.TRUE

class CustomDatabaseContainerProvider : MSSQLR2DBCDatabaseContainerProvider() {

    override fun supports(options: ConnectionFactoryOptions?): Boolean {
        return DRIVER == options?.getRequiredValue(ConnectionFactoryOptions.DRIVER)
    }

    override fun createContainer(options: ConnectionFactoryOptions?): R2DBCDatabaseContainer {
        val image = MSSQLServerContainer.IMAGE + ":" + (options?.getValue(IMAGE_TAG_OPTION) ?: DEFAULT_TAG)

        return CustomDatabaseContainer(
            MSSQLServerContainer<Nothing>(
                DockerImageName.parse(image)
                    .asCompatibleSubstituteFor(MSSQLServerContainer.IMAGE)
            ).apply {
                acceptLicense()
                withReuse(TRUE == options?.getValue(REUSABLE_OPTION))
            }
        )
    }

    override fun getMetadata(options: ConnectionFactoryOptions?): ConnectionFactoryMetadata? {
        return super.getMetadata(options?.removeCustom())
    }

    companion object {
        const val DRIVER = "custom-sqlserver"
        const val DEFAULT_TAG = "latest"
    }
}
