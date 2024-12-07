import java.util.Scanner;

public class basicPractice {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter the 1st number : ");
        int a = input.nextInt();
        System.out.println("Please Enter the 2nd number: ");
        int b = input.nextInt();
        int sum = a + b;
        int diff = a - b;
        int mul = a * b;
        int div = a / b;
        int mod = a % b;
        System.out.println("The Sum of the Two number entered is :" + sum);
        System.out.println("The Difference of the Two number entered is :" + diff);
        System.out.println("The Multiplication of the Two number entered is :" + mul);
        System.out.println("The Division of the Two number entered is :" + div);
        System.out.println("The Reminder of the Two number entered is :" + mod);
    }

}
