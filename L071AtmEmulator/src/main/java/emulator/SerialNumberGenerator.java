package emulator;

public final class SerialNumberGenerator {

    private static final int MIN = 0;
    private static final int MAX = 9999;
    private static final String SPACE = " ";

    private SerialNumberGenerator(){

    }

    public static String getSerialNumber(){
        return getNumber(rnd(MIN,MAX)) + SPACE + getNumber(rnd(MIN,MAX)) + SPACE + getNumber(rnd(MIN,MAX)) + SPACE + getNumber(rnd(MIN,MAX));
    }

    private static String getNumber(int num){
        if(num<10)
            return "000" + num;
        else if(num<100)
            return "00" + num;
        else if(num<1000)
            return "0" + num;
        else return String.valueOf(num);
    }

    private static int  rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
