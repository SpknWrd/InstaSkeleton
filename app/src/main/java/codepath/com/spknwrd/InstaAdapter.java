package codepath.com.spknwrd;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.Viewholder>{
        private List<Post> mPosts;
        private Context context;
        private HomeActivity activity;

        FeedFragment.CommentListener listener;


    public InstaAdapter(FeedFragment.CommentListener listener){
            mPosts = new ArrayList<Post>();
            this.listener = listener;
        }
        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View tweetView = inflater.inflate(R.layout.item_post, parent, false);
            Viewholder viewHolder = new Viewholder(tweetView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
            final Post post = mPosts.get(position);
            final Drawable heart = context.getResources().getDrawable(R.drawable.ic_vector_heart);
            final Drawable empty = context.getResources().getDrawable(R.drawable.heart_empty);

            post.getLikeRelation().getQuery().findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> results, ParseException e) {
                    ParseUser me = ParseUser.getCurrentUser();
                    if (e == null) {
                        for(int i = 0 ; i < results.size();i++) {
                            String s = me.getUsername();
                            String n =results.get(i).getUsername();
                            if (s.equals(n)) {
                                holder.liked=true;
                                holder.bFavorite.setBackground(heart);
                                return;
                            }
                        }
                    }
                    holder.liked=false;
                    holder.bFavorite.setBackground(empty);
                }
            });


            try {
                holder.tvUsername.setText(post.getAuthor().fetchIfNeeded().getUsername());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.tvBody.setText(post.getCaption());
            holder.favoriteCounter.setText(""+post.getLike());


            try {
                ParseFile pf = ((ParseFile)post.getAuthor().fetchIfNeeded().get("profile"));
                if(pf != null)
                    GlideApp.with(context)
                        .load(pf.getUrl())
                        .into(holder.ivProfileImage);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GlideApp.with(context)
                    .load(post.getMedia().getUrl())
                    .into(holder.ivPicture);
           holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.launchProfile(post.getAuthor());
                }
            });
           holder.bReply.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Log.d("DEBUG","here");
                   listener.launchComment(post);
               }
           });
           Date d = post.getCreatedAt();
           String s = d.toString();
           holder.tvTime.setText(getRelativeTimeAgo(d));

            holder.bFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!holder.liked) {
                        post.like(ParseUser.getCurrentUser());
                        holder.bFavorite.setBackground(heart);

                    }else{
                        post.unlike(ParseUser.getCurrentUser());
                        holder.bFavorite.setBackground(empty);
                    }
                    holder.liked = !holder.liked;
                    holder.favoriteCounter.setText("" + post.getLike());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.ivProfileImage)
            ImageView ivProfileImage;
            @BindView(R.id.tvUserName)
            TextView tvUsername;
            @BindView(R.id.tvBody)
            TextView tvBody;
            @BindView(R.id.ivPicture)
            ImageView ivPicture;
            @BindView(R.id.bReply)
            Button bReply;
            @BindView(R.id.bFavorite)
            Button bFavorite;
            @BindView(R.id.favoriteCounter)
            TextView favoriteCounter;
            boolean liked;
            @BindView(R.id.tvTime)
            TextView tvTime;

            public Viewholder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }
        public void clear() {
            mPosts.clear();
            notifyDataSetChanged();
        }
        public void addAll(List<Post> list) {
            mPosts.addAll(list);
            notifyDataSetChanged();
        }
    public String getRelativeTimeAgo(Date rawJsonDate) {


        String relativeDate = "";
            long dateMillis = rawJsonDate.getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();


        return relativeDate;
    }
}

