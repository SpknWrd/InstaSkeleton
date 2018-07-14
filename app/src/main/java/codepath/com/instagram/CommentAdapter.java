package codepath.com.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder>{
    private List<Comment> mComments;
    private Context context;

    public CommentAdapter(){
        mComments = new ArrayList<Comment>();
    }
    @NonNull
    @Override
    public CommentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        CommentAdapter.Viewholder viewHolder = new Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentAdapter.Viewholder holder, int position) {
        final Comment comment = mComments.get(position);
        try {
            ParseUser author = comment.getAuthor().fetchIfNeeded();
            holder.tvUsername.setText(author.getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvBody.setText(comment.getBody());


    }
    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivProfileImage)
        ImageView ivProfileImage;
        @BindView(R.id.tvUserName)
        TextView tvUsername;
        @BindView(R.id.tvBody) TextView tvBody;


        public Viewholder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
    public void clear() {
        mComments.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> list) {
        mComments.addAll(list);
        notifyDataSetChanged();
    }

}
