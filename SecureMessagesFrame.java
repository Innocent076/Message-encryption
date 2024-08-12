
package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import za.ac.tut.encryption.MessageEncryptor;


public class SecureMessagesFrame extends JFrame{
    private JPanel headingPnl;
    private JPanel plainMessagePnl;
    private JPanel encryptedMessagePnl;
    private JPanel bottomPnl;
    private JPanel mainPnl;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    
    private JMenuItem openFileMenuItem;
    private JMenuItem encryptMessageMenuItem;
    private JMenuItem saveEncryptedMessgMenuItem;
    private JMenuItem clearMenuItem;
    private JMenuItem exitMenuItem;
    
    //label
    private JLabel headingLabel;
    
    //create textArea
    private JTextArea plainMessageTxtArea;
    private JTextArea encryptedTxtArea;
    
    //create Scroll pane
    private JScrollPane plainMessageScrllPane;
    private JScrollPane encryptedMessageScrllPane;
    
    
    
    public SecureMessagesFrame() {
        setTitle("Secure Messages");
        setSize(600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setResizable(true);
        
        //create panels
        menuBar = new JMenuBar();
        
        mainPnl = new JPanel(new BorderLayout());
        
        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        plainMessagePnl = new JPanel(new GridLayout());
        plainMessagePnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Plain message"));
        
        encryptedMessagePnl = new JPanel(new GridLayout());
        encryptedMessagePnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Encrypted message"));
        
        bottomPnl = new JPanel(new FlowLayout());
        
        fileMenu = new JMenu("File");
        openFileMenuItem = new JMenuItem("Open file...");
        openFileMenuItem.addActionListener(new OpenFileListener());
        
        
        encryptMessageMenuItem = new JMenuItem("Encrypt message...");
        encryptMessageMenuItem.addActionListener(new EncryptMessageListener());
        
         saveEncryptedMessgMenuItem = new JMenuItem("Save encrypted message...");
         saveEncryptedMessgMenuItem.addActionListener(new SaveEncryptedMessageListener());
        
        clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(new ClearListener());
        
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitListener());
        
        //add file menu items
        fileMenu.add(openFileMenuItem);
        fileMenu.add(encryptMessageMenuItem);
        fileMenu.add(saveEncryptedMessgMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(clearMenuItem);
        fileMenu.add(exitMenuItem);
        
        //add the menues to the bar
        menuBar.add(fileMenu);
        
        //labels
        headingLabel = new JLabel("Message Encrypter");
        headingLabel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        headingLabel.setFont(new Font(Font.SERIF,Font.BOLD + Font.ITALIC,18));
        headingLabel.setForeground(Color.BLUE);
        
        //text area
        plainMessageTxtArea = new JTextArea(10, 30);
        encryptedTxtArea = new JTextArea(10,30);
        
        //create plain message Scrollpane
        plainMessageScrllPane = new JScrollPane(plainMessageTxtArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        //create encrypted message scroll pane
        encryptedMessageScrllPane = new JScrollPane(encryptedTxtArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        //add the menubar to the frame 
        setJMenuBar(menuBar);
        
        //add heading to its panel
        headingPnl.add(headingLabel,BorderLayout.CENTER);
        
        plainMessagePnl.add(plainMessageScrllPane);
        encryptedMessagePnl.add(encryptedMessageScrllPane);
        
        bottomPnl.add(plainMessagePnl);
        bottomPnl.add(encryptedMessagePnl);
        
        mainPnl.add(headingLabel,BorderLayout.NORTH);
        mainPnl.add(bottomPnl,BorderLayout.SOUTH);
        
        add(mainPnl);
        
        pack();
        
        setVisible(true);
    }
    
    private class OpenFileListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(SecureMessagesFrame.this);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try(BufferedReader br = new BufferedReader(new FileReader(file))){
                  plainMessageTxtArea.read(br,null);
                }catch(IOException ex){
                    JOptionPane.showMessageDialog(SecureMessagesFrame.this,"File Couldn't be opened");
                }
            }
        }
        
    }
    
    private class EncryptMessageListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String plainMessage = plainMessageTxtArea.getText();
            String encryptedMessage = MessageEncryptor.encryptMessage(plainMessage);
            encryptedTxtArea.setText(encryptedMessage);
        }
        
    }
    
    private class SaveEncryptedMessageListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           JFileChooser fileChooser = new JFileChooser();
           int result = fileChooser.showSaveDialog(SecureMessagesFrame.this);
           if(result == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
                   encryptedTxtArea.write(bw);
               }catch(IOException ex){
                   JOptionPane.showMessageDialog(SecureMessagesFrame.this,"File couldn't be saved");
               }
           }
        }
        
    }
    
    
    private class ClearListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            plainMessageTxtArea.setText("");
            encryptedTxtArea.setText("");
        }
        
    }
    
    
    private class ExitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }
    
    
    
    
    
    
}
