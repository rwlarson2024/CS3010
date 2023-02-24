import java.util.*;
import java.io.*;
public class pro2 
{
    public static void main (String[] args) throws Exception
    {
       /*  File file = new File("sys1.lin");
        Scanner sc = new Scanner(file);
        while(sc.hasNext(" "))
            System.out.println(sc.nextLine());*/
            
        double[][] array1 = {{3,4,3},
                             {1,5,-1},
                             {6,3,7}};
        double[]   constant1 = {10,7,15};
        NaiveGaussian(array1, constant1);
        for(int i = 0; i <= array1.length-1; i++)
        {
            System.out.print(constant1[i]+ " ");
        }

    }   
    public static void FwdElimination (double[][] coeff, double[] constant) 
    {
        int n = constant.length-1;
        for (int k = 1; k < n-1; k++)
        {
            for(int i = k+1; i < n ; i++)
            {
                double mult = coeff[i][k] / coeff[k][k];
                coeff[i][k] = mult;
                for(int j = k+1; j < n; j++)
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
        for (int i = n-1 ; i > 0 ; i-- )
        {
            double sum = constant[i];
            for(int j = i+1;j < n ; j-- )
            {
                sum = sum - (coeff[i][j] * solution[j]);
            }
            solution[i] = sum / coeff[i][i];
        }
    }
    public static void NaiveGaussian(double coeff[][], double constant[])
    {
        int n = constant.length;
        double solution[] = new double[n];
        FwdElimination(coeff, constant);
        BackSubst(coeff, constant, solution);
        
    }
    public static void SPPFwdElimination(int coeff[][], int constant[], int ind[])
    {
        int n = constant.length;
        int scaling[] = new int[n];
        for(int i = 0; i < n-1 ; i ++)
        {
            int smax = 0;
            for(int j = 0; j < n-1; j++)
            {
                smax = Math.max(smax, coeff[i][j]);
            }
            scaling[i] = smax;
        }
        for(int k = 0; k <n-1; k++)
        {
            int rmax = 0;
            int maxIndex = k;
            for(int i = k; i < n -1; i++)
            {
                int r = Math.abs(coeff[ind[i]][k]/scaling[ind[i]]) ;
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
            

            for(int i = k+1; i<n; i++)
            {
                int mult = coeff[ind[i]][k] / coeff[ind[k]][k];
                for(int j = k+1; j < n ; j++)
                {
                    coeff[ind[i]][j] = coeff[ind[i]][j] - mult * coeff[ind[k]][j];
                }
                constant[ind[i]] = constant[ind[i]] - mult * constant[ind[k]];
            }
        }
    }
    public static void SPPBackSubst(int coeff[][], int constant[], int sol[], int ind[])
    {
        int n = constant.length;
        sol[n] = constant[ind[n]]/ coeff[ind[n]][n];
        for (int i =1; i<n-1 ;i++ )
        {
            int sum = constant[ind[i]];
            for(int j = i+1; j<n; j++)
            {
                sum = sum - coeff[ind[i]][j] * sol[j];
            }
            sol[i] = sum / coeff[ind[i]][i];
        }
    }
    public static void SPPGaussian(int coeff[][], int constant[])
    {
        int n = constant.length;
        int sol[] = new int[n];
        int ind[] = new int[n];
        for(int i = 0; i<n; i++)
        {
            ind[i] = i;
        }
        SPPFwdElimination(coeff, constant, ind);
        SPPBackSubst(coeff, constant, sol, ind);
    }
    
}