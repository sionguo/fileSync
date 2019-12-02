package cn.guoxy.global;

import cn.guoxy.listener.TCPListener;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TCPServerHandler implements Runnable {
    private Socket socket;

    public TCPServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input =  socket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
