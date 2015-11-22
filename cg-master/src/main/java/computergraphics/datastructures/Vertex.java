/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.datastructures;

import java.util.LinkedList;
import java.util.List;

import computergraphics.math.Vector3;

/**
 * Representation of a vertex.
 * 
 * @author Philipp Jenke
 */
public class Vertex {

  /**
   * 3D position of the vertex.
   */
  private Vector3 position = new Vector3(0, 0, 0);
  
  public void setPosition(Vector3 newPosition){
	  position = newPosition;
  }

  /**
   * (Normalized) normal direction of the vertex.
   */
  private Vector3 normal = new Vector3(1, 0, 0);

  /**
   * Color value at the vertex
   */
  private Vector3 color = new Vector3(0, 0, 0);

  /**
   * Reference to one of the outgoing half edges.
   * Can be null.
   */
  private HalfEdge halfEgde = null;

  /**
   * Constructor.
   *
   * @param position Initial value for position.
   */
  public Vertex(Vector3 position) {
    this.position.copy(position);
  }

  /**
   * Constructor.
   *
   * @param position Initial value for position.
   * @param normal   Initial value for normal.
   */
  public Vertex(Vector3 position, Vector3 normal) {
    this.position.copy(position);
    this.normal.copy(normal);
  }

  /**
   * Constructor.
   *
   * @param position Initial value for position.
   * @param normal   Initial value for normal.
   */
  public Vertex(Vector3 position, Vector3 normal, Vector3 color) {
    this.position.copy(position);
    this.normal.copy(normal);
    this.color.copy(color);
  }

  public Vector3 getPosition() {
    return position;
  }

  public Vector3 getNormal() {
    return normal;
  }

  public Vector3 getColor() {
    return color;
  }

  public void setNormal(Vector3 normal) {
    this.normal.copy(normal);
  }

  public void setColor(Vector3 color) {
    this.color.copy(color);
  }

  public HalfEdge getHalfEdge() {
    return halfEgde;
  }

  public void setHalfEgde(HalfEdge halfEgde) {
    this.halfEgde = halfEgde;
  }

  @Override
  public String toString() {
    return "Vertex";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vertex vertex = (Vertex) o;

    if (!getPosition().equals(vertex.getPosition())) return false;
    if (getColor() != null ? !getColor().equals(vertex.getColor()) : vertex.getColor() != null) return false;
    return !(halfEgde != null ? !halfEgde.equals(vertex.halfEgde) : vertex.halfEgde != null);

  }

  @Override
  public int hashCode() {
    int result = getPosition().hashCode();
    result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
    result = 31 * result + (halfEgde != null ? halfEgde.hashCode() : 0);
    return result;
  }
  
}