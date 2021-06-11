import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Review
{
    private JDialog review;
    private JTextArea[] stats;
    private Dimension dimensions;
    
    public Review(ArrayList<Player> p)
    {
        review = new JDialog();
        review.setTitle("Stats of players");
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        review.setLocation(dimensions.width/4, dimensions.height/4);
        review.setSize(dimensions.width/2, dimensions.height/2);
        
        stats = new JTextArea[p.size()];
        
        for(int i=0; i<p.size(); i++) //print players stats.
        {
            stats[i]=new JTextArea();
            stats[i].append("NAME : " + p.get(i).player_name + "\n");
            stats[i].append("HP : " + p.get(i).hp + "\n");
            stats[i].append("Damage : " + p.get(i).damage + "\n");
            stats[i].append("Critical Chance : " + p.get(i).critical_chance + "\n");
            stats[i].append("Position of Player : " + p.get(i).posX + " , " + p.get(i).posY + "\n");
            stats[i].append("Coins : " + p.get(i).coins + "\n");
            stats[i].append("Order of play : " + (i+1));
            
            stats[i].setBackground(Color.BLACK);
            stats[i].setForeground(Color.WHITE);
            stats[i].setEditable(false);
            review.add(stats[i]);
        }
        
        review.setLayout(new GridLayout());
        
        review.setModal(true);
        review.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        review.setVisible(true);
        
    }

}
