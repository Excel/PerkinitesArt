   package _editor;
   import java.awt.*;
   import java.util.*;
   
   
   public class NPCSequence{
      private String state;
      private String name;
      private ArrayList<ArrayList<NPCAction>> actions;
      public NPCSequence(){
      }
      public String getState(){
         return state;
      }
      public String getName(){
         return name;
      }
      public ArrayList<ArrayList<NPCAction>> getActions(){
         return actions;
      }
      public void setState(String s){
         state = s;
      }
      public void setName(String n){
         name = n;
      }
      public void setActions(ArrayList<ArrayList<NPCAction>> a){
         actions = a;
      }
      
   }