package computergraphics.scenegraph;

import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

public class TriangleMeshNode extends Node
{
	private HalfEdgeTriangleMesh triangleMesh;
	private int displayList;
	
	public TriangleMeshNode(HalfEdgeTriangleMesh mesh){
		this.triangleMesh = mesh;
		this.displayList = 0;
	}

	
	@Override
	public void drawGl(GL2 gl) {
        if(displayList == 0){
        	System.out.println("Test");
        	buildList(gl);
        }
        else{
        	System.out.println("test2");
        	gl.glCallList(displayList);
        }
    }	
	
	public void buildList(GL2 gl){
		this.displayList = gl.glGenLists(1);
		gl.glNewList(displayList, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLES);
		Vector3[] vertexPositions = triangleMesh.getAllVertexPositions();

        List<TriangleFacet> triangles = triangleMesh.getTriangles();
        
        System.out.println("" + triangles.size());
        
        for (TriangleFacet triangle : triangles) {
            
            Vertex v1 = triangle.getHalfEdge().getStartVertex();
            Vertex v2 = triangle.getHalfEdge().getNext().getStartVertex();
            Vertex v3 = triangle.getHalfEdge().getNext().getNext().getStartVertex();
            Vector3 normal = triangle.getNormal();
            Vector3 v1Position = v1.getPosition();
            Vector3 v2Position = v2.getPosition();
            Vector3 v3Position = v3.getPosition();
            		
            
            gl.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
            gl.glVertex3d(v1Position.getX(), v1Position.getY(), v1Position.getZ());
            gl.glVertex3d(v2Position.getX(), v2Position.getY(), v2Position.getZ());
            gl.glVertex3d(v3Position.getX(), v3Position.getY(), v3Position.getZ());
            
        }
        gl.glEnd();
        gl.glEndList();
	}
}
