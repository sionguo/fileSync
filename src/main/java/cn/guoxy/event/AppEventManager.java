package cn.guoxy.event;

import java.util.*;

public class AppEventManager {
    private Collection<AppListener> listeners;

    private volatile static AppEventManager appEventManager;
    private  AppEventManager(){}

    /**
     * 添加监听器
     *
     * @author GuoXiaoyong
     * @date 2019/11/14/014
     */
    private void addAppListener(AppListener appListener) {
        if (Objects.isNull(listeners)) {
            listeners = new HashSet<>();
        }
        listeners.add(appListener);
    }

    /**
     * 移除监听器
     *
     * @author GuoXiaoyong
     * @date 2019/11/14/014
     */
    private void removeAppListener(AppListener appListener) {
        if (Objects.isNull(listeners)) {
            return;
        }
        listeners.remove(appListener);
    }

    public void trigger(AppEventsEnum appEventsEnum) {
        AppEvent appEvent = new AppEvent(this,appEventsEnum);
        notityListeners(appEvent);
    }

    public void trigger(Object source, AppEventsEnum appEventsEnum) {
        AppEvent appEvent = new AppEvent(source,appEventsEnum);
        notityListeners(appEvent);
    }
    /**
     * 通知监听器
     *
     * @author GuoXiaoyong
     * @date 2019/11/15/015
     */
    private void notityListeners(AppEvent event) {
        if (Objects.isNull(listeners)) {
            listeners = Collections.EMPTY_SET;
        }
        Iterator<AppListener> iter = listeners.iterator();
        while (iter.hasNext()) {
            AppListener listener = iter.next();
            listener.processAppEvent(event);
        }
    }

    public static AppEventManager getInstance(){
        if (Objects.isNull(appEventManager)){
            synchronized (AppEventManager.class){
                if (Objects.isNull(appEventManager)){
                    appEventManager = new AppEventManager();
                    appEventManager.addAppListener(new UDPListenerImpl());
                    appEventManager.addAppListener(new TCPListenerImpl());
                }
            }
        }
        return appEventManager;
    }

}
