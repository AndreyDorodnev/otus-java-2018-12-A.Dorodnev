package messageServer.workers;

import messageServer.annotations.Blocks;
import messageServer.messages.Message;

import java.io.IOException;

public interface MessageWorker {
    Message pool();

    void send(Message message);

    @Blocks
    Message take() throws InterruptedException;

    void close() throws IOException;
}
