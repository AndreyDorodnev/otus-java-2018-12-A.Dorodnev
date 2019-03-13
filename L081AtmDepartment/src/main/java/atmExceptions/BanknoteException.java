package atmExceptions;

public class BanknoteException extends Exception{
    private String message;

    public BanknoteException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
