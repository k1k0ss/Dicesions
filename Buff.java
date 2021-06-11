import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class Buff extends Node implements ActionListener
{
    private JDialog buff;
    private Dimension dimensions;
    private JTextArea[] info = new JTextArea[3];
    private String[] name = new String[3];
    private JButton dicebutton;
    private JButton[] player_choice = new JButton[3]; //keep the 3 random possible buff elements
    private ImageIcon backgroundImage;
    private JLabel label;
    private BuffElement chosen_element; //keep the buff element that the player won
    
    
    //buff object of buff node
    public Buff()
    {
        buff = new JDialog();
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        buff.setLocation(dimensions.width/4, dimensions.height/4);
        buff.setSize(dimensions.width/2, dimensions.height/2);
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        
        //get 3 random buff elements of the list
        for(int i=1; i<=3; i++)
        {
            name[i-1] = CatalogBuff.getElementName();
            info[i-1] = new JTextArea();
            info[i-1].append("Choice " + i + "\n\n\n" + name[i-1] + "\n\n\n" + "Increase: " + CatalogBuff.getElementKind(name[i-1]) + " by " + CatalogBuff.getElementValue(name[i-1]));         
            info[i-1].setBounds((i-1)*(buff.getWidth()/3),10,buff.getWidth()/3,buff.getHeight()/3);
            Border blackline = BorderFactory.createLineBorder(Color.BLACK);
            info[i-1].setBorder(blackline);
            info[i-1].setOpaque(false);
            info[i-1].setEditable(false);

        }
        
        dicebutton = new JButton("ROLL");
        dicebutton.setFocusPainted(false);
        dicebutton.setBounds(buff.getWidth()/3, buff.getHeight()/2, buff.getWidth()/3, buff.getHeight()/2);
        dicebutton.setOpaque(false);
        dicebutton.setContentAreaFilled(false);
        dicebutton.addActionListener(this);
        
        buff.add(info[0]);
        buff.add(info[1]);
        buff.add(info[2]);
        buff.add(dicebutton);
        buff.add(label);
        
        buff.setModal(true);
        buff.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        buff.setVisible(true);
        
        
    }
    
    //buff object after every fight the player won
    public Buff(Player p)
    {
        buff = new JDialog();
        buff.setTitle(p.player_name + " choose your reward");
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        buff.setLocation(dimensions.width/4, dimensions.height/4);
        buff.setSize(dimensions.width/2, dimensions.height/2);
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        
         //get 3 random elements
        for(int i=1; i<=3; i++)
        {
            name[i-1] = CatalogBuff.getElementName();
            player_choice[i-1] = new JButton(name[i-1] );         
            player_choice[i-1].setBounds((i-1)*(buff.getWidth()/3),10,buff.getWidth()/3,buff.getHeight()/3);
            player_choice[i-1].setOpaque(false);
            player_choice[i-1].setFocusPainted(false);
            player_choice[i-1].setContentAreaFilled(false);
            
            player_choice[i-1].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    String text = ae.getActionCommand(); //get the name of the element of this choice
                    chosen_element = CatalogBuff.getElement(text);                  
                    buff.dispose();
                }
            });
        }
        
        buff.add(player_choice[0]);
        buff.add(player_choice[1]);
        buff.add(player_choice[2]);
        buff.add(label);
        
        buff.setModal(true);
        buff.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        buff.setVisible(true);
    }
    
    //get one of the three elements based on dice result
    public void actionPerformed(ActionEvent e) {
        int choice = new MapDice().roll();
        ImageIcon image = new ImageIcon(getClass().getResource("result"+choice+".png"));
        
        if(choice == 1 || choice == 2)
        {
            chosen_element = CatalogBuff.getElement(name[0]);
            JOptionPane.showMessageDialog(null,name[0],"Congratulations!!", JOptionPane.INFORMATION_MESSAGE,image);
        }
        else if(choice == 3 || choice == 4)
        {
            chosen_element = CatalogBuff.getElement(name[1]);
            JOptionPane.showMessageDialog(null,name[1],"Congratulations!!", JOptionPane.INFORMATION_MESSAGE,image);
        }
        else
        {
            chosen_element = CatalogBuff.getElement(name[2]);
            JOptionPane.showMessageDialog(null,name[2],"Congratulations!!", JOptionPane.INFORMATION_MESSAGE,image);
        }
        
        buff.dispose();
    }
    
    //return the element
    public BuffElement getElement(){
        return this.chosen_element;
    }
   
}
