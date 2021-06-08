import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.awt.font.TextLayout;
import java.util.*;

public class Map extends Applet implements ActionListener
{
    private JFrame game;
    private DrawMap paint;
    private JButton shop, dicebutton;
    protected ArrayList <Player> players; //keep the players in order of playing
    private int[] order; //keep the results to decide order of play
    private int rounds = 0; //keep the round to remember whose turn is
    private int order_rounds = 1; //keep the rounds that are necessary to decide order
    private NodeDice node_dice = new NodeDice();
    private int movement; // variable to keep dice result and decide the correct move on the map
    private String prevNode,command;
    private Decision decide;
    private Buff boost;
    
    
    //create the map window
    public Map(ArrayList<Player> p)
    {
        game = new JFrame("DICESIONS");
        
        shop = new JButton("SHOP");
        shop.setForeground(Color.WHITE);
        shop.setBounds(1,1,100,100);
        shop.setOpaque(false);
        shop.setContentAreaFilled(false);
        shop.setBorderPainted(false);
        shop.setFocusPainted(false);
        shop.setEnabled(false);
        
        dicebutton = new JButton("DICE");
        dicebutton.setBounds(1,650,100,100);
        dicebutton.setForeground(Color.WHITE);
        dicebutton.setOpaque(false);
        dicebutton.setContentAreaFilled(false);
        dicebutton.setBorderPainted(false);
        dicebutton.setFocusPainted(false);
        
        players = p;
        order = new int[players.size()];
        paint = new DrawMap(players);
        
        game.add(shop);
        game.add(dicebutton);
        
        game.add(paint);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true); //fullscreen
        game.setVisible(true);
        
        //if the game starts, then the players will roll to decide the order
        if (rounds == 0)
        {
             JOptionPane.showMessageDialog(null, "Roll the dice and find out who goes first", "Let's start", JOptionPane.INFORMATION_MESSAGE);
        }
        
