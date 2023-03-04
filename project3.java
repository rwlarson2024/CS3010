
import java.io.*;
import java.util.*;
import java.util.function.DoubleFunction;

public class project3 
{
    public static void main(String[] args) throws Exception
    {
        boolean newt = false;
        boolean sec = false;
        boolean hybrid = false;
        boolean bisect = true;
        double iterationsStart = 10000;
        String fileName = null;
        //reads user's input from commandline and checks for all the different flags that change what version of 
        //calculations that are conducted.
        for (int i = 0; i < args.length; i++)
        {
            String arg = args[i];
            switch (arg)
            {
                case "-newt":
                    newt = true;
                    break;
                case "-sec":
                    sec = true;
                    break;
                case "-hybrid":
                    hybrid = true;
                    break;
                case "-maxIt":
                    if(i + 1 <args.length && args[i+1].matches("\\d+") )
                    {
                        iterationsStart = Double.parseDouble(args[i+1]);
                        i++;
                    }
                    else
                    {
                        System.err.println("invalid argument for -maxIt ....");
                        System.exit(1);
                    }
                    break;
                default:
                if(fileName == null)
                {
                    fileName = arg;
                }
                else
                {
                    System.err.println("invalid argument : " + arg);
                }
            }
            
        }
        File file = new File(fileName);
        if(!file.exists())
        {
            System.out.println("file does not exsist");
            System.exit(0);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
        {
            int lines = 0; 
            while(br.readLine() != null) lines++;
            br.close();
            double degree;
            double[] coeffx = new double[lines-1];
            String line;
            int i = 0;
            while ((line = br.readLine())!= null)
            {
                String[] values = line.split(" ");
                for(int j = 1; j <values.length;j++)
                {
                    if(i <= 0)
                    {
                        degree = Double.parseDouble(values[i]);
                        i++;
                    }
                    coeffx[j] = Double.parseDouble(values[j]);
                }
                
            }
            double[] solution= new double[3];
            
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



    public double[] bisection(double[] f, double d, double a, double b, int maxint, double esp)
    {
        double[] solutions = new double[3];
        solutions[0] = 0;
        solutions[1] = 0;
        solutions[2] = 0;
        double fa = f(f,d,a);
        double fb = f(f,d,b);

        if (fa * fb >= 0)
        {
            System.out.println("inadepuate values for a and b.");
            return solutions;
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
                solutions[0] = c; 
                solutions[1] = i;
                solutions[2] = 1; 


                return solutions;
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
        solutions[0] = 0; 
        solutions[1] = maxint;
        solutions[2] = -1;

        return solutions;
    }
    public double[] newtons(double[] f, double derF, double x, double degree, int maxInt, double eps, double delta)
    {
        double[] solutions = new double[3];
        solutions[0] = 0;
        solutions[1] = 0;
        solutions[2] = 0;
        double fx = f(f,degree,x);
        for(int i = 0; i<= maxInt; i++)
        {
            double fd = derivative(f,degree,x);
            if(Math.abs(fd) < delta)
            {
                System.out.println("small slope! ");
                return solutions;
            }
            double d = fx /fd;
            x = x - d;
            fx = f(f,degree,x);
            if(Math.abs(d) < eps)
            {
                System.out.println("algorithm has converged after "+ i +" iterations");
                solutions[0] = x;
                solutions[1] = i;
                solutions[2] = 1;
                return solutions;
            }
        }
        System.out.println("max itertations reached without convergence ...");
        return solutions;
    }
    public double[] secant(double[] f, double a, double b,double degree, int maxInt, double eps )
    {
        double[] solutions = new double[3];
        solutions[0] = 0;
        solutions[1] = 0;
        solutions[2] = 0;
        double fa = f(f,degree,a);
        double fb = f(f,degree,b);
        for(int i = 0; i < maxInt; i++)
        {
            if(Math.abs(fa) > Math.abs(f(b)))
            {
                //swap
            }
            double d = (b-a)/ (fb - fa);
            b = a;
            fb = fa;
            d = d * fa;
            if(Math.abs(d) < eps)
            {
                System.err.println("algorith has converged after " + i + " iterations");
                return a;
            }
            a = a -d;
            fa = f(f,degree,a);
        }
        System.out.println("Maximum number of iterations reached!");
        return solutions;
    }
}