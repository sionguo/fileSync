package cn.guoxy.event;

import java.util.EventListener;

public interface AppListener extends EventListener {
    void processAppEvent(AppEvent appEvent);
}
