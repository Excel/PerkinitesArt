   import java.util.ArrayList;
   import java.io.File;
   import java.io.FileReader;
   import java.io.FileNotFoundException;
   import java.io.IOException;
 
   import java.awt.*;  
	
   import javax.swing.*;
   import javax.swing.JList;
   import javax.swing.border.Border;

   import com.google.gson.*;
   import com.google.gson.stream.JsonReader;
   

   public class CharEditor {
   
      public static String path;
   
      public static ArrayList<String> chars;
      
      public static JPanel mainPanel;
      public static JComboBox boxChar;
   
      public static void main(String[] args) throws Exception {
      
         File cwd = new File(".");
         path = cwd.getCanonicalPath() + "\\..\\..\\p\\assets\\data\\characters\\";
      
         loadCharacterNames();
      
         showGUI();
      }
   
      public static void loadCharacterNames() throws Exception {
      
         chars = new ArrayList<String>();
      
         JsonReader reader = new JsonReader(new FileReader(path + "characters.json"));
      
         reader.beginObject();
         reader.setLenient(true);
      
         String name = reader.nextName();
         reader.beginArray();
         while (reader.hasNext()) {
            chars.add(reader.nextString());
         }
         reader.endArray();
      
         reader.endObject();
         reader.close();
      }
   
      public static void showGUI() {
      	
         JFrame frame = new JFrame("Perkinites Editor! :)");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
         Border blackline = BorderFactory.createLineBorder(Color.black);
         mainPanel = new JPanel();
         mainPanel.setBorder(blackline);
       	
         frame.add(mainPanel);
      	
         showInnerGUI();
      	
         frame.setFocusable(true);
         frame.pack();
         frame.setSize(640, 480);
         frame.setVisible(true);
      }
      
      public static void showInnerGUI() {
         boxChars = new JComboBox(chars.toArray());
         boxChars.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     CharEditor.changeCharacter();
                  }
               });
        
         mainPanel.add(boxChars);
      }
      
      public static void changeCharacter() {
         String petName = (String)boxChar.getSelectedItem();
      }
      
   	public static void save() {
   		
   	}
   }
