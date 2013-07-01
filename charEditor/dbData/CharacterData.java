   package dbData;

   import java.util.ArrayList;

   public class CharacterData extends UnitData{
   	
      public LevelBonus levelBonuses;
   
      public String weapon;
      
      public CharacterData() {
		
			levelBonuses = new LevelBonus();
			levelBonuses.health = 0;
			levelBonuses.attack = 0;
			levelBonuses.speed = 0;
      
      }
      
      public class LevelBonus {
         public int health, attack, speed;
      
      }
   }