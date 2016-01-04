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
import computergraphics.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene graph node which realizes drawing of a triangulated mesh.
 */
public class TriangulatedMeshNode extends Node {
	private HalfEdgeTriangleMesh mesh;
	private int displayList = -1;
	private boolean updated = false;


	public TriangulatedMeshNode(HalfEdgeTriangleMesh mesh) {
		this.mesh = mesh;
	}

	@Override
	public void drawGl(GL2 gl) {
		if (this.displayList == -1 || updated) {
			generateDisplaylist(gl);
			updated = false;
		}
		gl.glCallList(displayList);
	}

	public void generateDisplaylist(GL2 gl) {
		displayList = gl.glGenLists(1);
		gl.glNewList(displayList, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLES);

		for (int faceIndex = 0; faceIndex < mesh.getNumberOfTriangles(); faceIndex++) {
			TriangleFacet f1 = mesh.getFacet(faceIndex);
			Vector3 normalFace = f1.getNormal();
//			Vector3 color = f1.getHalfEdge().getStartVertex().getColor();

			gl.glNormal3d(normalFace.get(0), normalFace.get(1), normalFace.get(2));
//			gl.glColor3d(color.get(0), color.get(1), color.get(2));

			
			Vertex vx1 = f1.getHalfEdge().getStartVertex();
			Vector3 v1 = vx1.getPosition();
			
			Vertex vx2 = f1.getHalfEdge().getNextHalfEdge().getStartVertex();
			Vector3 v2 = vx2.getPosition();
			
			Vertex vx3 = f1.getHalfEdge().getNextHalfEdge().getNextHalfEdge().getStartVertex();
			Vector3 v3 = vx3.getPosition();
			
			gl.glNormal3d(vx1.getNormal().get(0),vx1.getNormal().get(1),vx1.getNormal().get(2));
			gl.glVertex3d(v1.get(0), v1.get(1), v1.get(2));
			
			gl.glNormal3d(vx2.getNormal().get(0),vx2.getNormal().get(1),vx2.getNormal().get(2));
			gl.glVertex3d(v2.get(0), v2.get(1), v2.get(2));
			
			gl.glNormal3d(vx3.getNormal().get(0),vx3.getNormal().get(1),vx3.getNormal().get(2));
			gl.glVertex3d(v3.get(0), v3.get(1), v3.get(2));

		}

		gl.glEnd();

		gl.glEndList();

	}

	public void laplace(){
		mesh.laplace();
		updated = true;
	}
}
