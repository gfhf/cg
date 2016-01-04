/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

/**
 * Representation of a cuboid with different dimensions in x-, y- and
 * z-direction.
 *
 * @author Philipp Jenke
 */
public class PlainNode extends Node {

    public Vector3 getPlainNormal() {
        Vector3 normal = new Vector3();
        normal.copy(plainNormal);

        return normal;
    }

    private Vector3 plainNormal;
    private Vector3 pe;
    /**
     * Width of the cuboid (x-direction).
     */
    private double width;
    /**
     * Height of the cuboid (y-direction).
     */
    private double height;

    /**
     * Depth of the cuboid (z-direction).
     */
    private Vector3 u;
    private Vector3 v;

    /**
     * @param pe     the support vector
     * @param u      directional vector (height)
     * @param v      directional vector (width)
     * @param width
     * @param height
     */
    public PlainNode(Vector3 pe, Vector3 u, Vector3 v, double width, double height) {

        // the support vector
        this.pe = new Vector3();
        this.pe.copy(pe);

        // directional vectors
        this.u = new Vector3();
        this.u.copy(u);
        this.u.normalize();
        this.v = new Vector3();
        this.v.copy(v);
        this.v.normalize();

        // caclulate the normal of the plain
        this.plainNormal = u.cross(v);

        this.width = width / 2.0;
        this.height = height / 2.0;
    }

    @Override
    public void drawGl(GL2 gl) {

        gl.glTranslated(pe.get(0), pe.get(1), pe.get(2));
        gl.glBegin(GL2.GL_QUADS);

        // front
        gl.glNormal3d(plainNormal.get(0), plainNormal.get(1), plainNormal.get(2));

        Vector3 upperRight = u.multiply(height).add(v.multiply(width));
        gl.glVertex3d(upperRight.getX(), upperRight.getY(), upperRight.getZ());

        Vector3 lowerRight = u.multiply(-height).add(v.multiply(width));
        gl.glVertex3d(lowerRight.getX(), lowerRight.getY(), lowerRight.getZ());

        Vector3 upperLeft = u.multiply(height).add(v.multiply(-width));
        gl.glVertex3d(upperLeft.getX(), upperLeft.getY(), upperLeft.getZ());

        Vector3 lowerLeft = u.multiply(-height).add(v.multiply(-width));
        gl.glVertex3d(lowerLeft.getX(), lowerLeft.getY(), lowerLeft.getZ());

        gl.glEnd();

    }

    @Override
    public IntersectionResult findIntersection(Node ignored, Ray3D ray) {
        double lambda = plainNormal.multiply(pe) - plainNormal.multiply(ray.getPoint());
        lambda = lambda / plainNormal.multiply(ray.getDirection());
        if (lambda > 0) {
            Vector3 intersectionPoint = ray.getPoint().add(ray.getDirection().multiply(lambda));
            Vector3 normal = new Vector3();
            normal.copy(plainNormal);
            return new IntersectionResult(intersectionPoint, normal, this);
        } else {
            return null;
        }
    }
}
