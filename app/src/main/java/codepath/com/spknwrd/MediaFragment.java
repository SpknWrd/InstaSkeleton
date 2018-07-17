package codepath.com.spknwrd;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MediaFragment extends Fragment {

    private static final String ARTIST = "artist";
    private static final String TITLE = "title";
    private static final String URL = "url";
    Uri uri;

    private MediaPlayer mMediaplayer;

    private boolean isPlaying=false;
    private boolean hasStarted=false;

    @BindView(R.id.songname)
    TextView tvTitle;
    @BindView(R.id.artistname) TextView tvArtistName;
    @BindView(R.id.songimage)
    ImageView ivImage;
    @BindView(R.id.playbtn)
    Button btPlay;
    @BindView(R.id.previoubtn) Button btPrevious;
    @BindView(R.id.nextbtn) Button btNext;
    @BindView(R.id.sharebtn) Button btShare;
    @BindView(R.id.likebtn) Button btLike;
    @BindView(R.id.addbtn) Button btAdd;
    @BindView(R.id.dislikebtn) Button btDislike;

    // TODO: Rename and change types of parameters
    private String artist;
    private String title;
    private String url;

    private OnFragmentInteractionListener mListener;

    public MediaFragment() {
        // Required empty public constructor
    }
    public static MediaFragment newInstance(String param1, String param2, String param3) {
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString(ARTIST, param1);
        args.putString(TITLE, param2);
        args.putString(URL,param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            artist = getArguments().getString(ARTIST);
            title = getArguments().getString(TITLE);
            url = getArguments().getString(URL);
        }
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_media, container, false);
        ButterKnife.bind(this,view);
        fetchAudioUrlFromFirebase();
        tvArtistName.setText(artist);
        tvTitle.setText(title);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying)
                {
                    mMediaplayer.pause();
                    Toast.makeText(getContext(),"pause",Toast.LENGTH_SHORT).show();
                    isPlaying = ! isPlaying;
                }
                else if(!hasStarted)
                {
                    final String url = uri.toString();
                    try {
                        mMediaplayer.setDataSource(url);
                        mMediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        mMediaplayer.prepareAsync();
                        hasStarted=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(),"play",Toast.LENGTH_SHORT).show();
                }
                else{
                    mMediaplayer.start();
                    isPlaying = ! isPlaying;

                }

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(url);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri2) {
                uri = uri2;
                Toast.makeText(getContext(),"uri",Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", e.getMessage());
                    }
                });
    }


}
