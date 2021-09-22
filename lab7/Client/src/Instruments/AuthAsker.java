package Instruments;

import java.io.Console;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AuthAsker {

    private Scanner scanner;
    String pattern = "(?=\\S+$).{3,}";

    public AuthAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    public String askLogin() {
        String login;
        while (true) {
            try {
                System.out.println("Enter login:");
                System.out.print("> ");
                login = scanner.nextLine().trim();
                if (login.equals("")) {
                    System.out.println("Login cannot be empty!");
                    continue;
                };
                break;
            } catch (NoSuchElementException e) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return login;
    }

    public String askPassword() {
        String password;
        while (true) {
            try {
                System.out.println("Enter password:");
                System.out.print("> ");
                Console console = System.console();
                if (console == null) {
                    password = scanner.nextLine();
                } else {
                    password = String.valueOf(console.readPassword());
                }
                if (password.equals("")) {
                    System.out.println("Password cannot be empty");
                    continue;
                };
                if (!password.matches(pattern)) {
                    System.out.println("Password cannot contain spaces and must have at least 3 characters!");
                    continue;
                };
                break;
            } catch (NoSuchElementException e) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return password;
    }

    public boolean askQuestion(String question) {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(finalQuestion);
                System.out.print("> ");
                answer = scanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) {
                    System.out.println("The answer must be either '+' or '-'");
                    continue;
                };
                break;
            } catch (NoSuchElementException e) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return answer.equals("+");
    }
}
