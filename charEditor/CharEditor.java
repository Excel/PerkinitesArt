   import dbData.*;
   	
   import java.lang.reflect.Type;

   import java.util.*;
   
   import java.io.BufferedReader;
   import java.io.File;
   import java.io.FileReader;
   import java.io.FileNotFoundException;
   import java.io.IOException;
 
   import java.awt.*;  
   import java.awt.event.*;
	
   import javax.swing.*; 
   import javax.swing.JList;
   import javax.swing.border.Border;

   import com.google.gson.*;
   import com.google.gson.stream.JsonReader;
   import com.google.gson.stream.JsonWriter;

   import java.awt.datatransfer.DataFlavor;
   import java.awt.datatransfer.Transferable;
   import java.awt.datatransfer.UnsupportedFlavorException;
   
   import java.io.FileWriter;  

   public class CharEditor {
   
      public static String path;
      public static String mode = "Character";
   
      public static String[] charIDs;
      public static String[] enemyIDs;
      
      public static ArrayList<CharacterData> charData = new ArrayList<CharacterData>();
      public static ArrayList<EnemyData> enemyData = new ArrayList<EnemyData>();
      public static JPanel mainPanel;
      public static JPanel listPanel;
      public static CharPanel charPanel;
      public static AbilityPanel abilityPanel;
     
      public static Border blackline = BorderFactory.createLineBorder(Color.black);
      
      public static void main(String[] args) throws Exception {
      
         AbilityData.init();
      
         File cwd = new File(".");
      	
         boolean test = tryPath(cwd, "\\..\\..\\Perkinites v2\\assets\\data\\") ||
            				tryPath(cwd, "\\..\\..\\p\\assets\\data\\") ||
            				tryPath(cwd, "\\assets\\data\\");
      	
         loadCharacters();
         loadEnemies();
      	
         showGUI();
      }
   	
   	/**
   	 * returns false if path passed in doesn't exist
   	 */
      public static boolean tryPath(File cwd, String p) throws Exception {
         path = cwd.getCanonicalPath() + p;
         File f = new File(path);
         System.out.println(path);
      	
         return f.exists();
      }
      private static class AbilityDataDeserializer implements JsonDeserializer<AbilityData> {
         public AbilityData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
         throws JsonParseException {
            Gson gson = new Gson();
            AbilityData aData = gson.fromJson(json, AbilityData.class);
            aData.fillInActualFields();
            Set keyset = aData.actualFields.keySet();
            Iterator it = keyset.iterator();
            while(it.hasNext()){
               String key = (String)it.next();
               JsonElement elem = json.getAsJsonObject().get(key);
               if(elem != null){
                  aData.actualFields.put(key, elem.toString());
               }
            }
            return aData;
            
         }
      }
      public static void loadCharacters() throws Exception {
         Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbilityData.class, new AbilityDataDeserializer())
            .create();
         ArrayList<String> tempIDs = new ArrayList<String>();
         JsonReader reader = new JsonReader(new FileReader(path + "characters\\characters.json"));
      
         reader.beginObject();
         reader.setLenient(true);
      
         String name = reader.nextName();
         reader.beginArray();
         while (reader.hasNext()) {
            String charName = reader.nextString();
          
            
            BufferedReader br = new BufferedReader(
                  new FileReader(path + "characters\\"+charName + ".json"));
            
            CharacterData character = gson.fromJson(br, CharacterData.class);
            charData.add(character);
            tempIDs.add(charName);
         }
         reader.endArray();
      
         reader.endObject();
         reader.close();
        
         charIDs = tempIDs.toArray(new String[tempIDs.size()]);
      }
      
      public static void loadEnemies() throws Exception {
         Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbilityData.class, new AbilityDataDeserializer())
            .create();
         ArrayList<String> tempIDs = new ArrayList<String>();
         JsonReader reader = new JsonReader(new FileReader(path + "enemies\\enemies.json"));
      
         reader.beginObject();
         reader.setLenient(true);
      
         String name = reader.nextName();
         reader.beginArray();
         while (reader.hasNext()) {
            String enemyName = reader.nextString();
            
            BufferedReader br = new BufferedReader(
                  new FileReader(path + "enemies\\" + enemyName + ".json"));
            
            EnemyData enemy = gson.fromJson(br, EnemyData.class);
            enemyData.add(enemy);
            tempIDs.add(enemyName);
         }
         reader.endArray();
      
         reader.endObject();
         reader.close();
        
         enemyIDs = tempIDs.toArray(new String[tempIDs.size()]);
      }	
   
      public static void showGUI() throws Exception {
      	
         JFrame frame = new JFrame("Perkinite Editor! Show that girl yo stuff homeboy ;)");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
         mainPanel = new JPanel();
         mainPanel.setBorder(blackline);
         mainPanel.setLayout(new BorderLayout());
       	
         frame.add(mainPanel);
      	       
        	// show character data
         charPanel = new CharPanel(charData, enemyData); 
         charPanel.updateCharPanel(0, mode); 
         
         abilityPanel = new AbilityPanel();
         
         abilityPanel.updateAbilityPanel(charData.get(0));
      
         listPanel = new JPanel();
         mainPanel.add(listPanel, BorderLayout.LINE_START);
         makeListPanel(0);
         JPanel subPanel = new JPanel();
         subPanel.setLayout(new GridLayout(1, 2));
         
         JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
         pane.setBorder(blackline);
         pane.add("Subject on File", (JPanel)charPanel);
         subPanel.add(pane);
         subPanel.add(abilityPanel);
         
         mainPanel.add(subPanel, BorderLayout.CENTER);
         frame.setFocusable(true);
         frame.pack();
         frame.setSize(720,480);
         frame.setVisible(true);
      }
      
      public static void makeListPanel(int index){
         mainPanel.remove(listPanel);
         listPanel = new JPanel();
         listPanel.setBorder(blackline);
         listPanel.setLayout(new BorderLayout());
      	
         DefaultListModel model = new DefaultListModel();
         final JList list = new JList(model);
         JPanel beginPanel = new JPanel();
         beginPanel.setLayout(new GridLayout(2,1));
         if (mode == "Character"){
            for(int i = 0; i < charIDs.length; i++){
               model.add(i, charIDs[i]);
            }
            beginPanel.add(new JLabel("List of Perkinites :)"));
         }
         else if (mode == "Enemy") {
            for(int i = 0; i < enemyIDs.length; i++){
               model.add(i, enemyIDs[i]);
            }
            beginPanel.add(new JLabel("List of Enemies :("));
         }
      
         JButton changeButton = new JButton("Change Mode");
         changeButton.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     if(mode == "Character")
                        mode = "Enemy";
                     else
                        mode = "Character";
                     makeListPanel(0);
                     charPanel.updateCharPanel(0, mode); 
                     if (mode == "Character") {
                        abilityPanel.updateAbilityPanel(charData.get(0));
                     } 
                     else if (mode == "Enemy") {
                        abilityPanel.updateAbilityPanel(enemyData.get(0));
                           
                     }
                  
                  
                  }
               });
      
         beginPanel.add(changeButton); 
         listPanel.add(beginPanel,BorderLayout.PAGE_START);
      	      
         listPanel.add(list, BorderLayout.LINE_START);
         listPanel.add(list, BorderLayout.CENTER);   
         list.setSelectedIndex(index);
      
         MouseListener mouseListener = 
            new MouseAdapter(){
               public void mouseClicked(MouseEvent e){
                  if(e.getButton() == 3){
                  
                     int index = list.locationToIndex(e.getPoint());
                     
                     JPopupMenu rcMenu = new JPopupMenu();
                     JMenuItem menuItem;
                  	
                     menuItem = new JMenuItem("Change ID");
                     menuItem.addActionListener(new ChangeIDListener(index));
                     rcMenu.add(menuItem);
                     menuItem = new JMenuItem("Create New Entry");
                     menuItem.addActionListener(new CreateNewListener());
                     rcMenu.add(menuItem);
                  	
                  
                     rcMenu.show(e.getComponent(),e.getX(), e.getY());
                     list.setSelectedIndex(index);
                  }
               
                  if(e.getButton() == 1 &&e.getClickCount() == 2){
                     int index = list.locationToIndex(e.getPoint());
                     charPanel.updateCharacter();
                     abilityPanel.updateAbilities();
                     charPanel.updateCharPanel(index, mode); 
                     if (mode == "Character") {
                        abilityPanel.updateAbilityPanel(charData.get(index));
                     }
                     else if (mode == "Enemy") {
                        abilityPanel.updateAbilityPanel(enemyData.get(index));
                     }
                  }
               }
            };     
         list.addMouseListener(mouseListener);
         JButton saveButton = new JButton("Save Changes");
         saveButton.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     try{
                        save();
                     }
                        catch(Exception ce){
                           ce.printStackTrace();
                        }
                  }
               });
      
         listPanel.add(saveButton, BorderLayout.PAGE_END);
         mainPanel.add(listPanel, BorderLayout.LINE_START);
         
         listPanel.revalidate();
         listPanel.repaint();
         
      	
      }
      
      public static class ChangeIDListener implements ActionListener{
         private int _index;
      
         public ChangeIDListener(int index){
            _index = index;
         }
         public void actionPerformed(ActionEvent e){
            JTextField idField;
            if (mode == "Character" ){
               idField = new JTextField(charIDs[_index], 20);
            }
            else if (mode == "Enemy") {
               idField = new JTextField(enemyIDs[_index], 20);
            }
            else {
               idField = new JTextField(charIDs[_index], 20);
            }
            JPanel myPanel = new JPanel();  
            myPanel.setLayout(new GridLayout(2, 1));
            myPanel.add(new JLabel("Char ID"));
            myPanel.add(idField);
                    	         	
            int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Properties - ID: " + _index, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
               if(!idField.getText().matches(".*\\w.*")){
                  idField.setText("BLANK");
               }
               if (mode == "Character") {
                  charIDs[_index] = idField.getText();
               } 
               else if (mode == "Enemy") {
                  enemyIDs[_index] = idField.getText();
               }
               makeListPanel(_index);
            }
         
         }
      }
      public static class CreateNewListener implements ActionListener{
      
         public void actionPerformed(ActionEvent e){
            JTextField idField = new JTextField("", 20);
            JPanel myPanel = new JPanel();  
            myPanel.setLayout(new GridLayout(2, 1));
            myPanel.add(new JLabel("Choose a Char ID"));
            myPanel.add(idField);
                    	         	
            int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Make a new Thing!", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
               if(!idField.getText().matches(".*\\w.*")){
                  idField.setText("BLANK");
               }
               
               ArrayList<String> temp = new ArrayList<String>();
               temp.add(idField.getText());
               UnitData cData = new UnitData();
               if (mode == "Character") {
                  temp = new ArrayList<String>(Arrays.asList(charIDs));
                  charIDs = temp.toArray(new String[temp.size()]);
                  cData = new CharacterData();
               }
               else if (mode == "Enemy") {
                  temp = new ArrayList<String>(Arrays.asList(enemyIDs));
                  charIDs = temp.toArray(new String[temp.size()]);
                  cData = new EnemyData();
               }  
            	
               cData.abilities = new ArrayList<AbilityData>();
               AbilityData ad = new AbilityData();
               ad.fillInActualFields();
               ad.type = "AttackDashSkillshot";
               AbilityData ad2 = new AbilityData();
               ad2.fillInActualFields();
               ad2.type = "AttackDashSkillshot";
            
               cData.abilities.add(ad);
               cData.abilities.add(ad2);
               if (mode == "Character") {
                    
                  charData.add((CharacterData)cData);
               } 
               else if (mode == "Enemy") {
                   
                  enemyData.add((EnemyData)cData);
               }  
            	
               makeListPanel(temp.size()-1);
               charPanel.updateCharacter();
               abilityPanel.updateAbilities();
               charPanel.updateCharPanel(temp.size()-1, mode); 
               if (mode == "Character") {
                  abilityPanel.updateAbilityPanel(charData.get(temp.size()-1));
               }
               else if (mode == "Enemy") {
                  abilityPanel.updateAbilityPanel(enemyData.get(temp.size()-1));
               }
              
               
            }
         
         }
      }
   
                	
      public static void save() throws Exception {
         charPanel.updateCharacter();
         abilityPanel.updateAbilities();
         Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(String.class, new StringSerializer())
            .registerTypeAdapter(AbilityData.class, new AbilityDataSerializer())
            .create();
            
         JsonWriter writer = new JsonWriter(new FileWriter(path + "characters\\characters.json"));
         writer.setLenient(true);
         writer.setIndent("\t");
               
         writer.beginObject();
         writer.name("characters");
         writer.beginArray();
         for(int i = 0; i < charIDs.length; i++){
            writer.value(charIDs[i]);
         }
            		
         writer.endArray();  
         writer.endObject();
         writer.close();                                
      
         for(int i = 0; i < charData.size(); i++){
            CharacterData cData = charData.get(i);
            
            String json = gson.toJson(cData);
            System.out.println(cData.health);
            FileWriter filewriter = new FileWriter(path + "characters\\"+charIDs[i]+  ".json");
            filewriter.write(json);
            filewriter.close();
            
         }
         
      	
         writer = new JsonWriter(new FileWriter(path + "enemies\\enemies.json"));
         writer.setLenient(true);
         writer.setIndent("\t");
               
         writer.beginObject();
         writer.name("enemies");
         writer.beginArray();
         for(int i = 0; i < enemyIDs.length; i++){
            writer.value(enemyIDs[i]);
         }
            		
         writer.endArray();  
         writer.endObject();
         writer.close();                                
      
         for(int i = 0; i < enemyData.size(); i++){
            EnemyData cData = enemyData.get(i);
            
            String json = gson.toJson(cData);
         
            FileWriter filewriter = new FileWriter(path + "enemies\\"+enemyIDs[i] + ".json");
            filewriter.write(json);
            filewriter.close();
            
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
      private static class AbilityDataSerializer implements JsonSerializer<AbilityData> {
         public JsonElement serialize(AbilityData src, Type typeOfSrc, JsonSerializationContext context) {
         
            JsonObject obj = new JsonObject();
            obj.addProperty("name", src.name);
            obj.addProperty("type", src.type);
            obj.addProperty("icon", src.icon);
            obj.addProperty("description", src.description);
            obj.addProperty("dmgBase", src.dmgBase);
            obj.addProperty("dmgRatio", src.dmgRatio);
            obj.addProperty("range", src.range);
            obj.addProperty("cd", src.cd);
            
            Set keyset = src.fields.get(src.type).keySet();
            Iterator it = keyset.iterator();
            Map<String, AbilityData.FieldTypes> f = AbilityData.fields.get(src.type);
                               
            while(it.hasNext()){
               String key = (String)it.next();
               AbilityData.FieldTypes value = f.get(key);
               if(value == AbilityData.FieldTypes.INT){
               
                  if(! (!src.actualFields.get(key).matches("^\\d*$") ||src.actualFields.get(key).length() == 0)){
                     int iValue = Integer.parseInt(src.actualFields.get(key));
                     if(iValue > 0)
                        obj.addProperty(key, iValue);
                  }
               }
               else if(value == AbilityData.FieldTypes.NUMBER){
                  if(! (!src.actualFields.get(key).matches("^\\d*$") ||src.actualFields.get(key).length() == 0)){
                     double dValue = Double.parseDouble(src.actualFields.get(key));
                     if(dValue > 0)
                        obj.addProperty(key, dValue);
                  }
               }
               else if(value == AbilityData.FieldTypes.BOOLEAN){
                  if(src.actualFields.get(key).matches("true") || src.actualFields.get(key).matches("false") ){
                     obj.addProperty(key, Boolean.parseBoolean(src.actualFields.get(key)));
                  }
               }
               else if(value == AbilityData.FieldTypes.STRING){
                  obj.addProperty(key, src.actualFields.get(key)); 
               }
            
               
            }
            return obj;
         }
      }  
   
   
   }
