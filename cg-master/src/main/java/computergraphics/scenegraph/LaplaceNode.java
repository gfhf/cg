package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

public class LaplaceNode extends Node 
{
	private HalfEdgeTriangleMesh triangulatedMesh;
	private List<Vertex> vertexList;
	private List<HalfEdge> halfEdgeList;
	private int displayList = -1;

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
	        vertexList = mesh.getVertexList();
	        halfEdgeList = mesh.getHalfEdgeList();
	        laplace();
	    }

	  @Override
		public void drawGl(GL2 gl) {
			
			if (this.displayList == -1) {
				generateDisplaylist(gl);
			}

			

			gl.glCallList(displayList);
		}
	  
	  public void laplace(){
		  double a = 0.5;
			
			HashMap<Vertex, Vector3> centerList = new HashMap();
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
					sum = sum.add(neighbour.getPosition());
				}
//				double xSquared = sum.getX()*sum.getX();
//				double ySquared = sum.getY()*sum.getY();
//				double zSquared = sum.getZ()*sum.getZ();
//				double center = 1 / (Math.sqrt((xSquared+ySquared+zSquared)));
//				centerList.put(v, center);
				centerList.put(v, sum);
			}
			for(Map.Entry<Vertex, Vector3> entry : centerList.entrySet()){
//				Vector3 newPostion = new Vector3()entry.getKey().getPosition()*a+(1-a)*entry.getValue();
//				entry.getKey().setPosition(newPosition);
				Vector3 ap = entry.getKey().getPosition().multiply(a);
				Vector3 ac = entry.getValue().multiply(1-a);
				Vector3 newPosition = new Vector3(ap).add(ac);
				entry.getKey().setPosition(newPosition);
			}
			for(Map.Entry<Vertex, Vector3> entry : centerList.entrySet()){
				vertexList = new ArrayList();
				vertexList.add(entry.getKey());
			}
			triangulatedMesh.computeTriangleNormals();
			triangulatedMesh.computeVertexNormals();
		}
	  
	  public void generateDisplaylist(GL2 gl) {
			displayList = gl.glGenLists(1);
			gl.glNewList(displayList, GL2.GL_COMPILE); // TODO Possible bug GL2? or
														// GL
			gl.glBegin(GL2.GL_TRIANGLES);

			for (int faceIndex = 0; faceIndex < triangulatedMesh.getNumberOfTriangles(); faceIndex++) {
				TriangleFacet f1 = triangulatedMesh.getFacet(faceIndex);
				Vector3 normalFace = f1.getNormal();
				Vector3 color = f1.getHalfEdge().getStartVertex().getColor();

				gl.glNormal3d(normalFace.get(0), normalFace.get(1), normalFace.get(2));
				gl.glColor3d(color.get(0), color.get(1), color.get(2));

				
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
}
