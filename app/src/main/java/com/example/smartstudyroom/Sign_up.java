package com.example.smartstudyroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Sign_up extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button bt_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        bt_reg = findViewById(R.id.bt_reg);

        // 회원가입 버튼 클릭 시 수행
       bt_reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어 있는 값을 get
                String id = et_id.getText().toString();
                String pw = et_pass.getText().toString();

                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int code = jsonResponse.getInt("code"); // 성공 키 값

                            if (code == 200){
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다. ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Sign_up.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else if (code == 409){
                                Toast.makeText(getApplicationContext(), "이미 사용중인 사용자가 있습니다. ", Toast.LENGTH_SHORT).show();
                            }
                            else { // 회원가입 실패 한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청하기
                RegisterRequest registerRequest = new RegisterRequest(id, pw, responseListner);
                RequestQueue queue = Volley.newRequestQueue(Sign_up.this);
                queue.add(registerRequest);
            }
        });
    }
}