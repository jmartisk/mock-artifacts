package org.example;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@Path("/datasource")
public class DataSourceService {

    @Inject
    AgroalDataSource defaultDS;

    @Inject
    @DataSource(value = "ds1")
    AgroalDataSource otherDS;

    private Logger logger = Logger.getLogger(DataSourceService.class.getName());

    @GET
    @Path("/")
    public void generate() throws SQLException {
        int count = ThreadLocalRandom.current().nextInt(20);
        logger.info("Obtaining and closing " + count + " connections from default data source...");
        for(int i = 0; i < ThreadLocalRandom.current().nextInt(20); i++) {
            try (Connection connection = defaultDS.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("SELECT 1");
                }
            }
        }
        count = ThreadLocalRandom.current().nextInt(20);
        logger.info("Obtaining and closing " + count + " connections from data source 'ds1'...");
        for(int i = 0; i < count; i++) {
            try (Connection connection = otherDS.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("SELECT 1");
                }
            }
        }
    }

}
