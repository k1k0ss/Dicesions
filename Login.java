import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Login extends JFrame implements ActionListener
{
    private JDialog login;
    private JLabel name_label, pass_label,empty_label;
    private Dimension dimensions;
    private JTextField username;
    private JPasswordField password;
    private JButton submit, register;
    private JCheckBox checkbox;
    private Choice choice;
    private ImageIcon backgroundImage;
    private JTextArea rivals;
    
    private ArrayList<User> opponents;
    
    public Login() 
    {
        login = new JDialog();
        login.setTitle("Log In Page");
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        login.setLocation(dimensions.width/4, dimensions.height/4);
        login.setSize(dimensions.width/2, dimensions.height/2);
        
        name_label = new JLabel("username : ");
        
        pass_label = new JLabel("password : ");
        
        empty_label = new JLabel();
        
        choice = new Choice();
        
        backgroundImage = new ImageIcon(getClass().getResource("map_background.jpg")); //Image of background
        
        opponents = new ArrayList<User>();
        
        username = new JTextField();
        username.setHorizontalAlignment(JTextField.CENTER);
        username.setForeground(Color.BLACK);
        username.setOpaque(false);
        username.setFont(username.getFont().deriveFont(Font.BOLD, 14f));
        username.getCaret().setVisible(false);
        
        password = new JPasswordField();
        password.setEchoChar('*'); //hide password by default
        password.setHorizontalAlignment(JTextField.CENTER);
        password.setForeground(Color.BLACK);
        password.setOpaque(false);
        password.setFont(password.getFont().deriveFont(Font.BOLD, 14f));
        password.getCaret().setVisible(false);
        
        checkbox = new JCheckBox("Show password"); //don't hide the password if the user clicks the box
        checkbox.setFocusPainted(false);
        
        submit = new JButton("SUBMIT");
        register = new JButton("REGISTER");
        
        login.add(name_label);
        login.add(username);
        login.add(pass_label);
        login.add(password);
        login.add(empty_label);
        login.add(checkbox);
        login.add(register);
        login.add(submit);
        
        register.addActionListener(this);
        
        submit.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae){
                String u = username.getText();
                String p = password.getText();
                
                if(u.equals("") || p.equals("")) //all fields must be completed
                {
                    JOptionPane.showMessageDialog(null,"You have to complete all fields", "Sorry!!!", JOptionPane.ERROR_MESSAGE);
                }
                else 
                {
                    verification(u,p); //check if there is user with these username and password
                    Object stringArray[] = {"Standard Online","Custom Online"};
                    int choose = JOptionPane.showOptionDialog(null,"Choose type of online game", "Let's start!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, stringArray[0]);
                    login.dispose();
                    
                    if(choose == JOptionPane.NO_OPTION) //check if player choose standard online game or custom online game
                    {
                        login = new JDialog();
                        login.setTitle("choose number of players");
                        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
                        login.setLocation(dimensions.width/4, dimensions.height/4);
                        login.setSize(dimensions.width/2, dimensions.height/2);
                        
                        
                        empty_label = new JLabel(backgroundImage);
                        
                        choice.setBounds(login.getWidth()/3, login.getHeight()/3, login.getWidth()/3, login.getHeight()/3);
                        choice.add("2 Players");
                        choice.add("3 Players");
                        choice.add("4 Players");
                        
                        login.add(choice);
                        login.add(empty_label);
                        
                        choice.addItemListener(new ItemListener() {  
                            public void itemStateChanged(ItemEvent event) {       
                                 String text = String.valueOf(choice.getItem(choice.getSelectedIndex())); //choose how many players do you want to play with
                                 for(int i = 2; i <= 4; i++)
                                 {
                                     if(text.equals(i + " Players"))
                                     {
                                         opponents = CatalogUsers.findOpponents(i); //find available opponents
                                     }
                                 }
                                 if(opponents.isEmpty()) //if there is no available opponent
                                 {
                                     JOptionPane.showMessageDialog(null,"There is no available players!!", "Sorry!!!", JOptionPane.ERROR_MESSAGE);
                                 }
                                 else
                                 {
                                     login.dispose();
                                     
                                     login = new JDialog();
                                     login.setTitle("Your rivals");
                                     
                                     dimensions = Toolkit.getDefaultToolkit().getScreenSize();
                                     login.setLocation(dimensions.width/4, dimensions.height/4);
                                     login.setSize(dimensions.width/2, dimensions.height/2);
                                     
                                     rivals = new JTextArea();
                                     for(int i=0; i<opponents.size(); i++)
                                     {
                                         rivals.append("- " + opponents.get(i).getUsername() + "\n"); //show the player and its oppoents
                                     }
                                     rivals.setBounds((login.getWidth()/3),login.getHeight()/3,login.getWidth()/3,login.getHeight()/3);
                                     Border blackline = BorderFactory.createLineBorder(Color.BLACK);
                                     rivals.setBorder(blackline);
                                     rivals.setOpaque(false);
                                     rivals.setEditable(false);
                                     
                                     login.add(rivals);
                                     login.add(empty_label);
                                     
                                     login.setModal(true);
                                     login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                     login.setVisible(true);
                                 }
                            }  
                        }); 
                        
                        login.setModal(true);
                        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        login.setVisible(true);
                    }
                    else
                    {
                        new StartGame().NumberOfPlayers();
                    }
                }
            }
        });
        
        checkbox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(checkbox.isSelected()) //checked box = show password, unchecked box = don't show password
                {
                    password.setEchoChar((char)0);
                }
                else
                {
                    password.setEchoChar('*');
                }
            }
        });
        login.setLayout(new GridLayout(4,1,0,30));
        login.setModal(true);
        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        login.setVisible(true);
        
        
    }
    
    public void actionPerformed(ActionEvent e){
        new Registration(); //proceed with user's registration
    }
    //verified user by username and password
    public void verification(String un, String pass){
        User user = CatalogUsers.getUser(un,pass); 
        if(Objects.isNull(user))
        {
            JOptionPane.showMessageDialog(null,"There is no such player!!", "Not Found!!", JOptionPane.ERROR_MESSAGE);
            login.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Let's find some opponents!!","Welcome back!!", JOptionPane.INFORMATION_MESSAGE);
            login.dispose();
        }
    }
}
