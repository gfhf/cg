package computergraphics.datastructures;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.PlainNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by me on 31.10.2015.
 */
public class PlainNodeTest {

    private PlainNode plainNodeUnderTest;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {

        Vector3 support = new Vector3(0, 0, 1);
        Vector3 u = new Vector3(1, 0, support.getZ());
        Vector3 v = new Vector3(0, 1, support.getZ());
        plainNodeUnderTest = new PlainNode(support, u, v, 1, 1);
    }

    @Test
    public void testCalculateRayReturnsResult() {
        // Arrange
        Ray3D ray = new Ray3D(new Vector3(), new Vector3(0, 0, 1));
        // Act
        IntersectionResult result = plainNodeUnderTest.findIntersection(null, ray);

        // Assert
        Assert.assertThat(result, notNullValue());
        Assert.assertThat(result.normal, is(plainNodeUnderTest.getPlainNormal()));
        Assert.assertThat(result.point, is(new Vector3(0, 0, 1)));
    }

    @Test
    public void testCalculateRayReturnsNull() {
        // Arrange
        Ray3D ray = new Ray3D(new Vector3(), new Vector3(0, 0, -1));

        // Act
        IntersectionResult result = plainNodeUnderTest.findIntersection(null, ray);

        // Assert
        Assert.assertThat(result, nullValue());
    }
}