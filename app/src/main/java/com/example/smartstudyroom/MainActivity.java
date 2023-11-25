package com.example.smartstudyroom;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText ID, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main", "Created");

        ID = findViewById(R.id.ID);
        Password = findViewById(R.id.Password);
        Button login = (Button) findViewById(R.id.Login);
        Button register = (Button) findViewById(R.id.Register);

        // 회원가입 버튼을 클릭 시 수행
        register.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Sign_up.class);
            startActivity(intent);
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어 있는 값을 get
                String idString = ID.getText().toString();
                String pw = Password.getText().toString();

                try {
                    // 문자열을 정수로 변환
                    int id = Integer.parseInt(idString);

                    Response.Listener<String> responseListener = response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("200"); // 성공 키 값
                            if (success) {
                                String id1 = jsonObject.getString("id");
                                String pw1 = jsonObject.getString("pw");

                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다. ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, NFC_Tag.class);
                                intent.putExtra("id", id1);
                                intent.putExtra("pw", pw1);
                                startActivity(intent);
                            } else { // 로그인 실패 한 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(id, pw, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);

                } catch (NumberFormatException e) {
                    // 정수로 변환할 수 없는 경우 예외 처리
                    Toast.makeText(getApplicationContext(), "유효한 ID를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
