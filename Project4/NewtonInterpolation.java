import java.util.Scanner;
import java.io.*;
public class NewtonInterpolation {

    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(System.in);

        // Read values for x and y
        System.out.println("Enter the name of the file to be interpolated : "); 
        String filename = input.nextLine();

        File file = new File(filename);
        Scanner fileScanner = new Scanner(file);
        String line = fileScanner.nextLine();
        String[] tokens = line.split(" ");
        int n = tokens.length;
        fileScanner.close();

        fileScanner = new Scanner(file);
        double[][] userData = new double[n][2];
        
        for(int i = 0; i < n; i++)
        {
            userData[i][0] = fileScanner.nextDouble();
        }
        fileScanner.nextLine();
        for( int i = 0; i < n; i++)
        {
            userData[i][1] = fileScanner.nextDouble();
        }
        fileScanner.close();

        while (true) {
            System.out.print("Enter a value for x (q to quit): ");
            String userInput = input.next();

            if (userInput.equals("q")) {
                break;
            }

            double x = Double.parseDouble(userInput);
            double result = interpolate(userData, x);

            System.out.println("f(" + x + ") = " + result);
        }

        input.close();
    }

    public static double interpolate(double[][] xy, double x) {
        int n = xy.length;
        double result = 0;

        for (int i = 0; i < n; i++) {
            double term = xy[i][1];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    term = term * (x - xy[j][0]) / (xy[i][0] - xy[j][0]);
                }
            }
            result += term;
        }

        return result;
    }
}
