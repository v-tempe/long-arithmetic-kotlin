public class SmokeTesting {
    public static void main(String[] args) {
        String magnitude = "99999";
        VastNatural longNum = BytePerDigit.Companion.invoke(magnitude);
        VastNatural longNum1 = BytePerDigit.Companion.invoke(magnitude);
        System.out.println(longNum.plus(longNum1));
        System.out.println("Hello, world!");
    }
}
