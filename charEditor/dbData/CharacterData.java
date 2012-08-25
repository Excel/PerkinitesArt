   package dbData;

   import java.util.ArrayList;

   public class CharacterData extends UnitData{
   	
      public LevelBonus levelBonuses;
   
      public String weapon;
      
      public CharacterData() {
      
      }
      
      public class LevelBonus {
         public int health, attack, speed;
      
      }
   }