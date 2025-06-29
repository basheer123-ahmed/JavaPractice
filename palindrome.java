public class palindrome {
    public static void main(String[] args) {
         
        String  s2 = "BASHEER";
        String reserved = new StringBuilder(s2).reverse().toString();
        System.out.println(s2.equals(reserved));
    }
}
