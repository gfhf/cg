package computergraphics.scenegraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import computergraphics.math.Vector3;

/**
 * Created by me on 16.12.2015.
 */
public class LineNode extends Node {

    private Vector3 _startVertex;
    private Vector3 _endVertex;

    public LineNode(Vector3 startVertex, Vector3 endVertex) {
        _startVertex = startVertex;
        _endVertex = endVertex;
    }

    /**
     * This method is called to draw the node using OpenGL commands. Override in
     * implementing nodes. Do not forget to call the same method for the children.
     *
     * @param gl
     */
    @Override
    public void drawGl(GL2 gl) {
        // draw tangent
        gl.glBegin(GL.GL_LINES);
        gl.glVertex3d(_startVertex.getX(), _startVertex.getY(), _startVertex.getZ());
        gl.glVertex3d(_endVertex.getX(), _endVertex.getY(), _endVertex.getZ());
        gl.glEnd();
    }
}
