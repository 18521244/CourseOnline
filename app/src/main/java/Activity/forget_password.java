package Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tutorial_v1.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Retrofit.IMyService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class forget_password extends AppCompatActivity {
    EditText forgetpass;
    Button send;
    TextView test;
    AlertDialog alertDialog;
    IMyService iMyService;
    Boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forgetpass=findViewById(R.id.forgetpassword);
        send = findViewById(R.id.sendToken);
        test =findViewById(R.id.test);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forgetpass.getText().toString().length()==0){
                    Toast.makeText(forget_password.this,"Vui lòng nhập Email của bạn",Toast.LENGTH_LONG).show();

                }else {
                Send();
                }
            }
        });
    }

    private void Send(){

        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);
        Call<String> forgetpas = iMyService.forgotPass(forgetpass.getText().toString());
        forgetpas.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Intent intent=new Intent(forget_password.this,reset_password.class);
                    intent.putExtra("mymail",forgetpass.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(forget_password.this,"lỗi",Toast.LENGTH_LONG).show();
                }

            }

            @Override
           public void onFailure(Call<String> call, Throwable t) {
               Log.e("failure",t.getLocalizedMessage() );

            }
        });


    }
}