   package _editor;
   import java.awt.*;
   import java.util.*;
   
   
   public class Teleport implements Cloneable{
      public ArrayList<Object> conditions;
      private Point entry;
      //private String map;
      public Exit exit;
      public Teleport(Point en, String m, Point ex){
         entry = en;
         exit = new Exit();
         exit.map = m;
         exit.x = ex.x;
         exit.y = ex.y;
         conditions = new ArrayList<Object>();
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
      
         result.exit = (Exit)exit.clone();
         if(conditions == null){
            conditions = new ArrayList<Object>();
         }
         ArrayList<Object> newConditions = new ArrayList<Object>();
         
        /* for(int i = 0; i < conditions.size(); i++){
            Object c = conditions.get(i).clone();
            newConditions.add(c);
         }*/   
         
         result.conditions = newConditions;
      
         return result;
      }
      public static class Exit implements Cloneable{
         public String map;
         public int x;
         public int y;
         
         public Exit(){
         }
      	
         @Override public Object clone() throws CloneNotSupportedException {
         //get initial bit-by-bit copy, which handles all immutable fields
            Exit result = (Exit)super.clone();
                  
            return result;
         }
      	
      }
   }