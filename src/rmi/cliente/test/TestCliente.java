package rmi.cliente.test;

import cliente.Cliente;

import java.util.Scanner;

public class TestCliente {
    public static void main(String[] args) throws Exception {
        String op=null;
        int id=1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Ingrese el id del empleado a consultar (0 para salir): ");
            id = scanner.nextInt();
            if (id != 0) {
                String resultado = Cliente.consultar(id);
                System.out.println("Resultado de la consulta: " + resultado);
            } else if (id == 0) {
                System.out.println("Saliendo...");
                break;
            } else {
                System.out.println("Operación cancelada.");
                break;
            }
            System.out.println("¿Desea realizar otra consulta? (s/n): ");
            op = scanner.next();
        } while (!op.equalsIgnoreCase("n") );
    }
}
