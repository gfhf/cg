package computergraphics.datastructures;

import computergraphics.math.Vector3;

public class ImplicitePlane implements IImpliciteFunction{

    private double r;
    private double a;
    

    public ImplicitePlane(){

        this.r = r;
        this.a = a;
    }

    public double calculate(Vector3 vector) {
    	
    	return vector.getX() + 2* vector.getY() - 3*vector.getZ()+1;
    }
}
