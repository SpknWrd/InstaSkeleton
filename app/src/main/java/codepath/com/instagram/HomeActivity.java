package codepath.com.instagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
implements CameraFragment.OnItemSelectedListener , LoginFragment.OnItemSelectedListener{


    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    FragmentTransaction ft;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        final Fragment fragment1 = new FeedFragment();
        final Fragment fragment2 = new CameraFragment();
        final Fragment fragment3 =  ProfileFragment.getInstance(ParseUser.getCurrentUser());

        ParseUser currentUser = ParseUser.getCurrentUser();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragmentTransaction.replace(R.id.placeholder, fragment1).commit();
                                return true;
                            case R.id.action_camera:
                                fragmentTransaction.replace(R.id.placeholder, fragment2).commit();
                                return true;
                            case R.id.action_profile:
                                fragmentTransaction.replace(R.id.placeholder, fragment3).commit();
                        }
                        return false;
                    }});



        if (currentUser != null) {
            launchFragment(new FeedFragment());
        } else {
            launchFragment(new LoginFragment());
        }
    }
    public void onPicture(byte [] b )
    {

        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder, Composefragment. newInstance(b));
        ft.addToBackStack(null);
        ft.commit();
    }
    public void onLogin()
    {
        launchFragment(new CameraFragment());
    }
    private void launchFragment(Fragment f)
    {
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder, f);
        ft.commit();
    }
}
