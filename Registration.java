import javax.swing.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;


public class Registration extends JFrame implements ActionListener
{
    private JDialog register;
    private JButton add_user;
    private JTextField username,password,email,age;
    private Dimension dimensions;
    
    public Registration()
    {
        register = new JDialog();
        register.setTitle("Welcome");
        
        dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        register.setLocation(dimensions.width/4, dimensions.height/4);
        register.setSize(dimensions.width/2, dimensions.height/2);
        
        username = new JTextField("USERNAME");
        username.setHorizontalAlignment(JTextField.CENTER);
        username.setForeground(Color.BLACK);
        username.setOpaque(false);
        username.setFont(username.getFont().deriveFont(Font.BOLD, 14f));
        username.getCaret().setVisible(false);
        
        username.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                username.setText("");
            }
        });
        
        password = new JTextField("PASSWORD");
        password.setHorizontalAlignment(JTextField.CENTER);
        password.setForeground(Color.BLACK);
        password.setOpaque(false);
        password.setFont(password.getFont().deriveFont(Font.BOLD, 14f));
        password.getCaret().setVisible(false);
        
        password.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                password.setText("");
            }
        });
        
        email = new JTextField("EMAIL");
        email.setHorizontalAlignment(JTextField.CENTER);
        email.setForeground(Color.BLACK);
        email.setOpaque(false);
        email.setFont(email.getFont().deriveFont(Font.BOLD, 14f));
        email.getCaret().setVisible(false);
        
        email.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                email.setText("");
            }
        });
        
        age = new JTextField("AGE");
        age.setHorizontalAlignment(JTextField.CENTER);
        age.setForeground(Color.BLACK);
        age.setOpaque(false);
        age.setFont(age.getFont().deriveFont(Font.BOLD, 14f));
        age.getCaret().setVisible(false);
        
        age.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                age.setText("");
            }
        });
        
        add_user = new JButton("ADD USER");
        
        register.add(username);
        register.add(password);
        register.add(email);
        register.add(age);
        register.add(add_user);
        
        add_user.addActionListener(this);
        
        register.setLayout(new GridLayout(5,1,20,20));
        register.setModal(true);
        register.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        register.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        User user = new User();
        String u = username.getText();
        String p = password.getText();
        String e = email.getText();
        String a = age.getText();
        
        String check = "^[A-Za-z0-9+_.-]+@(.+)$";  
        Pattern pattern = Pattern.compile(check); 
        Matcher matcher = pattern.matcher(e);  
        
        if(!u.equals("") && !p.equals("") && !e.equals("") && !a.equals("")) //if all fields are filled
        {
            if(alreadyExists(u,e)) //if username and password exists, then this user already exists
            {
                username.setText("USERNAME");
                password.setText("PASSWORD");
                email.setText("EMAIL");
                age.setText("AGE");
                
                JOptionPane.showMessageDialog(null,"This player already exists!!!","Sorry!!!", JOptionPane.ERROR_MESSAGE);
            }
            else if(!matcher.matches()) //put a correct type of email
            {
                email.setText("EMAIL");
                JOptionPane.showMessageDialog(null,"Your email must be : hiwdi2_dijs@email.com","Invalid Email!!!", JOptionPane.ERROR_MESSAGE);
            }
            else if(!a.matches("[0-9]+")) //age must be integers numbers 
            {
                age.setText("AGE");
                JOptionPane.showMessageDialog(null,"Only integers numbers!!","Invalid Value!!!", JOptionPane.ERROR_MESSAGE);

            }
            else
            {
                if(Integer.parseInt(a) < 10) //user should be at least 10 years old
                {
                    JOptionPane.showMessageDialog(null,"You are too young for this game!!!","Sorry!!!", JOptionPane.ERROR_MESSAGE);
                    age.setText("AGE");
                }
                else if(Integer.parseInt(a) > 110) //user can't be over 110 years old
                {
                    JOptionPane.showMessageDialog(null,"Not old enough,huh??", "You must be kidding!!", JOptionPane.ERROR_MESSAGE);
                    age.setText("AGE");
                }
                else //all good!! proceed with users registration and save him to the catalog of users
                {
                    user = new User();
                    user.setUsername(u);
                    user.setPassword(p);
                    user.setEmail(e);
                    user.setAge(Integer.parseInt(a));
                    
                    CatalogUsers.addUser(user);
                    JOptionPane.showMessageDialog(null,"Your registration is completed!!","Welcome!! Nice to meet you!!", JOptionPane.INFORMATION_MESSAGE);
                    register.dispose();
                    
                }
            }
        }
        else //there should be no empty field during registration
        {
            username.setText("USERNAME");
            password.setText("PASSWORD");
            email.setText("EMAIL");
            age.setText("AGE");
            JOptionPane.showMessageDialog(null,"You have to complete all fields", "Sorry!!!", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    //method to check if player already exists
    public boolean alreadyExists(String un, String mail){
        return CatalogUsers.foundPlayer(un,mail);
    }
}
