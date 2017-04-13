import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;

/**
 * @author jmartisk
 */
@Singleton
@Startup
public class SingletonTimerCreator {

    @Resource
    private SessionContext ctx;

    @PostConstruct
    public void createTimers() {
        TimerConfig g = new TimerConfig();
        g.setInfo("asdf");
        g.setPersistent(false);
        Timer timer  = ctx.getTimerService().createIntervalTimer(0, 1000, g);
    }

    @Timeout
    public void doit(Timer timer){
        System.out.println(timer.toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
