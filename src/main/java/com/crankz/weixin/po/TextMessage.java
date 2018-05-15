package com.crankz.weixin.po;

public class TextMessage extends BaseMessage {
    private String PicUrl;  // 图片链接（由系统生成）
    private String MediaId; // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MsgId;   // 消息id，64位整型
    private String Content;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
