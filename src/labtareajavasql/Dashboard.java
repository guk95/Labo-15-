/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labtareajavasql;

import labtareajavasql.funciones;
import labtareajavasql.SQL;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author jorge.vasquez
 */
public class Dashboard extends JFrame {

    public ULatinaLayOut ul, ultmp;
    private final SQL sql;
    private final funciones FNC;
    UsuarioLogueado current = new UsuarioLogueado();

    public Dashboard() {
        this.ul = new ULatinaLayOut(800, 600, 4);
        this.sql = new SQL();
        this.FNC = new funciones();

        setTitle("Dashboard Module");
        setLayout(ul.getLayOut());
        setPreferredSize(ul.setComponentDimension());
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel Menu = new JPanel();

        ULatinaLayOut nsPanel = new ULatinaLayOut(160, 600, 4);
        Menu.setLayout(nsPanel.getLayOut());
        Menu.setSize(nsPanel.setComponentDimension());
        Menu.setBackground(new Color(48, 44, 43));

        JButton btn_usuarios = new JButton();
        btn_usuarios.setText("Usuarios");
        btn_usuarios.setBounds(nsPanel.getRectangle(140, 30));

        btn_usuarios.addActionListener((ae) -> {
            btnUsuarios_mouseClicked();
        });

        JButton btn_update = new JButton();
        btn_update.setText("Update User");
        btn_update.setBounds(nsPanel.getRectangle(140, 30));

        btn_update.addActionListener((ae) -> {

            try {
                btn_update_mouseClicked();
            } catch (ParseException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        JButton btn_Empleados = new JButton();
        btn_Empleados.setText("Revisar empleado");
        btn_Empleados.setBounds(nsPanel.getRectangle(140, 30));
        btn_Empleados.addActionListener((al) -> {
            btn_RevisarEmpleado();

        });

        Menu.add(btn_usuarios);
        Menu.add(btn_update);
        Menu.add(btn_Empleados);
        add(Menu);
        pack();
        setVisible(true);
    }

    public void btnUsuarios_mouseClicked() {
        ArrayList<String> cols = new ArrayList<>(Arrays.asList("idusuario", "Nombre", "Password"));
        ultmp = new ULatinaLayOut(300, 300, 4);

        JFrame nn = new JFrame();
        nn.setSize(ultmp.setComponentDimension());
        JTable table = FNC.createTable(cols);
        JScrollPane scrollPane = new JScrollPane(table);
        nn.add(scrollPane);
        nn.setVisible(true);

        ResultSet rs = sql.SELECT(""
                + "SELECT `idusuario`, `Nombre`, `Password` "
                + "FROM `Mario_Login`",
                new ArrayList<>()
        );
        if (sql.Exists(rs)) {
            try {
                while (rs.next()) {
                    Object[] result = {
                        rs.getObject("idusuario"),
                        rs.getObject("Nombre"),
                        rs.getObject("Password")
                    };
                    FNC.addrow(table, result);
                }
            } catch (SQLException ex) {
                System.out.println("no object fetch'd");
            }
        }
    }

    public void btn_update_mouseClicked() throws ParseException {

        JLabel lbl_id_actual = new JLabel();
        JTextField txt_id_actual = new JTextField();
        JLabel lbl_cambiar = new JLabel();
        JTextField txt_cambiar = new JTextField();
        JLabel lbl_nombre = new JLabel();
        JTextField txt_nombrenuevo = new JTextField();
        JLabel lbl_edad = new JLabel();
        JTextField txt_edad = new JTextField();
        JLabel lbl_fecha = new JLabel();
        JFormattedTextField txt_fecha = new JFormattedTextField(new FormatoFecha());
        JLabel lbl_amonestaciones = new JLabel();
        JTextField txt_amonestaciones = new JTextField();
        JComboBox jbox_Departamento = new JComboBox();
        JButton btn_updateSQL = new JButton();
        if (current.getCargo().compareTo("DBA") == 0) {
            txt_cambiar.setEditable(true);
        } else {
            txt_cambiar.setEditable(false);
        }
        txt_fecha.setFocusLostBehavior(WIDTH);
        try {
            FormatoFecha fecha = new FormatoFecha();
            fecha.stringToValue(txt_fecha.getText());
            txt_fecha.setValue(new Date());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        ultmp = new ULatinaLayOut(600, 600, 4);
        JFrame tmpFrame = new JFrame("Actulizacion de empleado");
        tmpFrame.setLayout(ultmp.getLayOut());
        tmpFrame.setSize(ultmp.setComponentDimension());
        Object[][] obj0
                = {
                    {lbl_id_actual, 140, 30, "ID Empleado a cambiar"},
                    {txt_id_actual, 140, 30}
                };
        ultmp.setRow(obj0);
        Object[][] obj
                = {
                    {lbl_cambiar, 140, 30, "Codigo Empleado"},
                    {txt_cambiar, 140, 30}
                };
        ultmp.setRow(obj);

        Object[][] obj1
                = {
                    {lbl_nombre, 140, 30, "Nombre Empleado"},
                    {txt_nombrenuevo, 140, 30}
                };
        ultmp.setRow(obj1);
        Object[][] obj2
                = {
                    {lbl_edad, 140, 30, "Edad Empleado"},
                    {txt_edad, 140, 30}
                };
        ultmp.setRow(obj2);
        Object[][] obj3
                = {
                    {jbox_Departamento, 140, 30, "Departamento"},};
        ultmp.setRow(obj3);
        Object[][] obj4
                = {
                    {lbl_fecha, 140, 30, "Fecha de ingreso "},
                    {txt_fecha, 140, 30}
                };
        ultmp.setRow(obj4);
        Object[][] obj5
                = {
                    {lbl_amonestaciones, 140, 30, "Amonestaciones"},
                    {txt_amonestaciones, 140, 30}
                };
        ultmp.setRow(obj5);
        ArrayList<Object> generic = new ArrayList();
        ResultSet rs = sql.SELECT("Select `Departamentos` from `Departamentos_Mario` ", generic);
        try {
            while (rs.next()) {
                generic.add(rs.getObject("Departamentos"));

            }
            for (int i = 0; i < generic.size(); i++) {
                jbox_Departamento.addItem(generic.get(i));

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        generic.clear();
//         ResultSet sr = sql.SELECT("Select `Codigo de empleado` from `Persona_Laboratorio_Mario`", generic);
//        try {
//            while (rs.next()) {
//                generic.add(rs.getObject("Departamentos"));
//
//            }
//          txt_cambiar.setText(generic.toString());
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }

        btn_updateSQL.setText("Actualizar Usuario");
        btn_updateSQL.setBounds(ultmp.getRectangle(220, 30));
        btn_updateSQL.addActionListener((a) -> {

            if (current.getCargo().compareTo("DBA") == 0) {
                ArrayList<Object> objs = new ArrayList<>();

                objs.addAll(Arrays.asList(txt_cambiar.getText(), txt_nombrenuevo.getText(),
                        txt_edad.getText(), (Date) jbox_Departamento.getSelectedItem(),
                        txt_amonestaciones, txt_id_actual.getText()));
                boolean result = sql.exec("UPDATE `Personal_Laboratorio_Mario`"
                        + " SET `Codigo de empleado`=?,"
                        + "`Nombre_Empleado`=?,"
                        + "`Fecha_Ingreso`=?,"
                        + "`Departamento_Empleado`=?,"
                        + "`Numero_Amonestaciones`=?,"
                        + "`Edad_Empleado`=? WHERE `Codigo de empleado`=?", objs);

                if (result) {
                    JOptionPane.showMessageDialog(null, "Usuario modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }

            } else {
                ArrayList<Object> objs = new ArrayList<>();
                objs.addAll(Arrays.asList(txt_nombrenuevo.getText(), txt_fecha.getText(),
                        jbox_Departamento.getSelectedItem(),
                        txt_amonestaciones.getText(), txt_edad.getText(), txt_id_actual.getText()));
                boolean result = sql.exec("UPDATE `Personal_Laboratorio_Mario`"
                        + "SET `Nombre_Empleado`=?,"
                        + " `Fecha_Ingreso`= CAST(? AS DATETIME),"
                        + "`Departamento_Empleado`=?,"
                        + " `Numero_Amonestaciones`=?,"
                        + " `Edad_Empleado`=? WHERE `Codigo_de_empleado`=?", objs);
                //SET `Nombre_Empleado`=?,`Fecha_Ingreso`= CAST(? AS DATETIME),`Departamento_Empleado`=?,`Numero_Amonestaciones`=?,`Edad_Empleado`=? WHERE `Codigo_de_empleado`=?",objs);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Usuario modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }

            }

        });
        tmpFrame.add(lbl_cambiar);
        tmpFrame.add(txt_cambiar);
        tmpFrame.add(lbl_id_actual);
        tmpFrame.add(txt_id_actual);
        tmpFrame.add(lbl_nombre);
        tmpFrame.add(txt_nombrenuevo);
        tmpFrame.add(lbl_edad);
        tmpFrame.add(txt_edad);
        tmpFrame.add(jbox_Departamento);
        tmpFrame.add(btn_updateSQL);
        tmpFrame.add(lbl_fecha);
        tmpFrame.add(txt_fecha);
        tmpFrame.add(lbl_amonestaciones);
        tmpFrame.add(txt_amonestaciones);

        tmpFrame.setVisible(true);
    }

    public void btn_RevisarEmpleado() {
        JLabel lbl_Search = new JLabel();
        JTextField txt_search = new JTextField();
        JLabel lbl_Codigonombre = new JLabel();
        JLabel lbl_Codigo = new JLabel();
        JLabel lbl_Nombrenombre = new JLabel();
        JLabel lbl_Nombre = new JLabel();
        JLabel lbl_Fechanombre = new JLabel();
        JLabel lbl_Fecha = new JLabel();
        JLabel lbl_Departamentonombre = new JLabel();
        JLabel lbl_Departamento = new JLabel();
        JLabel lbl_Edadnombre = new JLabel();
        JLabel lbl_Edad = new JLabel();
        JLabel lbl_Amonestacionesnombre = new JLabel();
        JLabel lbl_Amonestaciones = new JLabel();
        JLabel lbl_Despidonombre = new JLabel();
        JLabel lbl_Despido = new JLabel();
        JButton btn_revisar = new JButton();

        ultmp = new ULatinaLayOut(600, 600, 4);
        JFrame tmpFrame = new JFrame("Actulizacion de empleado");
        tmpFrame.setLayout(ultmp.getLayOut());
        tmpFrame.setSize(ultmp.setComponentDimension());
        Object[][] obj0
                = {
                    {lbl_Search, 140, 30, "ID Empleado:"},
                    {txt_search, 140, 30}
                };
        ultmp.setRow(obj0);
        btn_revisar.setText("Revisar");
        btn_revisar.setBounds(ultmp.getRectangle(220, 30));

        Object[][] obj1
                = {
                    {lbl_Codigonombre, 140, 30, "ID Empleado: "},
                    {lbl_Codigo, 140, 30}
                };
        ultmp.setRow(obj1);
        Object[][] obj2
                = {
                    {lbl_Nombrenombre, 140, 30, "Nombre del Empleado: "},
                    {lbl_Nombre, 140, 30}
                };
        ultmp.setRow(obj2);
        Object[][] obj3
                = {
                    {lbl_Fechanombre, 140, 30, "Fecha de ingreso: "},
                    {lbl_Fecha, 140, 30}
                };
        ultmp.setRow(obj3);
        Object[][] obj4
                = {
                    {lbl_Departamentonombre, 140, 30, "Departamento: "},
                    {lbl_Departamento, 140, 30}
                };
        ultmp.setRow(obj4);
        Object[][] obj5
                = {
                    {lbl_Edadnombre, 140, 30, "Edad: "},
                    {lbl_Edad, 140, 30}
                };
        ultmp.setRow(obj5);
        Object[][] obj6
                = {
                    {lbl_Amonestacionesnombre, 140, 30, "Amonestaciones: "},
                    {lbl_Amonestaciones, 140, 30}
                };
        ultmp.setRow(obj6);
        Object[][] obj7
                = {
                    {lbl_Despidonombre, 140, 30, "Posible despido: "},
                    {lbl_Despido, 140, 30}
                };
        ultmp.setRow(obj7);

        btn_revisar.addActionListener((al) -> {

            ArrayList<Object> objs = new ArrayList<>();
            objs.addAll(Arrays.asList(txt_search.getText()));
            try {
                ResultSet rs = sql.SELECT("Select `Codigo_de_empleado`, `Nombre_Empleado`,"
                        + "`Fecha_Ingreso`,"
                        + "`Departamento_Empleado`,`Numero_Amonestaciones`,"
                        + "`Edad_Empleado`"
                        + "From `Personal_Laboratorio_Mario` WHERE `Codigo_de_empleado`=?", objs);
                if (sql.Exists(rs)) {
                    try {
                        while (rs.next()) {
                            lbl_Codigo.setText(rs.getObject("Codigo_de_empleado").toString());
                            lbl_Nombre.setText(rs.getObject("Nombre_Empleado").toString());
                            lbl_Fecha.setText(rs.getObject("Fecha_Ingreso").toString());
                            lbl_Departamento.setText(rs.getObject("Departamento_Empleado").toString());
                            lbl_Amonestaciones.setText(rs.getObject("Numero_Amonestaciones").toString());
                            lbl_Edad.setText(rs.getObject("Edad_Empleado").toString());
                            if ((Integer) rs.getObject("Numero_Amonestaciones") > 3) {
                                lbl_Despido.setText("SI");
                            } else {
                                lbl_Despido.setText("NO");
                            }
                    
                        }
                    } catch (SQLException ex) {
                        System.out.println("No existe el empleado");
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error" + ex);
            }

        }
        );
        tmpFrame.add(lbl_Search);

        tmpFrame.add(txt_search);

        tmpFrame.add(lbl_Codigonombre);

        tmpFrame.add(lbl_Codigo);

        tmpFrame.add(lbl_Nombrenombre);

        tmpFrame.add(lbl_Nombre);

        tmpFrame.add(lbl_Fechanombre);

        tmpFrame.add(lbl_Fecha);

        tmpFrame.add(lbl_Departamentonombre);

        tmpFrame.add(lbl_Departamento);

        tmpFrame.add(lbl_Edadnombre);

        tmpFrame.add(lbl_Edad);

        tmpFrame.add(lbl_Amonestacionesnombre);

        tmpFrame.add(lbl_Amonestaciones);

        tmpFrame.add(lbl_Despidonombre);

        tmpFrame.add(lbl_Despido);

        tmpFrame.add(btn_revisar);

        tmpFrame.setVisible(
                true);

    }

}
