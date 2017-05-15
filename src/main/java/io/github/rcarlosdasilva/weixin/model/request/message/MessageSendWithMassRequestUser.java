package io.github.rcarlosdasilva.weixin.model.request.message;

import java.util.List;

public class MessageSendWithMassRequestUser {

  private List<String> users;
  private String user;

  public MessageSendWithMassRequestUser(List<String> users) {
    super();
    this.users = users;
  }

  public MessageSendWithMassRequestUser(String user) {
    super();
    this.user = user;
  }

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

}
