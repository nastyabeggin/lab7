package clientModule;

import clientModule.util.AuthManager;
import clientModule.util.Console;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        AuthManager authManager = new AuthManager(scanner);
        Console console = new Console(scanner, authManager);
        Client client = new Client(HOST, PORT, console);
        client.run();
        scanner.close();
    }

}
