/**
 * WP Computer Graphik WS 15/16
 * Praktikumgruppe Nummer ${todo}
 * stephan.berngruber@haw-hamburg.de
 * rene.goetsch@haw-hamburg.de
 * Aufgabenblatt Nr. 2
 * Verwendete Quellen:
 * http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene graph node which realizes drawing of a triangulated mesh.
 */
public class TriangulatedMeshNode extends Node {
    /**
     * The mesh to be realized.
     */
    private ITriangleMesh triangulatedMesh;
    private int displayListName;


    /**
     * Setter.
     *
     * @param triangulatedMesh
     */
    public void setTriangulatedMesh(HalfEdgeTriangleMesh triangulatedMesh) {
        this.triangulatedMesh = triangulatedMesh;
        displayListName = 0;

    }

    /**
     * Constructor.
     */
    public TriangulatedMeshNode(HalfEdgeTriangleMesh mesh) {
        triangulatedMesh = mesh;
    }

    @Override
    public void drawGl(GL2 gl) {
        if (displayListName == 0) {
            buildList(gl);
        } else {
            gl.glCallList(displayListName);
        }
//        gl.glBegin(GL2.GL_TRIANGLES);
//
//        List<TriangleFacet> triangles = triangulatedMesh.getAllTriangles();
//
//        for (TriangleFacet triangle : triangles) {
//            HalfEdge halfEdgeOfTriangle = triangle.getHalfEdge();
//            ArrayList<Vertex> vertexes = new ArrayList<>();
//            vertexes.add(halfEdgeOfTriangle.getStartVertex());
//            vertexes.add(halfEdgeOfTriangle.getNextHalfEdge().getStartVertex());
//            vertexes.add(halfEdgeOfTriangle.getNextHalfEdge().getNextHalfEdge().getStartVertex());
//            for (Vertex vertex : vertexes) {
//                gl.glVertex3d(vertex.getPosition().getX(), vertex.getPosition().getY(), vertex.getPosition().getZ());
//            }
//        }
//        // Restore original state
//        gl.glEnd();
    }

    public void buildList(GL2 gl) {
        for (int i = 1; displayListName != 0; i++) {
            displayListName = gl.glGenLists(i);
        }
        gl.glNewList(displayListName, GL2.GL_TRIANGLES);
        gl.glBegin(GL2.GL_TRIANGLES);

        List<TriangleFacet> triangles = triangulatedMesh.getAllTriangles();

        for (TriangleFacet triangle : triangles) {
            HalfEdge halfEdgeOfTriangle = triangle.getHalfEdge();
            ArrayList<Vertex> vertexes = new ArrayList<>();
            vertexes.add(halfEdgeOfTriangle.getStartVertex());
            vertexes.add(halfEdgeOfTriangle.getNextHalfEdge().getStartVertex());
            vertexes.add(halfEdgeOfTriangle.getNextHalfEdge().getNextHalfEdge().getStartVertex());
            for (Vertex vertex : vertexes) {
                gl.glVertex3d(vertex.getPosition().getX(), vertex.getPosition().getY(), vertex.getPosition().getZ());
            }
        }
        // Restore original state
        gl.glEnd();
        gl.glEndList();
    }
}
