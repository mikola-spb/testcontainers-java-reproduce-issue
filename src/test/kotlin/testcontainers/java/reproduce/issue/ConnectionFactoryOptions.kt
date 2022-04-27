package testcontainers.java.reproduce.issue

import io.r2dbc.mssql.MssqlConnectionFactoryProvider
import io.r2dbc.spi.ConnectionFactoryOptions

fun ConnectionFactoryOptions.removeCustom(): ConnectionFactoryOptions = mutate()
    .option(ConnectionFactoryOptions.DRIVER, MssqlConnectionFactoryProvider.MSSQL_DRIVER)
    .build()
