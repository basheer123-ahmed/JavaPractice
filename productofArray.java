import java.util.Scanner;
public class productofArray{

         public static int p(int[] ar )
           {
            int p =1;
            for(int i=0 ;i<ar.length;i++){

                p= p*ar[i];
            }
            return p;

           }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n= sc.nextInt();

        int ar [] = new int[n];

        for(int i=0; i<ar.length;i++){

            ar[i]= sc.nextInt();
        }
          int res = p(ar);
          System.out.println(res);
        


        

           
    }
}
