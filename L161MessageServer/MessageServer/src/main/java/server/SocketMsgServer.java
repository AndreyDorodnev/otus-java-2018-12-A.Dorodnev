package server;

import com.google.gson.Gson;
import messages.Message;
import messages.MessageDbFs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketMsgServer implements SocketMsgServerMBean {

    private static final int THREADS_NUMBER = 2;
    private static final int FRONT_PORT = 5051;
    private static final int DB_PORT = 5052;
    private static final int CAPACITY = 1024;
    private static final String MESSAGES_SEPARATOR = "\n\n";

    private final ExecutorService executor;
    private final Map<String, ChannelMessages> channelMessages;

    public SocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        channelMessages = new ConcurrentHashMap<>();
    }

    public void start()throws Exception{
        executor.submit(this::answer);
        try(ServerSocketChannel frontSocketChannel = ServerSocketChannel.open();
            ServerSocketChannel dbSocketChannel = ServerSocketChannel.open()) {
            Selector selector = Selector.open();

            frontSocketChannel.bind(new InetSocketAddress("localhost", FRONT_PORT));
            frontSocketChannel.configureBlocking(false); //non blocking mode
            int ops = SelectionKey.OP_ACCEPT;
            frontSocketChannel.register(selector, ops, null);

            dbSocketChannel.bind(new InetSocketAddress("localhost", DB_PORT));
            dbSocketChannel.configureBlocking(false); //non blocking mode
            ops = SelectionKey.OP_ACCEPT;
            dbSocketChannel.register(selector, ops, null);

            while (true) {
                selector.select();//blocks
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    try {
                        if (key.isAcceptable()) {
                            SocketChannel channel = ((ServerSocketChannel) key.channel()). accept(); //non blocking accept
                            registerChannel(selector,channel);
                        } else if (key.isReadable()) {
                            readChannel(key);
                        }
                    } catch (IOException e) {
                        key.cancel();
                    } finally {
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void registerChannel(Selector selector,SocketChannel channel) throws IOException {
        String remoteAddress = channel.getRemoteAddress().toString();
        System.out.println("Connection Accepted: " + remoteAddress);

        System.out.println("Local Address:" + channel.getLocalAddress());

        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        channelMessages.put(remoteAddress, new ChannelMessages(channel));

    }

    private void readChannel(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
        int read = channel.read(buffer);
        if (read != -1) {
            String result = new String(buffer.array()).trim();
            if(result.length()<=0)
                return;
            System.out.println("---------------------------------------------------------------");
            System.out.println("Message received: " + result + " from: " + channel.getRemoteAddress());
            channelMessages.get(channel.getRemoteAddress().toString()).messages.add(result);
        } else {
            key.cancel();
            String remoteAddress = channel.getRemoteAddress().toString();
            channelMessages.remove(remoteAddress);
            System.out.println("Connection closed, key canceled");
        }
    }

    private void writeChannel(SocketChannel channel, Message message) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
//        buffer.put(message.toString().getBytes());
        buffer.put(new Gson().toJson(message).getBytes());
        buffer.put(MESSAGES_SEPARATOR.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private Object answer() {
        while (true) {
            for (Map.Entry<String, ChannelMessages> entry : channelMessages.entrySet()) {
                ChannelMessages channelMessages = entry.getValue();
                if (channelMessages.channel.isConnected()) {
                    channelMessages.messages.forEach(message -> {
                        try {
                            if(message.length()>0){
                                MessageDbFs msg = new Gson().fromJson(message,MessageDbFs.class);
                                if(msg.getRemoteAddr()==null){
                                    msg.setRemoteAddr(channelMessages.channel.getRemoteAddress().toString());
                                    sendToDb(msg);
                                }
                                else {
                                    sendToFront(msg);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    channelMessages.messages.clear();
                }
            }
        }

    }

    private void sendToFront(MessageDbFs message) throws IOException {
        System.out.println("Set to Front");
        writeChannel(channelMessages.get(message.getRemoteAddr()).channel,message);
    }

    private void sendToDb(MessageDbFs message) throws IOException {
        System.out.println("send to DB");
        for(ChannelMessages channelMessage: channelMessages.values()){
            String localAddress = channelMessage.channel.getLocalAddress().toString();
            if(localAddress.substring(localAddress.length()-4,localAddress.length()).equals(String.valueOf(DB_PORT))){
                writeChannel(channelMessage.channel,message);
                break;
            }
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {

    }

    private class ChannelMessages {
        private final SocketChannel channel;
        private final List<String> messages = new ArrayList<>();

        private ChannelMessages(SocketChannel channel) {
            this.channel = channel;
        }
    }

}
