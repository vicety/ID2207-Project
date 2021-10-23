import com.google.gson.Gson;
import okhttp3.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class LoginGUI extends JFrame {

    private Color background = new Color(55, 86, 150);
    private JPanel contentPane;
    private Icon log;
    private JLabel a1;
    private JTextField userID;
    private JLabel a2;
    private JPasswordField password;
    private JButton next;

    /**
     * Constructor
     * Create and initialize the GUI
     */
    public LoginGUI(){
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        //panel
        contentPane = new JPanel();
        contentPane.setBackground(background);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //log
        log = new ImageIcon("src/main/resources/log.png");
        JLabel logIcon = new JLabel();
        logIcon.setIcon(log);
        logIcon.setBounds(230, 20, 150, 150);
        contentPane.add(logIcon);
        JLabel title = new JLabel("SEP");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe Print", Font.BOLD, 90));
        title.setForeground(Color.ORANGE);
        title.setBounds(62, 175, 476, 116);
        contentPane.add(title);
        //user enter
        a1 = new JLabel("UserID");
        a1.setFont(new Font("Arial", Font.BOLD, 30));
        a1.setForeground(Color.ORANGE);
        a1.setBounds(100, 300, 150, 120);
        contentPane.add(a1);

        userID = new JTextField();
        userID.setBounds(250, 335, 200, 40);
        contentPane.add(userID);

        a2 = new JLabel("Code");
        a2.setFont(new Font("Arial", Font.BOLD, 30));
        a2.setForeground(Color.ORANGE);
        a2.setBounds(100, 350, 150, 120);
        contentPane.add(a2);

        password = new JPasswordField();
        password.setBounds(250, 395, 200, 40);
        contentPane.add(password);

        next = new Button("NEXT");
        next.setFont(new Font("Arial", Font.BOLD, 30));
        next.setBounds(215, 503, 157, 45);
        contentPane.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = userID.getText();
                String pwd = String.valueOf(password.getPassword());

                String url = "http://3.133.82.240:8080/login";
                String msg ="{\"userName\":\"" + uname + "\",\"password\":\"" + pwd +"\"}";
                Requester rster= new Requester(url, msg);
                String res = rster.getRes();
                System.out.println(res);
                Gson gson = new Gson();
                LoginJSON  loginjson= gson.fromJson(res, LoginJSON.class);
                if (loginjson.status.equals("ok")){
                    System.out.println(loginjson.sessionId);
                    ToDoListGUI todolistgui = new ToDoListGUI(loginjson.sessionId, loginjson.role);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Password Wrong", "tips", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public class LoginJSON{
        private String status;
        private String role;
        private String sessionId;
    }

    /**
     * Launch the application
     */
    public static void main(String[] args) {
        new LoginGUI();
    }

}