package rmi.cliente.vista;

import cliente.Cliente;

import javax.swing.*;
import java.util.List;

public class Vista extends JFrame {
    private JTextField txtClave, txtNombre, txtCorreo, txtCargo, txtSueldo;
    private JButton btnConsultar, btnAccion, btnLimpiar;
    private JComboBox<String> comboAccion;
    private JLabel lblNombre, lblCorreo, lblCargo, lblSueldo;

    private boolean modoActualizar = false;

    public Vista() {
        setTitle("CRUD Empleados");
        setSize(520, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setBounds(30, 30, 80, 25);
        add(lblClave);

        txtClave = new JTextField();
        txtClave.setBounds(120, 30, 120, 25);
        add(txtClave);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(260, 30, 100, 25);
        add(btnConsultar);

        JLabel lblAccion = new JLabel("Acción:");
        lblAccion.setBounds(30, 70, 80, 25);
        add(lblAccion);

        comboAccion = new JComboBox<>(new String[]{"Seleccionar", "Insertar", "Actualizar", "Eliminar", "Listar"});
        comboAccion.setBounds(120, 70, 120, 25);
        add(comboAccion);

        // Campos de datos
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 110, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 110, 200, 25);
        add(txtNombre);

        lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 150, 80, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(120, 150, 200, 25);
        add(txtCorreo);

        lblCargo = new JLabel("Cargo:");
        lblCargo.setBounds(30, 190, 80, 25);
        add(lblCargo);

        txtCargo = new JTextField();
        txtCargo.setBounds(120, 190, 200, 25);
        add(txtCargo);

        lblSueldo = new JLabel("Sueldo:");
        lblSueldo.setBounds(30, 230, 80, 25);
        add(lblSueldo);

        txtSueldo = new JTextField();
        txtSueldo.setBounds(120, 230, 200, 25);
        add(txtSueldo);

        btnAccion = new JButton("Ejecutar");
        btnAccion.setBounds(350, 70, 100, 25);
        add(btnAccion);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(350, 110, 100, 25);
        add(btnLimpiar);

        mostrarCampos(false, false, false, false);
        habilitarCampos(true, false, false, false, false);

        btnConsultar.addActionListener(e -> consultarEmpleado());
        btnAccion.addActionListener(e -> ejecutarAccion());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        comboAccion.addActionListener(e -> actualizarVistaPorAccion());
    }

    private void actualizarVistaPorAccion() {
        String accion = (String) comboAccion.getSelectedItem();
        limpiarCampos();
        modoActualizar = false;
        switch (accion) {
            case "Insertar":
                mostrarCampos(true, true, true, true);
                habilitarCampos(true, true, true, true, true);
                btnAccion.setText("Insertar");
                break;
            case "Actualizar":
                mostrarCampos(true, true, true, true);
                habilitarCampos(true, false, false, false, false);
                btnAccion.setText("Actualizar");
                modoActualizar = true;
                break;
            case "Eliminar":
                mostrarCampos(false, false, false, false);
                habilitarCampos(true, false, false, false, false);
                btnAccion.setText("Eliminar");
                break;
            case "Listar":
                mostrarCampos(false, false, false, false);
                btnAccion.setText("Listar");
                listarEmpleados();
                break;
            default:
                mostrarCampos(false, false, false, false);
                btnAccion.setText("Ejecutar");
        }
    }

    private void mostrarCampos(boolean nombre, boolean correo, boolean cargo, boolean sueldo) {
        lblNombre.setVisible(nombre);
        txtNombre.setVisible(nombre);
        lblCorreo.setVisible(correo);
        txtCorreo.setVisible(correo);
        lblCargo.setVisible(cargo);
        txtCargo.setVisible(cargo);
        lblSueldo.setVisible(sueldo);
        txtSueldo.setVisible(sueldo);
    }

