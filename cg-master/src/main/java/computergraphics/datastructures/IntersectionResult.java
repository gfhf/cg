package computergraphics.datastructures;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.Node;

/**
 * Representation of the intersection result.
 */
public class IntersectionResult {

  /**
   * The intersection happens at this point.
   */
  public Vector3 point;

  /**
   * Normal at the given point.
   */
  public Vector3 normal;

  /**
   * Intersected object
   */
  public Node object;

  public IntersectionResult(Vector3 point, Vector3 normal, Node object) {
    this.point = point;
    this.normal = normal;
    this.object = object;
  }
}
