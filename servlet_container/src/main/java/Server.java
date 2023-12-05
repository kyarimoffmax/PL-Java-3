import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.List;

public class Server {
    private static final int PORT = 8080;
    public Server() {
    }

    public void fillServletContainer(
            ConnectionDB connectionDB, Quaery quaery, List<Class> tables
    ) throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        for (Class t:tables) {
            context.addServlet(
                    new ServletHolder(
                            new Servlet(
                                    connectionDB,
                                    quaery,
                                    t
                            )
                    ),
                    "/%s".formatted(t.getSimpleName())
            );
        }

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });
        server.setHandler(handlers);
        server.start();
        server.join();
    }


}