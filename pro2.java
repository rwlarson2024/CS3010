import java.util.*;
import java.io.*;
public class pro2 
{
    public static void main (String[] args) throws Exception
    {
        Scanner keyboard = new Scanner(System.in);
        boolean spp = false;
        System.out.println("--spp?(--spp or enter)");
        String choice = keyboard.nextLine();
        String checkChoice = "--spp";
        if(choice.equals(checkChoice))
        {
            spp = true;
        }
        System.out.print("Enter file name: ");
        String fileName = keyboard.nextLine();
        File file = new File(fileName);
        if (!file.exists()) // Checks if file entered exists
        {
            System.out.println("File does not exist, exiting");
            System.exit(0);
        }
        double[][] Coeff;
        double[] Constants;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            // Count number of lines in file
            int lines = 0;
            while (br.readLine() != null) lines++;
            br.close();

            // Initialize arrays
            Coeff = new double[lines-1][];
            Constants = new double[0];

            // Read file again and store values into arrays
            BufferedReader brr = new BufferedReader(new FileReader(fileName));
            String line;
            int i = 0;
            while ((line = brr.readLine()) != null) 
            {
                String[] values = line.split(" ");
                double[] row = new double[values.length];
                for (int j = 0; j < values.length; j++) {
                    row[j] = Double.parseDouble(values[j]);
                }
                if (i < lines-1) {
                    Coeff[i] = row;
                } else {
                    Constants = row;
                }
                i++;
            }
            brr.close();
            /*System.out.println("Coeff:");
            for (double[] row : Coeff) {
                for (double value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }

            System.out.println("Constants:");
            for (double value : Constants) {
                System.out.print(value + " ");
            }
            System.out.println();

        */
        keyboard.close();
        int n = Constants.length;
        double[] solution= new double[n];
        if(spp == true)
        {
            solution = SPPGaussian(Coeff, Constants);
        }
        else
        {
            solution = NaiveGaussian(Coeff, Constants);
        }
        String outputFile = "sys1.sol"; 
        write(outputFile, solution);
        /* 
        System.out.println("The solutions are : ");
        for(int p = 0; p <= Coeff.length-1; p++)
        {
            System.out.print(solution[p]+ " ");
        }*/
        }

    } 
    public static void write(String outputFile, double[] solutions) throws IOException
    {
        BufferedWriter out = null;
        out = new BufferedWriter(new FileWriter(outputFile));
        for(int i =0; i < solutions.length; i++)
        {
            out.write(solutions[i]+" ");
            out.newLine();
        }
        out.flush();
        out.close();
    }    
    public static void FwdElimination (double[][] coeff, double[] constant) 
    {
        int n = constant.length-1;
        for (int k = 0; k <= n-1; k++)
        {
            for(int i = k+1; i <= n ; i++)
            {
                double mult = coeff[i][k] / coeff[k][k];
                for(int j = k; j <= n; j++)
                {
                    coeff[i][j] = coeff[i][j] - (mult * coeff[k][j]); 
                }
            constant[i] = constant[i] - (mult * constant[k]);
            }
        }
            
    }
    public static void BackSubst(double[][] coeff, double[] constant, double[] solution)
    {
        int n = constant.length-1;
        solution[n] = constant[n] / coeff[n][n];
        for (int i = n ; i >= 0 ; i-- )
        {
            double sum = constant[i];
            for(int j = i+1;j <= n ; j++ )
            {
                sum = sum - (coeff[i][j] * solution[j]);
            }
            solution[i] = sum / coeff[i][i];
        }
    }
    public static double[] NaiveGaussian(double coeff[][], double constant[])
    {
        int n = constant.length;
        double solution[] = new double[n];
        FwdElimination(coeff, constant);
        BackSubst(coeff, constant, solution);
        return solution;
        
    }
    public static void SPPFwdElimination(double coeff[][], double constant[], int ind[])
    {
        int n = constant.length;
        double scaling[] = new double[n];
        n = n-1;
        for(int i = 0; i <= n ; i ++)
        {
            double smax = 0;
            for(int j = 0; j <= n; j++)
            {
                smax = Math.max(smax, Math.abs(coeff[i][j]));
            }
            scaling[i] = smax;
        }
        for(int k = 0; k <= n-1; k++)
        {
            double rmax = 0;
            int maxIndex = k;
            for(int i = k; i <= n; i++)
            {
                double r = Math.abs(coeff[ind[i]][k]/scaling[ind[i]]) ;
                if(r>rmax)
                {
                    rmax = r;
                    maxIndex = i;
                }
            }

            int swap1 = 0;
            swap1 = ind[maxIndex];
            ind[maxIndex] = ind[k];
            ind[k] = swap1;
            

            for(int i = k+1; i<=n; i++)
            {
                double mult = coeff[ind[i]][k] / coeff[ind[k]][k];
                for(int j = k+1; j <= n ; j++)
                {
                    coeff[ind[i]][j] = coeff[ind[i]][j] - mult * coeff[ind[k]][j];
                }
                constant[ind[i]] = constant[ind[i]] - mult * constant[ind[k]];
            }
        }
    }
    public static void SPPBackSubst(double coeff[][], double constant[], double sol[], int ind[])
    {
        int n = constant.length-1;
        sol[n] = constant[ind[n]]/ coeff[ind[n]][n];
        for (int i = n-1; i >= 0 ;i-- )
        {
            double sum = constant[ind[i]];
            for(int j = i+1; j<=n; j++)
            {
                sum = sum - coeff[ind[i]][j] * sol[j];
            }
            sol[i] = sum / coeff[ind[i]][i];
        }
    }
    public static double[] SPPGaussian(double coeff[][], double constant[])
    {
        int n = constant.length;
        double sol[] = new double[n];
        int ind[] = new int[n];
        for(int i = 0; i<n; i++)
        {
            ind[i] = i;
        }
        SPPFwdElimination(coeff, constant, ind);
        SPPBackSubst(coeff, constant, sol, ind);
        return sol;
    }
    
}