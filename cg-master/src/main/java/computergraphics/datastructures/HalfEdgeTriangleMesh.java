/**
 * WP Computer Graphik WS 15/16
 * Praktikumgruppe Nummer ${todo}
 * stephan.berngruber@haw-hamburg.de
 * rene.goetsch@haw-hamburg.de
 * Aufgabenblatt Nr. 2
 * Verwendete Quellen:
 * https://en.wikipedia.org/wiki/RGB_color_model
 * http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/
 */

package computergraphics.datastructures;

import computergraphics.math.Vector3;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * A half edge triangle mesh representation.
 */
public class HalfEdgeTriangleMesh implements ITriangleMesh {

    /**
     * If true code that is only there for debugging reasons will be run,
     * else code will not be run.
     */
    private final boolean DEBUG = true;

    private List<Vertex> vertexList;
    private ArrayList<HalfEdge> halfEdgeList;
    private List<TriangleFacet> facetList;

    public HalfEdgeTriangleMesh() {
        vertexList = new ArrayList<>();
        halfEdgeList = new ArrayList<>();
        facetList = new ArrayList<>();
    }

    /**
     * Add a new triangle to the mesh with the vertex indices a, b, c.
     * The indexes of the vertexes are zero based.
     *
     * @param vertexIndex1
     * @param vertexIndex2
     * @param vertexIndex3
     * @throws IllegalArgumentException if at least one vertex index parameter is greater than the maximum index of the created vertexes.
     * @throws IllegalArgumentException if triangle already existent.
     */
    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
    	Vertex v1 = vertexList.get(vertexIndex1);
    	Vertex v2 = vertexList.get(vertexIndex2);
    	Vertex v3 = vertexList.get(vertexIndex3);
    	
    	HalfEdge h12 = new HalfEdge(v1);
    	HalfEdge h23 = new HalfEdge(v2);
    	HalfEdge h31 = new HalfEdge(v3);
    	
    	ArrayList<HalfEdge> newEdges = new ArrayList();
    	newEdges.add(h12);
    	newEdges.add(h23);
    	newEdges.add(h31);
    	
    	h12.setNextHalfEdge(h23);
    	h23.setNextHalfEdge(h31);
    	h31.setNextHalfEdge(h12);
    	
    	TriangleFacet triangle = new TriangleFacet(h12);
    	
    	h12.setFacet(triangle);
    	h23.setFacet(triangle);
    	h31.setFacet(triangle);
    	
    	v1.setHalfEgde(h12);
    	v2.setHalfEgde(h23);
    	v3.setHalfEgde(h31);
    	
    	for(HalfEdge h : newEdges){
    		Vertex startVertex = h.getStartVertex();
    		Vertex endVertex = h.getNextHalfEdge().getStartVertex();
    		
    		for(HalfEdge h2 : halfEdgeList){
    			Vertex otherStartVertex = h2.getStartVertex();
    			Vertex otherEndVertex = h2.getNextHalfEdge().getStartVertex();
    			
    			if(startVertex == otherEndVertex && endVertex == otherStartVertex){
    				h.setOppositeHalfEdge(h2);
    				h2.setOppositeHalfEdge(h);
    			}
    		}
    	}
    	
    	halfEdgeList.add(h12);
    	halfEdgeList.add(h23);
    	halfEdgeList.add(h31);
    	
    	facetList.add(triangle);
    }
    
    

    private void createThirdEdge(ArrayList<Vertex> vertexesForNewTriangle, TriangleFacet newFacet, ArrayList<HalfEdge> edgesForNewTriangle) {
        ArrayList<Vertex> vertexesWithOutboundEdges = new ArrayList<>();
        for (HalfEdge currentEdge : edgesForNewTriangle) {
            for (Vertex currentVertex : vertexesForNewTriangle) {
                // if true, we found the start vertex for our new edge
                if (currentEdge.getStartVertex().equals(currentVertex)) {
                    vertexesWithOutboundEdges.add(currentVertex);
                    break;
                }
            }
        }
        ArrayList<Vertex> vertexesForNewTriangleClone = new ArrayList<>(vertexesForNewTriangle);
        vertexesForNewTriangleClone.removeAll(vertexesWithOutboundEdges);

        Vertex startVertex = vertexesForNewTriangleClone.get(0);
        HalfEdge edge = new HalfEdge(startVertex);
        edge.setFacet(newFacet);
        // find an edge that is the right candidate to be next edge for the new created edge
        for (HalfEdge nextEdgeCandidate : edgesForNewTriangle) {
            if (nextEdgeCandidate.hasNextHalfEdge()) {
                edge.setNextHalfEdge(nextEdgeCandidate);
                // set edge to be next edge for previous edge
                nextEdgeCandidate.getNextHalfEdge().setNextHalfEdge(edge);
                break;
            }
        }
        edgesForNewTriangle.add(edge);
    }

