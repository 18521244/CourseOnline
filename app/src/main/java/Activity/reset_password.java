package Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tutorial_v1.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.RetrofitClient;
import  Retrofit.IMyService;

public class reset_password extends AppCompatActivity {
    EditText token, newpassword;
    TextView back;
    Button update;
    String mail,toke,np;
    IMyService iMyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        token = findViewById(R.id.Token);
        back = findViewById(R.id.back);
        update = findViewById(R.id.update);
        newpassword = findViewById(R.id.npassword);
        mail=getIntent().getStringExtra("mymail");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reset_password.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkvalid()){
                    Resetpass();
                }
            }

        });

    }
    private boolean checkvalid(){
        boolean valid = true;
        if (token.getText().toString().length()==0)
        {
            valid = false;
            Toast.makeText(this, "Vui lòng nhập mã xác nhận", Toast.LENGTH_SHORT).show();
            return valid;
        }
        else {
             valid = true;
        }
        if (newpassword.getText().toString().length()==0 ||newpassword.getText().toString().length()<8||newpassword.getText().toString().length()>16){
            valid = false;
            Toast.makeText(this, "Mật khẩu mới phải từ 8 đến 16 ký tự", Toast.LENGTH_SHORT).show();
            return valid;
        }else{
             valid = true;
        }
        return valid;
    }

    private void Resetpass(){
        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);
        Call<String> resetpas = iMyService.resetPass(mail,token.getText().toString(),newpassword.getText().toString());
        resetpas.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(reset_password.this,"Đã khôi phục mật khẩu",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(reset_password.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(reset_password.this,"lỗi",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage() );

            }
        });
    }

}