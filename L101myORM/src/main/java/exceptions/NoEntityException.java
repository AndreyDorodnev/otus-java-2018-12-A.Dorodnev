package exceptions;

public class NoEntityException extends Exception {
    private String message;

    public NoEntityException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
