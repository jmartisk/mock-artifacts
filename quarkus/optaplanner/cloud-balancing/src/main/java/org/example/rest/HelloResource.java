package org.example.rest;

import com.sun.tools.javac.util.List;
import org.example.generator.CloudBalanceProblemGenerator;
import org.example.model.CloudBalance;
import org.example.model.Computer;
import org.example.model.Process;
import org.example.solver.CloudBalancingScoreCalculator;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Path("/")
public class HelloResource {

    @Inject
    SolverManager<CloudBalance, Long> solverManager;

    @Inject
    CloudBalanceProblemGenerator generator;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CloudBalance solve() throws ExecutionException, InterruptedException {
        CloudBalance problem = generator.generate();

        // Solve the problem
        Long id = ThreadLocalRandom.current().nextLong();
        SolverJob<CloudBalance, Long> job = solverManager.solve(id, problem);
        return job.getFinalBestSolution();
    }

}
