package com.icecream.server.client;

import java.util.Date;

public class ArticleResponse {
  private String message;
  private int msgCode;
  private String title;
  private String link;
  private String content;
  private Date publishedTime;
  private String channelName;

  public ArticleResponse(String message, int msgCode) {
    this.message = message;
    this.msgCode = msgCode;
    this.title = "";
    this.link =  "";
    this.content =  "";
    this.publishedTime =  new Date();
    this.channelName =  "";
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getMsgCode() {
    return msgCode;
  }

  public void setMsgCode(int msgCode) {
    this.msgCode = msgCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getPublishedTime() {
    return publishedTime;
  }

  public void setPublishedTime(Date publishedTime) {
    this.publishedTime = publishedTime;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  @Override
  public String toString() {
    return "ArticleResponse{" +
            "message='" + message + '\'' +
            ", msgCode=" + msgCode +
            ", title='" + title + '\'' +
            ", link='" + link + '\'' +
            ", content='" + content + '\'' +
            ", publishedTime=" + publishedTime +
            ", channelName='" + channelName + '\'' +
            '}';
  }
}
