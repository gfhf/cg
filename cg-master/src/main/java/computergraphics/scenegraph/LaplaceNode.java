package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

public class LaplaceNode extends Node 
{
	private ITriangleMesh triangulatedMesh;

	/**
	   * Scaling factors in x-, y- and z-direction.
	   */
	  private final Vector3 translation = new Vector3(0, 0, 0);
	  
	  /**
	   * Getter.
	   */
	  public Vector3 getTranslation(){
		  return translation;
	  }

	  /**
	   * Constructor.
	   */
	  public LaplaceNode(HalfEdgeTriangleMesh mesh) {
	        triangulatedMesh = mesh;
	    }

	  @Override
	  public void drawGl(GL2 gl) {
	    // Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply scaling
	    gl.glTranslated(translation.get(0), translation.get(1), translation.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();
	  }
	  
	  public void laplace(){
			double a = 0.5;
			
			HashMap<Vertex, Double> centerList = new HashMap();
			for(Vertex v : vertexList){
				ArrayList<Vertex> neighbours = new ArrayList();
				Vector3 sum = new Vector3(0,0,0);
				for(HalfEdge e  : halfEdgeList){
					Vertex startVertex = e.getStartVertex();
					Vertex endVertex = e.getNextHalfEdge().getStartVertex();
					if(startVertex.equals(v) && !neighbours.contains(endVertex)){
						neighbours.add(endVertex);
					}
					if(endVertex.equals(v) && !neighbours.contains(startVertex)){
						neighbours.add(startVertex);
					}
				}
				for(Vertex neighbour : neighbours){
					sum.add(neighbour.getPosition());
				}
				double xSquared = sum.getX()*sum.getX();
				double ySquared = sum.getY()*sum.getY();
				double zSquared = sum.getZ()*sum.getZ();
				double center = 1 / (Math.sqrt((xSquared+ySquared+zSquared)));
				centerList.put(v, center);
			}
			for(Map.Entry<Vertex, Double> entry : centerList.entrySet()){
				entry.getKey().
			}
		}
}