        dicebutton.addActionListener(this);
        
    }
    
        
    public void actionPerformed(ActionEvent e) {
        if ( rounds == 0 )
        {
            if ( order_rounds <= players.size() ) //rounds needed to decide order
            {
                order[order_rounds - 1] = node_dice.roll();     
                System.out.println(order[order_rounds - 1]);
                
                ImageIcon image = new ImageIcon(getClass().getResource("result"+order[order_rounds - 1]+".png"));
                dicebutton.setIcon(image);
                
                order_rounds++;
                
                
            }
            if( order_rounds > players.size() ) //sort players by dice results. The player with the highest result goes first. In case of tie, the first player that rolls goes first 
            {
                for(int i=0; i<players.size() - 1; i++)
                {
                    int max = order[i];
                
                    for(int j=i+1; j<players.size(); j++)
                    {
                        if(order[j] > max)
                        {
                            order[i] = order[j];
                            order[j] = max;
                            max = order[i];
                            Collections.swap(players,i,j);
                        }
                    }
                }
                for( int i = 0; i<players.size(); i++ )
                {
                    System.out.println(players.get(i).player_name);
                }
                shop.setEnabled(true);
                rounds++; //when the players are sorted, start round 1
            }
        }
        else
        {
            movement();
           
        }
    }
    
    //method to make the players move based on dice result
    public void movement(){
        ArrayList<String> moves = new ArrayList<String>(); //keep possible moves
        String move_choice;
        
        
        int x = players.get(rounds - 1).getPosX();
        int y = players.get(rounds - 1).getPosY();
        
        moves = getPossibleMoves(x,y);        
        movement = node_dice.roll();
        System.out.println(movement); 
        
        ImageIcon image = new ImageIcon(getClass().getResource("result"+movement+".png"));
        dicebutton.setIcon(image);
        
        if( moves.size() == 2)
        {
            if (movement == 1 || movement == 2 || movement == 3)
            {
                move_choice = moves.get(0);
            }
            else
            {
                move_choice = moves.get(1);
            }
        }
        else if( moves.size() == 3)
        {
            if(movement == 1 || movement == 2)
            {
                move_choice = moves.get(0);
            }
            else if(movement == 3 || movement == 4)
            {
                move_choice = moves.get(1);
            }
            else
            {
                move_choice = moves.get(2);
            }
        }
        else
        {
            if(movement == 1 || movement == 2)
            {
                move_choice = moves.get(0);
            }
            else if(movement == 3)
            {
                move_choice = moves.get(1);
            }
            else if(movement == 4)
            {
                move_choice = moves.get(2);
            }
            else
            {
                move_choice = moves.get(3);
            }
        }
        
        informPlayerPos(move_choice,x,y);
        
        checkNode();
        
        rounds = (rounds%players.size()) + 1;
    }
    
    public ArrayList<String> getPossibleMoves(int x, int y){
        ArrayList<String> moves = new ArrayList<String>();
        
        if( x==1 && y==1 )
        {
            moves.add("RIGHT");
            moves.add("DOWN");
        }
        else if ( x==1 && y==4 )
        {
            moves.add("UP");
            moves.add("RIGHT");
        }
        else if ( x==4 && y==1 )
        {
            moves.add("LEFT");
            moves.add("DOWN");
        }
        else if ( x==4 && y==4)
        {
            moves.add("LEFT");
            moves.add("UP");
        }
        else if( x==1 )
        {
            moves.add("UP");
            moves.add("RIGHT");
            moves.add("DOWN");
        }
        else if( x==4 )
        {
            moves.add("UP");
            moves.add("LEFT");
            moves.add("DOWN");
        }
        else if( y==1 )
        {
            moves.add("LEFT");
            moves.add("DOWN");
            moves.add("RIGHT");
        }
        else if( y==4 )
        {
            moves.add("LEFT");
            moves.add("UP");
            moves.add("RIGHT");
        }
        else
        {
            moves.add("LEFT");
            moves.add("DOWN");
            moves.add("UP");
            moves.add("RIGHT");
        }
        
        return moves;
    }
    
    public void informPlayerPos(String s, int x, int y){
        switch (s) {
            case "LEFT":
                players.get(rounds - 1).setPlayerPosition(x-1,y);
                prevNode = paint.getTypeOfNode(x-1,y); //get the type of Node, the player is going to move
                break;
            case "DOWN":
                players.get(rounds - 1).setPlayerPosition(x,y+1);
                prevNode = paint.getTypeOfNode(x,y+1);
                break;
            case "RIGHT":
                players.get(rounds - 1).setPlayerPosition(x+1,y);
                prevNode = paint.getTypeOfNode(x+1,y);
                break;
            case "UP":
                players.get(rounds - 1).setPlayerPosition(x,y-1);
                prevNode = paint.getTypeOfNode(x,y-1);
                break;
     }
     System.out.println(prevNode);
        
     game.getContentPane().removeAll();
        
     game.add(shop);
     game.add(dicebutton);
     game.add(paint=new DrawMap(players));

     game.setVisible(true);
    }
    
    public void checkNode(){
        
        if(prevNode == "C")
        {
        }
        else if(prevNode == "D")
        {

           decide = new Decision(players.get(rounds - 1), getPossibleMoves(players.get(rounds - 1).getPosX(), players.get(rounds - 1).getPosY()));

           if(!decide.getCommand().equals(""))
           {
            System.out.println("here we are!!");
            players.get(rounds - 1).increaseCoins();
            players.get(rounds - 1).showStats();
            informPlayerPos(decide.getCommand(), players.get(rounds - 1).getPosX(), players.get(rounds - 1).getPosY());
            checkNode();           
           }
          
        } 
        else if(prevNode == "B")
        {
            //CatalogBuff.showBuffs();
            boost = new Buff();
            if(boost.getElement() != null)
            {
                System.out.println(boost.getElement().getName());
                System.out.println(players.get(rounds - 1 ).player_name);
                players.get(rounds - 1).increaseStat(boost.getElement());
                players.get(rounds - 1).showStats();
            }
        }
        else
        {
        }
      }

    }
