import java.util.*;
import java.io.*;
public class interpolation 
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(System.in);
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
        double [][] divDiff = new double[n][n];
        for(int i = 0; i <n; i++)
        {
            divDiff[i][0] = userData[i][1];
        }
        for(int j = 1; j < n; j++)
        {
            for(int i = j; i < n; i++)
            {
                divDiff[i][j] = (divDiff[i][j-1] - divDiff[i-1][j-1] / userData[i][0] - userData[i-j][0]);
            }
        }

        while(true)
        {
            System.out.print("enter a value to interpolate (q to quit)");
            String input2 = input.next();
            if(input2.equals("q"))
            {
                break;
            }
            double x = Double.parseDouble(input2);
            double fx = divDiff[0][0];
            double term = 1;

            for(int i = 1; i < n ;i++)
            {
                term *= (x - userData[i-1][0]);
                fx += divDiff[i][i] * term;
            }
            System.out.println("interpolated value at " + x +" is " + fx);
        }
        input.close();
    }
}
    