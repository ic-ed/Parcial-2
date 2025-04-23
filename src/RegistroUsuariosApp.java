import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

record User(String name, String email, String password) {

    @Override
    public String toString() {
        return String.format("Nombre: %s | Correo: %s", name, email);
    }
}

class Validator {
    static final Pattern NAME = Pattern.compile("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$");
    static final Pattern EMAIL = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[a-z].*[a-z].*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");

    static boolean validName(String s) { return NAME.matcher(s).matches(); }
    static boolean validEmail(String s) { return EMAIL.matcher(s).matches(); }
    static boolean validPassword(String s) { return PASSWORD.matcher(s).matches(); }
}

class UserRegistry {
    final List<User> users = new ArrayList<>();

    boolean register(String name, String email, String password) {
        if (!Validator.validName(name) || !Validator.validEmail(email) || !Validator.validPassword(password)) {
            return false;
        }
        users.add(new User(name, email, password));
        return true;
    }

    void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        users.forEach(System.out::println);
    }
}

public class RegistroUsuariosApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRegistry registry = new UserRegistry();

        System.out.println("\n=== Registro de Usuarios ===\nEscribe 'exit' como nombre para salir.\n");

        while (true) {
            System.out.print("Nombre completo: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) break;

            System.out.print("Correo electrónico: ");
            String email = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            if (registry.register(name, email, password)) {
                System.out.println("✔ Registro exitoso\n");
            } else {
                System.out.println("✖ Datos inválidos. Inténtalo de nuevo.\n");
            }
        }

        System.out.println("\n=== Lista de usuarios registrados ===");
        registry.listUsers();
        scanner.close();
    }
}
