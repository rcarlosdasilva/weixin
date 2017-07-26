package io.github.rcarlosdasilva.weixin.api.internal.impl;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.MenuApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.menu.MenuCreateRequest;
import io.github.rcarlosdasilva.weixin.model.request.menu.MenuDeleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.menu.MenuQueryCompleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.menu.MenuQueryRequest;
import io.github.rcarlosdasilva.weixin.model.request.menu.MenuTestRequest;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Menu;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuCompleteResponse;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuCreateResponse;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuInfoResponse;

/**
 * 自定义菜单相关API实现
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MenuApiImpl extends BasicApi implements MenuApi {

  public MenuApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public boolean create(Menu menu) {
    Preconditions.checkNotNull(menu);
    Preconditions.checkNotNull(menu.getButtons());

    MenuCreateRequest requestModel = new MenuCreateRequest();
    requestModel.setButtons(menu.getButtons());

    return post(Boolean.class, requestModel);
  }

  @Override
  public long createWithConditional(Menu menu) {
    Preconditions.checkNotNull(menu);
    Preconditions.checkNotNull(menu.getButtons());
    Preconditions.checkNotNull(menu.getMatchRule());

    MenuCreateRequest requestModel = new MenuCreateRequest();
    requestModel.setButtons(menu.getButtons());
    requestModel.setMatchRule(menu.getMatchRule());
    requestModel.withConditional();

    MenuCreateResponse responseModel = post(MenuCreateResponse.class, requestModel);
    return responseModel == null ? Convention.GLOBAL_FAIL_ID : responseModel.getMenuId();
  }

  @Override
  public MenuInfoResponse query() {
    MenuQueryRequest requestModel = new MenuQueryRequest();

    return get(MenuInfoResponse.class, requestModel);
  }

  @Override
  public boolean delete() {
    MenuDeleteRequest requestModel = new MenuDeleteRequest();

    return get(Boolean.class, requestModel);
  }

  @Override
  public boolean deleteWithConditional(long menuId) {
    MenuDeleteRequest requestModel = new MenuDeleteRequest();
    requestModel.setMenuId(menuId);
    requestModel.withConditional();

    return post(Boolean.class, requestModel);
  }

  @Override
  public MenuInfoResponse testWithConditional(String userLabel) {
    MenuTestRequest requestModel = new MenuTestRequest();
    requestModel.setUserLabel(userLabel);

    return post(MenuInfoResponse.class, requestModel);
  }

  @Override
  public MenuCompleteResponse queryComplete() {
    MenuQueryCompleteRequest requestModel = new MenuQueryCompleteRequest();

    return get(MenuCompleteResponse.class, requestModel);
  }

}
