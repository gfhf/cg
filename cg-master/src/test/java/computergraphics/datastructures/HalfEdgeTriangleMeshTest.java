package computergraphics.datastructures;

import computergraphics.math.Vector3;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by me on 31.10.2015.
 */
public class HalfEdgeTriangleMeshTest {

    private HalfEdgeTriangleMesh meshUnderTest;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        meshUnderTest = new HalfEdgeTriangleMesh();
    }

    /**
     * Adding a single triangle to a mesh with only 3 Vertexes. Vertexes are not in use by other triangles.
     *
     * @throws Exception
     */
    @Test
    public void testAddASingleTriangleGreenway() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));

        // Act
        meshUnderTest.addTriangle(0, 1, 2);

        // Assert
        assertThat(meshUnderTest.getNumberOfTriangles(), is(1));

        checkFacetConcistency(meshUnderTest.getFacet(0));
    }


    /**
     * Adding two triangles that are not connected.
     *
     * @throws Exception
     */
    @Test
    public void testAddTriangleWithMultipleNotConnectedTriangles() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));

        meshUnderTest.addVertex(new Vertex(new Vector3(1, 1, 1)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 1)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 1, 0)));

        // Act
        meshUnderTest.addTriangle(0, 1, 2);
        meshUnderTest.addTriangle(3, 4, 5);

        // Assert
        assertThat(meshUnderTest.getNumberOfTriangles(), is(2));
        assertThat(meshUnderTest.getNumberOfHalfEdges(), is(6));

        checkFacetConcistency(meshUnderTest.getFacet(0));
        checkFacetConcistency(meshUnderTest.getFacet(1));
    }

    /**
     * Adding a triangle that consists of one vertex that is already in use by another triangle.
     *
     * @throws Exception
     */
    @Test
    public void testAddTriangleMultipleUseOfVertex() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 1)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 1, 1)));

        // Act
        meshUnderTest.addTriangle(0, 1, 2);
        meshUnderTest.addTriangle(2, 3, 4);

        // Assert
        assertThat(meshUnderTest.getNumberOfTriangles(), is(2));
        assertThat(meshUnderTest.getNumberOfHalfEdges(), is(6));

        checkFacetConcistency(meshUnderTest.getFacet(0));
        checkFacetConcistency(meshUnderTest.getFacet(1));
    }

    /**
     * Adding a triangle that consists of 2 vertexes that are already in use by another triangle and that needs to have two halfedges to
     * be created. These new halfedges have no opposing halfedges.
     *
     * @throws Exception
     */
    @Test
    public void testAddTriangleMultipleUseOfVertexesTwoEdgesToBeCreated() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 1, 1)));

        // Act
        meshUnderTest.addTriangle(0, 1, 2);
        meshUnderTest.addTriangle(1, 2, 3);

        // Assert
        assertThat(meshUnderTest.getNumberOfTriangles(), is(2));
        assertThat(meshUnderTest.getNumberOfHalfEdges(), is(6));

        checkFacetConcistency(meshUnderTest.getFacet(0));
        checkFacetConcistency(meshUnderTest.getFacet(1));
    }

    /**
     * Adding a triangle that consists of 2 vertexes that are already in use by another triangle and that needs to have one halfedge to
     * be created that has no opposing halfedge.
     *
     * @throws Exception
     */
    @Test
    public void testAddTriangleMultipleUseOfVertexesOneEdgeToBeCreated() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 1)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 1)));

        // Act
        meshUnderTest.addTriangle(0, 1, 2);
        meshUnderTest.addTriangle(2, 3, 4);
        meshUnderTest.addTriangle(2, 4, 0);

        // Assert
        assertThat(meshUnderTest.getNumberOfTriangles(), is(3));
        assertThat(meshUnderTest.getNumberOfHalfEdges(), is(9));

        checkFacetConcistency(meshUnderTest.getFacet(0));
        checkFacetConcistency(meshUnderTest.getFacet(1));
        checkFacetConcistency(meshUnderTest.getFacet(2));
    }


    @Test
    public void testComputeTriangleNormals() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 1)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 0, 1)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));
        meshUnderTest.addTriangle(0, 1, 2);

        Vector3 expectedNormalVector = new Vector3(0.7071067812, 0.7071067812, 0);
        // Act
        meshUnderTest.computeTriangleNormals();

        // Assert
        TriangleFacet facet = meshUnderTest.getFacet(0);
        assertThat(expectedNormalVector, equalTo(facet.getNormal()));
        assertThat(Math.round(facet.getNormal().getNorm()), is(1L));
    }

    private void checkFacetConcistency(TriangleFacet facet) {

        HalfEdge facetHalfEdge = facet.getHalfEdge();
        assertThat(facetHalfEdge, notNullValue());

        // EDGES
        HalfEdge[] edges = new HalfEdge[3];
        edges[0] = facetHalfEdge;
        edges[1] = facetHalfEdge.getNextHalfEdge();
        edges[2] = edges[1].getNextHalfEdge();

        assertThat(edges[0], notNullValue());
        assertThat(edges[2], notNullValue());
        assertThat(edges[3], notNullValue());

        // no edges equal
        assertThat(edges[0], not(equalTo(edges[1])));
        assertThat(edges[0], not(equalTo(edges[2])));
        assertThat(edges[1], not(equalTo(edges[2])));

        // all edges have next edges
        assertThat(edges[0].getNextHalfEdge(), sameInstance(edges[1]));
        assertThat(edges[1].getNextHalfEdge(), sameInstance(edges[2]));
        assertThat(edges[2].getNextHalfEdge(), sameInstance(edges[0]));

        // edges have start vertex
        assertThat(edges[0].getStartVertex(), notNullValue());
        assertThat(edges[0].getStartVertex(), notNullValue());
        assertThat(edges[1].getStartVertex(), notNullValue());

        // edges do not have same start vertex
//      assertNotEquals(edges[0].getStartVertex(), edges[1].getStartVertex());
//      assertNotEquals(edges[1].getStartVertex(), edges[2].getStartVertex());
//      assertNotEquals(edges[2].getStartVertex(), edges[0].getStartVertex());

        // edges have same facet
        assertThat(edges[0].getFacet(), equalTo(edges[1].getFacet()));
        assertThat(edges[1].getFacet(), equalTo(edges[2].getFacet()));

        // VERTEXES
        Vertex[] vertexes = new Vertex[3];

        vertexes[0] = edges[0].getStartVertex();
        vertexes[1] = edges[1].getStartVertex();
        vertexes[2] = edges[2].getStartVertex();

        // vertexes not equal
//        assertThat(vertexes[0], not(equalTo(vertexes[1])));
//        assertThat(vertexes[1], not(equalTo(vertexes[2])));
//        assertThat(vertexes[2], not(equalTo(vertexes[0])));
    }

    /**
     * Adding a triangle that is in between three other triangles.
     *
     * @throws Exception
     */
    @Test
    public void testAddTriangleInBetweenThreeExistingTriangles() throws Exception {
        // Arrange
        meshUnderTest.addVertex(new Vertex(new Vector3(1, 1, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0.5, 0.5, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 0, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 1, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(0, 2, 0)));
        meshUnderTest.addVertex(new Vertex(new Vector3(1.5, 0.5, 0)));

        // Act
        meshUnderTest.addTriangle(0, 1, 5);
        meshUnderTest.addTriangle(1, 2, 3);
        meshUnderTest.addTriangle(3, 4, 5);
        meshUnderTest.addTriangle(1, 3, 5);


        // Assert
        assertThat(meshUnderTest.getNumberOfTriangles(), is(4));
        assertThat(meshUnderTest.getNumberOfHalfEdges(), is(12));

        checkFacetConcistency(meshUnderTest.getFacet(0));
        checkFacetConcistency(meshUnderTest.getFacet(1));
        checkFacetConcistency(meshUnderTest.getFacet(2));
        checkFacetConcistency(meshUnderTest.getFacet(3));
    }
}