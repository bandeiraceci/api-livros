package br.com.api;

import br.com.api.config.Datasource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ConectionTest {


    @Test
    void connectionTest() {
        Connection connection = Datasource.getConnection();
        assertNotNull(connection);
    }
}
