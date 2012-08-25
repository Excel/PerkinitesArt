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
  
   public class AbilityPanel extends JPanel{
   
      public static ArrayList<AbilityData> abilityData;
      public static Border blackline = BorderFactory.createLineBorder(Color.black);
      
      public static String path = "";
      
      public static  DnDTabbedPane sub;
      
   
         
      public static ArrayList<EditAbilityPanel> editPanels = new ArrayList<EditAbilityPanel>();
         
      public AbilityPanel(){
         super();
      }
      public void updateAbilityPanel(CharacterData cData){
         removeAll();
         setBorder(blackline);
         setLayout(new BorderLayout());
      
         
         sub = new DnDTabbedPane();
         //sub.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
         sub.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
         abilityData = cData.abilities;
         editPanels = new ArrayList<EditAbilityPanel>();
         for(int i = 0; i < cData.abilities.size(); i++){
            AbilityData aData = cData.abilities.get(i);
            EditAbilityPanel eap = new EditAbilityPanel(aData);
            editPanels.add(eap);
            sub.addTab(aData.name, eap);
         
         }
         add(sub, BorderLayout.CENTER);
         revalidate();
         repaint();
      }
      public static class EditAbilityPanel extends JPanel{
         public JTextField nameField, iconField, dmgBaseField, dmgRatioField, rangeField, cdField;
         public JTextArea descriptionArea;
         public JComboBox typeBox;
         public JPanel addPanel;
         
         public ArrayList<JLabel> addLabels;
         public ArrayList<JTextField> addFields;
         
         public EditAbilityPanel(AbilityData aData){
            super();
            setLayout(new BorderLayout());
         
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new GridLayout(8, 2));
         
         //Fields and stuff are here.
         
            JLabel faceIcon = new JLabel(createAbilityIcon(aData.icon));
            nameField = new JTextField(aData.name,20);
            ArrayList<String> attacks = new ArrayList<String>(aData.fields.keySet());
            typeBox = new JComboBox(attacks.toArray());
            typeBox.setSelectedIndex(attacks.indexOf(aData.type));
            ChangeAddListener cal = new ChangeAddListener();
            cal.setAbilityData(aData);
            typeBox.addActionListener(cal);
            
            iconField = new JTextField(aData.icon, 30);
            descriptionArea = new JTextArea(aData.description);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            dmgBaseField = new JTextField(aData.dmgBase, 4);
            dmgRatioField = new JTextField(aData.dmgRatio, 4);
            rangeField = new JTextField(aData.range+"", 30);
            cdField = new JTextField(aData.cd+"", 30);
             	
            add(faceIcon, BorderLayout.PAGE_START);
            editPanel.add(new JLabel("Name:"));
            editPanel.add(nameField);
            
            // QuickUpdateListener qul = new QuickUpdateListener();
            // nameField.addActionListener(qul);
            
            editPanel.add(new JLabel("Type of Attack:"));
            editPanel.add(typeBox);
            editPanel.add(new JLabel("Icon:"));
            editPanel.add(iconField);
            editPanel.add(new JLabel("Description:"));
            editPanel.add(descriptionArea);
            editPanel.add(new JLabel("Damage Base:"));
            editPanel.add(dmgBaseField);
            editPanel.add(new JLabel("Damage Ratio:"));
            editPanel.add(dmgRatioField);
         
            editPanel.add(new JLabel("Range:"));
            editPanel.add(rangeField);
            editPanel.add(new JLabel("Cooldown:"));
            editPanel.add(cdField);
         
            addPanel = new JPanel();
            add(addPanel, BorderLayout.PAGE_END);
            updateAddPanel(aData);
            add(editPanel, BorderLayout.CENTER);
                       
         
         }
         
         public class ChangeAddListener implements ActionListener{
            private AbilityData aData;
            
            public void setAbilityData(AbilityData a){
               aData = a;
            }
            public void actionPerformed(ActionEvent e){
               JComboBox typeBox = (JComboBox)e.getSource();
               String type = (String)typeBox.getSelectedItem();
               aData.type = type;
               updateAddPanel(aData);
            }
         }
         // public class QuickUpdateListener implements ActionListener{
         // 
            // public void actionPerformed(ActionEvent e){
               // sub.setTitleAt();
            //    
            // }
         // }
         public void updateAddPanel(AbilityData aData){
         
            remove(addPanel);
            addPanel = new JPanel();
          
            addPanel.setBorder(blackline);
            
            addLabels = new ArrayList<JLabel>();
            addFields = new ArrayList<JTextField>();
            
            Map<String, AbilityData.FieldTypes> map = AbilityData.fields.get(aData.type);
         
            Set fields = map.keySet();
            Iterator it = fields.iterator();
            addPanel.setLayout(new GridLayout(fields.size(), 2));
            while(it.hasNext()){
               String key = (String)it.next();
               JLabel label = new JLabel(key);
               addLabels.add(label);
               addPanel.add(label);
               JTextField field = new JTextField(aData.actualFields.get(key));
               addFields.add(field);
               addPanel.add(field);
            }
            
            add(addPanel, BorderLayout.PAGE_END);  
            revalidate();
            repaint();
         }
      }
   
   	
      public static ImageIcon createAbilityIcon(String id){
         ImageIcon image;
         String filename = "";
         File cwd = new File("\\..");
            
         try{
            boolean test = tryPath(cwd, "\\Projects\\Games\\Flash Games\\Perkinites v2\\assets\\icons") ||
               			tryPath(cwd, "\\..\\..\\p\\assets\\icons") ||
               			tryPath(cwd, "\\assets\\icons");
         
            filename = path + "\\" + id;
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
      public void updateAbilities(){
         for(int i = 0; i < abilityData.size(); i++){
            AbilityData aData = abilityData.get(i);
            EditAbilityPanel editPanel = (EditAbilityPanel)sub.getComponentAt(i);
            aData.name = editPanel.nameField.getText();
            aData.icon = editPanel.iconField.getText();
            aData.description = editPanel.descriptionArea.getText();
            aData.dmgBase = editPanel.dmgBaseField.getText();
            aData.dmgRatio = editPanel.dmgRatioField.getText();
            aData.range = Integer.parseInt(editPanel.rangeField.getText());
            aData.cd = Integer.parseInt(editPanel.cdField.getText());
            ArrayList<String> attacks = new ArrayList<String>(aData.fields.keySet());
            aData.type = attacks.get(editPanel.typeBox.getSelectedIndex());
            
           
            for(int j = 0; j < editPanel.addLabels.size(); j++){
            
               JLabel addLabel = editPanel.addLabels.get(j);
               JTextField addField = editPanel.addFields.get(j);
               Map<String, AbilityData.FieldTypes> f = aData.fields.get(aData.type);
               AbilityData.FieldTypes value = f.get(addLabel.getText());
               if(value == AbilityData.FieldTypes.INT || value == AbilityData.FieldTypes.NUMBER){
                  if(! (!addField.getText().matches("^\\d*$") || addField.getText().length() == 0)){
                     aData.actualFields.put(editPanel.addLabels.get(j).getText(), 
                        editPanel.addFields.get(j).getText()); 
                  }
               }
               else if(value == AbilityData.FieldTypes.BOOLEAN){
                  if(addField.getText().matches("true") || addField.getText().matches("false") ){
                     aData.actualFields.put(editPanel.addLabels.get(j).getText(), 
                        editPanel.addFields.get(j).getText()); 
                  }
               }
               else if(value == AbilityData.FieldTypes.STRING){
                  aData.actualFields.put(editPanel.addLabels.get(j).getText(), 
                        editPanel.addFields.get(j).getText());   
               }
                    
                  
            
                
            }
         }
      }
   
      public static boolean tryPath(File cwd, String p) throws Exception {
         path = cwd.getCanonicalPath() + p;
         File f = new File(path);
      	
         return f.exists();
      }
      
   
   
   }