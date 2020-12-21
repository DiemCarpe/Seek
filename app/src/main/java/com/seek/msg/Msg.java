package com.seek.msg;

//消息类
public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    //消息内容
    public String getContent() {
        return content;
    }

    //消息类型
    public int getType() {
        return type;
    }
}
