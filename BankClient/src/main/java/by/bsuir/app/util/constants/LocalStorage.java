package by.bsuir.app.util.constants;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.entity.CreditInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalStorage {
    private static Account account;
    private static List<CreditInfo> credits;
    private static List<Contract> contracts;
//    private static List<Mark> marks;
//    private static Long feedback_id;
//    private static String question;
//    private static Feedback feedback;
    private static Set<CreditInfo> creditsToCompare;
//    private static Product selectedProduct;
//    private static Long productID;


    public static void setContracts(List<Contract> contracts) {
        LocalStorage.contracts = contracts;
    }

    public static Contract getFirstContract() {
        if (!contracts.isEmpty()) {
            Contract contract = contracts.iterator().next();
            contracts.remove(contract);

            return contract;
        }
        return null;
    }

    public static CreditInfo getFirstCreditToCompare() {
        if (!creditsToCompare.isEmpty()) {
            CreditInfo credit = creditsToCompare.iterator().next();
            creditsToCompare.remove(credit);

            return credit;
        }
        return null;
    }

    public static CreditInfo getFirstCredit() {
        if (!credits.isEmpty()) {
            CreditInfo car = credits.get(0);
            credits.remove(0);
            return car;
        }
        return null;
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account a) {
        account = a;
    }

    public static List<CreditInfo> getCredits() {
        return credits;
    }

    public static void setCredits(List<CreditInfo> credits) {
        LocalStorage.credits = credits;
    }

    public static void addCredit(CreditInfo credit) {
       credits.add(credit);
    }

    public static Set<CreditInfo> getCreditsToCompare() {
        if (creditsToCompare == null) {
            creditsToCompare = new HashSet<>();
        }
        return creditsToCompare;
    }

    public static void addCreditToCompare(CreditInfo credit) {
        creditsToCompare.add(credit);
    }
}
