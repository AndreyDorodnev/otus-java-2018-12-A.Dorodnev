package messageServer;

import messageServer.server.SocketMsgServer;
import messageServer.runner.ProcessRunnerImpl;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain {

    private static final String FRONT_START_COMMAND = "java -jar FrontendClient/target/front-jar-with-dependencies.jar -port 8080 -dbAddress 5052";
    private static final String FRONT_START_COMMAND2 = "java -jar FrontendClient/target/front-jar-with-dependencies.jar -port 8081 -dbAddress 5053";
    private static final String DB_START_COMMAND = "java -jar DataBaseClient/target/database-jar-with-dependencies.jar -port 5052";
    private static final String DB_START_COMMAND2 = "java -jar DataBaseClient/target/database-jar-with-dependencies.jar -port 5053";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().Start();
    }

    public void Start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        startClient( executorService );

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("messageServer.server:type=messageServer.server");

        SocketMsgServer server = new SocketMsgServer();
        mbs.registerMBean(server, name);

        System.out.println("Start messageServer.server");
        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(DB_START_COMMAND);
                new ProcessRunnerImpl().start(DB_START_COMMAND2);
                new ProcessRunnerImpl().start(FRONT_START_COMMAND);
                new ProcessRunnerImpl().start(FRONT_START_COMMAND2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

    }

}
