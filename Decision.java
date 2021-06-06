import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class Decision extends Node implements ActionListener
{
    private JDialog decision;
    private JLabel label;
    private JTextArea info;
    private Dimension dimensions;
    private ImageIcon backgroundImage;
    private String command="";
    private JTextField  choose_path;
    private ArrayList<String> possibleMoves = new ArrayList<String>();

    
    public Decision(Player player,  ArrayList<String> moves)
    {

        decision = new JDialog();//"Decision Node of " + player.player_name);
        decision.setTitle("Decision Node of " + player.player_name);
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        decision.setLocation(dimensions.width/4, dimensions.height/4);
        decision.setSize(dimensions.width/2, dimensions.height/2);
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        label = new JLabel(backgroundImage);
        
        choose_path = new JTextField("CHOOSE YOUR PATH");
        choose_path.setBounds(decision.getWidth()/4, decision.getHeight()/4, decision.getWidth()/2, 50);
        choose_path.setOpaque(false);
        choose_path.setHorizontalAlignment(JTextField.CENTER);
        choose_path.setForeground(Color.BLACK);
        choose_path.setFont(choose_path.getFont().deriveFont(Font.BOLD, 14f));
        choose_path.getCaret().setVisible(false);
        
        possibleMoves = moves;
        
        
        choose_path.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                choose_path.setText("");
            }
        });
        
        choose_path.addActionListener(this);

        info = new JTextArea();
        info.append("POSSIBLE MOVES \n");
        for(int i=0; i<moves.size(); i++)
        {
            info.append("- " + moves.get(i) + "\n");
        }
        info.setBounds(decision.getWidth()/3,2*(decision.getHeight()/3),decision.getWidth()/3,100);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        info.setBorder(blackline);
        info.setOpaque(false);
        //info.setEnabled(false);
        info.setEditable(false);
        
        decision.add(info);
        decision.add(choose_path);
        decision.add(label);
        
        decision.setModal(true);
        
        //decision.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        decision.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        decision.setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e) {
        
        setCommand(choose_path.getText());
        boolean flag = false;
        
        for(int i=0; i<possibleMoves.size(); i++)
        {
            System.out.println(possibleMoves.get(i));
            if(possibleMoves.get(i).equals(command))
            {
                flag = true;
            }
        }
        System.out.println(flag);
        if (!flag)
        {
            command = "";
            JOptionPane.showMessageDialog(null,"Type a different one...","Not Available Order", JOptionPane.ERROR_MESSAGE);
        }
        else
        {          
            decision.dispose();
        }
      }
    
    public void setCommand(String s){
        command = s.toUpperCase();
    }
    
    public String getCommand(){
        return command;
    }
    
}
