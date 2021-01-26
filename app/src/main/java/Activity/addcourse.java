package Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorial_v1.R;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Model.UserAccount;
import Retrofit.IMyService;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.IMyService;
import Retrofit.RetrofitClient;

public class addcourse extends AppCompatActivity {
    EditText namecourse,muctieu,mota,gia,giamgia;
    Spinner danhmuc;
    Button add,upload;
    String tenkh,muctieukh,motakh,giakh,giamgiakh,danhmuckh;
    TextView path;
    Bitmap bitmap;
    ImageView tes;
    File file;
    boolean flag=false;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    UserAccount userAccount = new UserAccount();
    String token;

    private static final int REQUEST_CODE=43;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        namecourse = findViewById(R.id.namecourse);
        danhmuc = findViewById(R.id.danhmuc);
        muctieu = findViewById(R.id.muctieu);
        mota = findViewById(R.id.mota);
        gia = findViewById(R.id.gia);
        giamgia = findViewById(R.id.giamgia);
        upload = findViewById(R.id.upload);
        add = findViewById(R.id.addnewcourse);
        path = findViewById(R.id.uri);
        tes = findViewById(R.id.imtest);

        /*tenkh = namecourse.getText().toString();
        muctieukh=muctieu.getText().toString();
        motakh=mota.getText().toString();
        giakh = gia.getText().toString();
        giamgiakh=giamgia.getText().toString();*/

        sharedPreferences = getApplicationContext().getSharedPreferences("MyUser",Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        //namecourse.setText(token);
        ArrayList<String> category = new ArrayList<String>();
        category.add("Toán tin");
        category.add("Công nghệ thông tin");
        category.add("Ngoại ngữ");

        ArrayAdapter arrayAdapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item,category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        danhmuc.setAdapter(arrayAdapter);

        danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                danhmuckh =category.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickfile();

            }
        });

       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              addnewcourse();

           }
       });
    }
    private void pickfile()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data!=null){
                Uri uri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    tes.setImageBitmap(bitmap);
                    file = new File(getRealPathFromURI(uri));
                    path.setText(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private void addnewcourse(){

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),"newCourse");
        MultipartBody.Part part = MultipartBody.Part.createFormData("photo", file.getName(), fileReqBody);


        Retrofit retrofitClient= RetrofitClient.getInstance();
        IMyService iMyService=retrofitClient.create(IMyService.class);

        Call<ResponseBody> add = iMyService.createCourse(namecourse.getText().toString(),muctieu.getText().toString(),mota.getText().toString(),danhmuckh,gia.getText().toString(),giamgia.getText().toString(),part,token);
        add.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                Toast.makeText(addcourse.this,"ok",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(addcourse.this,LoginActivity.class);
                startActivity(intent);
                }else {
                    Toast.makeText(addcourse.this,"lỗi",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(addcourse.this,"lỗi",Toast.LENGTH_LONG).show();
            }
        });


    }
}