package cn.guoxy.listener;

import cn.guoxy.global.GlobalSetting;
import cn.hutool.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 监听TCP
 *
 * @author GuoXiaoyong
 * @date 2019/11/1/001
 * @time 14:25
 */
public class TCPListener implements Runnable {
    private ServerSocket serverSocket;
    private static Log log = Log.get();

    public TCPListener() {
        try {
            Integer tcp_port = GlobalSetting.getInstance().getProps().getInt("tcp_port", 5000);
            serverSocket = new ServerSocket(tcp_port);
        } catch (IOException e) {
            log.error("端口占用",e);
            System.exit(0);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (Objects.isNull(serverSocket)) {
                break;
            }
            try {
                Socket socket = serverSocket.accept();
                InputStream is =  socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                Set set = (Set) ois.readObject();
                GlobalSetting.getInstance().addMembers(set);

                log.debug("处理完成，当前对等节点列表为{}", GlobalSetting.getInstance().getMembers());
            } catch (IOException e) {
                log.error(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
