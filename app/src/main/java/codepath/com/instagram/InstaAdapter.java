package codepath.com.instagram;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.Viewholder>{
        private List<Post> mPosts;
        Context context;

        public InstaAdapter(List<Post> l){
            mPosts = l;
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

            /*if(post.favorited)
                holder.bFavorite.setBackground(heart);
            else
                holder.bFavorite.setBackground(empty);*/

            try {
                holder.tvUsername.setText(post.getAuthor().fetchIfNeeded().getUsername());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.tvBody.setText(post.getCaption());
            //holder.retweetCount.setText(""+tweet.retweetCount);
            //holder.favoriteCounter.setText(""+tweet.favoriteCount);


            /*GlideApp.with(context)
                    .load(post.getMedia().getUrl())
                    .into(holder.ivProfileImage);*/
            GlideApp.with(context).load(post.getMedia().getUrl()).into(holder.ivPic);

           /* holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,Profile.class);
                    i.putExtra("user",Parcels.wrap(tweet.user));
                    context.startActivity(i);
                }
            });*/
           /* holder.tvTime.setText(getRelativeTimeAgo(tweet.createdAt));
            holder.bReply.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    timeline.onComposeAction(tweet);
                }});*/
            /*holder.bFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    client.favorite(tweet.uid,handler,tweet.favorited);
                    if(tweet.favorited) {
                        holder.bFavorite.setBackground(empty);
                        holder.favoriteCounter.setText(""+(Integer.parseInt(""+holder.favoriteCounter.getText().toString())-1) );
                    }
                    else
                    {
                        holder.bFavorite.setBackground(heart);
                        holder.favoriteCounter.setText(""+(Integer.parseInt(""+holder.favoriteCounter.getText().toString())+1) );
                    }
                    tweet.favorited = !tweet.favorited;
                }
            });*/

            /*if(tweet.pic == null)
                holder.ivPic.setVisibility(View.GONE);
            else
                GlideApp.with(context).load(tweet.pic).into(holder.ivPic);*/
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
            @BindView(R.id.ivProfileImage)
            ImageView ivProfileImage;
            @BindView(R.id.tvUserName)
            TextView tvUsername;
            @BindView(R.id.tvBody) TextView tvBody;
            @BindView(R.id.tvTime) TextView tvTime;
            @BindView(R.id.ivPicture) ImageView ivPic;
            @BindView(R.id.bReply)
            Button bReply;
            @BindView(R.id.bFavorite)  Button bFavorite;
            @BindView(R.id.favoriteCounter) TextView favoriteCounter;

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
            mPosts.clear();
            notifyDataSetChanged();
        }

        public void addAll(List<Post> list) {
            mPosts.addAll(list);
            notifyDataSetChanged();
        }

}
