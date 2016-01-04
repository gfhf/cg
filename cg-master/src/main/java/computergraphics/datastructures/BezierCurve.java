package computergraphics.datastructures;

import computergraphics.math.Vector3;

public class BezierCurve extends AbstractCurve {
    public BezierCurve(Vector3[] givenPoints) {
        _controlPoints = givenPoints;
        System.out.println(getDegree());
    }

    @Override
    public Vector3 calculateBaseFunction(double t) {
        Vector3 result = new Vector3();

        double nfaculty = faculty(getDegree());

        for (int i = 0; i <= getDegree(); i++) {

            double ifaculty = faculty(i);
            double nifaculty = faculty(getDegree() - i);
            double nOverI = nfaculty / (ifaculty * nifaculty);
            double tPow_i = Math.pow(t, i);
            double oneMinus_tPow_nMinus_i = Math.pow(1 - t, getDegree() - i);
            double _BniOf_t = nOverI * tPow_i * oneMinus_tPow_nMinus_i;

            //Bn,i(t)
            result = result.add(_controlPoints[i].multiply(_BniOf_t));
        }
        return result;
    }

    private int faculty(int limit) {
        int result = 1;
        for (int i = 1; i <= limit; i++) {
            result *= i;
        }
        return result;
    }

    //siehe Folien Seite 40
    @Override
    public Vector3 getTangent(double t) {
        //Differenzenquotient h
        double h = 0.00001;
        //p'(t) = (p(t+h)-p(t))/h für ein kleines h (z.B. 1e-5).
        Vector3 pOf_tPlus_h = calculateBaseFunction(t-h);
        Vector3 pOf_tPlus_hMinusP_Of_t = pOf_tPlus_h.add(calculateBaseFunction(t).multiply(-1));
        return pOf_tPlus_hMinusP_Of_t.devide(h);
    }
}
