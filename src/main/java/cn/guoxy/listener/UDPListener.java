package cn.guoxy.listener;

import cn.guoxy.event.AppEventManager;
import cn.guoxy.event.AppEventsEnum;
import cn.guoxy.global.GlobalSetting;
import cn.guoxy.model.Member;
import cn.guoxy.util.BytesUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.Objects;

public class UDPListener implements Runnable {
    private DatagramSocket socket;
    private static Log log = Log.get();

    public UDPListener() {
        Integer udp_port = GlobalSetting.getInstance().getProps().getInt("udp_port", 4999);
        try {
            socket = new DatagramSocket(udp_port);
        } catch (SocketException e) {
            log.error("端口{}占用,建立连接失败", udp_port);
            System.exit(0);
        }
    }

    @Override
    public void run() {
        log.info("持续监听UDP请求中···");
        while (true) {
            DatagramPacket packet;
            if (!Objects.isNull(socket)) {
                byte[] buf = new byte[4096];
                packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                    int state = BytesUtil.bytes2Int(packet.getData());
                    log.debug("state = {}", state);
                    String clientIp = packet.getAddress().getHostAddress();
                    log.info("收到UDP请求，nodeIp = {}", clientIp);
                    log.debug("clientIp >> {}", clientIp);
                    List<String> locals = GlobalSetting.getInstance().getLocalIPList();
                    log.debug("locals {}", locals);
                    boolean flag = false;
                    for (String local : locals) {
                        if (StringUtils.equals(local, clientIp)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        log.info("收到本机IP，不加以处理,ip={}", clientIp);
                        continue;
                    }
                    Integer tcp_port = GlobalSetting.getInstance().getProps().getInt("tcp_port", 5000);
                    Member member = new Member();
                    member.setIp(clientIp);
                    member.setPort(tcp_port);
                    if (state == AppEventsEnum.START.getState()) {
                        GlobalSetting.getInstance().addMember(member);
                        AppEventManager.getInstance().trigger(member, AppEventsEnum.SHARE);
                    } else if (AppEventsEnum.STOP.getState() == state) {
                        GlobalSetting.getInstance().removeMember(member);
                    }
                } catch (IOException e) {
                    log.error(e);
                }
            }
            log.debug("处理完成，当前对等节点列表为{}", GlobalSetting.getInstance().getMembers());
        }

    }

    public static void main(String[] args) {
        ThreadUtil.excAsync(new UDPListener(), false);
    }


}
