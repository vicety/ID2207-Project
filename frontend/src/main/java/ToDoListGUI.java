import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToDoListGUI extends JFrame {

    private Color background = new Color(55, 86, 150);
    private String sessionId;
    private String role;
    private JMenu menu;
    private JMenuBar menuBar;
    private JPanel contentPane;
    private JPanel formPane;

    public ToDoListGUI(String Id, String role){
        this.sessionId = Id;
        this.role = role;
        //System.out.println("TodoList"+Id);
        init();

    }

    public void init(){
        //menu
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menu = new JMenu("More");
        JMenuItem menuItem1 = new JMenuItem("My Information");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You are " + role, "tips", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menu.add(menuItem1);
        menuBar.add(menu);
        addMoreMenuItem();

        setTitle("FormList");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        //panel
        contentPane = new JPanel();
        contentPane.setBackground(background);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        //north
        JPanel title = new JPanel();
        JLabel a1 = new JLabel("ToDoList");
        a1.setForeground(new Color(55, 86, 150));
        a1.setFont(new Font("Segoe Print", Font.BOLD, 50));
        title.setPreferredSize(new Dimension(600, 80));
        title.add(a1);
        contentPane.add(title, BorderLayout.NORTH);
        //CENTER
        formPane = new JPanel();
        JScrollPane sp = new JScrollPane();
        sp.setViewportView(formPane);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(sp, BorderLayout.CENTER);
        //SOUTH
        JPanel btnPane = new JPanel();
        contentPane.add(btnPane, BorderLayout.SOUTH);
        btnPane.setPreferredSize(new Dimension(600, 100));
        btnPane.setLayout(null);

        Button rereshbt = new Button("REFRESH", background );
        rereshbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "ToDoList Updated", "tips", JOptionPane.PLAIN_MESSAGE);
                refresh();
            }
        });
        rereshbt.setFont(new Font("Arial", Font.BOLD, 15));
        rereshbt.setForeground(Color.WHITE);
        rereshbt.setBounds(400, 25, 119, 65);
        btnPane.add(rereshbt);

        Button backbt = new Button("BACK", background );
        backbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginGUI();
            }
        });
        backbt.setFont(new Font("Arial", Font.BOLD, 15));
        backbt.setForeground(Color.WHITE);
        backbt.setBounds(55, 25, 119, 65);
        btnPane.add(backbt);


        refresh();
        setVisible(true);
    }

    private void addMoreMenuItem() {
        if(role.equals("CS")){
            JMenuItem menuItem = new JMenuItem("Create Event Form");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EventForm fm = EventForm.createEventForm("EVENT");
                    new FormGUI(fm);
                }
            });
            menu.add(menuItem);
        }
        else if(role.equals("SM/PM")){
            JMenuItem menuItem1 = new JMenuItem("Task Distribution");
            menuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TaskForm fm = TaskForm.createTaskForm("TASK");
                    new FormGUI(fm);
                }
            });
            JMenuItem menuItem2 = new JMenuItem("Staff Recruitment Request");
            menuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FinancialForm fm = FinancialForm.createFinancialForm("HR");
                    new FormGUI(fm);
                }
            });
            JMenuItem menuItem3 = new JMenuItem("Financial Requests");
            menuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RecruiForm fm = RecruiForm.createRecruiForm("FINANCIAL");
                    new FormGUI(fm);
                }
            });
            menu.add(menuItem1);
            menu.add(menuItem2);
            menu.add(menuItem3);
        }


    }

    public void refresh(){
        formPane.removeAll();
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\"}";
        String url = "http://3.133.82.240:8080/reqFormList";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        System.out.println(res);
        //String res="[{\"formId\":\"123\",\"formType\":\"EVENT\"}," +
        //       "{\"formId\":\"321\",\"formType\":\"EVENT\"}]";
        //PARSE
        //创建Gson对象
        Gson gson = new Gson();
        //获取JsonArray对象
        JsonArray jsonElements = JsonParser.parseString(res).getAsJsonArray();
        //新建一个ArrayList
        ArrayList<Form> beans = new ArrayList<>();
        //遍历JsonArray对象中的elements
        for (JsonElement bean : jsonElements) {
            Form bean1 = gson.fromJson(bean, Form.class);//解析
            //model.addRow(new String[] { bean1.formId, bean1.formType });
            beans.add(bean1);
        }
        //System.out.println("ID"+beans.get(0).formId);
        //System.out.println("ID"+beans.get(0).formType);

        Button[] formButton = new Button[beans.size()];

        if(beans.size() == 0) {
            JLabel emptyMsg = new JLabel("Nothing here", JLabel.CENTER);
            emptyMsg.setFont(new Font("Segoe Print", Font.BOLD, 30));
            JPanel emptyPane = new JPanel();
            emptyPane.setLayout(new BorderLayout());
            emptyPane.add(emptyMsg, BorderLayout.SOUTH);
            formPane.add(emptyPane);
        }
        else
            formPane.setLayout(new GridLayout(6,1,20,20));

        for(int i = 0; i < formButton.length; i++){
            GridBagConstraints gc = new GridBagConstraints();
            formButton[i] = new Button("Form ID: "+beans.get(i).formId+"     Form Type: "+beans.get(i).formType);
            formButton[i].setFont(new Font("Segoe Print", Font.BOLD, 20));
            formButton[i].setForeground(Color.BLACK);
            formButton[i].addActionListener(new requestFormListener(beans.get(i)));
            formPane.add(formButton[i]);

        }


        revalidate();
    }

    class requestFormListener implements ActionListener{
        Form b;
        requestFormListener(Form bean){
            b = bean;
        }
        public void actionPerformed(ActionEvent e) {
            if(b.formType.equals("EVENT")){
                System.out.println("Event"+sessionId);
                EventForm fm = EventForm.getEventForm(b.formId,sessionId);
                new FormGUI(fm);
            }
            else if (b.formType.equals("TASK")){
                System.out.println("TASK"+sessionId);
                TaskForm fm = TaskForm.getTaskForm(b.formId,sessionId);
                new FormGUI(fm);
            }
            else if (b.formType.equals("HR")){
                System.out.println("HR"+sessionId);
                RecruiForm fm = RecruiForm.getRecruiForm(b.formId,sessionId);
                new FormGUI(fm);
            }
            else if (b.formType.equals("FINANCIAL")){
                System.out.println("FINANCIAL"+sessionId);
                FinancialForm fm = FinancialForm.getFinancialForm(b.formId,sessionId);
                new FormGUI(fm);

            }
        }
    }

    public static void main(String[] args) {
        new ToDoListGUI("996b41c62520dc9a158561825436c565", "SM/PM");
    }
}
