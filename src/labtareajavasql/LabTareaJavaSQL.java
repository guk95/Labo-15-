/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labtareajavasql;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author laboratorio
 */
public class LabTareaJavaSQL extends JFrame {

    private final SQL sql;
    private final ULatinaLayOut ul;
    public JLabel lbl_error_msg = new JLabel();

    UsuarioLogueado current = new UsuarioLogueado();

    public LabTareaJavaSQL() {
        this.ul = new ULatinaLayOut(300, 200, 4, 20);
        this.sql = new SQL();

        setTitle("Clase 1 Progamación 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(ul.getLayOut());
        setPreferredSize(ul.setComponentDimension());
        setResizable(false);

        initComponents();
    }

    public void initComponents() {
        JButton btn = new JButton();
        JTextField txt_username = new JTextField();
        JTextField txt_password = new JTextField();
        JLabel lbl_username = new JLabel();
        JLabel lbl_pass = new JLabel();

        lbl_error_msg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        lbl_error_msg.setForeground(Color.red);

        Object[][] obj1
                = {
                    {lbl_username, 100, 30, "Nombre"},
                    {txt_username, 100, 30}
                };
        ul.setRow(obj1);

        Object[][] obj
                = {
                    {lbl_pass, 100, 30, "Password"},
                    {txt_password, 100, 30}
                };
        ul.setRow(obj);

        btn.setBounds(ul.getRectangle(200, 30));

        btn.setText("Login");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                btnMouseClicked(txt_username.getText(), txt_password.getText());
            }
        });

        lbl_error_msg.setBounds(ul.getRectangle(200, 25));

        setLocationRelativeTo(null);
        add(lbl_username);
        add(txt_username);
        add(lbl_pass);
        add(txt_password);
        add(btn);
        add(lbl_error_msg);
        pack();
    }

    public void btnMouseClicked(String nom, String pass) {
        if (nom.isEmpty() || pass.isEmpty()) {
            lbl_error_msg.setText("Empty user or Password");
            return;
        }
        ArrayList<Object> arr = new ArrayList<>();
        arr.addAll(Arrays.asList(nom, pass));

        ResultSet rs = sql.SELECT(""
                + "SELECT `idusuario`, `Nombre`, `Password`,`tipoLogin` "
                + "FROM `Mario_Login` "
                + "WHERE `Nombre`=? AND `Password`=? AND (`tipoLogin`= \"Admin\" Or`tipoLogin`= \"DBA\") ",
                arr);

        if (!sql.Exists(rs)) {
            lbl_error_msg.setText("Invalid User or Password");
        } else {
            try {
                while (rs.next()) {
                    current.setNombre((String) rs.getObject("Nombre"));
                    current.setCargo((String) rs.getObject("tipoLogin"));
                }

            } catch (SQLException e) {
                System.out.println("Error" + e);
            }

            lbl_error_msg.setForeground(Color.green);
            lbl_error_msg.setText("login correct.");
            Dashboard dashboard = new Dashboard();
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(()
                -> {
            LabTareaJavaSQL mySQL4Java = new LabTareaJavaSQL();
            mySQL4Java.setVisible(true);

        });

    }

}
