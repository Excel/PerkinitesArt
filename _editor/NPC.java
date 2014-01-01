package _editor;
import java.awt.*;
import java.util.*;


public class NPC implements Cloneable{
      public ArrayList<Object> conditions;
   private String id;
   private String sprite;
   private String direction;
   private Point position;
   private ArrayList<String> sequences;
   public NPC(String ID, String s, String d, Point p, ArrayList<String> seq){
      id = ID;
      sprite = s;
      direction = d;
      position = p;
      sequences = seq;
      conditions = new ArrayList<Object>();
   }
   public String getID(){
      return id;
   }
   public String getSprite(){
      return sprite;
   }
   public String getDirection(){
      return direction;
   }
   public Point getPosition(){
      return position;
   }
   public ArrayList<String> getSequences(){
      return sequences;
   }
   public void setID(String ID){
      id = ID;
   }
   public void setSprite(String s){
      sprite = s;
   }
   public void setDirection(String d){
      direction = d;
   }
   public void setPosition(Point p){
      position = p;
   }
   public void setSequences(ArrayList<String> seq){
      sequences = seq;
   }
   //MODIFY ACTIONS
	
   @Override public Object clone() throws CloneNotSupportedException {
   //get initial bit-by-bit copy, which handles all immutable fields
      NPC result = (NPC)super.clone();
   
   
   
      return result;
   }
   
   public static class Condition implements Cloneable{
      public String type;
      public String name;
   	
      public Condition(){
      }
      
      @Override public Object clone() throws CloneNotSupportedException {
      //get initial bit-by-bit copy, which handles all immutable fields
         Condition result = (Condition)super.clone();
               
         return result;
      }
   
   }

}