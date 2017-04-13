package tester;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.resource.ResourceException;

import org.jboss.logging.Logger;

import experiment.DummyConnectionFactory;
import experiment.DummyConnectionImpl;

/**
 * @author Jan Martiska
 */
@Startup
@Singleton
public class StartupConnectionTester {

    private static Logger logger = Logger.getLogger(StartupConnectionTester.class.getName());

    @Resource(lookup = "java:jboss/DummyConnection")
    private DummyConnectionFactory connectionFactory;

    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    public void tryObtainConnection() {
        System.out.println("*************************************************************");
        System.out.println("*************************************************************");
        try {
            logger.info("Obtained connection factory from JNDI: " + connectionFactory);
            DummyConnectionImpl connection = connectionFactory.getConnection();
            logger.info("Application obtained connection: " + connection.toString());
            DummyConnectionImpl connection2 = connectionFactory.getConnection();
            logger.info("Application obtained connection2: " + connection2.toString());
            logger.info("Sleeping...");
            TimeUnit.SECONDS.sleep(5);
            logger.info("Finished sleeping, closing both handles");
            connection.close();
            connection2.close();
            logger.info("Connections closed.");
            TimeUnit.SECONDS.sleep(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
