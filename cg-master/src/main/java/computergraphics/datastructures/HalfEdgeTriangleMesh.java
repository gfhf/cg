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

import computergraphics.math.MathHelpers;
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

        // set opposite
        for (HalfEdge h : newEdges) {
            Vertex startVertex = h.getStartVertex();
            Vertex endVertex = h.getNextHalfEdge().getStartVertex();

            for (HalfEdge h2 : halfEdgeList) {
                Vertex otherStartVertex = h2.getStartVertex();
                Vertex otherEndVertex = h2.getNextHalfEdge().getStartVertex();

                if (startVertex == otherEndVertex && endVertex == otherStartVertex) {
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

    /**
     * Add a new vertex to the vertex list. The new vertex is appended to the end
     * of the list.
     *
     * @param vertex Vertex to be added.
     * @return Index of the vertex in the vertex list.
     */
    @Override
    public int addVertex(Vertex vertex) {
        vertexList.add(vertex);
        return vertexList.size() - 1;
    }

    /**
     * Getter.
     *
     * @return Number of triangles in the mesh.
     */
    @Override
    public List<TriangleFacet> getAllTriangles() {
        return new ArrayList<>(facetList);
    }

    /**
     * Getter.
     *
     * @return Number of triangles in the mesh.
     */
    @Override
    public int getNumberOfTriangles() {
        return facetList.size();
    }

    /**
     * Getter.
     *
     * @return Number of vertices in the triangle mesh.
     */
    @Override
    public int getNumberOfVertices() {
        return vertexList.size();
    }

    /**
     * Getter
     *
     * @param index Index of the vertex to be accessed.
     * @return Vertex at the given index.
     */
    @Override
    public Vertex getVertex(int index) {
        return vertexList.get(index);
    }

    /**
     * Return the facet at the given index.
     *
     * @param facetIndex Index of the facet.
     * @return Facet at the index, null if the index is invalid.
     */
    @Override
    public TriangleFacet getFacet(int facetIndex) {
        return facetList.get(facetIndex);
    }

    /**
     * Clear mesh - remove all triangles and vertices.
     */
    @Override
    public void clear() {
        vertexList.clear();
        facetList.clear();
        halfEdgeList.clear();
    }

    /**
     * Compute the normals for all triangles (facets) in the mesh.
     */
    @Override
    public void computeTriangleNormals() {
        for (TriangleFacet facet : facetList) {
            HalfEdge edge1 = facet.getHalfEdge();
            HalfEdge edge2 = edge1.getNextHalfEdge();
            HalfEdge edge3 = edge2.getNextHalfEdge();

            if (DEBUG && !edge3.getNextHalfEdge().equals(edge1)) {
                throw new IllegalStateException("Edges should be lined up in a circle.");
            }

            Vector3 vertex0 = edge1.getStartVertex().getPosition();
            Vector3 vertex1 = edge2.getStartVertex().getPosition();
            Vector3 vertex2 = edge3.getStartVertex().getPosition();

            Vector3 normal = vertex1.subtract(vertex0).cross(vertex2.subtract(vertex0)).getNormalized();
            facet.setNormal(normal);
        }
    }

    public void computeVertexNormals() {
        for (Vertex vertex : vertexList) {
            ArrayList<TriangleFacet> connectedTriangles = new ArrayList<>();
            Vector3 normal = new Vector3(0, 0, 0);
            for (HalfEdge edge : halfEdgeList) {
                if (edge.getStartVertex().equals(vertex) && !connectedTriangles.contains(edge.getFacet())) {
                    connectedTriangles.add(edge.getFacet());
                }
            }
            for (TriangleFacet triangle : connectedTriangles) {
                normal = normal.add(triangle.getNormal());
            }
            normal.normalize();
            vertex.setNormal(normal);
        }


    }

    public void laplace() {
        double alpha = 0.3;

        HashMap<Vertex, Vector3> centerList = new HashMap();

        for (Vertex vertex : vertexList) {
            centerList.put(vertex, calculateBarycenter(vertex));
        }

        // calculate new vertex positions
        for (Map.Entry<Vertex, Vector3> entry : centerList.entrySet()) {
            Vector3 originalVertexPosition = entry.getKey().getPosition();
            Vector3 ap = originalVertexPosition.multiply(alpha);

            Vector3 barycentricPosition = entry.getValue();
            Vector3 ac = barycentricPosition.multiply(1 - alpha);
            Vector3 newPosition = ap.add(ac);
            entry.setValue(newPosition);
        }

        // set new vertex positions
        for (Map.Entry<Vertex, Vector3> entry : centerList.entrySet()) {
            entry.getKey().setPosition(entry.getValue());
        }

        computeTriangleNormals();
        computeVertexNormals();
    }

    private Vector3 calculateBarycenter(Vertex vertex) {
        ArrayList<Vertex> neighboursVertexes;
        Vector3 sum = new Vector3(0, 0, 0);

        neighboursVertexes = getNeighboursVertexes(vertex);
        for (Vertex neighbour : neighboursVertexes) {
            sum = sum.add(neighbour.getPosition());
        }
        //sum = sum.devideValueByVector(1);
        sum = sum.multiply(1.0 / neighboursVertexes.size());
        return sum;
    }

    private ArrayList<Vertex> getNeighboursVertexes(Vertex vertex) {
        ArrayList<Vertex> neighboursVertexes = new ArrayList();
        HalfEdge startEdge = vertex.getHalfEdge();
        HalfEdge currentEdge = startEdge;
        do {
            neighboursVertexes.add(currentEdge.getOppositeHalfEdge().getStartVertex());
            currentEdge = currentEdge.getOppositeHalfEdge().getNextHalfEdge();
        }
        while (currentEdge != startEdge);
        return neighboursVertexes;
    }

    public void calculateCurveColor() {
        HashMap<Vertex, Double> curveValues = new HashMap();
        double kmin = Integer.MAX_VALUE;
        double kmax = Integer.MIN_VALUE;

        for (Vertex vertexPi : vertexList) {
            //ArrayList<Vertex> neighbours = getNeighboursVertexes(vertexPi);
            List<TriangleFacet> facetsOfPi = getAdjacentTriangleFacets(vertexPi);
            double sum = 0.0;

            for (TriangleFacet triangleFacetPj : facetsOfPi) {
                double piXpj = vertexPi.getNormal().multiply(triangleFacetPj.getNormal());
                double lenghtPi = 1;//vertexPi.getNormal().getLenght();
                double lenghtPj = 1; // vertexPj.getNormal().getLenght();
                double lenghtPiXlenghtPj = lenghtPi * lenghtPj;

                double div = piXpj / lenghtPiXlenghtPj;

                sum += Math.acos(div);
            }
            double gamma = 1.0 / facetsOfPi.size() * sum;


            double sumArea = 0.0;
            for (TriangleFacet triangle : facetsOfPi) {
                sumArea += triangle.getArea();
            }

            // kr�mmung
            double curvature = gamma / sumArea;

            curveValues.put(vertexPi, curvature);

            if (curvature < kmin) {
                kmin = curvature;
            }
            if (curvature > kmax) {
                kmax = curvature;
            }
        }

        for (Map.Entry<Vertex, Double> entry : curveValues.entrySet()) {
            double curvature = entry.getValue();
            double f = ((curvature - kmin) / (kmax - kmin));

            Vector3 colorVector = new Vector3(0, 1, 0).multiply(f);
            Vertex vertex = entry.getKey();
            vertex.setColor(colorVector);
        }
    }

    private List<TriangleFacet> getAdjacentTriangleFacets(Vertex vertexPi) {
        HalfEdge startEdge = vertexPi.getHalfEdge();
        HalfEdge currentEdge = startEdge;

        List<TriangleFacet> facetsOfPi = new ArrayList();
        do {
            facetsOfPi.add(currentEdge.getFacet());
            if (currentEdge.hasOppositeHalfEdge()) {
                currentEdge = currentEdge.getOppositeHalfEdge().getNextHalfEdge();
            } else {
                currentEdge = startEdge;
            }

        } while (startEdge != currentEdge);
        return facetsOfPi;
    }

    public List<HalfEdge> getHalfEdgeList() {
        return halfEdgeList;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

	public int getVertexIndex(Vertex vertex) {
		return vertexList.indexOf(vertex);
	}

	@Override
    public void setTextureFilename(String filename) {
        throw new NotImplementedException();
    }

    @Override
    public String getTextureFilename() {
        throw new NotImplementedException();
    }

    public int getNumberOfHalfEdges() {
        return halfEdgeList.size();
    }
}
