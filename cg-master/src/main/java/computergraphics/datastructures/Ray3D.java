package computergraphics.datastructures;

import computergraphics.math.Vector3;

/**
 * Representation of a ray in 3-space.
 * 
 * @author Philipp Jenke
 *
 */
public class Ray3D {

  /**
   * Starting point.
   */
  private Vector3 point = new Vector3();

  /**
   * Direction
   */
  private Vector3 direction = new Vector3();

  /**
   * Constructor.
   */
  public Ray3D(Vector3 point, Vector3 direction) {
    this.point.copy(point);
    this.direction.copy(direction);
  }

  @Override
  public String toString() {
    return point + " + lambda * " + direction;
  }

  /**
   * Getter.
   */
  public Vector3 getDirection() {
    return direction;
  }

  /**
   * Getter.
   */
  public Vector3 getPoint() {
    return point;
  }

}
