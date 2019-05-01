package com.icecream.server.client;

import com.icecream.server.entity.RssFeed;

import java.util.Set;

public class FeedsResponse {
  private String message;
  private int msgCode;
  private Set<RssFeed> data;

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

  @Override
  public String toString() {
    return "FeedsResponse{" +
            "message='" + message + '\'' +
            ", msgCode=" + msgCode +
            ", data=" + data +
            '}';
  }
}
