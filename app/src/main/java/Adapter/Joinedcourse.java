package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.courseItem;

public class Joinedcourse extends RecyclerView.Adapter<Joinedcourse.ViewHolder> {
    java.util.List<courseItem> List;


    public void setData(List<courseItem> List) {
        this.List = List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Joinedcourse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.course_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Joinedcourse.ViewHolder holder, int position) {
        holder.title.setText(List.get(position).getTitle());


        Picasso.get().load(List.get(position).getUrl()).placeholder(R.drawable.empty23).error(R.drawable.empty23).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imageView);
        holder.joinedAt.setText(List.get(position).getCreateAt());
        holder.progressBar.setProgress(List.get(position).getPercent());
        holder.percentText.setText("" + List.get(position).getPercent() + "%");
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, joinedAt, percentText;
        private ImageView imageView;
        ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.joinedCourseName1);
            joinedAt = view.findViewById(R.id.joinedAt);
            imageView = view.findViewById(R.id.joinedCourseImg);
            progressBar = view.findViewById(R.id.joinedCourseProgressbar);
            percentText = view.findViewById(R.id.joinedCoursePercent);
        }
    }
}

