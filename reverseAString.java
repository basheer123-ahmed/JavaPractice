public class reverseAString {
    public static void main(String[] args) {
        String s1 = "Basheer";
        String reversed = "";

        for(int i= s1.length()-1 ; i>=0; i--){
            reversed +=s1.charAt(i);
        }
        System.out.println(reversed);
        

    }
    
}
 
}