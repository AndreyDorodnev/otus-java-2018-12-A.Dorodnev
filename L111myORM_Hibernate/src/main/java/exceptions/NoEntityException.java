package exceptions;

public class NoEntityException extends RuntimeException {
    private String message;

    public NoEntityException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
