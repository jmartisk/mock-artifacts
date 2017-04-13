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
@WebServlet(urlPatterns = "/restart")
public class RestartJobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer oldExecutionId = null;
        try {
            oldExecutionId = Integer.parseInt(req.getParameterValues("id")[0]);
        } catch (Exception e) {
            resp.getWriter().append("Please specify an executionId in the 'id' parameter");
            e.printStackTrace();
            return;
        }

        Properties jobParameters = Tools.convertMapToProperties(req.getParameterMap());
        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        final long executionId = jobOperator.restart(oldExecutionId, jobParameters);
        resp.getWriter().append("Restarted the job 'people' (old executionId=" + oldExecutionId + ") with these parameters:\n");
        resp.getWriter().append(jobParameters.toString());
        resp.getWriter().append("\nThe new executionId is... " + executionId);

    }


}
