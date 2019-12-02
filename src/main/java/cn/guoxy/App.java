package cn.guoxy;

import cn.guoxy.event.AppEventManager;
import cn.guoxy.event.AppEventsEnum;
import cn.guoxy.global.GlobalSetting;
import cn.guoxy.listener.TCPListener;
import cn.guoxy.listener.UDPListener;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;

import java.io.IOException;
import java.net.*;

/**
 * Hello world!
 */
public class App {
    private static Log log = Log.get();

    public static void main(String[] args) {
        UDPListener udpListener = new UDPListener();
        ThreadUtil.excAsync(udpListener,false);
        AppEventManager.getInstance().trigger(AppEventsEnum.START);

        TCPListener tcpListener = new TCPListener();
        ThreadUtil.excAsync(tcpListener,false);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("系统即将退出，正在做最后清理");
                AppEventManager.getInstance().trigger(AppEventsEnum.STOP);
            }
        }));
    }
}
