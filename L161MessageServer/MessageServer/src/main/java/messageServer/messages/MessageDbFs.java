package messageServer.messages;

public class MessageDbFs extends Message {

    private String remoteAddr;
    private MessageCommand command;
    private String data;
    private int webSocketId;
    private int dbAddress;

    public MessageDbFs() {
        super(MessageDbFs.class);
    }

    public MessageDbFs(int dbAddress, String remoteAddr, MessageCommand command, Integer webSocketId, String data) {
        super(MessageDbFs.class);
        this.dbAddress = dbAddress;
        this.remoteAddr = remoteAddr;
        this.command = command;
        this.data = data;
        this.webSocketId = webSocketId;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public MessageCommand getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }

    public void setCommand(MessageCommand command) {
        this.command = command;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getWebSocketId() {
        return webSocketId;
    }

    public void setWebSocketId(int webSocketId) {
        this.webSocketId = webSocketId;
    }

    public int getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(int dbAddress) {
        this.dbAddress = dbAddress;
    }
}
