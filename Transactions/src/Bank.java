import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank {

    public Map<String, Account> accounts = new HashMap<>();
    private Map<String, Boolean> lockAccount = new HashMap<>();
    private final Random random = new Random();

    public void setAccount(String number, Account account) {
        accounts.put(number, account);
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        boolean Fraud = true;

        if (amount > 50000) {
            transaction(fromAccountNum, toAccountNum, amount);

            try {
                Fraud = isFraud(fromAccountNum, toAccountNum, amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Fraud == true) {
                lockAccount.put(fromAccountNum, true);
                lockAccount.put(toAccountNum, true);
            }
        } else {
            transaction(fromAccountNum, toAccountNum, amount);
        }

    }

    public long getBalance(String accountNum) {
        long balance = 0l;

        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getKey().equals(accountNum)) {
                balance = entry.getValue().getMoney();
            }
        }

        return balance;
    }

    public long getSumAllAccounts() {
        long balance = 0;

        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            balance += entry.getValue().getMoney();
        }

        return balance;
    }

    public void transaction(String fromAccountNum, String toAccountNum, long amount) {
        long sumAllAccounts = 0;
        String accountFrom = "";
        String accountTo = "";
        long amountFrom = 0;
        long amountTo = 0;

        for (String accountNum : lockAccount.keySet()) {
            if (accountNum.equals(fromAccountNum) || accountNum.equals(toAccountNum)) {
                break;
            }
        }
        sumAllAccounts = getSumAllAccounts();

        amountFrom = getBalance(fromAccountNum);
        accountFrom = getKeyAccount(fromAccountNum);
        amountTo = getBalance(toAccountNum);
        accountTo = getKeyAccount(toAccountNum);

        if (amountFrom > amount) {
            amountFrom -= amount;
            amountTo += amount;

            synchronized (accounts) {
                accounts.get(accountFrom).setMoney(amountFrom);
                accounts.get(accountTo).setMoney(amountTo);
            }
        } else {
            System.out.println("Средств в количестве: " + amountFrom + " на счете: " + fromAccountNum + " недостаточно. Сумма перевода: " + amount);
        }

        sumAllAccounts -= getSumAllAccounts();
        if (sumAllAccounts != 0) {
            System.out.println("Ошибка в общем балансе банка = " + sumAllAccounts);
        }
    }

    public  String getKeyAccount(String accountNum) {
        String key = "";

        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getKey().equals(accountNum)) {
                key = entry.getKey();
            }
        }

        return key;
    }
}
