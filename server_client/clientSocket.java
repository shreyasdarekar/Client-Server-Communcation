package server_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class clientSocket {
    InetAddress IP = null;
    int port = 12345;

    public clientSocket() {
        try {
            this.IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Not Able to create client Account..! Try Again");
        }

    }

    public void Connect() {
        try {
            Socket client = new Socket(this.IP, this.port);

            while (true){
                new  Thread(()-> read(client)).start();
                new Thread(()-> send(client)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        }



    void read(Socket client){
        String msg ;
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(client.getInputStream()));

            msg = read.readLine() ;

            System.out.println(msg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void send(Socket client){
        String msg ;
        System.out.println("Enter MSG :");

        try {
            Scanner input = new Scanner(System.in);
            msg = input.nextLine();

            PrintWriter out = new PrintWriter(client.getOutputStream() , true);
            out.println(msg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    }






