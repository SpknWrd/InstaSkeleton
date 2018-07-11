package codepath.com.instagram;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Composefragment extends Fragment {

    @BindView(R.id.etCaption)
    EditText etCaption;
    @BindView(R.id.btUpload)
    Button btUpload;
    @BindView(R.id.ivPic)
    ImageView ivPic;

    byte [] byte_array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this,view);
        byte_array =getArguments().getByteArray("bitmap");
        ivPic.setImageBitmap(BitmapFactory.decodeByteArray(byte_array, 0, byte_array.length));

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseFile file = new ParseFile(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()))
                        , byte_array);
                Post p= Post.newInstance(ParseUser.getCurrentUser(),file,etCaption.getText().toString());
                p.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getActivity(),"callback",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        return view;
    }
    public static Composefragment newInstance(byte [] b)
    {
        Composefragment cf = new Composefragment();
        Bundle args = new Bundle();
        args.putByteArray("bitmap", b);
        cf.setArguments(args);
        return cf;
    }

}
