package computergraphics.datastructures;

import computergraphics.math.Vector3;

public class ImpliciteTorus implements IImpliciteFunction{

    private double r;
    private double a;
    

    public ImpliciteTorus(double r, double a){

        this.r = r;
        this.a = a;
    }

    public double calculate(Vector3 vector) {
        double xsquared = Math.pow(vector.getX(), 2);
        double ysquared = Math.pow(vector.getY(), 2);
        double zsquared = Math.pow(vector.getZ(), 2);
        double rsquared = Math.pow(r, 2);
        double asquared = Math.pow(a, 2);
    	
    	return Math.pow((xsquared + ysquared + zsquared + rsquared - asquared), 2) - 4*rsquared*(xsquared + ysquared);
    }
}
