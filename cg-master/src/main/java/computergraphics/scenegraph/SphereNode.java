/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

/**
 * Geometry of a simple sphere.
 *
 * @author Philipp Jenke
 */
public class SphereNode extends Node {

    /**
     * Sphere radius.
     */
    private double radius;

    /**
     * Resolution (in one dimension) of the mesh.
     */
    private int resolution;

    /**
     * Getter.
     */
    public Vector3 getCenter() {
        return center;
    }

    /**
     * The center of the sphere.
     */
    private final Vector3 center;

    /**
     * Constructor.
     */
    public SphereNode(double radius, int resolution, Vector3 center) {
        this.radius = radius;
        this.resolution = resolution;
        this.center = center;
    }

    @Override
    public void drawGl(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final int slices = resolution;
        final int stacks = resolution;
        gl.glTranslated(center.get(0), center.get(1), center.get(2));
        glu.gluSphere(earth, radius, slices, stacks);
    }
    
    public IntersectionResult findIntersection(Node ignored, Ray3D ray) {
    	Vector3 ps = ray.getPoint();
    	Vector3 vs = ray.getDirection();
    	
    	double p = 2*ps.multiply(vs)-2*center.multiply(vs)/vs.multiply(vs);
    	double q = (ps.multiply(ps)-2*ps.multiply(center)+center.multiply(center)-radius*radius)/vs.multiply(vs);
    	
    	double lambda1 = -(p/2)+Math.sqrt(p*p/4-q);
    	double lambda2 = -(p/2)-Math.sqrt(p*p/4-q);
    	
    	Vector3 intersectionPoint = null;
    	if(lambda1 > 0 && lambda1 < lambda2)
    	{
    		intersectionPoint = ray.getPoint().add(ray.getDirection().multiply(lambda1));
    	}
    	else if(lambda2 > 0){
    		intersectionPoint = ray.getPoint().add(ray.getDirection().multiply(lambda2));
    	}
    	
    	if(intersectionPoint == null){
    		return null; 
    	}
    	else{
    		Vector3 intersectionPointNormal = new Vector3((intersectionPoint.getX()-center.getX())/radius, 
        			(intersectionPoint.getY()-center.getY())/radius, 
        			(intersectionPoint.getZ()-center.getZ())/radius);
            return new IntersectionResult(intersectionPoint, intersectionPointNormal, this);
    	}
    }
}
