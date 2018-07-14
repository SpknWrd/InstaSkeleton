package codepath.com.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureAdapter  extends RecyclerView.Adapter<codepath.com.instagram.PictureAdapter.Viewholder>{
    private List<String> mUrls;
    private Context context;

    public PictureAdapter(){
        mUrls = new ArrayList<String>();
    }
    @NonNull
    @Override
    public codepath.com.instagram.PictureAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_picture, parent, false);
        codepath.com.instagram.PictureAdapter.Viewholder viewHolder = new codepath.com.instagram.PictureAdapter.Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final codepath.com.instagram.PictureAdapter.Viewholder holder, int position) {
        final String string = mUrls.get(position);
        GlideApp.with(context)
                .load(string)
                .into(holder.ivPicture);

    }
    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivPicture)
        ImageView ivPicture;



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
        mUrls.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<String> list) {
        mUrls.addAll(list);
        notifyDataSetChanged();
    }

}
