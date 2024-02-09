package org.example;

import util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;

public class ClientApp {

    private String serverAddress;
    private int port;

    public ClientApp(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public static void main(String[] args) {
        new ClientApp("localhost", 8888).run();
    }


    void run() {
        try(Socket socket = new Socket(serverAddress, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Prompt prompt = new Prompt(System.in)) {
            while (true) {
                String data = in.readUTF();
                if (data.equals("exit")) {
                    System.out.println("goodbye");
                    break;
                }
                System.out.println(data);
                out.writeUTF(prompt.input());

            }

        } catch (Exception e) {
            System.out.println("클라이언트 실행 오류");
            e.printStackTrace();
        }
    }
}