package com.example;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private InetAddress IP;
    private JLabel label0, label1;

    public static void main(String[] args){
        Server obj = new Server();
    }

    private void GUI(){
        label0 = new JLabel();
        label1 = new JLabel();
        label0.setFont(new Font(label0.getFont().getName(), Font.PLAIN, 15));
        label1.setFont(new Font(label0.getFont().getName(), Font.PLAIN, 15));
        label0.setBounds (5, 5, 300, 40);
        label1.setBounds(5, 45, 300, 40);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 100);
        this.setVisible(true);
        this.add(label0);
        this.add(label1);
        label0.setText("IP of my system is : " + IP.getHostAddress() + "\n");
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
                System.out.println("Connected!!");

                // Transfer data
                while (true){
                    byte[] b = new byte[1024];
                    int length;
                    length = in.read(b);
                    String s = new String(b);
                    System.out.println("[Server Said]" + s);
                    label1.setText("The result from App is " + s);
                }
            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
