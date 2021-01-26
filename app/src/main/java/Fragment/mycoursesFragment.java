package Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tutorial_v1.R;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.JoinedCourseAdapter;
import Adapter.Joinedcourse;
import Adapter.courseAdapter;
import Model.courseItem;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.*;

public class mycoursesFragment extends Fragment {

    ArrayList<courseItem> courseItems = new ArrayList<>();
    Joinedcourse joinedAdapter;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    TextView tv;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview =inflater.inflate(R.layout.fragment_mycourses, container, false);
        recyclerView=rootview.findViewById(R.id.my_joined_course);
        // tv=rootview.findViewById(R.id.sss);
        joinedAdapter=new Joinedcourse();
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //courseAdapter.setHasStableIds(true);
        // Toast.makeText(getContext(), sharedPreferences.getString("id",null), Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(joinedAdapter);
        //getJoinedCourse();

        return  rootview;
    }
    boolean flag=false;
    String temp="";
    private void getJoinedCourse() {
        Toast.makeText(getContext(), "Loi he thong", Toast.LENGTH_SHORT).show();
        /*IMyService iMyService;
        AlertDialog alertDialog;
        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);

        Call<List<courseItem>> JoinList = iMyService.JoinedCourse("http://149.28.24.98:9000/join/get-courses-joined-by-user/"+sharedPreferences.getString("id",""));
        JoinList.enqueue(new Callback<List<courseItem>>() {
            @Override
            public void onResponse(Call<List<courseItem>> call, Response<List<courseItem>> response) {
                if(response.isSuccessful()){
                    List<courseItem> JoinedCourseList = response.body();
                    joinedAdapter.setData(JoinedCourseList);
                    recyclerView.setAdapter(joinedAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<courseItem>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage() );
            }
        });*/


        /*alertDialog= new SpotsDialog.Builder().setContext(getContext()).build();
        alertDialog.show();
        iMyService.getJoinedCourse("http://149.28.24.98:9000/join/get-courses-joined-by-user/"+sharedPreferences.getString("id","")).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onNext(String response) {
                        flag=true;
                        temp=response;
                    }

                    @Override
                    public void onError(Throwable e) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        alertDialog.dismiss();

                                    }
                                }, 500);
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onComplete() {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        alertDialog.dismiss();

                                    }
                                }, 500);

                        if(flag==true)
                        {
                            //Toast.makeText(getContext(), "Cssss", Toast.LENGTH_SHORT).show();
                            try {


                                //JSONObject jsonObject=new JSONObject(temp);


                                JSONArray ja=new JSONArray(temp);
                                // JSONArray jsonArray=jsonObject.getJSONArray("");
                                for(int i=0;i<ja.length();i++)
                                {

                                    JSONObject jo=ja.getJSONObject(i);
                                    JSONObject jo2=jo.getJSONObject("idCourse");
                                    courseItem ci=new courseItem();
                                    ci.setID(jo2.getString("_id"));
                                    ci.setTitle(jo2.getString("name"));
                                    ci.setUrl("http://149.28.24.98:9000/upload/course_image/"+jo2.getString("image"));
                                    ci.setPercent(jo.getInt("percentCompleted"));

                                    ci.setCreateAt(jo.getString("created_at"));
                                    courseItems.add(ci);
                                    courseAdapter.notifyDataSetChanged();

                                    // if(i==7) Toast.makeText(getContext(), jo.getString("image"), Toast.LENGTH_LONG).show();


                                }
                                flag=true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                            Toast.makeText(getContext(), "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();

                    }
                });*/
        //tv.setText(temp);
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(), "asdasdasdasdasd", Toast.LENGTH_SHORT).show();
        if(requestCode == 1903) {


            if(resultCode == Activity.RESULT_OK) {
                courseItems.clear();
                joinedAdapter.notifyDataSetChanged();
                getJoinedCourse();


            } else {

            }
        }
    }*/

}
