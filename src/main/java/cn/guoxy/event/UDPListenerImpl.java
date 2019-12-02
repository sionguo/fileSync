package cn.guoxy.event;

import cn.guoxy.global.GlobalSetting;
import cn.guoxy.util.BytesUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.log.Log;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 事件监听实现类
 *
 * @author GuoXiaoyong
 * @date 2019/11/15/015
 * @time 9:14
 */
public class UDPListenerImpl implements AppListener {
    private static final Log log = Log.get();

    @Override
    public void processAppEvent(AppEvent appEvent) {
        if (appEvent.getState().equals(AppEventsEnum.START) || appEvent.getState().equals(AppEventsEnum.STOP)) {
            DatagramSocket socket = null;
            Integer udp_port = GlobalSetting.getInstance().getProps().getInt("udp_port", 4999);
            List<String> ips = GlobalSetting.getInstance().getLocalIPList();
            Integer state = appEvent.getState().getState();
            byte[] arr = BytesUtil.int2Bytes(state);
            try {
                if (!CollectionUtil.isEmpty(ips)) {
                    for (String ip : ips) {
                        log.debug("ip = {}  port = {}", ip, udp_port);
                        if (StringUtils.equals("127.0.0.1", ip)) {
                            continue;
                        }
                        socket = new DatagramSocket(new InetSocketAddress(ip, 0));
                        DatagramPacket packet = new DatagramPacket(arr, arr.length, InetAddress.getByName("255.255.255.255"), udp_port);
                        socket.send(packet);
                        socket.close();
                    }
                } else {
                    socket = new DatagramSocket();
                    DatagramPacket packet = new DatagramPacket(arr, arr.length, InetAddress.getByName("255.255.255.255"), udp_port);
                    socket.send(packet);
                }
            } catch (IOException e) {
                log.error(e);
                if (Objects.nonNull(socket)) {
                    socket.close();
                }
                System.exit(0);
            } finally {
                if (Objects.nonNull(socket)) {
                    socket.close();
                }
            }
        }
    }
}
