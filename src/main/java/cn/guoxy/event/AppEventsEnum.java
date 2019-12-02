package cn.guoxy.event;

public enum AppEventsEnum {
    /**
     * 启动
     */
    START("启动", 1),
    /**
     * 停止
     */
    STOP("停止", 2),
    /**
    *
    *@author GuoXiaoyong
    *@date 2019/11/15/015
    *@time 13:59
    */
    SHARE("共享节点",3);

    private String text;
    private Integer state;

    AppEventsEnum(String text, Integer state) {
        this.text = text;
        this.state = state;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


}
