public class Main {
    public static void main(String[] args) {
        long money;
        long digitAccountNum;
        String accNumber;
        String number;

        Bank bank = new Bank();

        for (int i = 1; i <= 100; i++) {
            number = Integer.toString(i);
            digitAccountNum = Math.round(Math.random() * 1_0000_0000_0000_0000L);
            accNumber = Long.toString(digitAccountNum);
            money = Math.round(Math.random() * 100000);
            bank.setAccount(number, new Account(accNumber, money));

        }

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 100; j++) {
                    String fromAccountNum = Long.toString(Math.round(Math.random() * 100));
                    String toAccountNum = Long.toString(Math.round(Math.random() * 100));
                    long amount = Math.round(Math.random() * 100000);
                    if (fromAccountNum.equals(toAccountNum) || fromAccountNum.equals("0") || toAccountNum.equals("0")) {
                        j--;
                        continue;
                    } else {
                        bank.transfer(fromAccountNum, toAccountNum, amount);
                    }
                }
            }).start();
        }
    }
}
