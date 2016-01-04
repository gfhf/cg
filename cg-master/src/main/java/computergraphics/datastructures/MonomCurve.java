package computergraphics.datastructures;

import computergraphics.math.Vector3;

/**
 * Created by Me on 13.12.2015.
 */
public class MonomCurve extends AbstractCurve {

    private Vector3[] _givenPoints;

    public MonomCurve(Vector3[] givenPoints) {
        _givenPoints = givenPoints;
        _controlPoints = new Vector3[_givenPoints.length];
        calculateControlPoints();
    }


    @Override
    public Vector3 calculateBaseFunction(double t) {
        Vector3 result = new Vector3(0, 0, 0);

        for (int controlPointId = 0; controlPointId < _controlPoints.length; controlPointId++) {
            //p(t) siehe Folien Seite 16
            result = result.add(_controlPoints[controlPointId].multiply(Math.pow(t, controlPointId)));
        }
        return result;
    }


    private void calculateControlPoints() {
        _controlPoints[0] = _givenPoints[0];

        //siehe Folien Seite 18
        if (getDegree() == 1) {
            _controlPoints[1] = _givenPoints[1].subtract(_givenPoints[0]);
        }
        //siehe Folien Seite 20
        if (getDegree() == 2) {
            Vector3 p1x4 = _givenPoints[1].multiply(4);
            Vector3 p0x3 = _givenPoints[0].multiply(3);
            _controlPoints[1] = p1x4.subtract(p0x3).subtract(_givenPoints[2]);
            _controlPoints[2] = _givenPoints[2].subtract(_givenPoints[0]).subtract(_controlPoints[1]);
        }
    }

    @Override
    public Vector3 getTangent(double t) {
        Vector3 result = new Vector3(0, 0, 0);

        for (int controlPointId = 1; controlPointId < _controlPoints.length; controlPointId++) {
            //p(t) siehe Folien Seite 16
            result = result.add(_controlPoints[controlPointId].multiply(controlPointId).multiply(Math.pow(t, controlPointId - 1)));
        }
        return result.getNormalized();
    }
}