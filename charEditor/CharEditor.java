   import dbData.*;
   
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

   import java.awt.datatransfer.DataFlavor;
   import java.awt.datatransfer.Transferable;
   import java.awt.datatransfer.UnsupportedFlavorException;
   

   public class CharEditor {
   
      public static String path;
   
      public static String[] chars;
      
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
   
      public static void loadCharacterNames() throws Exception {
         Gson gson = new Gson();
         ArrayList<String> temp = new ArrayList<String>();
      
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
            temp.add(character.name);
         }
         reader.endArray();
      
         reader.endObject();
         reader.close();
         
         chars = temp.toArray(new String[temp.size()]);
      }
   
      public static void showGUI() throws Exception {
      	
         JFrame frame = new JFrame("Perkinite Editor! Show that girl yo stuff homeboy ;)");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
         mainPanel = new JPanel();
         mainPanel.setBorder(blackline);
         mainPanel.setLayout(new BorderLayout());
       	
         frame.add(mainPanel);
      	       
        	// show character data
         makeListPanel();
                
         charPanel = new CharPanel(charData, chars); 
         charPanel.updateCharPanel(chars[0]); 
         
         abilityPanel = new AbilityPanel();
         abilityPanel.updateAbilityPanel(charData.get(0));
      
         mainPanel.add(listPanel, BorderLayout.LINE_START);
         JPanel subPanel = new JPanel();
         subPanel.setLayout(new GridLayout(1, 2));
         subPanel.add(charPanel);
         subPanel.add(abilityPanel);
         
         mainPanel.add(subPanel, BorderLayout.CENTER);
         frame.setFocusable(true);
         frame.pack();
         frame.setSize(640, 480);
         frame.setVisible(true);
      }
      
      public static void makeListPanel(){
      
         listPanel = new JPanel();
         listPanel.setBorder(blackline);
         listPanel.setLayout(new BorderLayout());
      	
         DefaultListModel model = new DefaultListModel();
         final JList list = new JList(model);
         
      	
         for(int i = 0; i < chars.length; i++){
            model.add(i, chars[i]);
         }
      	      
         
         listPanel.add(list, BorderLayout.LINE_START);
         listPanel.add(new JLabel("List of Perkinites :)"), BorderLayout.PAGE_START);
         listPanel.add(list, BorderLayout.CENTER);
         
         list.setSelectedIndex(Arrays.asList(chars).indexOf(selectedCharacter));
         
         MouseListener mouseListener = 
            new MouseAdapter(){
               public void mouseClicked(MouseEvent e){
                  if(e.getButton() == 1 &&e.getClickCount() == 2){
                     int index = list.locationToIndex(e.getPoint());
                     charPanel.updateCharacter();
                    abilityPanel.updateAbilities();
                     charPanel.updateCharPanel(chars[index]); 
                     abilityPanel.updateAbilityPanel(charData.get(index));
                  }
               }
            };     
         list.addMouseListener(mouseListener);
         
         JButton saveButton = new JButton("Save Changes");
         saveButton.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     save();
                  }
               });
         listPanel.add(saveButton, BorderLayout.PAGE_END);
         
      	
      }
                	
      public static void save() {
      	
      }  
   }
