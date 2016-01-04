package computergraphics.datastructures;

import computergraphics.math.Vector3;

/**
 * Created by Me on 13.12.2015.
 */
public abstract class AbstractCurve {

    protected Vector3[] _controlPoints;

    public AbstractCurve() {

    }

    public int getDegree() {
        if(_controlPoints == null){
            throw new UnsupportedOperationException("_controllPoints must be set before calling this method.");
        }
        return _controlPoints.length -1;
    }

    public Vector3[] getControlPoints() {
        return _controlPoints;
    }

    public abstract Vector3 calculateBaseFunction(double t);

    public abstract Vector3 getTangent(double t);

}
