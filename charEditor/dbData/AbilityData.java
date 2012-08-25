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
      
      public static Map<String, Map<String, FieldTypes>> fields;
      public Map<String, String> actualFields = new HashMap<String, String>();
      
      //public Buff buff;
   
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
         f.put("stopAtEnemy", FieldTypes.BOOLEAN);
         fields.put("AttackDashSkillshot", f);
      
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
   }