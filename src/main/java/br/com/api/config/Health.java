package br.com.api.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class Health {

    @GetMapping
    public Map<String, Object> up() throws SQLException {
        Map<String, Object> map = new HashMap<>();
        Connection con = Datasource.getConnection();
        if (con != null) {
            map.put("status", "OK");
            map.put("schema", con.getSchema());
            map.put("catalog", con.getCatalog());
            map.put("timeout", con.getNetworkTimeout());
            return map;
        }
        map.put("status", "ERROR");
        map.put("versao", "2.0.0");
        return map;
    }
}