private TriangleFacet createTriangleWithEdgesNotOpposedToOhterEdges(Vertex vertex1,Vertex vertex2,Vertex vertex3,ArrayList<Vertex>vertexesForNewTriangle){
        HalfEdge halfEdge1=new HalfEdge(vertex1);
        HalfEdge halfEdge2=new HalfEdge(vertex2);
        HalfEdge halfEdge3=new HalfEdge(vertex3);

        ArrayList<HalfEdge>edgesForNewTriangle=new ArrayList<>();
        edgesForNewTriangle.add(halfEdge1);
        edgesForNewTriangle.add(halfEdge2);
        edgesForNewTriangle.add(halfEdge3);

        TriangleFacet facet=new TriangleFacet(halfEdge1);

        for(int i=0;i<edgesForNewTriangle.size();i++){
        edgesForNewTriangle.get(i).setFacet(facet);

        // using modulo here to avoid out of bound access
        edgesForNewTriangle.get(i).setNextHalfEdge(edgesForNewTriangle.get((i+1)%edgesForNewTriangle.size()));

        vertexesForNewTriangle.get(i).setHalfEgde(edgesForNewTriangle.get(i));
        }
        halfEdgeList.addAll(edgesForNewTriangle);
        return facet;
        }

private boolean existsTriangleWithVertexes(Set<Vertex>vertexes){
        for(TriangleFacet facet:facetList){
        if(getAllVertexesOfFacet(facet).equals(vertexes)){
        return true;
        }
        }
        return false;
        }

/**
 * Gets all vertexes of the given facet.
 *
 * @param facet
 * @return The vertexes of the facet.
 */
private Set<Vertex>getAllVertexesOfFacet(TriangleFacet facet){
        HashSet<Vertex>facetVertexes=new HashSet<>();

        for(HalfEdge edge:halfEdgeList){
        if(edge.getFacet().equals(facet)){
        facetVertexes.add(edge.getStartVertex());
        }
        }
        return facetVertexes;
        }

/**
 * Get all edges registered for this mesh that are surrounding the reached in facet.
 *
 * @param facet
 * @return All edges surrounding the facet.
 */
private Set<HalfEdge>getAllEdgesOfAFacet(TriangleFacet facet){
        Set<HalfEdge>facetSurroundingEdges=new HashSet<>();

        for(HalfEdge edge:halfEdgeList){
        if(edge.getFacet().equals(facet)){
        facetSurroundingEdges.add(edge);
        }
        if(facetSurroundingEdges.size()==3){
        return facetSurroundingEdges;
        }
        }
        System.err.println("Error: Shouldn't be getting here: Found a facet with < 3 half-edges, all facets need to have 3 half-edges...");
        return facetSurroundingEdges;
        }

/**
 * Returns all facets that consist of at least one vertex in the reached in list.
 *
 * @param vertexIndexes not null.
 * @return The list of facets.
 */
private ArrayList<TriangleFacet>getExistingFacetsWithAttachedVertexes(ArrayList<Integer>vertexIndexes){
        ArrayList<TriangleFacet>result=new ArrayList<>();

        for(TriangleFacet facet:facetList){
        for(Vertex vertex:getAllVertexesOfFacet(facet)){
        int vertexIndex=vertexList.indexOf(vertex);
        if(vertexIndexes.contains(vertexIndex)){
        result.add(facet);
        break;
        }
        }
        }
        return result;
        }

/**
 * Add a new vertex to the vertex list. The new vertex is appended to the end
 * of the list.
 *
 * @param vertex Vertex to be added.
 * @return Index of the vertex in the vertex list.
 */
@Override
public int addVertex(Vertex vertex){
        vertexList.add(vertex);
        return vertexList.size()-1;
        }

/**
 * Getter.
 *
 * @return Number of triangles in the mesh.
 */
@Override
public List<TriangleFacet>getAllTriangles(){
        return new ArrayList<>(facetList);
        }

/**
 * Getter.
 *
 * @return Number of triangles in the mesh.
 */
