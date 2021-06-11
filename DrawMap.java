import java.util.ArrayList;
import java.applet.Applet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.util.*;
import java.io.*;

//paint the map nodes
public class DrawMap extends JPanel //JComponent
{
    String[][] nodes = {{"C", "D", "C", "B"} ,{"B", "C", "D", "C"}, {"C", "C", "B", "D"} ,{"D", "B", "C", "C"}}; //nodes of map without players
    
    public DrawMap(ArrayList <Player> players)
    {
        for(Player player : players)
        {
            nodes[player.posX-1][player.posY-1] = player.player_name; //get players in the map
        }

    }
    
    @Override
    public void paintComponent(Graphics G){
        super.paintComponent(G); //paint the map
        Image back = Toolkit.getDefaultToolkit().getImage("map_background.jpg");
        G.drawImage(back,0,0,this);
        
        for(int i=1; i<=4 ; i++)
        {
            for (int j=1; j<=4; j++)
            {
                
                G.drawOval(i*300,j*150,40,40);
                G.setColor(Color.WHITE);
                G.drawString(nodes[i-1][j-1],((i*300) + 16), ((j*150) + 24));
                G.setColor(Color.BLACK);
                if(i<4)
                {
                    G.drawLine((i*300) + 40, (j*150) + 20, ((i+1)*300) , (j*150) + 20);
                }
                if(j<4)
                {
                    G.drawLine((i*300) + 20, (j*150) + 40, (i*300) + 20, ((j+1)*150));
                }
            }
        }
        

    }
    
    //get the type of node    
    public String getTypeOfNode(int x, int y){
        return nodes[x-1][y-1];
    }
}
