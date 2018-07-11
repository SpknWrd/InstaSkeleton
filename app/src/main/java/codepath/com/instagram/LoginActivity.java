package codepath.com.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.etPassword)
    EditText Password;
    @BindView(R.id.etUsername)
    EditText Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void onLogin(View v)
    {
        finish();
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);

       /* String pass = Password.getText().toString();
        String user = Username.getText().toString();
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(getApplicationContext(), "you did it!",Toast.LENGTH_LONG).show();

                } else {
                }
            }
        });*/
    }
    public void onSignUp(View view)
    {
        ParseUser user = new ParseUser();
        user.setUsername(Username.getText().toString());
        user.setPassword(Password.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"signed up",Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });
    }
}