@Override
public int getNumberOfTriangles(){
        return facetList.size();
        }

/**
 * Getter.
 *
 * @return Number of vertices in the triangle mesh.
 */
@Override
public int getNumberOfVertices(){
        return vertexList.size();
        }

/**
 * Getter
 *
 * @param index Index of the vertex to be accessed.
 * @return Vertex at the given index.
 */
@Override
public Vertex getVertex(int index){
        return vertexList.get(index);
        }

/**
 * Return the facet at the given index.
 *
 * @param facetIndex Index of the facet.
 * @return Facet at the index, null if the index is invalid.
 */
@Override
public TriangleFacet getFacet(int facetIndex){
        return facetList.get(facetIndex);
        }

/**
 * Clear mesh - remove all triangles and vertices.
 */
@Override
public void clear(){
        vertexList.clear();
        facetList.clear();
        halfEdgeList.clear();
        }

/**
 * Compute the normals for all triangles (facets) in the mesh.
 */
@Override
public void computeTriangleNormals(){
        for(TriangleFacet facet:facetList){
        HalfEdge edge1=facet.getHalfEdge();
        HalfEdge edge2=edge1.getNextHalfEdge();
        HalfEdge edge3=edge2.getNextHalfEdge();

        if(DEBUG&&!edge3.getNextHalfEdge().equals(edge1)){
        throw new IllegalStateException("Edges should be lined up in a circle.");
        }

        Vector3 vertex0=edge1.getStartVertex().getPosition();
        Vector3 vertex1=edge2.getStartVertex().getPosition();
        Vector3 vertex2=edge3.getStartVertex().getPosition();

        Vector3 normal=vertex1.subtract(vertex0).cross(vertex2.subtract(vertex0)).getNormalized();
        facet.setNormal(normal);
        }
    }

public void computeVertexNormals(){
	for(Vertex vertex : vertexList)
	{
		ArrayList<TriangleFacet> connectedTriangles = new ArrayList<TriangleFacet>();
		Vector3 normal = new Vector3(0,0,0);
		for(HalfEdge edge : halfEdgeList){
			if(edge.getStartVertex().equals(vertex) && !connectedTriangles.contains(edge.getFacet())){
				connectedTriangles.add(edge.getFacet());
			}
		}
		for(TriangleFacet triangle : connectedTriangles){
			normal = normal.add(triangle.getNormal());
		}
		normal.normalize();
		vertex.setNormal(normal);
	}
	
	
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
//		double xSquared = sum.getX()*sum.getX();
//		double ySquared = sum.getY()*sum.getY();
//		double zSquared = sum.getZ()*sum.getZ();
//		double center = 1 / (Math.sqrt((xSquared+ySquared+zSquared)));
//		centerList.put(v, center);
		centerList.put(v, sum);
	}
	for(Map.Entry<Vertex, Vector3> entry : centerList.entrySet()){
//		Vector3 newPostion = new Vector3()entry.getKey().getPosition()*a+(1-a)*entry.getValue();
//		entry.getKey().setPosition(newPosition);
		Vector3 ap = entry.getKey().getPosition().multiply(a);
		Vector3 ac = entry.getValue().multiply(1-a);
		Vector3 newPosition = new Vector3(ap.add(ac));
		entry.getKey().setPosition(newPosition);
	}
	for(Map.Entry<Vertex, Vector3> entry : centerList.entrySet()){
		vertexList = new ArrayList();
		vertexList.add(entry.getKey());
	}
	computeTriangleNormals();
	computeVertexNormals();
}

public void calculateCurveColor(){
	HashMap<Vertex, Double> kList = new HashMap();
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
			currentEdge = currentEdge.getOpposite().getNextHalfEdge();
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
	for(Map.Entry<Vertex, Double> entry : kList.entrySet()){
		double f = ((entry.getValue()-kmin) / (kmax-kmin));
		Vector3 ColorVector = new Vector3(0,1,0).multiply(f);
		entry.getKey().setColor(ColorVector);
	}
}

public List<HalfEdge> getHalfEdgeList(){
	return halfEdgeList;
}

public List<Vertex> getVertexList(){
	return vertexList;
}

@Override
public void setTextureFilename(String filename){
        throw new NotImplementedException();
        }

@Override
public String getTextureFilename(){
        throw new NotImplementedException();
        }

public int getNumberOfHalfEdges(){
        return halfEdgeList.size();
        }
        }
