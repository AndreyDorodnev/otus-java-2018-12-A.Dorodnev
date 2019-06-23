import embedded.JettyService;
import fs.ClientSocketMessageWorker;
import org.eclipse.jetty.servlet.ServletContextHandler;
import workers.SocketMessageWorker;

import java.io.IOException;

public class FrontMain {
    public static void main(String[] args) throws Exception {
        JettyService jettyService = new JettyService(Integer.valueOf(args[1]));
//        JettyService jettyService = new JettyService();
        jettyService.config();
        jettyService.start();
    }



}
