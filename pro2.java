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
        int n = constant1.length;
        double[] solution= new double[n];
        solution = NaiveGaussian(array1, constant1);
        for(int i = 0; i <= array1.length-1; i++)
        {
            System.out.print(solution[i]+ " ");
        }
        solution = SPPGaussian(array1,constant1);
        System.out.println();
        for(int i = 0; i <= array1.length-1; i++)
        {
            System.out.print(solution[i]+ " ");
        }

    }   
    public static void FwdElimination (double[][] coeff, double[] constant) 
    {
        int n = constant.length-1;
        for (int k = 0; k <= n-1; k++)
        {
            for(int i = k+1; i <= n ; i++)
            {
                double mult = coeff[i][k] / coeff[k][k];
                //coeff[i][k] = mult;
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
            for(int i = k; i <= n-1; i++)
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
        for (int i =1; i<n-1 ;i++ )
        {
            double sum = constant[ind[i]];
            for(int j = i+1; j<n; j++)
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