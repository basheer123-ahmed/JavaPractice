
import java.util.Scanner;
public class small {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int [] ar = new int [n];

        for(int i=0;i<n;i++){
            ar[i]= sc.nextInt();
        }

        int min = ar[0];

        for(int i=1;i<n;i++){
            if(ar[i]<min){
                min = ar[i];
            }
              


        }
        System.out.println(min+"min");
        
    }
    
}
