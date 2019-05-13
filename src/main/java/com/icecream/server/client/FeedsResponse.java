package com.icecream.server.client;

import com.icecream.server.entity.RssFeed;

import java.util.Set;

/**
 * This class is a data class for channel response.
 *
 * @author NicoleMayer
 */
public class FeedsResponse {
  private String message;
  private int msgCode;
  private Set<RssFeed> data;

  /**
   * This is a constructor for FeedsResponse class.
   */
  public FeedsResponse() {
  }

  /**
   * This is a constructor for FeedsResponse class.
   *
   * @param message The description of the response.
   * @param msgCode The number for this message.
   * @param data    The data of rss feed list if succeed.
   */
  public FeedsResponse(String message, int msgCode, Set<RssFeed> data) {
    this.message = message;
    this.msgCode = msgCode;
    this.data = data;
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

  public Set<RssFeed> getData() {
    return data;
  }

  public void setData(Set<RssFeed> data) {
    this.data = data;
  }

  /**
   * String representation of the channel response.
   *
   * @return String stands for the channel response.
   */
  @Override
  public String toString() {
    return "FeedsResponse{"
            + "message='" + message + '\''
            + ", msgCode=" + msgCode
            + ", data=" + data
            + '}';
  }
}
