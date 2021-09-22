package Client;

import Instruments.AuthManager;
import Instruments.ConsoleApp;
import Instruments.UserScanner;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserScanner  userScanner = new UserScanner(scanner);
        AuthManager authManager = new AuthManager(scanner);
        ConsoleApp consoleApp = new ConsoleApp(userScanner, authManager);
        consoleApp.interactiveMode();
    }
}
