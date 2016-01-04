package computergraphics.datastructures;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.PlainNode;
import computergraphics.scenegraph.SphereNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by me on 31.10.2015.
 */
public class SphereNodeTest {

    private SphereNode SphereNodeUnderTest;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {

        Vector3 support = new Vector3(0, 0, 1);
        Vector3 u = new Vector3(1, 0, support.getZ());
        Vector3 v = new Vector3(0, 1, support.getZ());
        SphereNodeUnderTest = new SphereNode(0.5,20,new Vector3(1,1,1));
    }

    @Test
    public void testCalculateRayReturnsResult() {
        // Arrange
        Ray3D ray = new Ray3D(new Vector3(), new Vector3(1, 1, 1));
        // Act
        IntersectionResult result = SphereNodeUnderTest.findIntersection(null, ray);

        // Assert
        Assert.assertThat(result, notNullValue());
        double lambda = 1-Math.sqrt(1-2.75/3);
        Assert.assertThat(result.point, is(new Vector3(lambda, lambda, lambda)));
        Vector3 intersectionNormal = new Vector3((lambda-1)/0.5,(lambda-1)/0.5,(lambda-1)/0.5);
        Assert.assertThat(result.normal, is(intersectionNormal));
    }

    @Test
    public void testCalculateRayReturnsNull() {
        // Arrange
    	
        Ray3D ray = new Ray3D(new Vector3(), new Vector3(-1, -1, -1));

        // Act
        IntersectionResult result = SphereNodeUnderTest.findIntersection(null, ray);

        // Assert
        Assert.assertThat(result, nullValue());
    }
}