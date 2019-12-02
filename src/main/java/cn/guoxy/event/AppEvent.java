package cn.guoxy.event;

import java.util.EventObject;

public class AppEvent extends EventObject {

    private AppEventsEnum state;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AppEvent(Object source, AppEventsEnum state) {
        super(source);
        this.state=state;
    }

    public AppEventsEnum getState() {
        return state;
    }

    public void setState(AppEventsEnum state) {
        this.state = state;
    }
}
