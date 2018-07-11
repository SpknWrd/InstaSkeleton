package codepath.com.instagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedFragment extends Fragment {

    InstaAdapter adapter;
    ArrayList<Post> posts;
    @BindView(R.id.rvPost)
    RecyclerView rvPost;
    //MenuItem miActionProgressItem;

    public static int REQUEST_CODE = 20;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    public FeedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //final Post.Query postsQuery = new Post.Query();
        //postsQuery.getTop();
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this,view);
        posts = new ArrayList<Post>();
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));

        //DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);

        adapter = new InstaAdapter(posts);

        //rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setAdapter(adapter);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        populateTimeline();

        return view;
    }
    public void fetchTimelineAsync(int page) {
        adapter.clear();
        populateTimeline();
        swipeContainer.setRefreshing(false);
    }
    private void populateTimeline(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e== null){
                    for(int i = 0 ; i < objects.size();i++)
                        posts.add(objects.get(i));
                    adapter.addAll(posts);
                }
                else{
                    Log.d("FeedError","failed");
                    e.printStackTrace();
                }
            }
        });
    }


}
