package atmExceptions;

public class CurrencyException extends Exception {
    private String message;

    public CurrencyException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
