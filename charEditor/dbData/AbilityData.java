   package dbData;

   import java.util.*;

   public class AbilityData {
      public String name;
      public String type;
      public String icon;
      public String description;
   
      public String dmgBase;
      public String dmgRatio;
   
      public int range;
      public int cd;
   	
      public String animLabel;
      
      public static Map<String, Map<String, FieldTypes>> fields;
      public Map<String, String> actualFields = new HashMap<String, String>();
      
      public AttackBuff buffs;
   
   // fill dictionary with attack fields
   // attack type -> (field name, fieldType)
      public static void init() {
         fields = new HashMap<String, Map<String, FieldTypes>>();
      
         Map<String, FieldTypes> f;
      
         f = new HashMap<String, FieldTypes>();
         f.put("angle", FieldTypes.INT);
         fields.put("AttackCone", f);
      
         f = new HashMap<String, FieldTypes>();
         f.put("goToCastPoint", FieldTypes.BOOLEAN);
         fields.put("AttackSmartcast", f);
      
         f = new HashMap<String, FieldTypes>();
         f.put("radius", FieldTypes.INT);
         fields.put("AttackPoint", f);
      
         f = new HashMap<String, FieldTypes>();
         f.put("width", FieldTypes.INT);
         f.put("penetrates", FieldTypes.INT);
         f.put("speed", FieldTypes.INT);
         fields.put("AttackSkillshot", f);
      
         f = new HashMap<String, FieldTypes>();
         f.put("width", FieldTypes.INT);
         f.put("penetrates", FieldTypes.INT);
         f.put("speed", FieldTypes.INT);
         fields.put("AttackDashSkillshot", f);
      	
         f = new HashMap<String, FieldTypes>();
         f.put("width", FieldTypes.INT);
         f.put("penetrates", FieldTypes.INT);
         f.put("speed", FieldTypes.INT);
         f.put("hardTarget", FieldTypes.BOOLEAN);
         f.put("expires", FieldTypes.INT);			
         fields.put("AttackHomingProjectiles", f);
      
      }
      public void fillInActualFields(){
         Set keyset = fields.keySet();
         Iterator it = keyset.iterator();
         while(it.hasNext()){
            String key = (String)it.next();
            Map<String, FieldTypes> f = fields.get(key);
            Set fKeySet = f.keySet();
            Iterator fit = fKeySet.iterator();
            while(fit.hasNext()){
               String type = (String)fit.next();
               FieldTypes value = f.get(type);
               if(value == FieldTypes.INT || value == FieldTypes.NUMBER)
                  actualFields.put(type, "0");
               else if(value == FieldTypes.BOOLEAN)
                  actualFields.put(type, "false");
               else if(value == FieldTypes.STRING)
                  actualFields.put(type, "");
            	
            }
         }
      
      }
      
   
      public enum FieldTypes {
         INT, NUMBER, BOOLEAN, STRING;
      }
      public class AttackBuff{
         public Buff self = new Buff();
         public Buff team  = new Buff();
         public Buff allies  = new Buff();
         public Buff enemies = new Buff();
      }
      public class Buff{
      
         // public static String[] statTypes = {"Heal", "Attack", "Defense", "Range", 
            // "Movement", "Cooldown", "Poison", "Aura"};
         // public static String[] changeTypes = {"Add", "Mult"}; 
         
         public int healAdd = 0;
         public double healMissingMult = 0;
         
         public double attackMult = 1;
         public int attackAdd = 1;
         
         public double defenseMult = 1;
         public int defenseAdd = 0;
      
         public double rangeMult = 1;
         public int rangeAdd = 0;
         
         public double movementMult = 1;
         public int movementAdd = 0;
      	
         public double cooldownMult = 1;
         public int cooldownAdd = 0;
         
         public double poisonMult = 1;
         public int poisonAdd = 0;
         
         public int auraRange = 0;
         public double auraMult = 0;
      
         public boolean stun = false;
         public boolean detect = false;
         public boolean vanish = false;
         public boolean invincibility = false;
         
         public boolean snare = false;
         public boolean silence = false;
         public boolean charm = false;
         public boolean fear = false;
         public boolean enrage = false;
         public boolean hex = false;  
         
         public int duration = 0;
         
         public Buff(){
         
         }
      
      }
   }