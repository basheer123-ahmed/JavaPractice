public class palindrome1{

    public static void main(String[] args) {

        
        String s1 = "Basheer";
        String s2 = new StringBuilder(s1).reverse().toString();

        if(s1.equals(s2)){
            System.out.println("Palindrome");
        }else{
            System.out.println("Not Palindrome");
        }
    }
}