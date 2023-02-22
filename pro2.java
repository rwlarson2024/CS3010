public class pro2 
{
    public static void main (String[] args)
    {

    }   
    public static void FwdElimination (int coeff[][], int constant) 
    {
        for (int k = 0; k < constant-1; k++)
        {
            for(int i = k+1;i < constant ; i++)
            {
                int mult = coeff[i][k] / coeff[k][k];
                for(int j = k;j < constant ; j++)
                {
                    coeff[i][j] = coeff[i][j] - mult * coeff[k][j]; 
                }
            constant = constant[i] - mult * constant[k];
            }
        }
            
    }
}
