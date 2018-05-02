package iconmobiletechnology.goozix_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

import iconmobiletechnology.goozix_test.Model.AutorizationUserModel;
import iconmobiletechnology.goozix_test.Model.User;
import iconmobiletechnology.goozix_test.RestClient.ApiService;
import iconmobiletechnology.goozix_test.RestClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUserActivity extends AppCompatActivity {

    private int mTypeUser;
    private String mUserLogin;

    public static Intent getStartIntentAuth(Context context, int mTypeUser) {
        Intent intent = new Intent(context, ReposActivity.class);
        intent.putExtra("TypeUser", mTypeUser);
        return intent;
    }

    public static Intent getStartIntentUser(Context context, String mUserLogin, int mTypeUser) {
        Intent intent = new Intent(context, ReposActivity.class);
        intent.putExtra("TypeUser", mTypeUser);
        intent.putExtra("UserLogin", mUserLogin);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTypeUser = (int) getIntent().getSerializableExtra("TypeUser");
        if (mTypeUser == 0) {
            AutorizationUserModel mAuthUser = (AutorizationUserModel) getIntent().getSerializableExtra("AutorizationUserModel");
            UiCreateAboutUser(mAuthUser.getLogin(),mAuthUser.getName(),mAuthUser.getEmail(),mAuthUser.getCompany(),
                    mAuthUser.getBlog(), mAuthUser.getLocation(), mAuthUser.getBio(), mAuthUser.getAvatarUrl());
            UiCreateAboutAuthRepos(mAuthUser);
        }
        else
        {
            mUserLogin = (String) getIntent().getSerializableExtra("UserLogin");
            getUserProfile();
        }
    }

    @SuppressLint("SetTextI18n")
    private void UiCreateAboutUser(String m_login, String m_name, String m_email, String m_company, String m_blog, String m_location, String m_bio, String m_url) {
        NestedScrollView Scroll = (NestedScrollView) this.findViewById(R.id.scroll_box);
        Scroll.setVisibility(View.VISIBLE);
        ProgressBar ProgressBar = (ProgressBar) this.findViewById(R.id.progress_load);
        ProgressBar.setVisibility(View.GONE);

        TextView login = (TextView) this.findViewById(R.id.text_login);
        login.setText(m_login);
        TextView name = (TextView) this.findViewById(R.id.text_name);
        name.setText(m_name);
        TextView email = (TextView) this.findViewById(R.id.text_email);
        email.setText(m_email);

        TextView company = (TextView) this.findViewById(R.id.text_company);
        if (m_company != null) company.setText("Company: " + m_company);
        else company.setVisibility(View.GONE);

        TextView blog = (TextView) this.findViewById(R.id.text_blog);
        if (m_blog != null) blog.setText("Blog: " + m_blog);
        else blog.setVisibility(View.GONE);

        TextView location = (TextView) this.findViewById(R.id.text_location);
        if (m_location != null) location.setText("Location: " + m_location);
        else location.setVisibility(View.GONE);

        TextView bio = (TextView) this.findViewById(R.id.text_bio);
        if (m_bio != null) bio.setText("Bio: " + m_bio);
        else bio.setVisibility(View.GONE);


        ImageView icon = (ImageView) this.findViewById(R.id.image_avatar);
        Uri uri = Uri.parse(m_url);
        Glide.with(this).load(uri).into(icon);

        Button btn_to_repos = (Button) this.findViewById(R.id.button_to_repos);
        btn_to_repos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTypeUser == 0) startActivity(getStartIntentAuth(AboutUserActivity.this, mTypeUser));
                else startActivity(getStartIntentUser(AboutUserActivity.this, mUserLogin ,mTypeUser));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void UiCreateAboutAuthRepos(AutorizationUserModel AuthUser) {
        TextView private_gists_count = (TextView) this.findViewById(R.id.text_private_gists_count);
        private_gists_count.setText("Private gists count: " + AuthUser.getPrivateGists().toString());
        private_gists_count.setVisibility(View.VISIBLE);

        TextView total_private_repositories_count = (TextView) this.findViewById(R.id.text_total_private_repositories_count);
        total_private_repositories_count.setText("Total Private Repositories Count: " + AuthUser.getTotalPrivateRepos().toString());
        total_private_repositories_count.setVisibility(View.VISIBLE);

        TextView owned_private_repositores_count = (TextView) this.findViewById(R.id.text_owned_private_repositores_count);
        owned_private_repositores_count.setText("Owned Private Repositores Count: " + AuthUser.getOwnedPrivateRepos().toString());
        owned_private_repositores_count.setVisibility(View.VISIBLE);

        TextView publicRepos = (TextView) this.findViewById(R.id.text_publicRepos);
        publicRepos.setText("Public Repositores Count: " + AuthUser.getPublicRepos().toString());
        publicRepos.setVisibility(View.VISIBLE);
    }

    private void getUserProfile()
    {
        ApiService Service = ((RestClient) getApplicationContext()).getService();
        Call<User> User = Service.getUser(mUserLogin);
        User.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User User = response.body();
                    assert User != null;
                    UiCreateAboutUser(User.getLogin(),User.getName(),User.getEmail(),User.getCompany(),
                            User.getBlog(),User.getLocation(),User.getBio(),User.getAvatarUrl());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(AboutUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
