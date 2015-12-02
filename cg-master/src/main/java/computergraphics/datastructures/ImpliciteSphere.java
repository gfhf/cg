package computergraphics.datastructures;

import computergraphics.math.Vector3;

/**
 * Created by Me on 29.11.2015.
 */
public class ImpliciteSphere implements IImpliciteFunction{

    private double radius;

    public ImpliciteSphere(double radius){

        this.radius = radius;
    }

    public double calculate(Vector3 vector) {
        return Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2) + Math.pow(vector.getZ(), 2) - Math.pow(radius, 2);
    }
}
