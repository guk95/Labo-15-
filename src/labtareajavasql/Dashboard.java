/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labtareajavasql;

import labtareajavasql.funciones;
import labtareajavasql.SQL;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
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
import javax.swing.SpringLayout;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 *
 * @author jorge.vasquez
 */
public class Dashboard extends JFrame {

    public ULatinaLayOut ul, ultmp;
    private final SQL sql;
    private final funciones FNC;
    UsuarioLogueado current = new UsuarioLogueado();
    JPanel GenericPanel = new JPanel();
    JPanel Menu = new JPanel();
    ULatinaLayOut GenericnsPanel = new ULatinaLayOut(600, 600, 4);

    public Dashboard() {
        this.ul = new ULatinaLayOut(800, 600, 4);
        this.sql = new SQL();
        this.FNC = new funciones();
        Object[][] obj1
                = {
                    {Menu, 160, 600, 4},
                    {GenericPanel, 640, 600, 4}
                };
        ul.setRow(obj1);

        setTitle("Dashboard Module");
        setLayout(ul.getLayOut());
        setPreferredSize(ul.setComponentDimension());
        setResizable(false);
        initComponents();
    }

    private void initComponents() {

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
        add(GenericPanel);
        pack();
        setVisible(true);
//            Object[][] obj1
//                = {
//                    {Menu, 160, 600,4},
//                    {GenericPanel, 400, 600,4}
//                };
//       nsPanel.setRow(obj1);

    }

    public void btnUsuarios_mouseClicked() {

        GenericPanel.removeAll();
        GenericPanel.updateUI();
        super.paintAll(getGraphics());
        ULatinaLayOut GenericnsPanel = new ULatinaLayOut(600, 600, 4);
        ArrayList<String> cols = new ArrayList<>(Arrays.asList("idusuario", "Nombre", "Password"));
        ;
        GenericPanel.setSize(GenericnsPanel.setComponentDimension());
        JTable table = FNC.createTable(cols);
        JScrollPane scrollPane = new JScrollPane(table);

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
        GenericPanel.add(scrollPane);
    }

