package dummy.aux;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author jmartisk
 * @since 12/6/12
 */
@Interceptor
@ShouldBeIntercepted
public class AwesomeInterceptor implements Serializable {

    @Inject
    @NamedAfterClass
    private Logger logger;

    @AroundInvoke
    public Object doStuff(InvocationContext invocationContext) throws Exception {
        logger.info("I am an awesome interceptor and I am intercepting a " + invocationContext.getMethod().getName() + " method call of " + invocationContext.getTarget().getClass().getName());
        return invocationContext.proceed();
    }
}
