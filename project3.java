
import java.io.*;
import java.util.*;
import java.util.function.DoubleFunction;

public class project3 
{
    public static void main(String[] args) throws Exception
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = keyboard.nextLine();
        File file = new File(fileName);
        if(!file.exists())
        {
            System.out.println("file does not exsist");
            System.exit(0);
        }
        float degree;
        float[] coeffx;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
        {
            int lines = 0; 
            while(br.readLine() != null) lines++;
            br.close();
            
        }
        

        


    }
    
    static double f (double[] vectorCoeff, double degree, double x)
    {
        int j = 0;
        double sol=0; 
        for (double i = degree; i<=0 ; i-- )
        {
            sol = sol + (vectorCoeff[j]*(Math.pow(x,i)));
            j++;
        }
        return sol;
    }
    
    static double derivative(double[] vectorCoeff, double degree, double x)
    {
        int j =0; 
        double sol = 0; 
        for(double i = degree; i<=0; i--)
        {
            sol = sol + (vectorCoeff[j])*(i)*(Math.pow(x,i-1));
            j++;
        }
        double i =0;
        return i;
    }



    public double bisection(double[] f, double d, double a, double b, int maxint, Double esp)
    {
        double fa = f(f,d,a);
        double fb = f(f,d,b);

        if (fa * fb >= 0)
        {
            System.out.println("inadepuate values for a and b.");
            return -1;
        }
        double error = b - a;
        for(int i = 0; i<= maxint; i++)
        {
            error = error/2;
            double c = a + error;
            double fc = f(f,d,c);

            if(Math.abs(error) < esp || fc == 0  )
            {
                System.out.println("algorithm has converged after " + i + " iterations!");
                return c;
            }
            if(fa *fc < 0)
            {
                b = c;
                fb = fc;
            }
            else
            {
                a = c;
                fa = fc;
            }
        }
        System.out.print("max iterations reached without convergence ...");
        return -1;
    }
    public float newtons(float f, float derF, float x, int maxInt, float eps, float delta)
    {
        float fx = f(x);
        for(int i = 0; i<= maxInt)
        {
            float fd = derF(x);
            if(Math.abs(fd) < delta)
            {
                System.out.println("small slope! ");
                return x;
            }
            float d = fx /fd;
            x = x - d;
            fx = f(x);
            if(Math.abs(d) < eps)
            {
                System.out.println("algorithm has converged after "+ i +" iterations");
                return x;
            }
        }
        System.out.println("max itertations reached without convergence ...");
        return x;
    }
    public float secant(float f, float a, float b, int maxInt, float eps)
    {
        float fa = f(a);
        float fb = f(b);
        for(int i = 0; i < maxInt; i++)
        {
            if(Math.abs(fa) > Math.abs(f(b)))
            {

            }
            float d = (b-a)/ (fb - fa);
            b = a;
            fb = fa;
            d = d * fa;
            if(Math.abs(d) < eps)
            {
                System.err.println("algorith has converged after " + i + " iterations");
                return a;
            }
            a = a -d;
            fa = f(a);
        }
        System.out.println("Maximum number of iterations reached!");
        return a;
    }
}

