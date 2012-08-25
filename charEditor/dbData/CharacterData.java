   package dbData;

   import java.util.ArrayList;

   public class CharacterData {
      public String name;
      public String id;
      public String sprite;
   
      public int health;
      public int defense;
      public int speed;
   	
      public LevelBonus levelBonuses;
   
      public String weapon;
   
      public ArrayList<AbilityData> abilities;
      
      public CharacterData(String charName) {
      
      }
      
      public class LevelBonus {
         public int health, attack, speed;
      
      }
   }