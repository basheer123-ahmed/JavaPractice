import java.util.Scanner;
public class sum{
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();

    int [] ar = new int[N];

    for(int i=0;i<N;i++){
        ar[i]= sc.nextInt();
    }

    int sum= ar[0];
 

    for(int i=0;i<N;i++){
     sum += ar[i]; 

        
    }
    
      System.out.println(sum+"sum");
    
    
    }
}