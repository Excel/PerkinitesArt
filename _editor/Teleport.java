   package _editor;
   import java.awt.*;
   import java.util.*;
   
   
   public class Teleport implements Cloneable{
      public ArrayList<Condition> conditions;
      private Point entry;
      //private String map;
      private Exit exit;
      public Teleport(Point en, String m, Point ex){
         entry = en;
         exit = new Exit();
         exit.map = m;
         exit.x = ex.x;
         exit.y = ex.y;
         conditions = new ArrayList<Condition>();
      }
      public Point getEntry(){
         return entry;
      }
      public String getMap(){
         return exit.map;
      }
      public Point getExit(){
         return new Point(exit.x, exit.y);
      }
      public void setEntry(Point en){
         entry = en;
      }
      public void setMap(String m){
         exit.map = m;
      }
      public void setExit(Point ex){
         exit.x = ex.x;
         exit.y = ex.y;
      }
      @Override public Object clone() throws CloneNotSupportedException {
      //get initial bit-by-bit copy, which handles all immutable fields
         Teleport result = (Teleport)super.clone();
      
         if(conditions == null){
            conditions = new ArrayList<Condition>();
         }
         ArrayList<Condition> newConditions = new ArrayList<Condition>();
         
         for(int i = 0; i < conditions.size(); i++){
            Condition c = new Condition();
            c.type = ""+conditions.get(i).type;
            c.name = ""+conditions.get(i).name;
            newConditions.add(c);
         }   
         
         result.conditions = newConditions;
      
         return result;
      }
      public static class Exit{
         public String map;
         public int x;
         public int y;
         
         public Exit(){
         }
      }
      public static class Condition{
         public String type;
         public String name;
      	
         public Condition(){
         }
      
      }
   }