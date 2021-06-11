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
    private JButton shop, dicebutton, review; //buttons of dice and review
    protected ArrayList <Player> players; //keep the players in order of playing
    private int[] order; //keep the results to decide order of play
    private int rounds = 0; //keep the round to remember whose turn is
    private int order_rounds = 1; //keep the rounds that are necessary to decide order
    private MapDice node_dice = new MapDice();
    private int movement; // variable to keep dice result and decide the correct move on the map
    private String prevNode,command;
    private Decision decide;
    private Buff boost;
    private Combat vsmonster, vsplayer;
    private static int numberofplayers; //keep the number of players, when the game starts
    
    
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
        
        review = new JButton("REVIEW");
        review.setBounds(1400, 650, 100,100);
        review.setForeground(Color.WHITE);
        review.setOpaque(false);
        review.setContentAreaFilled(false);
        review.setBorderPainted(false);
        review.setFocusPainted(false);
        review.setEnabled(false);
        
        //Review players stats in game
        review.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new Review(players);         
            }
            
        });
        
        players = p;
        keepNumberOfPlayers(players.size());
        order = new int[players.size()];
        paint = new DrawMap(players);
        
        game.add(shop);
        game.add(dicebutton);
        game.add(review);
        
        game.add(paint);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.setVisible(true);
        
        //if the game starts, then the players will roll to decide the order
        if (rounds == 0)
        {
             JOptionPane.showMessageDialog(null, "Roll the dice and find out who goes first", "Let's start", JOptionPane.INFORMATION_MESSAGE);
        }
        
        dicebutton.addActionListener(this);
        
        shop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                new Shop(players.get(rounds - 1)); //open the shop for this player (player who is about to play
            }
        
        
        });
        
    }
    
        
    public void actionPerformed(ActionEvent e) {
        if ( rounds == 0 )
        {
            if ( order_rounds <= players.size() ) //rounds needed to decide order
            {
                order[order_rounds - 1] = node_dice.roll();     
                
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
                
                shop.setEnabled(true);
                review.setEnabled(true);
                JOptionPane.showMessageDialog(null,"Check review", "Order of moves is settled!!", JOptionPane.INFORMATION_MESSAGE);
                rounds++; //when the players are sorted, start round 1
            }
        }
        else
        {
            movement(); //when the order of play is decided, start the rounds of movement
           
        }
    }
    
    //method to make the players move based on dice result
    public void movement(){
        ArrayList<String> moves = new ArrayList<String>(); //keep possible moves
        String move_choice;
        
        //get players current position
        int x = players.get(rounds - 1).getPosX(); 
        int y = players.get(rounds - 1).getPosY();
        
        moves = getPossibleMoves(x,y);//find the possible moves        
        movement = node_dice.roll(); //rol the dice 
        
        ImageIcon image = new ImageIcon(getClass().getResource("result"+movement+".png"));
        dicebutton.setIcon(image);
        
        //possibilities of dice results. Decide the next move of player
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
        
        informPlayerPos(move_choice,x,y); //inform player for its new position
        
        checkNode(); //check the new node and act properly
        
        rounds = (rounds%players.size()) + 1; //inform the round of players, so that they can play in order
    }
    
    //find player's possible moves, based on his current position(he can't get out of map bounds
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
    
    //method to inform about player's position
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
                prevNode = paint.getTypeOfNode(x+1,y); //keep prevNode
                break;
            case "UP":
                players.get(rounds - 1).setPlayerPosition(x,y-1);
                prevNode = paint.getTypeOfNode(x,y-1);
                break;
     }
        
     game.getContentPane().removeAll();
        
     game.add(shop);
     game.add(dicebutton);
     game.add(review);
     game.add(paint=new DrawMap(players));

     game.setVisible(true);
    }
    
    //check the new node for the right action
    public void checkNode(){
        
        if(prevNode == "C") //combat player vs monster
        {
            vsmonster = new Combat(players.get(rounds - 1));
            if(players.get(rounds - 1).hp <= 0)
            {
                players.remove(rounds - 1);
                game.getContentPane().removeAll();
                rounds = rounds - 1;
        
                game.add(shop);
                game.add(dicebutton);
                game.add(review);
                game.add(paint=new DrawMap(players));

                game.setVisible(true);
            }
        

        }
        else if(prevNode == "D") //node of decision
        {

           decide = new Decision(players.get(rounds - 1), getPossibleMoves(players.get(rounds - 1).getPosX(), players.get(rounds - 1).getPosY()));

           if(!decide.getCommand().equals(""))
           {
            players.get(rounds - 1).increaseCoins();
            informPlayerPos(decide.getCommand(), players.get(rounds - 1).getPosX(), players.get(rounds - 1).getPosY());
            checkNode();           
           }
          
        } 
        else if(prevNode == "B") //node of buff
        {
            boost = new Buff();
            if(boost.getElement() != null)
            {
                players.get(rounds - 1).increaseCoins();
                players.get(rounds - 1).increaseStat(boost.getElement());
            }
        }
        else //node with another player.
        {
            int found_rival=4; //There is no element on position 4 of player, but it is sure that there will find the correct player after loop. 
            for(int i = 0; i<players.size(); i++) //found the other player
            {
                if(players.get(i).player_name. equals(prevNode))
                {
                    found_rival = i;
                }
            }
            vsplayer = new Combat(players.get(rounds - 1), players.get(found_rival));
            if(players.get(rounds - 1).hp <= 0) //remove the lost player from the game 
            {
                players.remove(rounds - 1);
                game.getContentPane().removeAll();
        
                game.add(shop);
                game.add(dicebutton);
                game.add(review);
                game.add(paint=new DrawMap(players));

                game.setVisible(true);

                rounds = rounds - 1;                
            }
            else
            {
                players.remove(found_rival);
                game.getContentPane().removeAll();
        
                game.add(shop);
                game.add(dicebutton);
                game.add(review);
                game.add(paint=new DrawMap(players));

                game.setVisible(true);
                if((rounds - 1) > found_rival)
                {
                    rounds = rounds - 1;
                }
            }
        }
        if(numberofplayers > players.size() && players.size() == 1)
        {
            JOptionPane.showMessageDialog(null,"The winner is :" + players.get(0).player_name, "Congratulations!!!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        
        }
    //define the number of the players when the game starts
    public static void keepNumberOfPlayers(int x){
            numberofplayers = x;
    }
        
    }