    private void habilitarCampos(boolean clave, boolean nombre, boolean correo, boolean cargo, boolean sueldo) {
        txtClave.setEnabled(clave);
        txtNombre.setEditable(nombre);
        txtCorreo.setEditable(correo);
        txtCargo.setEditable(cargo);
        txtSueldo.setEditable(sueldo);
    }

    private void consultarEmpleado() {
        try {
            int id = Integer.parseInt(txtClave.getText());
            String resultado = Cliente.consultar(id);
            if (resultado != null && !resultado.isEmpty()) {
                String[] datos = resultado.split(",");
                if (datos.length == 4) {
                    txtNombre.setText(datos[0]);
                    txtCorreo.setText(datos[1]);
                    txtCargo.setText(datos[2]);
                    txtSueldo.setText(datos[3]);
                    mostrarCampos(true, true, true, true);
                    if (modoActualizar) {
                        habilitarCampos(false, true, true, true, true);
                    } else {
                        habilitarCampos(false, false, false, false, false);
                    }
                } else {
                    mostrarError("Formato de datos incorrecto.");
                }
            } else {
                mostrarError("No se encontró el empleado.");
            }
        } catch (NumberFormatException ex) {
            mostrarError("Ingrese un ID válido.");
        } catch (Exception ex) {
            mostrarError("Error al consultar: " + ex.getMessage());
        }
    }

    private void ejecutarAccion() {
        String accion = (String) comboAccion.getSelectedItem();
        switch (accion) {
            case "Insertar":
                insertarEmpleado();
                break;
            case "Actualizar":
                actualizarEmpleado();
                break;
            case "Eliminar":
                eliminarEmpleado();
                break;
            case "Listar":
                listarEmpleados();
                break;
            default:
                mostrarError("Seleccione una acción válida.");
        }
    }

    private void insertarEmpleado() {
        try {
            int id = Integer.parseInt(txtClave.getText());
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String cargo = txtCargo.getText();
            String sueldo = txtSueldo.getText();
            boolean exito = Cliente.insertar(id, nombre, correo, cargo, sueldo);
            if (exito) {
                mostrarInfo("Empleado insertado correctamente.");
                limpiarCampos();
            } else {
                mostrarError("No se pudo insertar el empleado.");
            }
        } catch (Exception ex) {
            mostrarError("Error al insertar: " + ex.getMessage());
        }
    }

    private void actualizarEmpleado() {
        try {
            int id = Integer.parseInt(txtClave.getText());
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String cargo = txtCargo.getText();
            String sueldo = txtSueldo.getText();
            boolean exito = Cliente.actualizar(id, nombre, correo, cargo, sueldo);
            if (exito) {
                mostrarInfo("Empleado actualizado correctamente.");
                limpiarCampos();
            } else {
                mostrarError("No se pudo actualizar el empleado.");
            }
        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarEmpleado() {
        try {
            int id = Integer.parseInt(txtClave.getText());
            boolean exito = Cliente.eliminar(id);
            if (exito) {
                mostrarInfo("Empleado eliminado correctamente.");
                limpiarCampos();
            } else {
                mostrarError("No se pudo eliminar el empleado.");
            }
        } catch (Exception ex) {
            mostrarError("Error al eliminar: " + ex.getMessage());
        }
    }

    private void listarEmpleados() {
        try {
            List<String> empleados = Cliente.listar();
            if (empleados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay empleados registrados.", "Lista de empleados", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("<html><body>");
                for (String emp : empleados) {
                    sb.append(emp).append("<br>");
                }
                sb.append("</body></html>");
                JLabel label = new JLabel(sb.toString());
                JOptionPane.showMessageDialog(this, label, "Lista de empleados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            mostrarError("Error al listar: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtClave.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtCargo.setText("");
        txtSueldo.setText("");
        mostrarCampos(false, false, false, false);
        habilitarCampos(true, false, false, false, false);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Vista vista = new Vista();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.setResizable(false);
    }
}