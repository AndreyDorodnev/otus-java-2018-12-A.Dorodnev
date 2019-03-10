package emulator;

public final class Banknote implements Comparable {

    private final int value;
    private final String currency = "RUB";

    public int getValue(){
        return value;
    }

    public String getCurrency(){
        return currency;
    }

    private Banknote(int value){
       this.value = value;
    }

    public enum Values {
        Rub_100,
        Rub_200,
        Rub_500,
        Rub_1000,
        Rub_2000,
        Rub_5000
    }

    public static Banknote getBanknote(Values value){
        switch (value){
            case Rub_100:
                return new Banknote(100);
            case Rub_200:
                return new Banknote(200);
            case Rub_500:
                return new Banknote(500);
            case Rub_1000:
                return new Banknote(1000);
            case Rub_2000:
                return new Banknote(2000);
            case Rub_5000:
                return new Banknote(5000);
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this)
            return true;
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return ((Banknote)obj).getValue()==getValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    public int compareTo(Object o) {
        if(this.getValue()>((Banknote)o).getValue())
            return 1;
        else if(this.getValue()==((Banknote)o).getValue())
            return 0;
        return -1;
    }
}
