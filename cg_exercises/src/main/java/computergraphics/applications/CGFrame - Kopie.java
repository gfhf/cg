/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import java.util.Vector;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.AnimationNode;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.GroupNode;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame extends AbstractCGFrame {
	
	public ShaderNode shaderNode;

  /**
   * 
   */
  private static final long serialVersionUID = 4257130065274995543L;

  /**
   * Constructor.
   */
  public CGFrame(int timerInverval) {
    super(timerInverval);

    // Shader node does the lighting computation
    shaderNode = new ShaderNode(ShaderType.PHONG);
    getRoot().addChild(shaderNode);
    // Simple triangle
    SingleTriangleNode triangleNode = new SingleTriangleNode();
    ScaleNode scaleNode = new ScaleNode(new Vector3(2.0, 2.0, 2.0));
    RotationNode rotationNode = new RotationNode(new Vector3(1.0, 1.0, 1.0), 180.0f);
    TranslationNode translationNode = new TranslationNode(new Vector3(0.0, -3.0, 0.0));
    shaderNode.addChild(translationNode);
    translationNode.addChild(rotationNode);
    rotationNode.addChild(scaleNode);
    scaleNode.addChild(triangleNode);

    // Sphere
    SphereNode sphereNode = new SphereNode(0.25, 20);
    translationNode.addChild(sphereNode);
    
    // Landschaft----------------------------------------------------------------------------------------------------------------------------
    GroupNode landscape = new GroupNode();
    CuboidNode cuboidNode = new CuboidNode(100.0, 0.1, 100.0);
    TranslationNode landscapePosition = new TranslationNode(new Vector3(40.0, -1.5, 40.0));
    shaderNode.addChild(landscape);
    landscape.addChild(landscapePosition);
    landscapePosition.addChild(cuboidNode);
    
    //Baum-----------------------------------------------------------------------------------------------------------------------------------
    for(int i = 0; i < 200; i++){
    	TranslationNode treePosition = new TranslationNode(new Vector3((double) (Math.random()*10)+i, 0.0,(double) i%10*5));
    	treePosition.addChild(createTree());
    	shaderNode.addChild(treePosition);
    }
    
    //Figur+----------------------------------------------------------------------------------------------------------------------------------
    for(int i = 0; i< 200; i++){
    	TranslationNode PersonPosition = new TranslationNode(new Vector3((double) (Math.random()*10)+i, 0.0,(double) i%10*5));
    	PersonPosition.addChild(createPerson());
    	shaderNode.addChild(PersonPosition);
    }
    
    //heli-----------------------------------------------------------------------------------------------------------------------------------
    shaderNode.addChild(createHeli());
  }

  /*
   * (nicht-Javadoc)
   * 
   * @see computergrafik.framework.ComputergrafikFrame#timerTick()
   */
  @Override
  protected void timerTick() {
//	  Node heli = shaderNode.getChildNode(shaderNode.getNumberOfChildren()-1);
//      Node helirotor = heli.getChildNode(heli.getNumberOfChildren()-1);
//      
//      for(int i = helirotor.getNumberOfChildren()-1; i >= 0;i--){
//    	  RotationNode rotation = new RotationNode(new Vector3(1.0, 100.0, 1.0), 1.0);
//    	  rotation.addChild(helirotor.getChildNode(i));
//    	  helirotor.deleteChildNode(i);
//    	  helirotor.addChild(rotation);
//      }
	  //System.out.println("Tick" + heli.getNumberOfChildren());
  }

  public void keyPressed(int keyCode) {
    System.out.println("Key pressed: " + (char) keyCode);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // The timer ticks every 100 ms.
    new CGFrame(100);
  }
  
  private GroupNode createTree(){
	GroupNode tree = new GroupNode();
  	
  	CuboidNode treestomp = new CuboidNode(1.0, 3.0, 1.0);
    SphereNode leaf = new SphereNode(1.0, 20);
    TranslationNode bt1 = new TranslationNode(new Vector3(0.0, 2.0, 0.0));
      
    tree.addChild(treestomp);
    tree.addChild(bt1);
    bt1.addChild(leaf);
      
    return tree;
  }
  
  private GroupNode createPerson(){
	  GroupNode person = new GroupNode();
	  
	  TranslationNode tg1 = new TranslationNode(new Vector3(0.0, 5.0,0.0));
	  person.addChild(tg1);
	    
	  SphereNode body = new SphereNode(1.0, 20);
	  TranslationNode bt2 = new TranslationNode(new Vector3(0.0, 0.5,0.0));
	  tg1.addChild(bt2);
	  bt2.addChild(body);
	    
	  CuboidNode leg1 = new CuboidNode(0.5,1.5,0.7);
	  TranslationNode bl1 = new TranslationNode(new Vector3(-0.5, -0.8, 0.0));
	  RotationNode rl1 = new RotationNode(new Vector3(1.0, 10.0, 40.0), 0.0f);
	  tg1.addChild(rl1);
	  rl1.addChild(bl1);
	  bl1.addChild(leg1);
	    
	  CuboidNode leg2 = new CuboidNode(0.5,1.5,0.7);
	  TranslationNode bl2 = new TranslationNode(new Vector3(0.5, -0.8, 0.0));
	  RotationNode rl2 = new RotationNode(new Vector3(1.0, 1.0, 1.0), 0.0f);
	  tg1.addChild(rl2);
	  rl2.addChild(bl2);
	  bl2.addChild(leg2);
	   
	  CuboidNode arm1 = new CuboidNode(0.5, 1.5, 0.7);
	  TranslationNode at1 = new TranslationNode(new Vector3(-1.0, -2.0, 0.0));
	  TranslationNode at0 = new TranslationNode(new Vector3(0.0, 2.3, 0.0));
	  RotationNode ar1 = new RotationNode(new Vector3(1.0, 10.0, 100.0), 60.0f);
	  tg1.addChild(at0);
	  at0.addChild(ar1);
	  ar1.addChild(at1);
	  at1.addChild(arm1);
	    
	  CuboidNode arm2 = new CuboidNode(0.5, 1.5, 0.7);
	  TranslationNode at2 = new TranslationNode(new Vector3(0.2, 1.4, 0.0));
	  TranslationNode at3 = new TranslationNode(new Vector3(0.0, 1.7, -0.2));
	  RotationNode ar2 = new RotationNode(new Vector3(1.0, 10.0, 100.0), 125.0f);
	  tg1.addChild(at2);
	  at2.addChild(ar2);
	  ar2.addChild(at3);
	  at3.addChild(arm2);
	    
	  SphereNode head = new SphereNode(0.6, 20);
	  TranslationNode ht1 = new TranslationNode(new Vector3(0.0, 1.8, 0.0));
	  tg1.addChild(ht1);
	  ht1.addChild(head);
	  
	  return person;
  }
  
  private GroupNode createHeli(){
	  GroupNode heli = new GroupNode();
	  
	  SphereNode helibody = new SphereNode(1.0, 20);
	  heli.addChild(helibody);
	    
	  SphereNode helicockpit = new SphereNode(0.7, 20);
	  TranslationNode helicockpitposition = new TranslationNode(new Vector3(0.0, 0.0, -0.5));
	  heli.addChild(helicockpitposition);
	  helicockpitposition.addChild(helicockpit);
	    
	    
	  CuboidNode heliback = new CuboidNode(0.3, 0.3, 4.0);
	  TranslationNode helibackposition = new TranslationNode(new Vector3(0.0, 0.0, 1.2));
	  heli.addChild(helibackposition);
	  helibackposition.addChild(heliback);
	    
	  CuboidNode helifeet1 = new CuboidNode(0.4, 0.4, 2.0);
	  TranslationNode helifeet1Position = new TranslationNode(new Vector3(-0.5, -1.0, 0.0));
	  heli.addChild(helifeet1Position);
	  helifeet1Position.addChild(helifeet1);
	    
	  CuboidNode helifeet2 = new CuboidNode(0.4, 0.4, 2.0);
	  TranslationNode helifeet2Position = new TranslationNode(new Vector3(0.5, -1.0, 0.0));
	  heli.addChild(helifeet2Position);
	  helifeet2Position.addChild(helifeet2);
	    
	  GroupNode helirotor = new GroupNode();
	  heli.addChild(helirotor);
	    
	  CuboidNode rotor1 = new CuboidNode(0.3, 0.3, 2.0);
	  TranslationNode rotor1position = new TranslationNode(new Vector3(0.0, 1.0, 1.0));
	  AnimationNode rotor1anim = new AnimationNode(new Vector3(1.0, 100.0, 1.0),0.0f);
	  helirotor.addChild(rotor1anim);
	  rotor1anim.addChild(rotor1position);
	  rotor1position.addChild(rotor1);
	    
	  CuboidNode rotor2 = new CuboidNode(0.3, 0.3, 2.0);
	  TranslationNode rotor2position = new TranslationNode(new Vector3(0.0, 1.0, -1.0));
	  AnimationNode rotor2anim = new AnimationNode(new Vector3(1.0, 100.0, 1.0),0.0f);
	  helirotor.addChild(rotor2anim);
	  rotor2anim.addChild(rotor2position);
	  rotor2position.addChild(rotor2);
	    
	  CuboidNode rotor3 = new CuboidNode(0.3, 0.3, 2.0);
	  TranslationNode rotor3position = new TranslationNode(new Vector3(0.0, 1.0, 1.0));
	  RotationNode rotor3position2 = new RotationNode(new Vector3(1.0, 100.0, 1.0) , 90.0f); 
	  TranslationNode rotor3position3 = new TranslationNode(new Vector3(1.0, -0.02, 1.0));
	  AnimationNode rotor3anim = new AnimationNode(new Vector3(1.0, 100.0, 1.0),0.0f);
	  helirotor.addChild(rotor3anim);
	  rotor3anim.addChild(rotor3position);
	  rotor3position.addChild(rotor3position2);
	  rotor3position2.addChild(rotor3position3);
	  rotor3position3.addChild(rotor3);
	    
	  CuboidNode rotor4 = new CuboidNode(0.3, 0.3, 2.0);
	  TranslationNode rotor4position = new TranslationNode(new Vector3(0.0, 1.0, -1.0));
	  RotationNode rotor4position2 = new RotationNode(new Vector3(1.0, 100.0, 1.0), 90.0f);
	  TranslationNode rotor4position3 = new TranslationNode(new Vector3(-1.0, 0.02, -1.0));
	  AnimationNode rotor4anim = new AnimationNode(new Vector3(1.0, 100.0, 1.0),0.0f);
	  helirotor.addChild(rotor4anim);
	  rotor4anim.addChild(rotor4position);
	  rotor4position.addChild(rotor4position2);
	  rotor4position2.addChild(rotor4position3);
	  rotor4position3.addChild(rotor4);
	  
	  return heli;
  }
}