    public void btn_update_mouseClicked() throws ParseException {
        GenericPanel.removeAll();
        GenericPanel.updateUI();
        super.paintAll(getGraphics());
        ULatinaLayOut GenericnsPanel = new ULatinaLayOut(600, 600, 4);

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

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        GenericPanel.setLayout(GenericnsPanel.getLayOut());
        GenericPanel.setSize(GenericnsPanel.setComponentDimension());

        Object[][] obj0
                = {
                    {lbl_id_actual, 140, 30, "ID Empleado a cambiar"},
                    {txt_id_actual, 140, 30}
                };
        GenericnsPanel.setRow(obj0);
        Object[][] obj
                = {
                    {lbl_cambiar, 140, 30, "Codigo Empleado"},
                    {txt_cambiar, 140, 30}
                };
        GenericnsPanel.setRow(obj);

        Object[][] obj1
                = {
                    {lbl_nombre, 140, 30, "Nombre Empleado"},
                    {txt_nombrenuevo, 140, 30}
                };
        GenericnsPanel.setRow(obj1);
        Object[][] obj2
                = {
                    {lbl_edad, 140, 30, "Edad Empleado"},
                    {txt_edad, 140, 30}
                };
        GenericnsPanel.setRow(obj2);
        Object[][] obj3
                = {
                    {jbox_Departamento, 140, 30, "Departamento"},};
        GenericnsPanel.setRow(obj3);
        Object[][] obj4
                = {
                    {lbl_fecha, 140, 30, "Fecha de ingreso "},
                    {txt_fecha, 140, 30}
                };
        GenericnsPanel.setRow(obj4);
        Object[][] obj5
                = {
                    {lbl_amonestaciones, 140, 30, "Amonestaciones"},
                    {txt_amonestaciones, 140, 30}
                };
        GenericnsPanel.setRow(obj5);
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
        txt_id_actual.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ArrayList<Object> objs = new ArrayList<>();
                objs.addAll(Arrays.asList(txt_id_actual.getText()));
                try {
                    ResultSet rs = sql.SELECT("Select `Codigo_de_empleado`, `Nombre_Empleado`,"
                            + "`Fecha_Ingreso`,"
                            + "`Departamento_Empleado`,`Numero_Amonestaciones`,"
                            + "`Edad_Empleado`"
                            + "From `Personal_Laboratorio_Mario` WHERE `Codigo_de_empleado`=?", objs);
                    if (sql.Exists(rs)) {
                        try {
                            while (rs.next()) {
                                txt_cambiar.setText(rs.getObject("Codigo_de_empleado").toString());
                                txt_nombrenuevo.setText(rs.getObject("Nombre_Empleado").toString());
                                txt_fecha.setText(rs.getObject("Fecha_Ingreso").toString());
                                txt_amonestaciones.setText(rs.getObject("Numero_Amonestaciones").toString());
                                txt_edad.setText(rs.getObject("Edad_Empleado").toString());

                            }
                        } catch (SQLException ex) {
                            System.out.println("No existe el empleado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "EL usuario no existe");
                        txt_cambiar.setText("");
                        txt_nombrenuevo.setText("");
                        txt_amonestaciones.setText("");
                        txt_edad.setText("");

                    }
                } catch (Exception ex) {
                    System.out.println("Error" + ex);

                }

            }
        });
        btn_updateSQL.setText("Actualizar Usuario");
        btn_updateSQL.setBounds(GenericnsPanel.getRectangle(220, 30));
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
        GenericPanel.add(lbl_cambiar);
        GenericPanel.add(txt_cambiar);
        GenericPanel.add(lbl_id_actual);
        GenericPanel.add(txt_id_actual);
        GenericPanel.add(lbl_nombre);
        GenericPanel.add(txt_nombrenuevo);
        GenericPanel.add(lbl_edad);
        GenericPanel.add(txt_edad);
        GenericPanel.add(jbox_Departamento);
        GenericPanel.add(btn_updateSQL);
        GenericPanel.add(lbl_fecha);
        GenericPanel.add(txt_fecha);
        GenericPanel.add(lbl_amonestaciones);
        GenericPanel.add(txt_amonestaciones);

    }

    public void btn_RevisarEmpleado() {
        GenericPanel.removeAll();
        GenericPanel.updateUI();
        super.paintAll(getGraphics());
        ULatinaLayOut GenericnsPanel = new ULatinaLayOut(600, 600, 4);

        UsuarioLogueado current = new UsuarioLogueado();
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
        JLabel lbl_espacio = new JLabel();
        JLabel lbl_Autorizar = new JLabel();
        JTextField txt_autorizar = new JTextField();
        JButton btn_autorizar = new JButton();
        if (current.getCargo().compareTo("DBA") == 0 || current.getCargo().compareTo("Admin") == 0) {
            txt_autorizar.setVisible(true);
            lbl_Autorizar.setVisible(true);
            btn_autorizar.setVisible(true);
        } else {
            txt_autorizar.setVisible(false);
            lbl_Autorizar.setVisible(false);
            btn_autorizar.setVisible(false);
        }
        txt_search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
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
                    } else {
                        JOptionPane.showMessageDialog(null, "EL usuario no existe");
                        lbl_Codigo.setText("");
                        lbl_Nombre.setText("");
                        lbl_Fecha.setText("");
                        lbl_Departamento.setText("");
                        lbl_Amonestaciones.setText("");
                        lbl_Edad.setText("");
                        lbl_Despido.setText("");

                    }
                } catch (Exception ex) {
                    System.out.println("Error" + ex);

                }

            }
        });
//        txt_search.addActionListener((al) -> {
//
//            ArrayList<Object> objs = new ArrayList<>();
//            objs.addAll(Arrays.asList(txt_search.getText()));
//            try {
//                ResultSet rs = sql.SELECT("Select `Codigo_de_empleado`, `Nombre_Empleado`,"
//                        + "`Fecha_Ingreso`,"
//                        + "`Departamento_Empleado`,`Numero_Amonestaciones`,"
//                        + "`Edad_Empleado`"
//                        + "From `Personal_Laboratorio_Mario` WHERE `Codigo_de_empleado`=?", objs);
//                if (sql.Exists(rs)) {
//                    try {
//                        while (rs.next()) {
//                            lbl_Codigo.setText(rs.getObject("Codigo_de_empleado").toString());
//                            lbl_Nombre.setText(rs.getObject("Nombre_Empleado").toString());
//                            lbl_Fecha.setText(rs.getObject("Fecha_Ingreso").toString());
//                            lbl_Departamento.setText(rs.getObject("Departamento_Empleado").toString());
//                            lbl_Amonestaciones.setText(rs.getObject("Numero_Amonestaciones").toString());
//                            lbl_Edad.setText(rs.getObject("Edad_Empleado").toString());
//                            if ((Integer) rs.getObject("Numero_Amonestaciones") > 3) {
//                                lbl_Despido.setText("SI");
//                            } else {
//                                lbl_Despido.setText("NO");
//                            }
//
//                        }
//                    } catch (SQLException ex) {
//                        System.out.println("No existe el empleado");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "EL usuario no existe");
//                    lbl_Codigo.setText("");
//                    lbl_Nombre.setText("");
//                    lbl_Fecha.setText("");
//                    lbl_Departamento.setText("");
//                    lbl_Amonestaciones.setText("");
//                    lbl_Edad.setText("");
//                    lbl_Despido.setText("");
//
//                }
//            } catch (Exception ex) {
//                System.out.println("Error" + ex);
//
//            }

