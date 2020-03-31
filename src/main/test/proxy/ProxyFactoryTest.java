package proxy;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.net.Proxy;
import java.util.Queue;
import java.util.regex.Matcher;
import static org.hamcrest.CoreMatchers.*;


import static org.junit.Assert.*;

public class ProxyFactoryTest {

    ProxyFactory proxyFactory;

    @Test
    public void getProxy() {
        Queue queue = ProxyFactory.getProxy("proxy.txt");
        assertNotNull(queue);
        assertThat(queue.contains(Proxy.NO_PROXY),is(true));
    }
}