import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.util.*;

public class Shop extends JFrame implements ActionListener
{
    private JDialog shop;
    private Dimension dimensions;
    private ImageIcon backgroundImage;
    private JLabel label;
    private JButton search;
    private JButton[] elements = new JButton[9];
    private JTextField search_element;
    
    private Player player;
    private boolean[] flags= new boolean[9];
    
    public Shop(Player p)
    {
        shop = new JDialog();
        shop.setTitle("SHOP");
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        shop.setLocation(dimensions.width/4, dimensions.height/4);
        shop.setSize(dimensions.width/2, dimensions.height/2);
        
        shop.setLayout(new GridLayout(2,5,40,40));
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        
        player = p;
        for(int i = 0; i < 9; i++)
        {
            flags[i] = CatalogBuff.getElementByNumber(i).getAvailable(); //array that keeps the positions of buffs, that have been bought by other players. if flags[1] = false, the buff in position 1 is unavailable
        }
        
        search = new JButton("SEARCH");
        search.setBackground(Color.BLACK);
        search.setForeground(Color.WHITE);
        search.setFocusPainted(false);
        search.addActionListener(this);
        
        search_element = new JTextField();
        search_element.setBounds(shop.getWidth()/4, shop.getHeight()/4, shop.getWidth()/2, 50);
        search_element.setOpaque(false);
        search_element.setHorizontalAlignment(JTextField.CENTER);
        search_element.setForeground(Color.BLACK);
        search_element.setFont(search_element.getFont().deriveFont(Font.BOLD, 14f));
        search_element.getCaret().setVisible(false);
        
        search_element.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int count = 0;
                String text = search_element.getText();
                search_element.setText("");
                
                //if we have the right kind of elements, print the available ones
                if(text.equals("HP") || text.equals("Damage") || text.equals("Critical"))
                {
                    BuffElement need = CatalogBuff.getElementB(text);
                    
                    shop.dispose();
                    shop = new JDialog();
                    
                    dimensions = Toolkit.getDefaultToolkit().getScreenSize();
                    shop.setLocation(dimensions.width/4, dimensions.height/4);
                    shop.setSize(dimensions.width/2, dimensions.height/2);
                    shop.setTitle(text + " boosts!!");
                    if(Objects.isNull(need)) //if there is no available element of this kind
                    {
                        JOptionPane.showMessageDialog(null, "There is no element of this category left!!!","SORRY!!!", JOptionPane.ERROR_MESSAGE);
                        shop.dispose();
                    }
                    else
                    {
                     while(!Objects.isNull(need))
                     {
                    
                        System.out.println(Objects.isNull(need));
                        elements[count] = new JButton(need.getName());
                        elements[count].setForeground(Color.BLACK);
                        elements[count].setBackground(Color.GRAY);
                        elements[count].setOpaque(false);
                        elements[count].setContentAreaFilled(false);
                        elements[count].setFocusPainted(false);
                        
                        elements[count].addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent event){
                                BuffElement need = CatalogBuff.getElement(event.getActionCommand());
                                if(need.getCost() <= player.coins) //if player have enough coins, give him the buff and decrease his coins by buff's price. Make the buff unavailable for other players
                                {
                                    player.increaseStat(need);
                                    player.coins = player.coins - need.getCost();
                                    need.setAvailable();
                                    flags[CatalogBuff.getPosition(need.getName())] = false;
                                    shop.dispose();
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "THIS ELEMENT COST " + need.getCost() +"!!! YOU DON'T HAVE ENOUGH COINS!!!","YOU HAVE " + player.coins + " COINS!!!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                        shop.add(elements[count]);
                        
                        count++;
                        need.setAvailable();
                        need = CatalogBuff.getElementB(text);
                        
                     }
                     for(int i = 0; i < 9; i++)
                     {
                        if((!CatalogBuff.getElementByNumber(i).getAvailable()) && flags[i] == true)
                        {
                            CatalogBuff.getElementByNumber(i).setAvailable(); //keep unavailable only the buffs that has been bought
                        }
                     }                  
                    }
                    shop.setLayout(new GridLayout());
                    shop.setModal(true);
                    shop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    shop.setVisible(true);
                } 
                else
                {
                    JOptionPane.showMessageDialog(null, "Possible search: HP, Damage or Critical boosts!!!","There is no such element!!!", JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
        
        });
        
        for(int i=0; i<9; i++)
        {
            elements[i] = new JButton(CatalogBuff.getElementByNumber(i).getName());
            elements[i].setForeground(Color.BLACK);
            elements[i].setBackground(Color.GRAY);
            elements[i].setOpaque(false);
            elements[i].setContentAreaFilled(false);
            elements[i].setFocusPainted(false);
            if(CatalogBuff.getElementByNumber(i).getAvailable()) //set the button's element disabled
            {
                elements[i].setEnabled(true);
            }
            else
            {
                elements[i].setEnabled(false);
            }
            //buy element without search, if the coins are enough                
            elements[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    BuffElement need = CatalogBuff.getElement(ae.getActionCommand());
                    if(need.getCost() <= player.coins)
                    {
                        player.increaseStat(need);
                        player.coins = player.coins - need.getCost();
                        need.setAvailable();
                        flags[CatalogBuff.getPosition(need.getName())] = false;
                        shop.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "THIS ELEMENT COST " + need.getCost() +"!!! YOU DON'T HAVE ENOUGH COINS!!!","YOU HAVE " + player.coins + " COINS!!!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            
            });
                
                
            shop.add(elements[i]);
            
        }
        
        shop.add(search);

        shop.setModal(true);
        shop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shop.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        shop.dispose();
        shop = new JDialog();
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        shop.setLocation(dimensions.width/4, dimensions.height/4);
        shop.setSize(dimensions.width/2, dimensions.height/2);
        
        shop.setTitle("ENTER THE KIND OF ELEMENT YOU NEED");
        
        shop.add(search_element);
        shop.add(label);
        
        
        shop.setModal(true);
        shop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shop.setVisible(true);
    }
}
