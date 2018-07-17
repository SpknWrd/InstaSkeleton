package codepath.com.spknwrd;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupFragment extends Fragment {

    @BindView(R.id.etPassword)
    EditText Password;
    @BindView(R.id.etUsername)
    EditText Username;
    @BindView(R.id.btSignup)
    Button btSignup;

    public static interface SignupListener{
        void onSignup();
    }

    SignupListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this,view);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();
                user.setUsername(Username.getText().toString());
                user.setPassword(Password.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        listener.onSignup();
                    }
                });
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.LoginListener) {
            listener = (SignupListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }


}
