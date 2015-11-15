package computergraphics.datastructures;

import computergraphics.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * A facet has a reference to one of oits half edges. This datastructure
 * represents a general mesh (triangle, quad, ...). However, we only use
 * triangle meshes here.
 *
 * @author Philipp Jenke
 */
public class TriangleFacet {

    /**
     * One of the half edges around the facet.
     */
    private HalfEdge halfEdge;

    /**
     * Facet normal
     */
    private Vector3 normal;

    public TriangleFacet() {
    }

    public TriangleFacet(HalfEdge halfEdge) {
        this.halfEdge = halfEdge;
    }

    public HalfEdge getHalfEdge() {
        return halfEdge;
    }

    public void setHalfEdge(HalfEdge halfEdge) {
        this.halfEdge = halfEdge;
    }

    @Override
    public String toString() {
        return "Triangular Facet";
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    /**
     * Compute the area of the facet. Area of the facet.
     *
     * @return Area of the triangle.
     */
    public double getArea() {
        Vector3 v0 = halfEdge.getStartVertex().getPosition();
        Vector3 v1 = halfEdge.getNextHalfEdge().getStartVertex().getPosition();
        Vector3 v2 = halfEdge.getNextHalfEdge().getNextHalfEdge().getStartVertex().getPosition();
        return v1.subtract(v0).cross(v2.subtract(v0)).getNorm() / 2.0;
    }

    /**
     * Compute the centroid (center of mass) of the triangle.
     *
     * @return Centroid of the triangle.
     */
    public Vector3 getCentroid() {
        Vector3 v0 = halfEdge.getStartVertex().getPosition();
        Vector3 v1 = halfEdge.getNextHalfEdge().getStartVertex().getPosition();
        Vector3 v2 = halfEdge.getNextHalfEdge().getNextHalfEdge().getStartVertex().getPosition();
        return (v0.add(v1).add(v2)).multiply(1.0 / 3.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TriangleFacet that = (TriangleFacet) o;

        return !(getHalfEdge() != null ? !getHalfEdge().equals(that.getHalfEdge()) : that.getHalfEdge() != null);

    }

    @Override
    public int hashCode() {
        return getHalfEdge() != null ? getHalfEdge().hashCode() : 0;
    }
}
