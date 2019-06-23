package messages;

public class MsgReadUser extends Message {

    private String id;

    public MsgReadUser(String id) {
        super(MsgReadUser.class);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
