package codepath.com.instagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity
implements CameraFragment.OnItemSelectedListener , LoginFragment.LoginListener,
        GalleryFragment.GalleryListener, ProfileFragment.ProfileInterface, FeedFragment.CommentListener,
        Composefragment.ComposeListener,
        SignupFragment.SignupListener
{


    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.imageView)
    ImageView image;
    @BindView(R.id.pbLoading)
    ProgressBar progressBar;
    MenuItem miActionProgressItem;

    FragmentTransaction ft;

    final FragmentManager fragmentManager = getSupportFragmentManager();


    ParseUser currentUser = ParseUser.getCurrentUser();
    Fragment fragment1 = new FeedFragment();
    Fragment fragment2 = new CameraFragment();
    Fragment fragment3 =  currentUser==null?null:ProfileFragment.getInstance(currentUser);
    LoginFragment loginFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
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
        if (currentUser == null) {
            gotoLogin();
        }
        else{
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.placeholder, fragment1).commit();
            bottomNavigationView.setSelectedItemId(R.id.action_profile);

        }

    }
    public void launchComment(Post post){
        launchFragment(CommentFragment.newInstance(post),true);
    }
    public void launchProfile(ParseUser user){
        launchFragment( ProfileFragment.getInstance(user),true);
    }
    public void onPicture(byte [] b )
    {
        launchFragment(Composefragment. newInstance(b),true);
    }
    public void onLogin(LoginFragment lf)
    {
        fragmentManager.beginTransaction().remove(lf);
        bottomNavigationView.setVisibility(View.VISIBLE);
        fragment3 = ProfileFragment.getInstance(ParseUser.getCurrentUser());
        launchFragment(fragment1,false);
    }
    public void clickSignUp(){
        launchFragment(new SignupFragment(), true);
    }
    public void onSignup(){
        ft = fragmentManager.beginTransaction();
        ft.remove(loginFragment);
        ft.replace(R.id.placeholder, fragment1);
        ft.commit();
        bottomNavigationView.setVisibility(View.VISIBLE);
        fragment3 = ProfileFragment.getInstance(ParseUser.getCurrentUser());
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
    public void onImageChosen(ParseUser user, String s) {
        user.put("profile", new ParseFile(new File(s)));
        user.saveInBackground();
    }
    public void onChangeImage(ParseUser user){
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder, GalleryFragment.getInstance(user));
        ft.addToBackStack(null);
        ft.commit();
    }
    public void onCompose(Composefragment cf){
        endProgress();
        ft = fragmentManager.beginTransaction();
        ft.remove(cf);
        ft.replace(R.id.placeholder, fragment1);
        ft.commit();
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
    public void onLogout(){
        ParseUser.logOut();
        gotoLogin();
    }
    private void launchFragment(Fragment f, boolean backstack)
    {
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder, f);
        if(backstack)
            ft.addToBackStack(null);
        ft.commit();
    }
    public void gotoLogin(){
        if(loginFragment == null)
            loginFragment = new LoginFragment();
        bottomNavigationView.setVisibility(GONE);
        launchFragment(new LoginFragment(), false);
    }
    public void startProgress(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    public void endProgress(){
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
