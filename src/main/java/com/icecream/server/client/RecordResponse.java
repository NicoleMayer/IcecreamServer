package com.icecream.server.client;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data class for record response.
 *
 * @author NicoleMayer
 */
public class RecordResponse {
  private String message;
  private int msgCode;
  private List<ArticleAndRecord> articleAndRecords;
  /**
   * This class is a inner class for record response.
   *
   * @author NicoleMayer
   */
  class ArticleAndRecord{
    String content;
    String record_path;

    public String getContent() { return content; }

    public void setContent(String content) {
      this.content = content;
    }

    public String getRecord_path() {
      return record_path;
    }

    public void setRecord_path(String record_path) {
      this.record_path = record_path;
    }

    /**
     * This is a constructor for ArticleAndRecord class.
     * @param content article's content
     * @param record_path path to store the record info
     */
    public ArticleAndRecord(String content, String record_path) {
      this.content = content;
      this.record_path = record_path;
    }
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

  public List<ArticleAndRecord> getArticleAndRecord() {
    return articleAndRecords;
  }

  public void setArticleAndRecords(List<ArticleAndRecord> articleAndRecords) { this.articleAndRecords = articleAndRecords; }

  /**
   * Add new articleAndRecords to the list variable.
   *
   * @param content article's content
   * @param record_path path to store the record info
   */
  public void addArticleAndRecord(String content, String record_path) {
    this.articleAndRecords.add(new ArticleAndRecord(content, record_path));
  }
  /**
   * This is a constructor for RecordResponse class.
   *
   * @param message response info
   * @param msgCode response number
   */
  public RecordResponse(String message, int msgCode) {
    this.message = message;
    this.msgCode = msgCode;
    this.articleAndRecords = new ArrayList<>();
  }
}
