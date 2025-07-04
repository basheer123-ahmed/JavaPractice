import java.util.Scanner;
public class large {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int [] ar = new int [n];

        for(int i=0;i<n;i++){
            ar[i]= sc.nextInt();
        }

        int max = ar[0];

        for(int i=1;i<n;i++){
            if(ar[i]>max){
                max = ar[i];
            }
              


        }
        System.out.println(max+"max");
        
    }
    
}
