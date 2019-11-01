package org.example;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

@Path("/")
public class Hello {

    @Inject
    AgroalDataSource defaultDS;

    @Inject
    @DataSource(value = "ds1")
    AgroalDataSource otherDS;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String hello() throws SQLException {
        try(Connection connection = defaultDS.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("SELECT 1");
            }
        }
        return "ok, acquire count for datasource=default: "  + defaultDS.getMetrics().acquireCount();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/other")
    public String other() throws SQLException, InterruptedException {
        try(Connection connection = otherDS.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                TimeUnit.SECONDS.sleep(5);
                statement.execute("SELECT 1");
            }
        }
        return "ok, acquire count for datasource=ds1: "  + otherDS.getMetrics().acquireCount();
    }
}