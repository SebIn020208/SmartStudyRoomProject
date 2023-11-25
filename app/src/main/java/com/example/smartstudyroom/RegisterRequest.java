package com.example.smartstudyroom;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    // 서버 URL 설정 ( PHP 파일 연동)
    final static private String URL = "http://iot.kuro9.dev/iot-comm/api/member/signup";
    private Map<String, String> map;

    public  RegisterRequest(String id, String pw, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("pw", pw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
