import server.SocketMsgServer;
import runner.ProcessRunnerImpl;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain {

    private static final String FRONT_START_COMMAND = "java -jar FrontendClient/target/front-jar-with-dependencies.jar -port 8080";
    private static final String FRONT_START_COMMAND2 = "java -jar FrontendClient/target/front-jar-with-dependencies.jar -port 8081";
    private static final String DB_START_COMMAND = "java -jar DataBaseClient/target/database-jar-with-dependencies.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().Start();
    }

    public void Start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

//        startClient( executorService );

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("server:type=server");

        SocketMsgServer server = new SocketMsgServer();
        mbs.registerMBean(server, name);

        System.out.println("Start server");
        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
//                new ProcessRunnerImpl().start(DB_START_COMMAND);
                new ProcessRunnerImpl().start(DB_START_COMMAND);
                new ProcessRunnerImpl().start(FRONT_START_COMMAND);
                new ProcessRunnerImpl().start(FRONT_START_COMMAND2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

    }

}
