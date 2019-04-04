package exceptions;

public class DaoOperationException extends RuntimeException {
    private String message;

    public DaoOperationException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
