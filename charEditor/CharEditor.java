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
      public static JPanel charPanel = new JPanel();
      public static JPanel abilityPanel = new JPanel();
      public static JComboBox boxAbilities;
      
      public static Border blackline = BorderFactory.createLineBorder(Color.black);
      
   
      public static void main(String[] args) throws Exception {
      
         File cwd = new File("\\..");
         path = cwd.getCanonicalPath() + "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\data\\characters\\";
         //path = cwd.getCanonicalPath() + "\\..\\..\\p\\assets\\data\\characters\\";
      
         loadCharacterNames();
      
         showGUI();
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
         mainPanel.setLayout(new GridLayout(1,2));
       	
         frame.add(mainPanel);
      	       
        	// show character data
         showCharacter(chars[0]);
       
                     
         mainPanel.add(charPanel);
         mainPanel.add(abilityPanel);
         
         frame.setFocusable(true);
         frame.pack();
         frame.setSize(640, 480);
         frame.setVisible(true);
      }
      
      public static void createCharPanel(){
      //CHAR Panel with have a list panel, an edit panel, and a sub-edit panel
         charPanel.removeAll();
         charPanel.setLayout(new BorderLayout());
        // charPanel.setBorder(blackline);
        
         JPanel listPanel = new JPanel();
         listPanel.setBorder(blackline);
         listPanel.setLayout(new BorderLayout());
         DefaultListModel model = new DefaultListModel();
         final JList list = new JList(model);
         
      	
         for(int i = 0; i < chars.length; i++){
            model.add(i, chars[i]);
         }
         //list.setBorder(blackline);
         //set up drag and drop here
         // list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
         // list.setTransferHandler(new ArrayListTransferHandler());
         // list.setDragEnabled(true);
         
         listPanel.add(list, BorderLayout.LINE_START);
         listPanel.add(new JLabel("List of Perkinites :)"), BorderLayout.PAGE_START);
         listPanel.add(list, BorderLayout.CENTER);
         
         charPanel.add(listPanel, BorderLayout.LINE_START);
         JPanel bigEditPanel = new JPanel();
         bigEditPanel.setLayout(new BorderLayout());
         
         bigEditPanel.setBorder(blackline);
         
         JPanel editPanel = new JPanel();
         editPanel.setLayout(new GridLayout(8, 1, 1, 0));
         
      	//find the correct entry in the array for that particular char
      	//When you drag and drop, it doesn't re-organize the array list.
      	// Until we do that, we might need this for loop
         CharacterData cData = charData.get(0);
         for(int i = 0; i < charData.size(); i++){
         
            if(charData.get(i).name.equals(selectedCharacter)){
               cData = charData.get(i);
               list.setSelectedIndex(i);
               break;
            }
         }
         
      	//Fields and stuff are here.
            
         JLabel faceIcon = new JLabel(createFaceIcon(cData.id));
         JTextField nameField = new JTextField(cData.name,30);
         JTextField idField = new JTextField(cData.id,30);
         JTextField spriteField = new JTextField(cData.sprite,30);
         JTextField healthField = new JTextField(cData.health+"",30);
         JTextField defenseField = new JTextField(cData.defense+"",30);
         JTextField speedField = new JTextField(cData.speed+"",30);
         JTextField weaponField = new JTextField(cData.weapon,30);
         JButton applyButton = new JButton("Apply Changes");
         applyButton.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                  
                  }
               });  
      	
      	
      	
         bigEditPanel.add(faceIcon, BorderLayout.PAGE_START);
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
         bigEditPanel.add(applyButton, BorderLayout.PAGE_END);
         
         bigEditPanel.add(editPanel, BorderLayout.CENTER);
         charPanel.add(bigEditPanel, BorderLayout.CENTER);
         
         MouseListener mouseListener = 
            new MouseAdapter(){
               public void mouseClicked(MouseEvent e){
                  if(e.getButton() == 1 &&e.getClickCount() == 2){
                     int index = list.locationToIndex(e.getPoint());
                     showCharacter(chars[index]);
                  }
               }
            };
            
         list.addMouseListener(mouseListener);
      
      
         charPanel.revalidate();
         charPanel.repaint();
      }
      public static ImageIcon createFaceIcon(String id){
         ImageIcon image;
         String filename = "";
         File cwd = new File("\\..");
         try{
            filename = cwd.getCanonicalPath() + "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\icons\\Face Icon - " + id + ".png";
         }
            catch (Exception e){
               e.printStackTrace();
              
            }
         image = new ImageIcon(filename);
         if(image.getIconWidth() == -1){
            try{
               filename = cwd.getCanonicalPath() + "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\icons\\Icon - Access Denied.png";  
            }
               catch (Exception e){
                  e.printStackTrace();
               }
            image = new ImageIcon(filename);
         }
         return new ImageIcon(filename);
      }
      public static ImageIcon createAbilityIcon(String id){
         ImageIcon image;
         String filename = "";
         File cwd = new File("\\..");
         try{
            filename = cwd.getCanonicalPath() + "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\icons\\Icon - " + id + ".png";
         }
            catch (Exception e){
               e.printStackTrace();
              
            }
         image = new ImageIcon(filename);
         if(image.getIconWidth() == -1){
            try{
               filename = cwd.getCanonicalPath() + "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\icons\\Icon - Access Denied.png";  
            }
               catch (Exception e){
                  e.printStackTrace();
               }
            image = new ImageIcon(filename);
         }
         return new ImageIcon(filename);
      
      }
      public static void createAbilityPanel(){
         abilityPanel.removeAll();
         abilityPanel.setBorder(blackline);
         abilityPanel.setLayout(new BorderLayout());
         
         CharacterData cData = charData.get(0);
         for(int i = 0; i < charData.size(); i++){
            if(charData.get(i).name.equals(selectedCharacter)){
               cData = charData.get(i);
               break;
            }
         }
         
         
         DnDTabbedPane sub = new DnDTabbedPane();
         sub.setFont( new Font( "Dialog", Font.BOLD, 14 ) );
         sub.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
         for(int i = 0; i < cData.abilities.size(); i++){
            AbilityData aData = cData.abilities.get(i);
            sub.addTab(aData.name, createEditAbilityPanel(aData));
         
         }
         abilityPanel.add(sub, BorderLayout.CENTER);
         abilityPanel.revalidate();
         abilityPanel.repaint();
      }
      public static JPanel createEditAbilityPanel(AbilityData aData){
         JPanel editAbilityPanel = new JPanel();
         editAbilityPanel.setLayout(new BorderLayout());
         
         JPanel editPanel = new JPanel();
         editPanel.setLayout(new GridLayout(8, 2, 1, 0));
         
      	//Fields and stuff are here.
      	
      
         JLabel faceIcon = new JLabel(createAbilityIcon(aData.name));
         JTextField nameField = new JTextField(aData.name,20);
             	
         editAbilityPanel.add(faceIcon, BorderLayout.PAGE_START);
         editPanel.add(new JLabel("Name:"));
         editPanel.add(nameField);
      
      	
      	
         JButton applyButton = new JButton("Apply Changes");
         applyButton.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                  
                  }
               });  
         editAbilityPanel.add(editPanel, BorderLayout.CENTER);
         editAbilityPanel.add(applyButton, BorderLayout.PAGE_END);
      	
      
      
         return editAbilityPanel;
      }
   	
      public static void showCharacter(String name) {
         selectedCharacter = name;
         ArrayList<String> abilityNames = new ArrayList<String>();
         
      	//find the correct entry in the array for that particular char
         CharacterData cData = charData.get(0);
         for(int i = 0; i < charData.size(); i++){
         
            if(charData.get(i).name.equals(name)){
               cData = charData.get(i);
               break;
            }
         }
         
      	//reload the char/ability panels
         createCharPanel();
         createAbilityPanel();
         
      }
   	
      
      public static void save() {
      	
      }
      
   	//BLAH BLAH BLAH DRAG AND DROP STUFF
      public static class ArrayListTransferHandler extends TransferHandler {
         DataFlavor localArrayListFlavor, serialArrayListFlavor;
      
         String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
         + ";class=java.util.ArrayList";
      
         JList source = null;
      
         int[] indices = null;
      
         int addIndex = -1; //Location where items were added
      
         int addCount = 0; //Number of items added
      
         public ArrayListTransferHandler() {
            try {
               localArrayListFlavor = new DataFlavor(localArrayListType);
            } 
               catch (ClassNotFoundException e) {
                  System.out
                     .println("ArrayListTransferHandler: unable to create data flavor");
               }
            serialArrayListFlavor = new DataFlavor(ArrayList.class, "ArrayList");
         }
      
         public boolean importData(JComponent c, Transferable t) {
            JList target = null;
            ArrayList alist = null;
            if (!canImport(c, t.getTransferDataFlavors())) {
               return false;
            }
            try {
               target = (JList) c;
               if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
                  alist = (ArrayList) t.getTransferData(localArrayListFlavor);
               } 
               else if (hasSerialArrayListFlavor(t.getTransferDataFlavors())) {
                  alist = (ArrayList) t.getTransferData(serialArrayListFlavor);
               } 
               else {
                  return false;
               }
            } 
               catch (UnsupportedFlavorException ufe) {
                  System.out.println("importData: unsupported data flavor");
                  return false;
               } 
               catch (IOException ioe) {
                  System.out.println("importData: I/O exception");
                  return false;
               }
         
         //At this point we use the same code to retrieve the data
         //locally or serially.
         
         //We'll drop at the current selected index.
            int index = target.getSelectedIndex();
         
         //Prevent the user from dropping data back on itself.
         //For example, if the user is moving items #4,#5,#6 and #7 and
         //attempts to insert the items after item #5, this would
         //be problematic when removing the original items.
         //This is interpreted as dropping the same data on itself
         //and has no effect.
            if (source.equals(target)) {
               if (indices != null && index >= indices[0] - 1
               && index <= indices[indices.length - 1]) {
                  indices = null;
                  return true;
               }
            }
         
            DefaultListModel listModel = (DefaultListModel) target.getModel();
            int max = listModel.getSize();
            if (index < 0) {
               index = max;
            } 
            else {
               index++;
               if (index > max) {
                  index = max;
               }
            }
            addIndex = index;
            addCount = alist.size();
            
            for (int i = 0; i < alist.size(); i++) {
               listModel.add(index++, alist.get(i));
            }
            return true;
         }
      
         protected void exportDone(JComponent c, Transferable data, int action) {
            if ((action == MOVE) && (indices != null)) {
               DefaultListModel model = (DefaultListModel) source.getModel();
            
            //If we are moving items around in the same list, we
            //need to adjust the indices accordingly since those
            //after the insertion point have moved.
               if (addCount > 0) {
                  for (int i = 0; i < indices.length; i++) {
                     if (indices[i] > addIndex) {
                        indices[i] += addCount;
                     }
                  }
               }
               for (int i = indices.length - 1; i >= 0; i--)
                  model.remove(indices[i]);
                  
               ArrayList<String> charCopy = new ArrayList<String>();
               for(int i = 0; i < model.getSize(); i++){
                  charCopy.add((String)model.get(i));
               }
            
               chars = charCopy.toArray(new String[charCopy.size()]); 
            }
                   
            indices = null;
            addIndex = -1;
            addCount = 0;
         }
      
         private boolean hasLocalArrayListFlavor(DataFlavor[] flavors) {
            if (localArrayListFlavor == null) {
               return false;
            }
         
            for (int i = 0; i < flavors.length; i++) {
               if (flavors[i].equals(localArrayListFlavor)) {
                  return true;
               }
            }
            return false;
         }
      
         private boolean hasSerialArrayListFlavor(DataFlavor[] flavors) {
            if (serialArrayListFlavor == null) {
               return false;
            }
         
            for (int i = 0; i < flavors.length; i++) {
               if (flavors[i].equals(serialArrayListFlavor)) {
                  return true;
               }
            }
            return false;
         }
      
         public boolean canImport(JComponent c, DataFlavor[] flavors) {
            if (hasLocalArrayListFlavor(flavors)) {
               return true;
            }
            if (hasSerialArrayListFlavor(flavors)) {
               return true;
            }
            return false;
         }
      
         protected Transferable createTransferable(JComponent c) {
            if (c instanceof JList) {
               source = (JList) c;
               indices = source.getSelectedIndices();
               Object[] values = source.getSelectedValues();
               if (values == null || values.length == 0) {
                  return null;
               }
               ArrayList alist = new ArrayList(values.length);
               for (int i = 0; i < values.length; i++) {
                  Object o = values[i];
                  String str = o.toString();
                  if (str == null)
                     str = "";
                  alist.add(str);
               }
               return new ArrayListTransferable(alist);
            }
            return null;
         }
      
         public int getSourceActions(JComponent c) {
            return COPY_OR_MOVE;
         }
      
         public class ArrayListTransferable implements Transferable {
            ArrayList data;
         
            public ArrayListTransferable(ArrayList alist) {
               data = alist;
            }
         
            public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {
               if (!isDataFlavorSupported(flavor)) {
                  throw new UnsupportedFlavorException(flavor);
               }
               return data;
            }
         
            public  DataFlavor[] getTransferDataFlavors() {
               return new DataFlavor[] { localArrayListFlavor,
                     serialArrayListFlavor };
            }
         
            public boolean isDataFlavorSupported(DataFlavor flavor) {
               if (localArrayListFlavor.equals(flavor)) {
                  return true;
               }
               if (serialArrayListFlavor.equals(flavor)) {
                  return true;
               }
               return false;
            }
         }
      }
           
         
   }
