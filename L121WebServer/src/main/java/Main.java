public class Main {

    public static void main(String[] args) {
        try {
            new MyWebServer().Start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
