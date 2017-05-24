package io.github.rcarlosdasilva.weixin.test.temp;

import io.github.rcarlosdasilva.weixin.core.json.Json;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;

public class JsonTest {

  public static void main(String[] args) {
    String jj = "{ \"authorization_info\": {\"authorizer_appid\": \"wxf8b4f85f3a794e77\", \"authorizer_access_token\": \"QXjUqNqfYVH0yBE1iI_7vuN_9gQbpjfK7hYwJ3P7xOa88a89-Aga5x1NMYJyB8G2yKt1KCl0nPC3W9GJzw0Zzq_dBxc8pxIGUNi_bFes0qM\", \"expires_in\": 7200, \"authorizer_refresh_token\": \"dTo-YCXPL4llX-u1W1pPpnp8Hgm4wpJtlR6iV0doKdY\", \"func_info\": [{\"funcscope_category\": {\"id\": 1}}, {\"funcscope_category\": {\"id\": 2}}, {\"funcscope_category\": {\"id\": 3}}]}}";
    
//    String jj = "{\"authorizer_access_token\": \"aaUl5s6kAByLwgV0BhXNuIFFUqfrR8vTATsoSHukcIGqJgrc4KmMJ-JlKoC_-NKCLBvuU1cWPv4vDcLN8Z0pn5I45mpATruU0b51hzeT1f8\", \"expires_in\": 7200, \"authorizer_refresh_token\": \"BstnRqgTJBXb9N2aJq6L5hzfJwP406tpfahQeLNxX0w\"}";
    
//    String jj = "{  \"authorizer_info\": {    \"nick_name\": \"微信SDK Demo Special\",    \"head_img\": \"http://wx.qlogo.cn/mmopen/GPy\",    \"service_type_info\": { \"id\": 2 },    \"verify_type_info\": { \"id\": 0 },    \"user_name\": \"gh_eb5e3a772040\",    \"principal_name\": \"腾讯计算机系统有限公司\",    \"business_info\": { \"open_store\": 0, \"open_scan\": 0, \"open_pay\": 0, \"open_card\": 0, \"open_shake\": 0 },    \"alias\": \"paytest01\",    \"qrcode_url\": \"URL\"  },  \"authorization_info\": {    \"appid\": \"wxf8b4f85f3a794e77\",    \"func_info\": [      { \"funcscope_category\": { \"id\": 1 } },      { \"funcscope_category\": { \"id\": 2 } },      { \"funcscope_category\": { \"id\": 3 } }    ]  }}";
    OpenPlatformAuthGetLicenseInformationResponse response = Json.fromJson(jj,
        OpenPlatformAuthGetLicenseInformationResponse.class);
//    List<OpenPlatformLisensableFunction> funs = response.getLicensingInformation()
//        .getLicencedFunctions();
    System.out.println(response);
  }

}
