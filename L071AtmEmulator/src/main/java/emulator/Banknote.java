package emulator;

public final class Banknote {

    private final int value;
    private final Currency currency;

    public int getValue(){
        return value;
    }

    public Currency getCurrency(){
        return currency;
    }

    private Banknote(Currency currency, int value){
        this.currency = currency;
        this.value = value;
    }

    public enum Values {
        Rub_100,
        Rub_200,
        Rub_500,
        Rub_1000,
        Rub_2000,
        Rub_5000,
        Usd_50,
        Usd_100,
        Usd_1000
    }
    public enum Currency {
        RUB,
        USD
    }

    public static Banknote getBanknote(Values value){
        switch (value){
            case Rub_100:
                return new Banknote(Currency.RUB,100);
            case Rub_200:
                return new Banknote(Currency.RUB,200);
            case Rub_500:
                return new Banknote(Currency.RUB,500);
            case Rub_1000:
                return new Banknote(Currency.RUB,1000);
            case Rub_2000:
                return new Banknote(Currency.RUB,2000);
            case Rub_5000:
                return new Banknote(Currency.RUB,5000);
            case Usd_50:
                return new Banknote(Currency.USD,50);
            case Usd_100:
                return new Banknote(Currency.USD,100);
            case Usd_1000:
                return new Banknote(Currency.USD,1000);
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
        return (getCurrency().toString()+getValue()).hashCode();
    }

}
