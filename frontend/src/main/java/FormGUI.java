import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class FormGUI extends JFrame {
    private JPanel contentPane;
    private Color background = new Color(55, 86, 150);
    //EventForm fm
    public FormGUI(Form fm){
        setSize(300, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        //panel
        contentPane = new JPanel();
        contentPane.setBackground(background);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(5, 5));

        //JTable
        Object[][] datas = {
                {"id", fm.formId},
                {"type", fm.formType}
        };

        String[] titles = {"Attribute", "Value"};
        DefaultTableModel model = new DefaultTableModel(datas, titles);

        Class cls = fm.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(int i=0; i<fields.length; i++){
            try {
                Field f = fields[i];
                f.setAccessible(true);
                //System.out.println(f.getName());
                //System.out.println(f.get(fm));
                model.insertRow(model.getRowCount(), new Object[]{f.getName(), f.get(fm)});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        JTable table = new JTable(model);
        //当有数据改变时
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getLastRow();
                String value = (String) table.getModel().getValueAt(row, 1);
                String key = (String) table.getModel().getValueAt(row, 0);
                //Form.updateForm(fm.formId, key, value);
                Form.updateForm(fm.formId, key, value);
                System.out.println(fm.formId);
                System.out.println(key);
                System.out.println(value);

            }
        });
        contentPane.add(new JScrollPane(table),BorderLayout.CENTER);

        Button okb = new Button("Send!");
        okb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Form.confirmForm(fm.formId);
                dispose();
            }
        });
        contentPane.add(okb,BorderLayout.SOUTH);

        revalidate();
        setVisible(true);
    }

    public static void main(String[] args) {
        new FormGUI(new EventForm());
    }
}
