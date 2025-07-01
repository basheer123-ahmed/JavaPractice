

public class Staticvariable {
    static int count = 0;  // static variable
         Staticvariable() {
        count++;  // increases the shared count
        System.out.println("Count is: " + count);
    }

   
    public static void main(String[] args) {
        Staticvariable c1 = new Staticvariable(); // Count is: 1
        Staticvariable c2 = new Staticvariable(); // Count is: 2
        Staticvariable c3 = new Staticvariable(); // Count is: 3
    }
}
