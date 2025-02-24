package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.view.login.LoginActivity;
import edu.byu.cs.tweeter.client.view.login.StatusDialogFragment;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements StatusDialogFragment.Observer, MainPresenter.MainView
{
    private MainPresenter presenter;
    private static final String LOG_TAG = "MainActivity";


    public static final String CURRENT_USER_KEY = "CurrentUser";

    private Toast logOutToast;
    private Toast postingToast;
    private Toast followToast;
    private User selectedUser;
    private TextView followeeCount;
    private TextView followerCount;
    private Button followButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        selectedUser = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if (selectedUser == null)
        {
            throw new RuntimeException("User not passed to activity");
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), selectedUser);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                StatusDialogFragment statusDialogFragment = new StatusDialogFragment();
                statusDialogFragment.show(getSupportFragmentManager(), "post-status-dialog");
            }
        });

        updateSelectedUserFollowingAndFollowers();

        TextView userName = findViewById(R.id.userName);
        userName.setText(selectedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(selectedUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        Picasso.get().load(selectedUser.getImageUrl()).into(userImageView);

        followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, "..."));

        followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, "..."));

        followButton = findViewById(R.id.followButton);

        if (selectedUser.compareTo(Cache.getInstance().getCurrUser()) == 0)
        {
            followButton.setVisibility(View.GONE);
        } else
        {
            followButton.setVisibility(View.VISIBLE);
            presenter.isFollowing(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), selectedUser);
        }

        followButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (followButton.getText().toString().equals(v.getContext().getString(R.string.following)))
                {
                    presenter.unfollow(Cache.getInstance().getCurrUserAuthToken(), selectedUser);

                } else
                {
                    presenter.follow(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.logoutMenu)
        {
            presenter.logout(Cache.getInstance().getCurrUserAuthToken());
            return true;
        } else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    public void logoutUser()
    {
        //Revert to login screen.
        Intent intent = new Intent(this, LoginActivity.class);
        //Clear everything so that the main activity is recreated with the login page.
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Clear user data (cached data).
        Cache.getInstance().clearCache();
        startActivity(intent);
    }

    @Override
    public void onStatusPosted(String post)
    {
        presenter.postStatus(Cache.getInstance().getCurrUserAuthToken(), post);
    }

    @Override
    public void navigateToUser(User user)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
        startActivity(intent);
    }

    @Override
    public void displayPostingInfoMessage(String message)
    {
        clearPostingInfoMessage();
        postingToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        postingToast.show();
    }

    @Override
    public void clearPostingInfoMessage()
    {
        if (postingToast != null)
        {
            postingToast.cancel();
            postingToast = null;
        }
    }

    @Override
    public void displayInfoMessage(String message)
    {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void displayLogoutInfoMessage(String message)
    {
        clearPostingInfoMessage();
        logOutToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        logOutToast.show();
    }

    @Override
    public void clearLogoutInfoMessage()
    {
        if (logOutToast != null)
        {
            logOutToast.cancel();
            logOutToast = null;
        }
    }

    @Override
    public void updateSelectedUserFollowingAndFollowers()
    {
        presenter.getFollowCounts(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
    }

    @Override
    public void updateFollowButton(boolean isFollowing)
    {
        // If follow relationship was removed.
        if (isFollowing)
        {
            followButton.setText(R.string.following);
            followButton.setBackgroundColor(getResources().getColor(R.color.white));
            followButton.setTextColor(getResources().getColor(R.color.lightGray));
        } else
        {
            followButton.setText(R.string.follow);
            followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void setFollowButtonEnabled(boolean value)
    {
        followButton.setEnabled(value);
    }

    @Override
    public void setFolloweeCount(int count)
    {
        followeeCount.setText(getString(R.string.followeeCount, String.valueOf(count)));
    }

    @Override
    public void setFollowerCount(int count)
    {
        followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));
    }

}