//        });
        GenericPanel.setLayout(GenericnsPanel.getLayOut());
        GenericPanel.setSize(GenericnsPanel.setComponentDimension());

        Object[][] obj0
                = {
                    {lbl_Search, 140, 30, "ID Empleado:"},
                    {txt_search, 140, 30}
                };
        GenericnsPanel.setRow(obj0);
        btn_revisar.setText("Revisar");
        btn_revisar.setBounds(GenericnsPanel.getRectangle(220, 30));

        Object[][] obj1
                = {
                    {lbl_Codigonombre, 140, 30, "ID Empleado: "},
                    {lbl_Codigo, 140, 30}
                };
        GenericnsPanel.setRow(obj1);
        Object[][] obj2
                = {
                    {lbl_Nombrenombre, 140, 30, "Nombre del Empleado: "},
                    {lbl_Nombre, 140, 30}
                };
        GenericnsPanel.setRow(obj2);
        Object[][] obj3
                = {
                    {lbl_Fechanombre, 140, 30, "Fecha de ingreso: "},
                    {lbl_Fecha, 140, 30}
                };
        GenericnsPanel.setRow(obj3);
        Object[][] obj4
                = {
                    {lbl_Departamentonombre, 140, 30, "Departamento: "},
                    {lbl_Departamento, 140, 30}
                };
        GenericnsPanel.setRow(obj4);
        Object[][] obj5
                = {
                    {lbl_Edadnombre, 140, 30, "Edad: "},
                    {lbl_Edad, 140, 30}
                };
        GenericnsPanel.setRow(obj5);
        Object[][] obj6
                = {
                    {lbl_Amonestacionesnombre, 140, 30, "Amonestaciones: "},
                    {lbl_Amonestaciones, 140, 30}
                };
        GenericnsPanel.setRow(obj6);
        Object[][] obj7
                = {
                    {lbl_Despidonombre, 140, 30, "Posible despido: "},
                    {lbl_Despido, 140, 30}
                };
        GenericnsPanel.setRow(obj7);
        Object[][] obj8
                = {
                    {lbl_Autorizar, 140, 30, "Autorizar a: "},
                    {txt_autorizar, 140, 30}
                };
        GenericnsPanel.setRow(obj8);
        btn_autorizar.setText("Autorizar");
        btn_autorizar.setBounds(GenericnsPanel.getRectangle(220, 30));

        btn_autorizar.addActionListener((al) -> {
            ArrayList<Object> autorizar = new ArrayList<>();
            autorizar.addAll(Arrays.asList(txt_autorizar.getText()));

            boolean result = sql.exec("Update `Mario_Login` SET `Lock`=1 WHERE `idusuario`=?", autorizar);

            if (result) {
                JOptionPane.showMessageDialog(null, "El usuario ya puede loguear");
            } else {
                JOptionPane.showMessageDialog(null, "Error contacte al DBA");
            }

        });
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
                } else {
                    JOptionPane.showMessageDialog(null, "EL usuario no existe");
                    lbl_Codigo.setText("");
                    lbl_Nombre.setText("");
                    lbl_Fecha.setText("");
                    lbl_Departamento.setText("");
                    lbl_Amonestaciones.setText("");
                    lbl_Edad.setText("");
                    lbl_Despido.setText("");
                }
            } catch (Exception ex) {
                System.out.println("Error" + ex);
            }

        }
        );
        GenericPanel.add(lbl_Search);

        GenericPanel.add(txt_search);

        GenericPanel.add(lbl_Codigonombre);

        GenericPanel.add(lbl_Codigo);

        GenericPanel.add(lbl_Nombrenombre);

        GenericPanel.add(lbl_Nombre);

        GenericPanel.add(lbl_Fechanombre);

        GenericPanel.add(lbl_Fecha);

        GenericPanel.add(lbl_Departamentonombre);

        GenericPanel.add(lbl_Departamento);

        GenericPanel.add(lbl_Edadnombre);

        GenericPanel.add(lbl_Edad);

        GenericPanel.add(lbl_Amonestacionesnombre);

        GenericPanel.add(lbl_Amonestaciones);

        GenericPanel.add(lbl_Despidonombre);

        GenericPanel.add(lbl_Despido);

        GenericPanel.add(btn_revisar);
        GenericPanel.add(lbl_Autorizar);
        GenericPanel.add(txt_autorizar);
        GenericPanel.add(btn_autorizar);

    }

}
