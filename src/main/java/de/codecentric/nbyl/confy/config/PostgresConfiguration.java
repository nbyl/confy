package de.codecentric.nbyl.confy.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("autodevops")
public class PostgresConfiguration {

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        String databaseName = dbUri.getPath();
        if(databaseName!= null && !"".equals(databaseName)) {
            databaseName = databaseName.replaceAll("/", "");
        }

        HikariDataSource ds = new HikariDataSource();
        ds.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        ds.setJdbcUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.addDataSourceProperty("databaseName", databaseName);
        ds.addDataSourceProperty("portNumber", dbUri.getPort());
        ds.addDataSourceProperty("sslMode", "disable");
        ds.addDataSourceProperty("serverName", dbUri.getHost());

        return ds;
    }
}
