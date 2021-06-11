import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.*;



public class Combat extends Node implements ActionListener
{
    private JDialog combat;
    private JButton dicebutton, resultchallenger1, resultchallenger2;
    private JTextArea challenger1, challenger2;
    private ImageIcon backgroundImage;
    private JLabel label;
    private Dimension dimensions;
    
    private Enemy monster; //computer monster
    private Player player, rival_player; //player vs player
    private int rounds = 0;
    
    private double challenger1_hp, challenger2_hp, monster_hp; //keep hp
    
    //Combat object when player fights monster
    public Combat(Player p)
    {
        combat = new JDialog();
        combat.setTitle("Let's Fight");
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        combat.setLocation(dimensions.width/4, dimensions.height/4);
        combat.setSize(dimensions.width/2, dimensions.height/2);
        
        player = p;
        monster = new Enemy();
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        
        dicebutton = new JButton("ROLL");
        dicebutton.setFocusPainted(false);
        dicebutton.setBounds(combat.getWidth()/3, combat.getHeight()/2, combat.getWidth()/3, combat.getHeight()/2);
        dicebutton.setOpaque(false);
        dicebutton.setContentAreaFilled(false);
        dicebutton.addActionListener(this);
        
        resultchallenger1 = new JButton();
        resultchallenger1.setBounds(10,2*(combat.getHeight()/3),100,100);
        resultchallenger1.setOpaque(false);
        resultchallenger1.setContentAreaFilled(false);
        resultchallenger1.setBorderPainted(false);
        resultchallenger1.setFocusPainted(false);
        resultchallenger1.setEnabled(false);
        
        resultchallenger2 = new JButton();
        resultchallenger2.setBounds(5*(combat.getWidth()/6),2*(combat.getHeight()/3),100,100);
        resultchallenger2.setOpaque(false);
        resultchallenger2.setContentAreaFilled(false);
        resultchallenger2.setBorderPainted(false);
        resultchallenger2.setFocusPainted(false);
        resultchallenger2.setEnabled(false);
        
        challenger1 = new JTextArea();
        informPlayer1Stats();
        
        challenger2 = new JTextArea();
        informMonsterStats();
        
        combat.add(dicebutton);
        combat.add(resultchallenger1);
        combat.add(resultchallenger2);
        combat.add(challenger1);
        combat.add(challenger2);
        combat.add(label);
        
        combat.setModal(true);
        combat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        combat.setVisible(true);
    }
    
