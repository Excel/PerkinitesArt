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
   
      public static String[] charIDs;
      
      public static ArrayList<CharacterData> charData = new ArrayList<CharacterData>();
      public static String selectedCharacter = "";   
      public static JPanel mainPanel;
      public static JPanel listPanel;
      public static CharPanel charPanel;
      public static AbilityPanel abilityPanel;
      public static JComboBox boxAbilities;
      
   	
      
      public static Border blackline = BorderFactory.createLineBorder(Color.black);
      
   
      public static void main(String[] args) throws Exception {
      
         AbilityData.init();
      
         File cwd = new File("\\..");
      	
         boolean test = tryPath(cwd, "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\data\\characters\\") ||
            				tryPath(cwd, "\\..\\..\\p\\assets\\data\\characters\\") ||
            				tryPath(cwd, "\\assets\\data\\characters\\");
      	
         loadCharacterNames();
      	
         showGUI();
      }
   	
   	/**
   	 * returns false if path passed in doesn't exist
   	 */
      public static boolean tryPath(File cwd, String p) throws Exception {
         path = cwd.getCanonicalPath() + p;
         File f = new File(path);
      	
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
      public static void loadCharacterNames() throws Exception {
         Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbilityData.class, new AbilityDataDeserializer())
            .create();
         ArrayList<String> tempNames = new ArrayList<String>();
         ArrayList<String> tempIDs = new ArrayList<String>();
         JsonReader reader = new JsonReader(new FileReader(path + "characters.json"));
      
         reader.beginObject();
         reader.setLenient(true);
      
         String name = reader.nextName();
         reader.beginArray();
         while (reader.hasNext()) {
            String charName = reader.nextString();
          
            
            BufferedReader br = new BufferedReader(
                  new FileReader(path + charName + ".json"));
            
            CharacterData character = gson.fromJson(br, CharacterData.class);
            charData.add(character);
            tempNames.add(character.name);
            tempIDs.add(charName);
         }
         reader.endArray();
      
         reader.endObject();
         reader.close();
        
         charIDs = tempIDs.toArray(new String[tempIDs.size()]);
      }
   
      public static void showGUI() throws Exception {
      	
         JFrame frame = new JFrame("Perkinite Editor! Show that girl yo stuff homeboy ;)");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
         mainPanel = new JPanel();
         mainPanel.setBorder(blackline);
         mainPanel.setLayout(new BorderLayout());
       	
         frame.add(mainPanel);
      	       
        	// show character data
         selectedCharacter = charData.get(0).name;
        
                
         charPanel = new CharPanel(charData); 
         charPanel.updateCharPanel(0); 
         
         abilityPanel = new AbilityPanel();
         abilityPanel.updateAbilityPanel(charData.get(0));
      
         listPanel = new JPanel();
         mainPanel.add(listPanel, BorderLayout.LINE_START);
         makeListPanel(0);
         JPanel subPanel = new JPanel();
         subPanel.setLayout(new GridLayout(1, 2));
         
         JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
         pane.setBorder(blackline);
         pane.add("Perkinite on File", (JPanel)charPanel);
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
         
      	
         for(int i = 0; i < charIDs.length; i++){
            model.add(i, charIDs[i]);
         }
      	      
         
         listPanel.add(list, BorderLayout.LINE_START);
         listPanel.add(new JLabel("List of Perkinites :)"), BorderLayout.PAGE_START);
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
                     menuItem = new JMenuItem("Create New Character");
                     menuItem.addActionListener(new CreateNewListener());
                     rcMenu.add(menuItem);
                  	
                  
                     rcMenu.show(e.getComponent(),e.getX(), e.getY());
                     list.setSelectedIndex(index);
                  }
               
                  if(e.getButton() == 1 &&e.getClickCount() == 2){
                     int index = list.locationToIndex(e.getPoint());
                     charPanel.updateCharacter();
                     abilityPanel.updateAbilities();
                     charPanel.updateCharPanel(index); 
                     abilityPanel.updateAbilityPanel(charData.get(index));
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
            JTextField idField = new JTextField(charIDs[_index], 20);
            JPanel myPanel = new JPanel();  
            myPanel.setLayout(new GridLayout(2, 1));
            myPanel.add(new JLabel("Char ID"));
            myPanel.add(idField);
                    	         	
            int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Character Properties - ID: " + _index, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
               if(!idField.getText().matches(".*\\w.*")){
                  idField.setText("BLANK");
               }
               charIDs[_index] = idField.getText();
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
               "Make a new Perkinite!", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
               if(!idField.getText().matches(".*\\w.*")){
                  idField.setText("BLANK");
               }
               
               ArrayList<String> temp = new ArrayList<String>(Arrays.asList(charIDs));
               temp.add(idField.getText());
               charIDs = temp.toArray(new String[temp.size()]);
               CharacterData cData = new CharacterData("");
               cData.abilities = new ArrayList<AbilityData>();
               AbilityData ad = new AbilityData();
               ad.fillInActualFields();
               ad.type = "AttackDashSkillshot";
               AbilityData ad2 = new AbilityData();
               ad2.fillInActualFields();
               ad2.type = "AttackDashSkillshot";
            
               cData.abilities.add(ad);
               cData.abilities.add(ad2);
               charData.add(cData);
               makeListPanel(temp.size()-1);
               charPanel.updateCharacter();
               abilityPanel.updateAbilities();
               charPanel.updateCharPanel(temp.size()-1); 
               abilityPanel.updateAbilityPanel(charData.get(temp.size()-1));
               
            }
         
         }
      }
   
                	
      public static void save() throws Exception {
         Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(AbilityData.class, new AbilityDataSerializer())
            .create();
            
         JsonWriter writer = new JsonWriter(new FileWriter(path + "characters.json"));
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
      
         for(int i = 0; i < 1; i++){
            CharacterData cData = charData.get(i);
            
            String json = gson.toJson(cData);
         
            FileWriter filewriter = new FileWriter(path + charIDs[i] + "_c.json");
            filewriter.write(json);
            filewriter.close();
            
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
            while(it.hasNext()){
               String key = (String)it.next();
               obj.addProperty(key, src.actualFields.get(key));
            }
            return obj;
         }
      }  
   
   
   }
