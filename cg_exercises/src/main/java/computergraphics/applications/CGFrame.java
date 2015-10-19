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
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.GroupNode;
import computergraphics.scenegraph.MovementNode;
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
    ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
    getRoot().addChild(shaderNode);
    
    //Scale
    ScaleNode scale = new ScaleNode(new Vector3(0.01,0.01,0.01));
    shaderNode.addChild(scale);
    
    
    // Simple triangle
    SingleTriangleNode triangleNode = new SingleTriangleNode();
    ScaleNode scaleNode = new ScaleNode(new Vector3(2.0, 2.0, 2.0));
    RotationNode rotationNode = new RotationNode(new Vector3(1.0, 1.0, 1.0), 180.0f);
    TranslationNode translationNode = new TranslationNode(new Vector3(0.0, -3.0, 0.0));
    ColorNode triangleColor = new ColorNode(0.14, 023., 0.7);
    shaderNode.addChild(translationNode);
    translationNode.addChild(triangleColor);
    triangleColor.addChild(rotationNode);
    rotationNode.addChild(scaleNode);
    scaleNode.addChild(triangleNode);

    // Sphere
    SphereNode sphereNode = new SphereNode(0.25, 20);
    ColorNode sphereColor = new ColorNode(0.3, 0.0, 0.7);
    translationNode.addChild(sphereColor);
    sphereColor.addChild(sphereNode);
    
 // Landschaft----------------------------------------------------------------------------------------------------------------------------
    GroupNode landscape = new GroupNode();
    CuboidNode cuboidNode = new CuboidNode(100.0, 0.1, 110.0);
    TranslationNode landscapePosition = new TranslationNode(new Vector3(35.0, -4.5, 45.0));
    ColorNode green = new ColorNode(0.0, 1.0, 0.0);
    scale.addChild(green);
    green.addChild(landscape);
    landscape.addChild(landscapePosition);
    landscapePosition.addChild(cuboidNode);
    
    //Baum-----------------------------------------------------------------------------------------------------------------------------------
    for(int i = 0; i < 200; i++){
    	TranslationNode treePosition = new TranslationNode(new Vector3((double) ((Math.random()*10)+i)%70, -3.0,(double) i%20*5));
    	treePosition.addChild(createTree());
    	scale.addChild(treePosition);
    }
    
    //Figur+----------------------------------------------------------------------------------------------------------------------------------
    for(int i = 0; i< 200; i++){
    	TranslationNode PersonPosition = new TranslationNode(new Vector3((double) ((Math.random()*10)+i)%70, -3.0,(double) i%20*5));
    	PersonPosition.addChild(createPerson());
    	scale.addChild(PersonPosition);
    }
    
    //heli-----------------------------------------------------------------------------------------------------------------------------------
    TranslationNode heliPos = new TranslationNode(new Vector3(30.0, 3.0, 50.0));
    RotationNode heliaxis = new RotationNode(new Vector3(0.0, 0.0, 1.0) ,0.0f);
    AnimationNode heliRotation =new AnimationNode(new Vector3(0.0, 1.0, 0.0) ,0.0, 20);
    MovementNode heliMove = new MovementNode(new Vector3(0.0, 0.0, 0.0), 60);
    scale.addChild(heliPos);
    heliPos.addChild(heliRotation);
    heliRotation.addChild(heliaxis);
    heliaxis.addChild(heliMove);
    heliMove.addChild(createHeli());
    
    
  }

  /*
   * (nicht-Javadoc)
   * 
   * @see computergrafik.framework.ComputergrafikFrame#timerTick()
   */
  @Override
  protected void timerTick() {
	  System.out.println("Tick");
  }

  public void keyPressed(int keyCode) {
    System.out.println("Key pressed: " + (char) keyCode);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // The timer ticks every 1000 ms.
    new CGFrame(1000);
  }
  private GroupNode createTree(){
		GroupNode tree = new GroupNode();
	  	
	  	CuboidNode treestomp = new CuboidNode(1.0, 3.0, 1.0);
	    SphereNode leaf = new SphereNode(1.5, 20);
	    TranslationNode leafPosition = new TranslationNode(new Vector3(0.0, 2.5, 0.0));
	    ColorNode leafColor = new ColorNode(0.0, 1.0, 0.0);
	    ColorNode stompColor = new ColorNode(0.4, 0.2, 0.0);
	    
	    tree.addChild(stompColor);
	    stompColor.addChild(treestomp);
	    tree.addChild(leafColor);
	    leafColor.addChild(leafPosition);
	    leafPosition.addChild(leaf);
	      
	    return tree;
	  }
	  
	  private GroupNode createPerson(){
		  GroupNode person = new GroupNode();
		  ColorNode personColor = new ColorNode(0.7, 0.5, 0.0);
		  
		  TranslationNode PersonPosition = new TranslationNode(new Vector3(0.0, 0.0,0.0));
		  person.addChild(personColor);
		  personColor.addChild(PersonPosition);
		    
		  SphereNode body = new SphereNode(1.0, 20);
		  TranslationNode bodyPosition = new TranslationNode(new Vector3(0.0, 0.5,0.0));
		  
		  
		  PersonPosition.addChild(bodyPosition);
		  bodyPosition.addChild(body);
		    
		  CuboidNode leg1 = new CuboidNode(0.5,1.5,0.7);
		  TranslationNode leg1Position = new TranslationNode(new Vector3(-0.5, -0.8, 0.0));
		  RotationNode leg1Position2 = new RotationNode(new Vector3(1.0, 10.0, 40.0), 0.0f);
		  PersonPosition.addChild(leg1Position2);
		  leg1Position2.addChild(leg1Position);
		  leg1Position.addChild(leg1);
		    
		  CuboidNode leg2 = new CuboidNode(0.5,1.5,0.7);
		  TranslationNode leg2Position = new TranslationNode(new Vector3(0.5, -0.8, 0.0));
		  RotationNode leg2Position2 = new RotationNode(new Vector3(1.0, 1.0, 1.0), 0.0f);
		  PersonPosition.addChild(leg2Position2);
		  leg2Position2.addChild(leg2Position);
		  leg2Position.addChild(leg2);
		   
		  CuboidNode arm1 = new CuboidNode(0.5, 1.5, 0.7);
		  TranslationNode armPosition = new TranslationNode(new Vector3(-1.0, -2.0, 0.0));
		  TranslationNode armPosition2 = new TranslationNode(new Vector3(0.0, 2.3, 0.0));
		  RotationNode ar1 = new RotationNode(new Vector3(1.0, 10.0, 100.0), 60.0f);
		  PersonPosition.addChild(armPosition2);
		  armPosition2.addChild(ar1);
		  ar1.addChild(armPosition);
		  armPosition.addChild(arm1);
		    
		  CuboidNode arm2 = new CuboidNode(0.5, 1.5, 0.7);
		  TranslationNode arm2Position = new TranslationNode(new Vector3(0.2, 1.4, 0.0));
		  TranslationNode arm2Position2 = new TranslationNode(new Vector3(0.0, 1.7, -0.2));
		  RotationNode armPosition3 = new RotationNode(new Vector3(1.0, 10.0, 100.0), 125.0f);
		  PersonPosition.addChild(arm2Position);
		  arm2Position.addChild(armPosition3);
		  armPosition3.addChild(arm2Position2);
		  arm2Position2.addChild(arm2);
		    
		  SphereNode head = new SphereNode(0.6, 20);
		  TranslationNode headPosition = new TranslationNode(new Vector3(0.0, 1.8, 0.0));
		  PersonPosition.addChild(headPosition);
		  headPosition.addChild(head);
		  
		  return person;
	  }
	  
	  private GroupNode createHeli(){
		  GroupNode heli = new GroupNode();
		  ColorNode heliColor = new ColorNode(0.7, 0.7, 0.7);
		  
		  
		  heli.addChild(heliColor);
		  
		  SphereNode helibody = new SphereNode(1.0, 20);
		  heliColor.addChild(helibody);
		    
		  SphereNode helicockpit = new SphereNode(0.7, 20);
		  TranslationNode helicockpitposition = new TranslationNode(new Vector3(0.0, 0.0, -0.5));
		  heliColor.addChild(helicockpitposition);
		  helicockpitposition.addChild(helicockpit);
		    
		    
		  CuboidNode heliback = new CuboidNode(0.3, 0.3, 4.0);
		  TranslationNode helibackposition = new TranslationNode(new Vector3(0.0, 0.0, 1.2));
		  heliColor.addChild(helibackposition);
		  helibackposition.addChild(heliback);
		    
		  GroupNode helifeet = new GroupNode();
		  ColorNode feetColor = new ColorNode(0.5, 0.0, 0.0);
		  heliColor.addChild(helifeet);
		  helifeet.addChild(feetColor);
		  
		  CuboidNode helifeet1 = new CuboidNode(0.4, 0.4, 2.0);
		  TranslationNode helifeet1Position = new TranslationNode(new Vector3(-0.5, -1.0, 0.0));
		  feetColor.addChild(helifeet1Position);
		  helifeet1Position.addChild(helifeet1);
		    
		  CuboidNode helifeet2 = new CuboidNode(0.4, 0.4, 2.0);
		  TranslationNode helifeet2Position = new TranslationNode(new Vector3(0.5, -1.0, 0.0));
		  heliColor.addChild(helifeet2Position);
		  helifeet2Position.addChild(helifeet2);
		    
		  AnimationNode rotorAnim = new AnimationNode(new Vector3(1.0, 100.0, 1.0) ,0.0, 5);
		  heliColor.addChild(rotorAnim);
		  
		  GroupNode helirotor = new GroupNode();
		  ColorNode RotorColor = new ColorNode(0.5, 0.0, 0.0);
		  rotorAnim.addChild(RotorColor);
		  RotorColor.addChild(helirotor);
		    
		  CuboidNode rotor1 = new CuboidNode(0.3, 0.3, 2.0);
		  TranslationNode rotor1position = new TranslationNode(new Vector3(0.0, 1.0, 1.0));
		  helirotor.addChild(rotor1position);
		  rotor1position.addChild(rotor1);
		    
		  CuboidNode rotor2 = new CuboidNode(0.3, 0.3, 2.0);
		  TranslationNode rotor2position = new TranslationNode(new Vector3(0.0, 1.0, -1.0));
		  helirotor.addChild(rotor2position);
		  rotor2position.addChild(rotor2);
		    
		  CuboidNode rotor3 = new CuboidNode(0.3, 0.3, 2.0);
		  TranslationNode rotor3position = new TranslationNode(new Vector3(0.0, 1.0, 1.0));
		  RotationNode rotor3position2 = new RotationNode(new Vector3(1.0, 100.0, 1.0) , 90.0f); 
		  TranslationNode rotor3position3 = new TranslationNode(new Vector3(1.0, -0.02, 1.0));
		  helirotor.addChild(rotor3position);
		  rotor3position.addChild(rotor3position2);
		  rotor3position2.addChild(rotor3position3);
		  rotor3position3.addChild(rotor3);
		    
		  CuboidNode rotor4 = new CuboidNode(0.3, 0.3, 2.0);
		  TranslationNode rotor4position = new TranslationNode(new Vector3(0.0, 1.0, -1.0));
		  RotationNode rotor4position2 = new RotationNode(new Vector3(1.0, 100.0, 1.0), 90.0f);
		  TranslationNode rotor4position3 = new TranslationNode(new Vector3(-1.0, 0.02, -1.0));
		  helirotor.addChild(rotor4position);
		  rotor4position.addChild(rotor4position2);
		  rotor4position2.addChild(rotor4position3);
		  rotor4position3.addChild(rotor4);
		  
		  return heli;
	  }
	}
