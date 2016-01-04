package computergraphics.datastructures;

import computergraphics.math.Vector3;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by me on 16.12.2015.
 */
public class TestMonomCurve {

    private MonomCurve _monomCurve;
    private Vector3[] _set1 = new Vector3[3];
    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {

        _set1[0] = new Vector3(0.5, 0, 0);
        _set1[1] = new Vector3(0, 0.5, 0);
        _set1[2] = new Vector3(0.5, 1, 0);
    }

    /**
     * Adding a single triangle to a mesh with only 3 Vertexes. Vertexes are not in use by other triangles.
     *
     * @throws Exception
     */
    @Test
    public void testCalculateBaseFunctionMeetsPoints() throws Exception {
        // Arrange & Act
        _monomCurve = new MonomCurve(_set1);

        // Assert
        assertThat(_set1[1], is(_monomCurve.calculateBaseFunction(0.5)));
    }
}
