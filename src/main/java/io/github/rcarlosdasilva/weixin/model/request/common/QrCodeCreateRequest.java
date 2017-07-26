package io.github.rcarlosdasilva.weixin.model.request.common;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.QrCodeAction;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 创建二维码请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class QrCodeCreateRequest extends BasicWeixinRequest {

  @SerializedName("expire_seconds")
  private long expireSeconds;
  @SerializedName("action_name")
  private String action;
  @SerializedName("action_info")
  private Map<String, Object> info = Maps.newHashMap();

  public QrCodeCreateRequest() {
    this.path = ApiAddress.URL_COMMON_QR_CREATE;
  }

  /**
   * 过期时间（秒）.
   * 
   * @param expireSeconds
   *          expire
   */
  public void setExpireSeconds(long expireSeconds) {
    this.expireSeconds = expireSeconds;
  }

  /**
   * 二维码类型.
   * 
   * @param action
   *          {@link QrCodeAction}
   */
  public void setAction(QrCodeAction action) {
    this.action = action.toString();
  }

  /**
   * 设置场景值ID.
   * 
   * @param sceneId
   *          scene id
   */
  public void setSceneId(int sceneId) {
    Map<String, Integer> scene = Maps.newHashMap();
    scene.put("scene_id", sceneId);
    info.put("scene", scene);
  }

  /**
   * 设置场景值ID（字符串形式的ID）.
   * 
   * @param sceneString
   *          scene string
   */
  public void setSceneString(String sceneString) {
    Map<String, String> scene = Maps.newHashMap();
    scene.put("scene_str", sceneString);
    info.put("scene", scene);
  }

}
