package codepath.com.spknwrd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CommentFragment extends Fragment {


    ArrayList<Comment> comments;
    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btSubmit)
    Button btSubmit;

    CommentAdapter adapter;
    Post post;

    public static CommentFragment newInstance(Post p) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putParcelable("post", p);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this,view);
        comments = new ArrayList<Comment>();

        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));

        post = getArguments().getParcelable("post");
        //DividerItemDecoration itemDecor = new DividerItemDecoration(this,
        // HORIZONTAL);

        adapter = new CommentAdapter();
        //rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Comment c = Comment.newInstance(etComment.getText().toString(),post.getAuthor());
                c.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        post.addComment(c);
                        addComment(c);
                    }
                });
            }
        });
        populateTimeline();
        return view;
        }
        private void addComment(Comment c){
            comments.add(c);
            adapter.notifyItemChanged(comments.size()-1);
        }
        private void populateTimeline(){
            adapter.clear();
            post.getCommentRelations().getQuery().findInBackground(new FindCallback<Comment>() {
                @Override
                public void done(List<Comment> results, ParseException e) {
                    if (e == null) {
                        for(int i = 0 ; i < results.size();i++)
                            comments.add(results.get(i));
                        adapter.addAll(comments);
                    } else {
                        // There was an error
                    }
                }
            });
        }
}
