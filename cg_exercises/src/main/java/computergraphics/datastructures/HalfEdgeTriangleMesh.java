package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This interface describes the valid operations for a triangle mesh structure.
 * 
 * @author Philipp Jenke
 */
public class HalfEdgeTriangleMesh implements ITriangleMesh
{
	private List<Vertex> vertice;
	private List<TriangleFacet> triangles;
	private List<HalfEdge> oppositeEdges;
	private List<HalfEdge> edges;
	
	public HalfEdgeTriangleMesh(){
		vertice = new ArrayList();
		triangles = new ArrayList();
		oppositeEdges = new ArrayList();
		edges = new ArrayList();
	}
	
	/**
	   * Add a new triangle to the mesh with the vertex indices a, b, c. The index
	   * of the first vertex is 0.
	   * 
	   * @param t
	   *          Container object to represent a triangle.
	   */
	@Override
	public void addTriangle(int vertexIndex1, int vertexIndex2,
			int vertexIndex3){
		int maxIndex = Math.max(Math.max(vertexIndex1, vertexIndex2), vertexIndex3);
        if (maxIndex >= vertice.size()) {
            throw new IllegalArgumentException("At least one vertex index parameter is greater than the maximum index of the created vertexes. Create required vertexes first.");
        }
        
        HalfEdge opposite = null;
        TriangleFacet triangle = new TriangleFacet();
        
        Vertex v1 = vertice.get(vertexIndex1);
        Vertex v2 = vertice.get(vertexIndex2);
        Vertex v3 = vertice.get(vertexIndex3);
        
        HalfEdge h12 = lookForEdge(v1,v2);
        HalfEdge h23 = lookForEdge(v2,v3);
        HalfEdge h31 = lookForEdge(v3,v1);
        
        if(h12 == null){
        	h12 = new HalfEdge(v1);
        	opposite = new HalfEdge(v2);
        	h12.setOpposite(opposite);
        	opposite.setOpposite(h12);
        	oppositeEdges.add(opposite);
        	edges.add(h12);
        	edges.add(opposite);
        }
        if(h23 == null){
        	h23 = new HalfEdge(v2);
        	opposite = new HalfEdge(v3);
        	h23.setOpposite(opposite);
        	opposite.setOpposite(h23);
        	oppositeEdges.add(opposite);
        	edges.add(h12);
        	edges.add(opposite);
        }
        if(h31 == null){
        	h31 = new HalfEdge(v3);
        	opposite = new HalfEdge(v1);
        	h31.setOpposite(opposite);
        	opposite.setOpposite(h31);
        	oppositeEdges.add(opposite);
        	edges.add(h12);
        	edges.add(opposite);
        }
        
        h12.setNext(h23);
        h23.setNext(h31);
        h31.setNext(h12);
        
        h12.setFacet(triangle);
        h23.setFacet(triangle);
        h31.setFacet(triangle);
        
        v1.setHalfEgde(h12);
        v2.setHalfEgde(h23);
        v3.setHalfEgde(h31);
        
        triangle.setHalfEdge(h12);
        triangles.add(triangle);
	}
	
	private HalfEdge lookForEdge(Vertex v1, Vertex v2){
		HalfEdge result = null;
		
		loop:
		for(HalfEdge h : oppositeEdges){
			if(h.getStartVertex() == v1){
				if(h.getOpposite().getStartVertex() == v2){
					result = h;
					break loop;
				}
			}
		}
		return result;
	}

	/**
	   * Add a new vertex to the vertex list. The new vertex is appended to the end
	   * of the list.
	   * 
	   * @param v
	   *          Vertex to be added.
	   * 
	   * @return Index of the vertex in the vertex list.
	   */
	@Override
	public int addVertex(Vertex v)
	{
		vertice.add(v);
        return vertice.size() - 1;
	}

	/**
	   * Getter.
	   * 
	   * @return Number of triangles in the mesh.
	   */
	@Override
	public int getNumberOfTriangles()
	{
		return triangles.size();
	}

	/**
	   * Getter.
	   * 
	   * @return Number of vertices in the triangle mesh.
	   */
	@Override
	public int getNumberOfVertices()
	{
		return vertice.size();
	}

	/**
	   * Getter
	   * 
	   * @param index
	   *          Index of the vertex to be accessed.
	   * @return Vertex at the given index.
	   */
	@Override
	public Vertex getVertex(int index)
	{
		return vertice.get(index);
	}

	/**
	   * Return the facet at the given index.
	   * 
	   * @param facetIndex
	   *          Index of the facet.
	   * @return Facet at the index, null if the index is invalid.
	   */
	@Override
	public TriangleFacet getFacet(int facetIndex)
	{
		return triangles.get(facetIndex);
	}

	/**
	   * Clear mesh - remove all triangles and vertices.
	   */
	@Override
	public void clear()
	{
		triangles.clear();
		vertice.clear();
		edges.clear();
		oppositeEdges.clear();
	}

	/**
	   * Compute the normals for all triangles (facets) in the mesh.
	   */
	@Override
	public void computeTriangleNormals()
	{
		for (TriangleFacet triangle : triangles) {
            HalfEdge edge1 = triangle.getHalfEdge();
            HalfEdge edge2 = edge1.getNext();
            HalfEdge edge3 = edge2.getNext();

            if (!edge3.getNext().equals(edge1)) {
                throw new IllegalStateException("Edges should be lined up in a circle.");
            }

            Vector3 vertex0 = edge1.getStartVertex().getPosition();
            Vector3 vertex1 = edge2.getStartVertex().getPosition();
            Vector3 vertex2 = edge3.getStartVertex().getPosition();

            Vector3 normal = vertex1.subtract(vertex0).cross(vertex2.subtract(vertex0)).getNormalized();
            triangle.setNormal(normal);
        }
		
	}

	@Override
	public void setTextureFilename(String filename)
	{
		throw new NotImplementedException();
		
	}

	@Override
	public String getTextureFilename()
	{
		throw new NotImplementedException();
	}
	
	public Vector3[] getAllVertexPositions() {
        Vector3[] vertexes = new Vector3[vertice.size()];

        for (int i = 0; i < vertexes.length; i++) {
            vertexes[i] = vertice.get(i).getPosition();
        }
        return vertexes;
    }
	
	public List<TriangleFacet> getTriangles(){
		return this.triangles;
	}
}
