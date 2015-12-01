package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;


public class KruemmungsNode extends Node {
	
	private HalfEdgeTriangleMesh triangulatedMesh;
	private List<Vertex> vertexList;
	private List<HalfEdge> halfEdgeList;
	private int displayList = -1;
	private HashMap<Vertex, Double> kList;

	/**
	 * Vector for saving the color values of this node.
	 */
	private Vector3 colorVector;

	/**
	 * Getter.
	 */
	public Vector3 getColor() {
		return colorVector;
	}

	/**
	 * Setter for the color represented by this node. Valid range for values is
	 * 0 to 1.0.
	 */
	public void setColor(Vector3 colorVector) {
		this.colorVector = colorVector;
	}

	/**
	 * Setter for the color represented by this node, excapting rgb values.
	 * Valid range for values is 0 to 1.0.
	 */
	public void setColor(double r, double g, double b) {
		setColor(new Vector3(r, g, b));
	}

	/**
	 * Initializes a ColorNode with the passed in vector interpreting its x
	 * component as red value, y component as green value and z component as
	 * blue value. Valid range for values is 0 to 1.0.
	 */
	public KruemmungsNode(HalfEdgeTriangleMesh mesh) {
        triangulatedMesh = mesh;
        vertexList = mesh.getVertexList();
        halfEdgeList = mesh.getHalfEdgeList();
	}

	@Override
	public void drawGl(GL2 gl) {
		/// Remember current state of the render system
		gl.glPushMatrix();

		// get the values saved in the color vector
		double[] colorValues = colorVector.data();
		gl.glColor3d(colorValues[0], colorValues[1], colorValues[2]);

		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}

		// Restore original state
		gl.glPopMatrix();
	}
	
	public void calculate(){
		kList = new HashMap();
		double kmin = Integer.MAX_VALUE;
		double kmax = Integer.MIN_VALUE;
		
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
			double piXpj = v.getPosition().multiply(sum);
			double xSquared = sum.getX()*sum.getX();
			double ySquared = sum.getY()*sum.getY();
			double zSquared = sum.getZ()*sum.getZ();
			double piXpj2 = (Math.sqrt((xSquared+ySquared+zSquared)));
			double arccos = Math.acos(piXpj/piXpj2);

			HalfEdge startEdge = v.getHalfEdge();
			HalfEdge currentEdge = startEdge;
			
			List<TriangleFacet> facetsOfV = new ArrayList();
			do{
				facetsOfV.add(currentEdge.getFacet());
				currentEdge = currentEdge.getOppositeHalfEdge().getNextHalfEdge();
			}while(startEdge != currentEdge);
			
			double A = 0;
			for(TriangleFacet triangle : facetsOfV){
				A += triangle.getArea();
			}
			
			double kruemmung = arccos/A;
			kList.put(v, kruemmung);
			if(kruemmung < kmin){
				kmin = kruemmung;
			}
			if(kruemmung > kmax){
				kmax = kruemmung;
			}
		}
		vertexList = new ArrayList<Vertex>();
		for(Map.Entry<Vertex, Double> entry : kList.entrySet()){
			double f = ((entry.getValue()-kmin) / (kmax-kmin));
			Vector3 ColorVector = new Vector3(0,1,0).multiply(f);
			entry.getKey().setColor(ColorVector);
		}
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