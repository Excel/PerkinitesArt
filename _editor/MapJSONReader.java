   package _editor;

   import java.awt.*;
   import java.util.*;
   
   import java.lang.reflect.Type;


   import com.google.gson.Gson;
   import com.google.gson.GsonBuilder;
   import com.google.gson.JsonDeserializer;
   import com.google.gson.JsonDeserializationContext;
   import com.google.gson.JsonElement;
   import com.google.gson.JsonObject;
   import com.google.gson.JsonParseException;
   import com.google.gson.stream.JsonReader;
   
   import com.google.gson.reflect.TypeToken;
   
   import java.io.File;
   
   import java.io.BufferedReader;
   import java.io.FileReader;
   import java.io.FileNotFoundException;
   import java.io.IOException;
   public class MapJSONReader{
   
      public static String path = "";
   
      public static String[] readBGMJSON() throws Exception{
         ArrayList<String> bgms = new ArrayList<String>();
         bgms.add("");
         Gson gson = new Gson();
         
         File dir1 = new File(".");
         try {
         
            boolean test = tryPath(dir1, "\\..\\Perkinites v2\\assets\\data\\") ||
               			tryPath(dir1, "\\..\\..\\p\\assets\\data\\") ||
               			tryPath(dir1, "\\assets\\data\\");
           
            try {
            
               JsonReader reader = new JsonReader(new FileReader(path + "sounds.json"));
               
               reader.beginObject();
               reader.setLenient(true);
               String name = reader.nextName();
               reader.beginArray();
               while (reader.hasNext()) {
                  reader.beginObject();
                  while(reader.hasNext()){
                     name = reader.nextName();
                     if(name.equals("name")){
                        bgms.add(reader.nextString());
                     }
                     else{
                        reader.skipValue();
                     }
                  }
                  reader.endObject();
               }
               reader.endArray();
               reader.endObject();
               reader.close();
            
            }
               catch(FileNotFoundException e){
                  e.printStackTrace();
               }
         }
            catch(IOException e){
               e.printStackTrace();
            }
            
         return bgms.toArray(new String[bgms.size()]);
      }
   
      public static String[] readEnemyJSON() throws Exception{
         ArrayList<String> enemies = new ArrayList<String>();
         Gson gson = new Gson();
         
         File dir1 = new File(".");
         try {
         
            boolean test = tryPath(dir1, "\\..\\Perkinites v2\\assets\\data\\") ||
               			tryPath(dir1, "\\..\\..\\p\\assets\\data\\") ||
               			tryPath(dir1, "\\assets\\data\\");
           
            try {
            
               JsonReader reader = new JsonReader(new FileReader(path + "enemies\\enemies.json"));
               
               reader.beginObject();
               reader.setLenient(true);
               String name = reader.nextName();
               reader.beginArray();
               while (reader.hasNext()) {
                  enemies.add(reader.nextString());
               }
               reader.endArray();
               reader.endObject();
               reader.close();
            }
               catch(FileNotFoundException e){
                  e.printStackTrace();
               }
         }
            catch(IOException e){
               e.printStackTrace();
            }
            
         return enemies.toArray(new String[enemies.size()]);
      }
      
   
   
   
      public static String[] readTilesetJSON() throws Exception{
         ArrayList<String> tilesets = new ArrayList<String>();
         Gson gson = new Gson();
         
         File dir1 = new File(".");
         try {
         
            boolean test = tryPath(dir1, "\\..\\Perkinites v2\\assets\\data\\") ||
               			tryPath(dir1, "\\..\\..\\p\\assets\\data\\") ||
               			tryPath(dir1, "\\assets\\data\\");
           
            try {
            
               JsonReader reader = new JsonReader(new FileReader(path + "maps\\tilesets.json"));
               
               reader.beginObject();
               reader.setLenient(true);
               String name = reader.nextName();
               reader.beginArray();
               while (reader.hasNext()) {
                  reader.beginObject();
                  while(reader.hasNext()){
                     name = reader.nextName();
                     if(name.equals("id") || name.equals("name")){
                        tilesets.add(reader.nextString());
                     }
                     else{
                        reader.skipValue();
                     }
                  }
                  reader.endObject();
               }
               reader.endArray();
               reader.endObject();
               reader.close();
            }
               catch(FileNotFoundException e){
                  e.printStackTrace();
               }
         }
            catch(IOException e){
               e.printStackTrace();
            }
            
         return tilesets.toArray(new String[tilesets.size()]);
      }
   
      public static ArrayList<Map> readMapArrayJSON(){
         ArrayList<Map> mapArray = new ArrayList<Map>();
         ArrayList<String> mapFiles = new ArrayList<String>();
         Gson gson = new GsonBuilder()
            .create();
      
         File dir1 = new File(".");
         try {
         
            boolean test = tryPath(dir1, "\\..\\Perkinites v2\\assets\\data\\") ||
               			tryPath(dir1, "\\..\\..\\p\\assets\\data\\") ||
               			tryPath(dir1, "\\assets\\data\\");
           
            try {
            
               JsonReader reader = new JsonReader(new FileReader(path + "maps\\maps.json"));
               
               reader.beginObject();
               reader.setLenient(true);
               String name = reader.nextName();
               reader.beginArray();
               while (reader.hasNext()) {
                  mapFiles.add(reader.nextString());
               }
               reader.endArray();
               reader.endObject();
               reader.close();
            
            
            } 
               catch (FileNotFoundException e) {
                  e.printStackTrace();
               }
               
         		
         		        
            for(int i = 0; i < mapFiles.size(); i++){
            
               System.out.println(path + "maps\\" + mapFiles.get(i) + ".json");
               BufferedReader br = new BufferedReader(
                  new FileReader(path + "maps\\" +  mapFiles.get(i) + ".json"));
            
            //convert the json string back to object
               Map map = gson.fromJson(br, Map.class);
               mapArray.add(map);
            }
         }
            catch(Exception e){
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