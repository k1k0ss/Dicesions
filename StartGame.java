import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.util.*;

public class StartGame extends JFrame implements ActionListener
{
    //Initialization of variables
    
    private JButton local, online, clash_warrior, clash_sorcerer, clash_archer;
    private JTextField player_Name;
    private JTextField number;
    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private ImageIcon backgroundImage;
    private JLabel label, clash;
    private String possibleName;
    private ArrayList <String> names;
    private ArrayList <Player> players;
    private JFrame game;

    public StartGame()
    {
        //Start game. Choose "Local" or "Online"
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        game = new JFrame();
        local = new JButton("Local Game"); //Option local game (button).
        local.setBounds(700,350,200,50);
        online = new JButton("Online Game"); //Option online game (button)
        online.setBounds(700,450,200,50);
        label.add(local);
        label.add(online);
        
        //Create the screen. (frame)
        
        game.add(label);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        local.addActionListener(this); 
        game.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
        game.setVisible(true);
        
    }
    
    public void NumberOfPlayers(){
        game.getContentPane().removeAll();
        label.removeAll();
        game.repaint(); //Clear the screen and rebuild it
        
        players = new ArrayList<Player>(); //List of players
        names = new ArrayList<String>();   //List of the names of the player
        
        player_Name = new JTextField("Player name..."); //Field to type player's name
        player_Name.setBounds(500,350,500,50);
        player_Name.setHorizontalAlignment(JTextField.CENTER);
        player_Name.setForeground(Color.BLACK);
        player_Name.setOpaque(false);
        player_Name.setFont(player_Name.getFont().deriveFont(Font.BOLD, 14f));
        player_Name.getCaret().setVisible(false);
        
        clash = new JLabel("PICK YOUR CLASS");
        clash.setBounds(650,50,200,50);
        
        clash_warrior = new JButton("WARRIOR");    //Choose warrior clash
        clash_warrior.setBounds(150,600,200,200);
        clash_warrior.setOpaque(false);
        clash_warrior.setContentAreaFilled(false);
        clash_warrior.setEnabled(false);
        
        clash_sorcerer = new JButton("SORCERER");   //Choose sorcerer clash
        clash_sorcerer.setBounds(650,600,200,200);
        clash_sorcerer.setOpaque(false);
        clash_sorcerer.setContentAreaFilled(false);
        clash_sorcerer.setEnabled(false);
        
        clash_archer = new JButton("ARCHER");       //Choose archer clash
        clash_archer.setBounds(1150,600,200,200);
        clash_archer.setOpaque(false);
        clash_archer.setContentAreaFilled(false);
        clash_archer.setEnabled(false);
        
        game.add(clash);
        game.add(clash_warrior);
        game.add(clash_sorcerer);
        game.add(clash_archer);
        game.add(player_Name);
        game.add(label);
        
  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        player_Name.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                player_Name.setText("");
            }
        });

        player_Name.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                //Get player's name. if name is " " make it Player #number of player 
                possibleName = player_Name.getText();
                if (possibleName.equals("")){
                    possibleName = "Player " + (players.size()+1);
                
                }
                player_Name.setText(possibleName);
                int size = names.size();
                
                if (names.isEmpty()) 
                {
                   names.add(possibleName);
                   System.out.println(names);
                }
                else
                {
                    //Check if player's name already exists
                    boolean flag = false;
                    for (String i : names) {
                        flag = i.equals(possibleName);
                        if (flag == true) {
                            JOptionPane.showMessageDialog(null,"Type a different one...","Name already exists!!", JOptionPane.ERROR_MESSAGE); 
                            break; 
                        }
                    }
                    if (flag == false) 
                    {
                        names.add(possibleName);
                        System.out.println(names);
                    }
                }
                if (size < names.size()) { 
                    clash_warrior.setEnabled(true);
                    clash_sorcerer.setEnabled(true);
                    clash_archer.setEnabled(true);
                    player_Name.setEnabled(false);
                }
            }
        });
        
        //Warrior choice
        clash_warrior.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e) {
             Warrior w = new Warrior();
             w.setStatsOfWarrior(names.get(names.size()-1));
             players.add(w);
             System.out.println(players.size());
             players.get(players.size() -1).showStats();
             if((players.size() < 4) && (JOptionPane.showConfirmDialog(null,"Do you want to add a new player?","New Player",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )) 
             {
                 clash_warrior.setEnabled(false);
                 clash_sorcerer.setEnabled(false);
                 clash_archer.setEnabled(false);
                 player_Name.setEnabled(true);
                 player_Name.setText(null);
                 
             }
             else
             {
                 game.getContentPane().removeAll();
                 label.removeAll();
                 game.repaint();
                 new Map();
             }

            }
            
        });
        
        //Sorcerer choice
        clash_sorcerer.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e) {
             Sorcerer s = new Sorcerer();
             s.setStatsOfSorcerer(names.get(names.size()-1));
             players.add(s);
             System.out.println(players.size());
             players.get(players.size() -1).showStats();
             if((players.size() < 4) && (JOptionPane.showConfirmDialog(null,"Do you want to add a new player?","New Player",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )) 
             {
                 clash_warrior.setEnabled(false);
                 clash_sorcerer.setEnabled(false);
                 clash_archer.setEnabled(false);
                 player_Name.setEnabled(true);
                 player_Name.setText(null);
             }
             else
             {
                 game.getContentPane().removeAll();
                 label.removeAll();
                 game.repaint();
                 new Map();
             }

            }
            
        });
        
        //Archer choice
        clash_archer.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e) {
             Archer a = new Archer();
             a.setStatsOfArcher(names.get(names.size()-1));
             players.add(a);
             System.out.println(players.size());
             players.get(players.size() -1).showStats();
             if((players.size() < 4) && (JOptionPane.showConfirmDialog(null,"Do you want to add a new player?","New Player",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )) 
             {
                 clash_warrior.setEnabled(false);
                 clash_sorcerer.setEnabled(false);
                 clash_archer.setEnabled(false);
                 player_Name.setEnabled(true);
                 player_Name.setText(null);
             }
             else
             {
                 game.getContentPane().removeAll();
                 label.removeAll();
                 game.repaint();
                 new Map();
             }

            }
            
        });
        game.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
        game.setVisible(true);
        
    } 
    
    

   //Create the players for the local game
   public void actionPerformed(ActionEvent e) {
        NumberOfPlayers();
   }
   
   

   
}
