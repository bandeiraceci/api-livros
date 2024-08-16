package br.com.api.config;

import br.com.api.enums.DbEnum;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class Datasource {

    private Datasource() {
    }

    private static final Logger LOGGER = Logger.getLogger(Datasource.class);

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con == null) {
                Class.forName(DbEnum.DRIVER.getValue());
                con = DriverManager.getConnection(
                        DbEnum.URL.getValue(),
                        DbEnum.USER.getValue(),
                        DbEnum.PASSWORD.getValue()
                );
            } else if (con.isClosed()) {
                con = null;
                return getConnection();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return con;
    }
}
