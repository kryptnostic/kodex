package com.kryptnostic.mail.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

import java.io.Serializable;

public class EmailRequest implements Serializable {

  private final Optional<String> from;
  private final Optional<String[]> to;
  private final Optional<String[]> cc;
  private final Optional<String[]> bcc;

  private final Optional<String> subject;
  private final Optional<String> body;

  public final static EmailRequest EMPTY_REQUEST = new EmailRequest(
      Optional.<String>absent(),
      Optional.<String[]>absent(),
      Optional.<String[]>absent(),
      Optional.<String[]>absent(),
      Optional.<String>absent(),
      Optional.<String>absent()
  );

  @JsonCreator
  @JsonIgnoreProperties(ignoreUnknown = true)
  public EmailRequest(
      @JsonProperty(Names.FROM_FIELD) Optional<String> from,
      @JsonProperty(Names.TO_FIELD) Optional<String[]> to,
      @JsonProperty(Names.CC_FIELD) Optional<String[]> cc,
      @JsonProperty(Names.BCC_FIELD) Optional<String[]> bcc,
      @JsonProperty(Names.SUBJECT_FIELD) Optional<String> subject,
      @JsonProperty(Names.BODY_FIELD) Optional<String> body) {

    this.from = from;
    this.to = to;
    this.cc = cc;
    this.bcc = bcc;
    this.subject = subject;
    this.body = body;
  }

  @JsonProperty(Names.FROM_FIELD)
  public Optional<String> getFrom() {
    return from;
  }

  @JsonProperty(Names.TO_FIELD)
  public Optional<String[]> getTo() {
    return to;
  }

  @JsonProperty(Names.CC_FIELD)
  public Optional<String[]> getCc() {
    return cc;
  }

  @JsonProperty(Names.BCC_FIELD)
  public Optional<String[]> getBcc() {
    return bcc;
  }

  @JsonProperty(Names.SUBJECT_FIELD)
  public Optional<String> getSubject() {
    return subject;
  }

  @JsonProperty(Names.BODY_FIELD)
  public Optional<String> getBody() {
    return body;
  }

}
