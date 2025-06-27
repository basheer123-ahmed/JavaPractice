
public class methodoverloading{

    int add(int a , int b, int c)
    {
        return a+b+c;
    }
    double add(double a, double b){
        return a+b;
    }
    

    public static void main(String[] args) {
        methodoverloading obj = new methodoverloading();
         System.out.println(obj.add(5.5,10.9));
         System.out.println(obj.add(2,5,7));
         System.out.println(obj.add(5,7));
       


    }
}
