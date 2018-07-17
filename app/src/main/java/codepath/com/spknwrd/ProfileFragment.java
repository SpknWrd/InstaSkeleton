package codepath.com.spknwrd;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment {

    ParseUser user;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.btLogout)
    Button btLogout;

    ArrayList<String> urls;
    @BindView(R.id.rvPics)
    RecyclerView rvPics;

    PictureAdapter adapter;




    ProfileInterface listener;
    public interface ProfileInterface {
         void onChangeImage(ParseUser user);
         void onLogout();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);

        user = getArguments().getParcelable("user");
        try {
            tvName.setText(user.fetchIfNeeded().getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChangeImage(user);
            }
        });
        try {
            ParseFile pf = (ParseFile) user.fetchIfNeeded().get("profile");
            if(pf != null)
            GlideApp.with(this)
                    .load(pf.getUrl()).centerCrop()
                    .into(ivProfile);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLogout();
            }
        });



        urls = new ArrayList<String>();

        rvPics.setLayoutManager(new GridLayoutManager(getContext(),2));

        //DividerItemDecoration itemDecor = new DividerItemDecoration(this,
        // HORIZONTAL);

        adapter = new PictureAdapter();
        //rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvPics.setAdapter(adapter);

        populateTimeline();

        return view;
    }
    public String getProfileImage(){
        ParseQuery<ProfilePic> query = ParseQuery.getQuery(ProfilePic.class);
        query.whereEqualTo("author", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ProfilePic>() {
            public void done(List<ProfilePic> itemList, ParseException e) {
                if (e == null) {
                    String firstItemId = itemList.get(0).getObjectId();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
        return "";
    }
    public static ProfileFragment getInstance(ParseUser u){
        ProfileFragment pf = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("user",u);
        pf.setArguments(args);
        return pf;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GalleryFragment.GalleryListener) {
            listener = (ProfileInterface) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }




    private void populateTimeline(){
        adapter.clear();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo("owner", user);
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> itemList, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < itemList.size();i++) {
                        try {
                            urls.add(((Post)itemList.get(i).fetchIfNeeded()).getMedia().getUrl());
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
                adapter.addAll(urls);


            }
        });


    }

}
