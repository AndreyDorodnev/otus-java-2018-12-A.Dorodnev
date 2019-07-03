package front;

import front.embedded.JettyService;

public class FrontMain {
    public static void main(String[] args) throws Exception {
        JettyService jettyService = new JettyService(Integer.valueOf(args[1]),Integer.valueOf(args[3]));
//        JettyService jettyService = new JettyService();
        jettyService.config();
        jettyService.start();
    }



}
