package com.example.smartstudyroom;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "https://iot.kuro9.dev/iot-comm/api/member/signup";
    private JSONObject jsonParams;

    public RegisterRequest(int id, String pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        jsonParams = new JSONObject();
        try {
            jsonParams.put("id", id);
            jsonParams.put("pw", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return jsonParams.toString().getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }
}
