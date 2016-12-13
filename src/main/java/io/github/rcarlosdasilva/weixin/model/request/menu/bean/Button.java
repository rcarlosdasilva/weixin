package io.github.rcarlosdasilva.weixin.model.request.menu.bean;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Button {

  private String name;
  private String type;
  private String key;
  private String url;
  @SerializedName("media_id")
  private String mediaId;
  @SerializedName("sub_button")
  private List<SubButton> subButtons;

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public void setSubButtons(List<SubButton> subButtons) {
    this.subButtons = subButtons;
  }

  /**
   * 添加一个子菜单按钮.
   * 
   * @param subButton
   *          按钮
   */
  public void addSubButton(SubButton subButton) {
    if (this.subButtons == null) {
      this.subButtons = Lists.newArrayListWithExpectedSize(5);
    }
    if (this.subButtons.size() < 5) {
      this.subButtons.add(subButton);
    }
  }

}