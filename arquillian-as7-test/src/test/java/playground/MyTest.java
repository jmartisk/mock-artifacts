package playground;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.InitialContext;
import java.util.logging.Logger;

/**
 * @author jmartisk
 * @since 12/13/12
 */
@RunWith(Arquillian.class)
public class MyTest {

    static Logger logger = Logger.getLogger("LOGGER");

    @Deployment(name="asdf")
    public static EnterpriseArchive deploy() {
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "myWWWapp.ear");

        JavaArchive source = ShrinkWrap.create(JavaArchive.class, "event-source.jar");
        source.addClasses(EventSource.class, SuperEvent.class, MyTest.class);
        source.addAsManifestResource(new StringAsset("Class-Path: event-consumer.jar\n"), "MANIFEST.MF");
        source.addAsResource(emptyEjbJar(), "META-INF/ejb-jar.xml");
        source.addAsManifestResource(new StringAsset(""), "beans.xml");

        JavaArchive consumer = ShrinkWrap.create(JavaArchive.class, "event-consumer.jar");
        consumer.addClasses(EventConsumer.class);
        consumer.addAsManifestResource(new StringAsset("Class-Path: event-source.jar\n"), "MANIFEST.MF");
        consumer.addAsResource(emptyEjbJar(), "META-INF/ejb-jar.xml");
        consumer.addAsManifestResource(new StringAsset(""), "beans.xml");

        ear.addAsModule(consumer);
        ear.addAsModule(source);
        logger.info(ear.toString(true));
        return ear;
    }

    /*@Deployment(name="tester")
    public static JavaArchive tt() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "testing");
        jar.addClasses(MyTest.class, EventSource.class, EventConsumer.class, SuperEvent.class);
        return jar;
    }
*/
    @Test
    @OperateOnDeployment("asdf")
//    @OperateOnDeployment("tester")
    public void doTest() throws Exception {
        InitialContext ctx = new InitialContext();
        EventSource source = (EventSource)ctx.lookup("java:global/myWWWapp/event-source/EventSource!playground.EventSource");
        Thread.sleep(4000);
        source.fireEvent();
        Thread.sleep(2000);
        EventConsumer consumer = (EventConsumer)ctx.lookup("java:global/myWWWapp/event-consumer/EventConsumer!playground.EventConsumer");
        Assert.assertTrue(consumer.received);
        ctx.close();
    }

    private static StringAsset emptyEjbJar() {
        return new StringAsset(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<ejb-jar xmlns=\"http://java.sun.com/xml/ns/javaee\" \n" +
                        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                        "         xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd\"\n" +
                        "         version=\"3.0\">\n" +
                        "   \n" +
                        "</ejb-jar>");
    }
}
