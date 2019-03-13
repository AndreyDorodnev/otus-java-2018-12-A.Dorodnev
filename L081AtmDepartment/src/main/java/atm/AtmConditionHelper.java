package atm;

import atmExceptions.BanknoteException;
import atmExceptions.CardException;
import atmExceptions.CurrencyException;
import atmExceptions.NotEnoughMoneyException;

public final class AtmConditionHelper {
    private AtmConditionHelper(){}

    public static boolean checkConditionGetMoney(Card card,int summ,int atmBalance,Banknote.Currency currency) throws CurrencyException, NotEnoughMoneyException, CardException {
        checkCard(card);
        checkSumm(summ);
        checkCurrency(card,currency);
        checkEnoughMoney(card,atmBalance,summ);
        return true;
    }

    public static boolean checkConditionPutMoney(Card card,Banknote banknote,int amount) throws CurrencyException, BanknoteException, CardException {
        checkCard(card);
        checkBanknote(banknote);
        checkCurrency(card,banknote.getCurrency());
        checkSumm(amount);
        return true;
    }

    public static void checkBanknote(Banknote banknote) throws BanknoteException {
        if(banknote==null)
            throw new BanknoteException("No banknote");
    }

    public static void checkCard(Card card) throws CardException {
        if(card==null)
            throw new CardException("No card");
    }
    public static void checkSumm(int summ){
        if(summ<=0)
            throw new IllegalArgumentException("Wrong required summ");
    }
    private static void checkCurrency(Card card,Banknote.Currency currency) throws CurrencyException {
        if(!card.getCurrency().equals(currency))
            throw new CurrencyException("Wrong currency");
    }
    private static void checkEnoughMoney(Card card, int atmBalance,int summ) throws NotEnoughMoneyException {
        if((card.getBalance()<summ))
            throw new NotEnoughMoneyException("Not enough money on card");
        if(atmBalance<summ)
            throw new NotEnoughMoneyException("Not enough money in ATM");
    }
}
