   import dbData.*;
   
   import java.util.*;
   
   import javax.swing.*;
   import javax.swing.border.Border;
   
   import java.awt.*;  
   import java.awt.event.*;
   
   import java.io.File;
   import java.io.FileReader;
   import java.io.FileNotFoundException;
   import java.io.IOException;

   public class CharPanel extends JPanel{
   
      public static JPanel listPanel, bigEditPanel, editPanel;
      public static Border blackline = BorderFactory.createLineBorder(Color.black);
      public static ArrayList<CharacterData> charData;
      public static String[] chars;
      
      public static JTextField nameField, idField, spriteField,
       healthField, defenseField, speedField, weaponField;
   
      
      public static String path = "";
      
      int selectedCharIndex = -1;
      
   
      
      public CharPanel(ArrayList<CharacterData> cData, String[] cs){
         super();
         charData = cData;
         chars = cs;
      }
      public void updateCharPanel(String selectedCharacter){
      //CHAR Panel with have a list panel, an edit panel, and a sub-edit panel
         removeAll();
         setLayout(new BorderLayout());
        //setBorder(blackline);
        
      
         bigEditPanel = new JPanel();
         bigEditPanel.setLayout(new BorderLayout());
         
         bigEditPanel.setBorder(blackline);
         
         editPanel = new JPanel();
         editPanel.setLayout(new GridLayout(8, 1, 1, 0));
         
         selectedCharIndex = Arrays.asList(chars).indexOf(selectedCharacter);
         CharacterData cData = charData.get(selectedCharIndex);
        
                
      	//Fields and stuff are here.
      	
         JLabel faceIcon = new JLabel(createFaceIcon(cData.id));
         
         bigEditPanel.add(faceIcon, BorderLayout.PAGE_START);
      	
         nameField = new JTextField(cData.name,20);
         idField = new JTextField(cData.id,20);
         spriteField = new JTextField(cData.sprite,20);
         healthField = new JTextField(cData.health+"",20);
         defenseField = new JTextField(cData.defense+"",20);
         speedField = new JTextField(cData.speed+"",20);
         weaponField = new JTextField(cData.weapon,20);
      
         editPanel.add(new JLabel("Name:"));
         editPanel.add(nameField);
         editPanel.add(new JLabel("ID:"));
         editPanel.add(idField);
         editPanel.add(new JLabel("Sprite:"));
         editPanel.add(spriteField);
         editPanel.add(new JLabel("Health:"));
         editPanel.add(healthField);
         editPanel.add(new JLabel("Defense:"));
         editPanel.add(defenseField);
         editPanel.add(new JLabel("Speed:"));
         editPanel.add(speedField);
         editPanel.add(new JLabel("Weapon:"));
         editPanel.add(weaponField);
         
         bigEditPanel.add(editPanel, BorderLayout.CENTER);
         add(bigEditPanel, BorderLayout.CENTER);
      
      
      
         revalidate();
         repaint();
      }
   	
      public static ImageIcon createFaceIcon(String id){
         ImageIcon image;
         String filename = "";
         File cwd = new File("\\..");
      
         try{
            boolean test = tryPath(cwd, "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\icons") ||
               			tryPath(cwd, "\\..\\..\\p\\assets\\icons") ||
               			tryPath(cwd, "\\assets\\icons");
            filename = path + "\\Face Icon - " + id + ".png";
         }
            catch (Exception e){
               e.printStackTrace();
              
            }
         image = new ImageIcon(filename);
         if(image.getIconWidth() == -1){
            try{
               filename = path + "\\Icon - Access Denied.png";  
            }
               catch (Exception e){
                  e.printStackTrace();
               }
            image = new ImageIcon(filename);
         }
         return new ImageIcon(filename);
      }
      
      public void updateCharacter(){
         CharacterData cData = charData.get(selectedCharIndex);
         cData.name = nameField.getText();
         cData.id =   idField.getText();
         cData.sprite = spriteField.getText();
         cData.health = Integer.parseInt(healthField.getText());
         cData.defense= Integer.parseInt(defenseField.getText());
         cData.speed = Integer.parseInt(speedField.getText());
         cData.weapon = weaponField.getText();
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
   
   
