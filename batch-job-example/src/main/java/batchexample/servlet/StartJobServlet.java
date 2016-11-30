package batchexample.servlet;

import java.io.IOException;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import batchexample.helpers.Tools;

/**
 * @author Jan Martiska
 */
@WebServlet(urlPatterns = "/start")
public class StartJobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Properties jobParameters = Tools.convertMapToProperties(req.getParameterMap());
        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        final long executionId = jobOperator.start("people", jobParameters);

        resp.getWriter().append("Started the job 'people' with these parameters:\n");
        resp.getWriter().append(jobParameters.toString());
        resp.getWriter().append("\nThe executionId is... " + executionId);
    }


}
