package atmExceptions;

public class NotEnoughMoneyException extends Exception {
    private String message;

   public NotEnoughMoneyException(String message){
        this.message = message;
   }

   public String toString(){
       return message;
   }

}
