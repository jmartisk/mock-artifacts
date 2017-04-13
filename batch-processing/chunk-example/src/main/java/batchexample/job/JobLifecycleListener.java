package batchexample.job;

import javax.batch.api.listener.JobListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
@Named
public class JobLifecycleListener implements JobListener {

    @Inject
    JobContext ctx;

    @Inject
    private Logger logger;

    @Override
    public void beforeJob() throws Exception {
        logger.info("Job with executionId=" + ctx.getExecutionId() + " starting");
    }

    @Override
    public void afterJob() throws Exception {
        logger.info("Job with executionId=" + ctx.getExecutionId() + " finished with status " + ctx.getExitStatus());
    }
}
