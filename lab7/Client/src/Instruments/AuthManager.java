package Instruments;

import data.TransferWrapper;
import data.User;

import java.util.Scanner;

public class AuthManager {
    private final String loginCommand = "sign_in";
    private final String registerCommand = "sign_up";
    private Scanner scanner;

    public AuthManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public TransferWrapper handle() {
        AuthAsker authAsker = new AuthAsker(scanner);
        String command = authAsker.askQuestion("You already have an account?") ? loginCommand : registerCommand;
        User user = new User(authAsker.askLogin(), authAsker.askPassword());
        return new TransferWrapper(command, "", user, 0);
    }
}
