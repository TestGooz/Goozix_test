package iconmobiletechnology.goozix_test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import iconmobiletechnology.goozix_test.Model.Repo;
import iconmobiletechnology.goozix_test.RecyclerView_Adapters.RecyclerView_AdapterRepos;
import iconmobiletechnology.goozix_test.RecyclerView_Adapters.item_repo_list;
import iconmobiletechnology.goozix_test.RestClient.ApiService;
import iconmobiletechnology.goozix_test.RestClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposActivity extends AppCompatActivity {

    private List<Repo> mReposList;
    private ArrayList<item_repo_list> mRecyclerList = new ArrayList<item_repo_list>();
    private int mTypeUser;
    private RecyclerView_AdapterRepos mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
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

        CreateRecyclerList();
        StertRestThr();
    }

    private void CreateRecyclerList() {
        RecyclerView rvMain = (RecyclerView) findViewById(R.id.recycler_repos);
        mAdapter = new RecyclerView_AdapterRepos(this, mRecyclerList);
        rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMain.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void StertRestThr()
    {
        ApiService Service = ((RestClient) getApplicationContext()).getService();
        Call<List<Repo>> repos;
        if (mTypeUser == 0) repos = Service.listAuthRepos();
        else
        {
            String mUserLogin = (String) getIntent().getSerializableExtra("UserLogin");
            assert mUserLogin != null;
            repos = Service.getListRepos(mUserLogin);
        }
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {
                if (response.isSuccessful()) {
                    mReposList = response.body();
                    assert mReposList != null;
                    for (Repo item : mReposList)
                    {
                        mRecyclerList.add(new item_repo_list(item.getName(),item.getFullName(), item.getDescription(), item.getHtmlUrl()));
                    }
                    mAdapter.notifyDataSetChanged();
                }
                else Toast.makeText(ReposActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                ProgressBarGone();
            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {
                ProgressBarGone();
                Toast.makeText(ReposActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ProgressBarGone()
    {
        ProgressBar ProgressBar = (ProgressBar) this.findViewById(R.id.progress_load);
        ProgressBar.setVisibility(View.GONE);
    }
}
