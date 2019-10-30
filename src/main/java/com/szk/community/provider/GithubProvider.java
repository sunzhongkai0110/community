package com.szk.community.controller.provider;

import com.alibaba.fastjson.JSON;
import com.szk.community.controller.dto.AccessTokenDTO;
import com.szk.community.controller.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            //将access_token=1af7e66f6b3e936ddcf2865dfcfbd7a0f1fd5544&scope=user&token_type=bearer拆分
            //先通过"&"进行拆分,再通过"="进行拆分
            String token=string.split("&")[0].split("=")[1];
            //String[] split = string.split("&");
            //String tokenStr = split[0];
            //String token = tokenStr.split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //把String的json对象自动转换解析成java类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }
}
