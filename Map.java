import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Map extends JFrame 
{
    private JFrame game;
    private JLabel label;
    private ImageIcon backgroundImage;
    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
     
    public Map()
    {
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg"));
        label = new JLabel(backgroundImage);
        game = new JFrame("Map");
        game.add(label);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize((int)screensize.getWidth(),(int)screensize.getHeight());
        game.setVisible(true);
    }


}
