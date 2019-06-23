package messages;

public class MsgCheckAuth extends Message {

    private String login;
    private String password;

    public MsgCheckAuth(String login, String password) {
        super(MsgCheckAuth.class);
        this.login = login;
        this.password = password;
//        this.webSocketId = webSocketId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

//    public Integer getSocketId() {
//        return webSocketId;
//    }
}
