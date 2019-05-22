package com.icecream.server.client;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is a data class for article response.
 *
 * @author NicoleMayer
 */
public class ArticleResponse {
  private String message;
  private int msgCode;
  private String title;
  private String link;
  private String content;
  private Date publishedTime;
  private String channelUrl;

  /**
   * This is a constructor for ArticleResponse class.
   */
  public ArticleResponse() {
  }

  /**
   * This is a constructor for ArticleResponse class.
   *
   * @param message The description of the response.
   * @param msgCode The number for this message.
   */
  public ArticleResponse(String message, int msgCode) {
    this.message = message;
    this.msgCode = msgCode;
    this.title = "";
    this.link = "";
    this.content = "";
    this.publishedTime = new Date();
    this.channelUrl = "";
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

  public String getChannelUrl() { return channelUrl; }

  public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }

  /**
   * String representation of the article response.
   *
   * @return String stands for the article response.
   */
  @Override
  public String toString() {
    return "ArticleResponse{"
            + "message='" + message + '\''
            + ", msgCode=" + msgCode
            + ", title='" + title + '\''
            + ", link='" + link + '\''
            + ", content='" + content + '\''
            + ", publishedTime=" + new SimpleDateFormat("dd-MM-yyyy").format(publishedTime) + '\''
            + ", channelName='" + channelUrl + '\''
            + '}';
  }
}
