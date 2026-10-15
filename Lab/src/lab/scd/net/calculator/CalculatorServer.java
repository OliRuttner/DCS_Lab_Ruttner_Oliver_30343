package lab.scd.net.calculator;

import java.io.*;
import java.net.*;

public class CalculatorServer {
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Calculator Server started. Listening on port " + PORT + "...");
        while (true) {
            try (Socket client = serverSocket.accept();
                 DataInputStream in = new DataInputStream(client.getInputStream());
                 DataOutputStream out = new DataOutputStream(client.getOutputStream())) {

                double a = in.readDouble();
                double b = in.readDouble();
                char op = in.readChar();

                String result;
                switch (op) {
                    case '+':
                        result = String.valueOf(a + b);
                        break;
                    case '-':
                        result = String.valueOf(a - b);
                        break;
                    case '*':
                        result = String.valueOf(a * b);
                        break;
                    case '/':
                        result = (b == 0) ? "Error: Division by 0" : String.valueOf(a / b);
                        break;
                    default:
                        result = "Error";
                        break;
                }
                System.out.println("Result: " + result);
                out.writeUTF(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}