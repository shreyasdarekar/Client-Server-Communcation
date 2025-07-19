package server_client;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class Server {
    InetAddress IP = null;
    int port = 12345;
    Socket[] add = new Socket[2];
    int i = 0;
    String stop ="";

    public Server() {
        try {
            this.IP = InetAddress.getLocalHost();
        } catch (UnknownHostException var2) {
            System.err.println("Error messege :" + String.valueOf(var2));
        }

    }

    public void Server101() {
        try {
            ServerSocket var1 = new ServerSocket(this.port);

            try {
                System.out.println("Server is listening on port " + this.port);

                while(true) {
                    while(true) {
                        try {
                            Socket var2 = var1.accept();
                            PrintStream var10000 = System.out;
                            String var10001 = String.valueOf(var2.getInetAddress());
                            var10000.println("Client connected from " + var10001 + ":" + var2.getPort());
                            String var3 = "Welcome to the server!";
                            var2.getOutputStream().write(var3.getBytes());
                            var2.getOutputStream().flush();

                            try {
                                BufferedReader var4 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
                                String var5 = var4.readLine();
                                System.out.println(var5);
                            } catch (IOException var7) {
                                System.err.println(":" + String.valueOf(var7));
                            }

                            var2.close();
                            var10000 = System.out;
                            var10001 = String.valueOf(var2.getInetAddress());
                            var10000.println("Closed connection with " + var10001 + ":" + var2.getPort());
                        } catch (IOException var8) {
                            System.err.println("Error handling client connection: " + var8.getMessage());
                            var8.printStackTrace();
                        }
                    }
                }
            } catch (Throwable var9) {
                try {
                    var1.close();
                } catch (Throwable var6) {
                    var9.addSuppressed(var6);
                }

                throw var9;
            }
        } catch (IOException var10) {
            System.err.println("Error creating server socket: " + var10.getMessage());
            var10.printStackTrace();
            System.out.println("server_client.Server Exiting");
        }
    }


    public void startServer(){
        try{
            ServerSocket myServer = new ServerSocket(this.port);
            System.out.println("Server is Started on port : " + this.port);
            System.out.println("Wait for client Connection...!");


            try {
                while(true) {
                    Socket clientSocket = myServer.accept();
                    add[i] = clientSocket;
                    i++ ;
                    if(add[1] ==null && add[0]!=null){
                        System.out.println("client"+(i)+" is connected ..!");
                        PrintWriter out = new PrintWriter(add[0].getOutputStream());
                        out.println("Wait for client2 connection..!");
                    }else {
                        PrintWriter out = new PrintWriter(add[0].getOutputStream());
                        out.println("client 2 is connected ..!");
                        System.out.println("client 2 is connected ..!");
                    }


                    if(add[1]!= null && add[0]!=null) {
                        new Thread(() -> handleClient(add[0], add[1])).start();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {

            System.out.println("Failed to start server at port :"+ this.port + "\n Try Again..!");
        }





    }


    public void handleClient(Socket client1 , Socket client2)  {
        System.out.println("System Establish Communication between " +
                "\n client1:"+client1.getRemoteSocketAddress() +
                " & client2:"+client2.getRemoteSocketAddress()
        );


       while (true) {

           if(Objects.equals(stop, "0000")){
               break ;
           }

         new Thread(()-> read(client1,client2)).start();

         new Thread(()-> read(client2,client1) ).start();



       }

        try {
            client1.close();
            client2.close();
            System.out.println("Client 1 & 2 is diconnected ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void read(Socket client1 ,Socket clent2){
        String msg ;
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(client1.getInputStream()));

            msg = read.readLine() ;
            stop = msg ;

            PrintWriter write = new PrintWriter(clent2.getOutputStream() , true);
            write.println(msg);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}

