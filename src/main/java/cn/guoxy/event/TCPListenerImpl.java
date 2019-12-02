package cn.guoxy.event;

import cn.guoxy.global.GlobalSetting;
import cn.guoxy.model.Member;
import cn.hutool.log.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPListenerImpl implements AppListener {
    private static final Log log = Log.get();
    @Override
    public void processAppEvent(AppEvent appEvent) {
        if (appEvent.getState().equals(AppEventsEnum.SHARE)){
            Member member = (Member) appEvent.getSource();
            try {
                Socket socket = new Socket(member.getIp(),member.getPort());
                OutputStream os =  socket.getOutputStream();
                ObjectOutputStream oob = new ObjectOutputStream(os);
                oob.writeObject(GlobalSetting.getInstance().getMembers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
