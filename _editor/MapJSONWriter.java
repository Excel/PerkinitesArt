   package _editor;

   import java.awt.*;
   import java.util.*;
   
   import java.lang.reflect.Type;

   import com.google.gson.ExclusionStrategy;

   import com.google.gson.FieldAttributes;
   import com.google.gson.Gson;
   import com.google.gson.GsonBuilder;
   import com.google.gson.JsonArray;
   import com.google.gson.JsonElement;
   import com.google.gson.JsonObject;
   import com.google.gson.JsonPrimitive;
   import com.google.gson.JsonSerializer;
   import com.google.gson.JsonSerializationContext;
   import com.google.gson.JsonParseException;
   import com.google.gson.stream.JsonWriter;
   
   import com.google.gson.reflect.TypeToken;
   
   import java.io.File;
   
   import java.io.BufferedReader;
   import java.io.FileWriter;
   import java.io.FileNotFoundException;
   import java.io.IOException;
   public class MapJSONWriter{
   
      public static String path;
      private static class MyExclusionStrategy implements ExclusionStrategy {
         private final Class<?> typeToSkip;
      
         private MyExclusionStrategy(Class<?> typeToSkip) {
            this.typeToSkip = typeToSkip;
         }
      
         public boolean shouldSkipClass(Class<?> clazz) {
            return (clazz == typeToSkip);
         }
      
         public boolean shouldSkipField(FieldAttributes f) {
            return f.getName().equals("mapMatrix");
         }
      }
      
      private static class StringSerializer implements JsonSerializer<String> {
         public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            if(src.toString().length() == 0){
               return null;
            }
            return new JsonPrimitive(src.toString());
         }
      }   
      public static ArrayList<Map> writeMapJSON(ArrayList<Map> mapArray){
         Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setExclusionStrategies(new MyExclusionStrategy(NPCSequence.class))
            .registerTypeAdapter(String.class, new StringSerializer())
            .create();
        
         File dir1 = new File(".");
         try {
            boolean test = tryPath(dir1, "\\..\\Perkinites v2\\assets\\data\\") ||
               			tryPath(dir1, "\\..\\..\\p\\assets\\data\\") ||
               			tryPath(dir1, "\\assets\\data\\");
            
         	
            try {
               JsonWriter writer = new JsonWriter(new FileWriter(path + "maps\\maps.json"));
               writer.setLenient(true);
               writer.setIndent("\t");
               
               writer.beginObject();
               writer.name("maps");
               writer.beginArray();
               for(int i = 0; i < mapArray.size(); i++){
                  writer.value(mapArray.get(i).getID());
               }
            		
               writer.endArray();  
               writer.endObject();
               writer.close();                                
            }  
               catch (IOException e) {
                  e.printStackTrace();
               }
            for(int i = 0; i < mapArray.size(); i++){
               Map map = mapArray.get(i);
            
               String json = gson.toJson(map);
            
               try {
                  FileWriter writer = new FileWriter(path + "maps\\"+ map.getID() + ".json");
                  writer.write(json);
                  writer.close();
               
               } 
                  catch (IOException e) {
                     e.printStackTrace();
                  }	
            
            }
         
         }
            catch (Exception e) {
               e.printStackTrace();
            }
               
         return mapArray;
      }
      /**
   	 * returns false if path passed in doesn't exist
   	 */
      public static boolean tryPath(File cwd, String p) throws Exception {
         path = cwd.getCanonicalPath() + p;
         File f = new File(path);
      	
         return f.exists();
      }   
   }