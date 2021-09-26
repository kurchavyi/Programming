package Client;

import Client.Instruments.ConsoleApp;
import Client.Instruments.UserScanner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static InputStream in;
    private static OutputStream out;

    public static void main(String[] args) {
            UserScanner userScanner = new UserScanner(new Scanner(System.in));
            ConsoleApp consoleApp = new ConsoleApp(userScanner);
            consoleApp.interactiveMode();
    }
}
