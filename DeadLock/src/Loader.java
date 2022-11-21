public class Loader {
    public static void main(String[] args) {
        final Friend vasya = new Friend("Вася");
        final Friend vitya = new Friend("Витя");

        new Thread(() -> vasya.throwBollTo(vitya)).start();
        new Thread(() -> vitya.throwBollTo(vasya)).start();
    }
}
