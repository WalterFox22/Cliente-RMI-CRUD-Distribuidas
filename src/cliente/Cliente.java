package cliente;

import rmi.servidor.clase.Servidor;
import java.rmi.Naming;
import java.util.List;

public class Cliente {
    private static Servidor getServicio() throws Exception {
        String rmiObjectName = "rmi://localhost/Datos";
        return (Servidor) Naming.lookup(rmiObjectName);
    }

    public static String consultar(int id) throws Exception {
        return getServicio().consultar(id);
    }

    public static boolean insertar(int id, String nombre, String correo, String cargo, String sueldo) throws Exception {
        return getServicio().insertar(id, nombre, correo, cargo, sueldo);
    }

    public static boolean actualizar(int id, String nombre, String correo, String cargo, String sueldo) throws Exception {
        return getServicio().actualizar(id, nombre, correo, cargo, sueldo);
    }

    public static boolean eliminar(int id) throws Exception {
        return getServicio().eliminar(id);
    }

    public static List<String> listar() throws Exception {
        return getServicio().listar();
    }
}