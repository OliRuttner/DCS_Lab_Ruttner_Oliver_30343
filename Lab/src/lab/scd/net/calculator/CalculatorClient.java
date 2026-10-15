package lab.scd.net.calculator;

import javax.swing.*;
import java.awt.FlowLayout;
import java.io.*;
import java.net.Socket;

public class CalculatorClient extends JFrame {
    private JTextField num1Field = new JTextField(5);
    private JTextField num2Field = new JTextField(5);
    private JTextField resultField = new JTextField(12);

    public CalculatorClient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(num1Field);
        add(num2Field);

        for (char op : new char[]{'+', '-', '*', '/'}) {
            JButton btn = new JButton(String.valueOf(op));
            btn.addActionListener(e -> calculate(op));
            add(btn);
        }

        resultField.setEditable(false);
        add(resultField);

        pack();
        setVisible(true);
    }

    private void calculate(char op) {
        try (Socket socket = new Socket("localhost", 8000);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeDouble(Double.parseDouble(num1Field.getText()));
            out.writeDouble(Double.parseDouble(num2Field.getText()));
            out.writeChar(op);

            resultField.setText(in.readUTF());
        } catch (Exception ex) {
            resultField.setText("Error");
        }
    }

    public static void main(String[] args) {
        new CalculatorClient();
    }
}