package org.example;

import io.agroal.api.AgroalDataSource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Path("/")
public class Hello {

    @Inject
    AgroalDataSource agroalDataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String hello() throws SQLException {
        try(Connection connection = agroalDataSource.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("SELECT 1");
            }
        }
        return "ok";
    }
}