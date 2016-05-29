package com.example;

import java.awt.Dimension;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private InetAddress IP;
    private JTextArea textArea;

    public static void main(String[] args){
        Server obj = new Server();
    }

    private void GUI(){
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(500, 550));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(scrollPane);
        this.setSize(400, 300);
        this.setVisible(true);
        textArea.append("IP of my system is : " + IP.getHostAddress() + "\n");
    }

    public Server(){

        try {
            // Detect server ip
            IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waiting to connect......");

            // Create server socket
            servSock = new ServerSocket(8000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
        GUI();
    }

    @Override
    public void run(){
        // Running for waiting multiple client
        while(true){
            try {
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();
                textArea.append("Connected!!\n");
                System.out.println("Connected!!");

                // Transfer data
                while (true){
                    byte[] b = new byte[1024];
                    int length;
                    length = in.read(b);
                    String s = new String(b);
                    System.out.println("[Server Said]" + s);
                    textArea.append("The result from App is " + s);
                }
            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
