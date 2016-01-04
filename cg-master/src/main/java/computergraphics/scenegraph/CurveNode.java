package computergraphics.scenegraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import computergraphics.datastructures.AbstractCurve;
import computergraphics.math.Vector3;

public class CurveNode extends Node {
    private AbstractCurve _curve;

    private double _tangentPoint = 0.5;

    public void set_tangentPoint(double _tangentPoint) {
        this._tangentPoint = _tangentPoint;
    }

    public double get_tangentPoint() {
        return _tangentPoint;
    }


    public CurveNode(AbstractCurve curve) {
        this._curve = curve;
    }

    @Override
    public void drawGl(GL2 gl) {
        drawCurve(gl);
        drawTangent(gl);
    }

    private void drawCurve(GL2 gl) {
        gl.glBegin(GL.GL_LINES);
        Vector3 point = _curve.calculateBaseFunction(0);
        gl.glVertex3d(point.getX(), point.getY(), point.getZ());

        for (double d = 0.01; d <= 1.0; d += 0.01) {
            point = _curve.calculateBaseFunction(d);
            gl.glVertex3d(point.getX(), point.getY(), point.getZ());
            gl.glVertex3d(point.getX(), point.getY(), point.getZ());
        }
        gl.glEnd();
    }

    private void drawTangent(GL2 gl) {
        gl.glColor3d(0, 0, 1);
        Vector3 tangentPoint = _curve.calculateBaseFunction(_tangentPoint);
        Vector3 point1 = tangentPoint.add(_curve.getTangent(_tangentPoint).multiply(0.2));
        Vector3 point2 = tangentPoint.substract(_curve.getTangent(_tangentPoint).multiply(0.2));

        // draw point
        // TODO?

        // draw tangent
        gl.glBegin(GL.GL_LINES);
        gl.glVertex3d(point1.getX(), point1.getY(), point1.getZ());
        gl.glVertex3d(point2.getX(), point2.getY(), point2.getZ());
        gl.glEnd();
    }
}
