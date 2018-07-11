package codepath.com.instagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LoginFragment extends Fragment {
    @BindView(R.id.etPassword)
    EditText Password;
    @BindView(R.id.etUsername)
    EditText Username;
    @BindView(R.id.btnLogin)
    Button btLogin;
    private Unbinder unbinder;

    public interface OnItemSelectedListener {
        public void onLogin();
    }
    private LoginFragment.OnItemSelectedListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = Password.getText().toString();
                String user = Username.getText().toString();
                ParseUser.logInInBackground(user, pass, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Toast.makeText(getActivity(), "you did it!",Toast.LENGTH_LONG).show();
                            listener.onLogin();

                        } else {
                        }
                    }
                });
            }
        });
        return view;
    }

public void onSignUp(View view)
        {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnItemSelectedListener) {
            listener = (LoginFragment.OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
