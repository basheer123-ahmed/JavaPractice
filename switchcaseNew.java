 
import java.util.Scanner;
public class switchcaseNew{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();

        switch(a){
            case 1 ->System.out.println("monday");
            case 2 ->System.out.println("Tuesday");
            
            case 3 ->System.out.println("Wednesday");
        
            case 4 ->System.out.println("Thursday");
            
            case 5  ->System.out.println("Friday");
            
            case 6 ->System.out.println("Saturday");
          
            case 7 ->System.out.println("Sunday");
            default ->System.out.println("Enter A Valid Number :)");
        }
    }
}