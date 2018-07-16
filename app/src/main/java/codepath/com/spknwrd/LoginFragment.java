package codepath.com.spknwrd;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    @BindView(R.id.tvSignup)
    TextView tvSignup;
    private Unbinder unbinder;

    public interface LoginListener {
         void onLogin(LoginFragment lf);
         void clickSignUp();
    }
    private LoginFragment.LoginListener listener;

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
                            listener.onLogin(LoginFragment.this);
                        } else {
                        }
                    }
                });
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickSignUp();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.LoginListener) {
            listener = (LoginFragment.LoginListener) context;
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
