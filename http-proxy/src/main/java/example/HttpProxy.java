package example;

import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

//adapted from: https://github.com/tomkraljevic/jetty-embed-reverse-proxy-example/blob/master/src/main/java/org/eclipse/jetty/embedded/ProxyServer.java
public class HttpProxy {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8099);

        ConnectHandler proxy = new ConnectHandler();
        server.setHandler(proxy);

        ServletContextHandler context = 
            new ServletContextHandler(proxy, "/", ServletContextHandler.SESSIONS);
        ServletHolder proxyServlet = new ServletHolder(ProxyServlet.Transparent.class);
        proxyServlet.setInitParameter("proxyTo", "http://localhost:8090/");
        proxyServlet.setInitParameter("prefix", "/");
        context.addServlet(proxyServlet, "/*");

        server.start();
    }
}