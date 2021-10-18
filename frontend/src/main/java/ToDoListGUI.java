import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ToDoListGUI extends JFrame {
    private String sessionId;
    JPanel contentPane;
    JPanel formPane;

    public ToDoListGUI(String Id){
        this.sessionId = Id;

        init();

    }

    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        //panel
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        //north
        JPanel title = new JPanel();
        JLabel cart = new JLabel("ToDoList");
        cart.setForeground(new Color(55, 86, 150));
        cart.setFont(new Font("Segoe Print", Font.BOLD, 50));
        title.setPreferredSize(new Dimension(600, 80));
        title.add(cart);
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
        setVisible(true);

        refresh();
    }

    public void refresh(){
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\"}";
        String url = "http://3.133.82.240:8080/reqFormList";
        Requester rster= new Requester(url, msg);
        //String res = rster.getRes();
        String res="[{\"formId\":\"123\",\"formType\":\"EVENT\"}," +
                "{\"formId\":\"123\"}]";
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
            beans.add(bean1);
        }
        System.out.println(beans.size());

        JPanel[] panels = new JPanel[beans.size()];
        GridBagLayout g = new GridBagLayout();

        if(beans.size() == 0) {
            JLabel emptyMsg = new JLabel("Nothing here", JLabel.CENTER);
            emptyMsg.setFont(new Font("Segoe Print", Font.BOLD, 30));
            JPanel emptyPane = new JPanel();
            emptyPane.setLayout(new BorderLayout());
            emptyPane.add(emptyMsg, BorderLayout.SOUTH);
            formPane.add(emptyPane);
        }
        else
            formPane.setLayout(g);

        for(int i = 0; i < panels.length; i++){
            GridBagConstraints gc = new GridBagConstraints();
            panels[i] = new JPanel();
            JLabel id = new JLabel(beans.get(i).formId);
            JLabel type = new JLabel(beans.get(i).formType);
            gc.gridx = 0;
            gc.ipadx = 60;
            g.addLayoutComponent(id, gc);
            g.addLayoutComponent(type, gc);
            formPane.add(id);
            formPane.add(type);
        }
        revalidate();
    }

    public static void main(String[] args) {
        new ToDoListGUI("123");
    }
}
