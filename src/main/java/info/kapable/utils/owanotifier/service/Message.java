/**
The MIT License (MIT)

Copyright (c) 2017 Mathieu GOULIN

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */package info.kapable.utils.owanotifier.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
  private String id;
  private Date receivedDateTime;
  private Recipient from;
  private Boolean isRead;
  private String subject;
  private String bodyPreview;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Date getReceivedDateTime() {
    return receivedDateTime;
  }
  public void setReceivedDateTime(Date receivedDateTime) {
    this.receivedDateTime = receivedDateTime;
  }
  public Recipient getFrom() {
    return from;
  }
  public void setFrom(Recipient from) {
    this.from = from;
  }
  public Boolean getIsRead() {
    return isRead;
  }
  public void setIsRead(Boolean isRead) {
    this.isRead = isRead;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getBodyPreview() {
    return bodyPreview;
  }
  public void setBodyPreview(String bodyPreview) {
    this.bodyPreview = bodyPreview;
  }
}