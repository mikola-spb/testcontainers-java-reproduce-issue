package testcontainers.java.reproduce.issue

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.r2dbc.core.DatabaseClient
import org.testcontainers.junit.jupiter.Testcontainers

@DataR2dbcTest
@Testcontainers
class TestcontainersTest {

    @Autowired
    private lateinit var client: DatabaseClient

    @Test
    fun canAccessDatabase() {
        client.sql("SELECT * FROM INFORMATION_SCHEMA.TABLES").fetch().rowsUpdated().block()
    }
}
