package iconmobiletechnology.goozix_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import iconmobiletechnology.goozix_test.Model.AutorizationUserModel;
import iconmobiletechnology.goozix_test.Model.UserSearchItems;
import iconmobiletechnology.goozix_test.Model.UsersSearchModel;
import iconmobiletechnology.goozix_test.RecyclerView_Adapters.RecyclerView_AdapterUsers;
import iconmobiletechnology.goozix_test.RestClient.ApiService;
import iconmobiletechnology.goozix_test.RestClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static int REQ_CODE_CHILD = 1;
    private int mCountSinseOld = 0;
    private int mTypreReturn = 0;
    private int mCountSinse = 0;
    private int mPageFlag = 1;
    private AutorizationUserModel mAuthUser;
    private NavigationView mNavigationView;
    private ArrayList<UsersSearchModel> mListUsers = new ArrayList<UsersSearchModel> ();
    private RecyclerView_AdapterUsers mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Intent getStartIntentAboutAuth(Context context, AutorizationUserModel AuthUser) {
        Intent intent = new Intent(context, AboutUserActivity.class);
        intent.putExtra("AutorizationUserModel", AuthUser);
        intent.putExtra("TypeUser", 0);
        return intent;
    }

    public static Intent getStartIntentAboutUser(Context context, String UserLogin) {
        Intent intent = new Intent(context, AboutUserActivity.class);
        intent.putExtra("TypeUser", 1);
        intent.putExtra("UserLogin", UserLogin);
        return intent;
    }

    public static Intent getStartIntentEdit(Context context, AutorizationUserModel AuthUser) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("AutorizationUserModel", AuthUser);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthUser = (AutorizationUserModel) getIntent().getSerializableExtra("AutorizationUserModel");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        UiCreate(mNavigationView, 0);
        CreatePaginationAndRefresh();
        CreateRecyclerViewAdapter();
        CreateProcessRest();
        CreateSearchF();
    }

    private void CreateSearchF() {
        EditText mSearchView = (EditText) findViewById(R.id.text_search);
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    RestSearchUser();
                    return true;
                }
                return false;
            }
        });

    }

    private void RestSearchUser()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        TextView text_search = (TextView) findViewById(R.id.text_search);
        if (text_search.getText().toString().equals(""))
        {
            mTypreReturn = 0;
            mCountSinse = 0;
            CreateProcessRest();
        }
        else {
            mTypreReturn = 1;
            ApiService Service = ((RestClient) getApplicationContext()).getService();
            Call<UserSearchItems> AllUser = Service.getSearchUsers(text_search.getText().toString());
            AllUser.enqueue(new Callback<UserSearchItems>() {
                @Override
                public void onResponse(@NonNull Call<UserSearchItems> call, @NonNull Response<UserSearchItems> response) {
                    if (response.isSuccessful()) {
                        mListUsers.clear();
                        mListUsers.addAll(Objects.requireNonNull(response.body()).getItems());
                        mAdapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<UserSearchItems> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void CreatePaginationAndRefresh()
    {
        NestedScrollView mScroller = (NestedScrollView) findViewById(R.id.scroll_allbody);
        mScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                    if (mTypreReturn == 0) {
                        mPageFlag = 1;
                        CreateProcessRest();
                    }
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mTypreReturn == 0) {
                    mPageFlag = 0;
                    mCountSinse = mCountSinseOld;
                    CreateProcessRest();
                }
                else RestSearchUser();
            }
        });
    }


    private void CreateRecyclerViewAdapter()
    {
        RecyclerView rvMain = (RecyclerView) findViewById(R.id.recycler_user_list);
        mAdapter = new RecyclerView_AdapterUsers(this, mListUsers) {
            @Override
            protected void onClickBody(int Position, ArrayList<UsersSearchModel> objects) {
                startActivity(getStartIntentAboutUser(MainActivity.this, objects.get(Position).getLogin()));
            }
        };
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        rvMain.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void CreateProcessRest()
    {
        ApiService Service = ((RestClient) getApplicationContext()).getService();
        Call<List<UsersSearchModel>> AllUser = Service.getUsers(mCountSinse);
        AllUser.enqueue(new Callback<List<UsersSearchModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<UsersSearchModel>> call, @NonNull Response<List<UsersSearchModel>> response) {
                if (response.isSuccessful()) {
                    if (mPageFlag == 1) {
                        if (mListUsers.size() > 0) mCountSinseOld = mCountSinse;
                    } else mListUsers.clear();
                    assert response.body() != null;
                    mListUsers.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    mCountSinse = mListUsers.get(mListUsers.size() - 1).getId();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UsersSearchModel>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UiCreate(NavigationView navigationView, int requestCode) {
        View headerLayout = navigationView.getHeaderView(0);
        TextView login = (TextView) headerLayout.findViewById(R.id.login);
        login.setText(mAuthUser.getLogin());
        TextView name = (TextView) headerLayout.findViewById(R.id.email);
        name.setText(mAuthUser.getName());
        ImageView icon = (ImageView) headerLayout.findViewById(R.id.img_icon);
        if (requestCode == 0) {
            Uri uri = Uri.parse(mAuthUser.getAvatarUrl());
            Glide.with(this)
                    .load(uri)
                    .into(icon);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(getStartIntentAboutAuth(this, mAuthUser));
        } else if (id == R.id.nav_edit) {
            startActivityForResult(getStartIntentEdit(this, mAuthUser), REQ_CODE_CHILD);
        } else if (id == R.id.nav_out) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_CHILD) {
            if (data != null) {
                mAuthUser = (AutorizationUserModel) Objects.requireNonNull(data.getExtras()).getSerializable("AutorizationUserModel");
                UiCreate(mNavigationView, 1);
            }
        }
    }
}
