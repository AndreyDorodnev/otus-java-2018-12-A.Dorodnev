package atmExceptions;

public class CardException extends Exception {
    private String message;

    public CardException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
