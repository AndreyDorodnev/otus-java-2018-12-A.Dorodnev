package messages;

public class MsgDelUser extends Message {

    private String id;

    public MsgDelUser(String id) {
        super(MsgDelUser.class);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}