    //Combat object when player fights another player
    public Combat(Player p1, Player p2)
    {
        combat = new JDialog();
        combat.setTitle("Let's Fight");
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        combat.setLocation(dimensions.width/4, dimensions.height/4);
        combat.setSize(dimensions.width/2, dimensions.height/2);
        
        player = p1;
        rival_player = p2; 
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        
        dicebutton = new JButton("ROLL");
        dicebutton.setFocusPainted(false);
        dicebutton.setBounds(combat.getWidth()/3, combat.getHeight()/2, combat.getWidth()/3, combat.getHeight()/2);
        dicebutton.setOpaque(false);
        dicebutton.setContentAreaFilled(false);
        dicebutton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            double power_of_hit;
        
            int diceresult = new NodeDice().roll(); //roll dice to calculate damage
            ImageIcon image = new ImageIcon(getClass().getResource("result"+diceresult+".png"));
            
            if(rounds == 0)
            {
                resultchallenger1.setIcon(image); //keep the result of the last roll of this player
                power_of_hit = player.getPowerOfHit(diceresult); //calculate the whole damage
                rival_player.decreaseHp(power_of_hit);  //reduce the hp of other player, according to the calculated damage
                rounds = 1;
            
                challenger2.setText(null);
                informPlayer2Stats(); //inform the field with player 2 stats
                
                challenger1.append("Cause " + power_of_hit + " damage to the enemy! \n\n");
                if(power_of_hit > player.damage + (player.damage*((double) diceresult/10)))
                {
                    challenger1.append("IT WAS A CRITICAL HIT!!!");
                }
                
            }
            else
            {
         
                resultchallenger2.setIcon(image);
                power_of_hit = rival_player.getPowerOfHit(diceresult); //calculate the whole damage
                player.decreaseHp(power_of_hit); //reduce the hp of this player

                rounds = 0;
            
                challenger1.setText(null);
                informPlayer1Stats(); //inform the field with player 1 stats
                
                challenger2.append("Cause " + power_of_hit + " damage to the enemy! \n\n");
                if(power_of_hit > rival_player.damage + (rival_player.damage*((double) diceresult/10)))
                {
                    challenger2.append("IT WAS A CRITICAL HIT!!!");
                }
            }
            if(rival_player.hp <= 0) //when player 2 is dead
            {
                JOptionPane.showMessageDialog(null,rival_player.player_name + " is dead!!!", player.player_name + " is the winner!!!", JOptionPane.INFORMATION_MESSAGE);
                Buff b = new Buff(player); //reward of player one(buff)
                combat.dispose();
                player.increaseStat(b.getElement()); //increase player one stats
                player.increaseCoins(); //get one coin
            }
            if( player.hp <= 0) //when player 1 is dead
            {
                JOptionPane.showMessageDialog(null,player.player_name + " is dead!!!", rival_player.player_name + " is the winner!!!", JOptionPane.INFORMATION_MESSAGE);
                Buff b = new Buff(rival_player); //reward of player 2(buff)
                combat.dispose();
                rival_player.increaseStat(b.getElement()); //increase player 2 stats
                rival_player.increaseCoins(); //get one coin
            }
            
        
          }
        });
        
        resultchallenger1 = new JButton();
        resultchallenger1.setBounds(10,2*(combat.getHeight()/3),100,100);
        resultchallenger1.setOpaque(false);
        resultchallenger1.setContentAreaFilled(false);
        resultchallenger1.setBorderPainted(false);
        resultchallenger1.setFocusPainted(false);
        resultchallenger1.setEnabled(false);
        
        resultchallenger2 = new JButton();
        resultchallenger2.setBounds(5*(combat.getWidth()/6),2*(combat.getHeight()/3),100,100);
        resultchallenger2.setOpaque(false);
        resultchallenger2.setContentAreaFilled(false);
        resultchallenger2.setBorderPainted(false);
        resultchallenger2.setFocusPainted(false);
        resultchallenger2.setEnabled(false);
        
        challenger1 = new JTextArea();
        informPlayer1Stats();
        
        challenger2 = new JTextArea();
        informPlayer2Stats();
        
        combat.add(dicebutton);
        combat.add(resultchallenger1);
        combat.add(resultchallenger2);
        combat.add(challenger1);
        combat.add(challenger2);
        combat.add(label);
        
        combat.setModal(true);
        combat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        combat.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        double power_of_hit;
        
        challenger1_hp = player.hp;
        monster_hp = monster.getHp();
        
        int diceresult = new NodeDice().roll();
        ImageIcon image = new ImageIcon(getClass().getResource("result"+diceresult+".png"));
            

        resultchallenger1.setIcon(image);
        power_of_hit = player.getPowerOfHit(diceresult); //calculate the whole damage
        monster.decreaseHp(power_of_hit);  //reduce the hp of monster, according to the calculated damage
        monster_hp = monster.getHp();   //save the hp
        rounds = 1;
            
        challenger2.setText(null);
        informMonsterStats();
        challenger1.setText(null);
        informPlayer1Stats();
            
        challenger1.append("Cause " + power_of_hit + " damage to the enemy! \n\n");
        if(power_of_hit > player.damage + (player.damage*((double) diceresult/10)))
        {
            challenger1.append("IT WAS A CRITICAL HIT!!!");
        }
        if(monster_hp <= 0)
        {
            JOptionPane.showMessageDialog(null,player.player_name + " is the winner!!!","Congratulations!!", JOptionPane.INFORMATION_MESSAGE);
            Buff b = new Buff(player); //reward of player since he defeats the monster (buff)
            combat.dispose();
            player.increaseStat(b.getElement()); //increase player's stats
            player.increaseCoins(); //get one coin
        }
        else
        {

            dicebutton.setEnabled(false);
            new Timer(3000, new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    dicebutton.setEnabled(true);
                    double power_of_hit;
                    int diceresult2 = new NodeDice().roll();
                    ImageIcon image2 = new ImageIcon(getClass().getResource("result"+diceresult2+".png"));

                    resultchallenger2.setIcon(image2);
                    power_of_hit = monster.getPowerOfHit(diceresult2); //whole monster's hit damage
                    player.decreaseHp(power_of_hit); //decrease player's hp
                    challenger1_hp = player.hp;
            
                    challenger1.setText(null);
                    informPlayer1Stats();
                    if(challenger1_hp <= 0) //if player's hp is zero, the player is dead
                    {
                        JOptionPane.showMessageDialog(null,player.player_name + " is dead!!!", "YOU'RE KILLED!!!", JOptionPane.INFORMATION_MESSAGE);
                        combat.dispose();
                    }
            
                    ((Timer) ae.getSource()).stop();
            
                }
            }).start();
            
        }

        
    }
    //write the player's stats in fight
    public void informPlayer1Stats(){
        challenger1.append(player.player_name + " Stats \n");
        challenger1.append("HP: " + player.hp + "\n");
        challenger1.append("Damage: " + player.damage + "\n");
        challenger1.append("Critical Chance: " + player.critical_chance + "\n\n");
        challenger1.setBounds(0,10,combat.getWidth()/2,combat.getHeight()/3);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        challenger1.setBorder(blackline);
        challenger1.setOpaque(false);
        challenger1.setEditable(false);

    }
    //write the second player's stats in fight 
     public void informPlayer2Stats(){
        challenger2.append(rival_player.player_name + " Stats \n");
        challenger2.append("HP: " + rival_player.hp + "\n");
        challenger2.append("Damage: " + rival_player.damage + "\n");
        challenger2.append("Critical Chance: " + rival_player.critical_chance + "\n\n");
        challenger2.setBounds(combat.getWidth()/2,10,combat.getWidth()/2,combat.getHeight()/3);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        challenger2.setBorder(blackline);
        challenger2.setOpaque(false);
        challenger2.setEditable(false);

    }
    //write monster's stats in fight
    public void informMonsterStats(){
        challenger2.append(monster.getName() + " Stats \n");
        challenger2.append("HP: " + monster.getHp() + "\n");
        challenger2.append("Damage: " + monster.getDamage() + "\n");
        challenger2.setBounds(combat.getWidth()/2,10,combat.getWidth()/2,combat.getHeight()/3);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        challenger2.setBorder(blackline);
        challenger2.setOpaque(false);
        challenger2.setEditable(false);
    }
    
    public double getChallenger1Hp(){
        return challenger1_hp;
    }
    
    public double getMonsterHp(){
        return monster_hp;
    }
    

    }

