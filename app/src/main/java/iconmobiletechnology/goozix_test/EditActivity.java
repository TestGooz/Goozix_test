package iconmobiletechnology.goozix_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import iconmobiletechnology.goozix_test.Model.AutorizationUserModel;
import iconmobiletechnology.goozix_test.Model.UpdateUserBody;
import iconmobiletechnology.goozix_test.RestClient.ApiService;
import iconmobiletechnology.goozix_test.RestClient.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    AutorizationUserModel mAuthUser;
    // UI references.
    private AutoCompleteTextView mNameView;
    private EditText mBlogView;
    private EditText mCompanyView;
    private EditText mLocationView;
    private EditText mBioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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

        mNameView = (AutoCompleteTextView) findViewById(R.id.text_nane);
        mNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        mBlogView = (EditText) findViewById(R.id.text_blog);
        mBlogView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        mCompanyView = (EditText) findViewById(R.id.text_company);
        mCompanyView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        mLocationView = (EditText) findViewById(R.id.text_location);
        mLocationView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        mBioView = (EditText) findViewById(R.id.text_bio);
        mBioView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    Update();
                    return true;
                }
                return false;
            }
        });

        Button mUpdateButton = (Button) findViewById(R.id.button_update);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });

        UiCreate((AutorizationUserModel) getIntent().getSerializableExtra("AutorizationUserModel"));
    }

    private void UiCreate(AutorizationUserModel AuthUser)
    {
        mAuthUser = AuthUser;

        mNameView.setText(AuthUser.getName());
        mBlogView.setText(AuthUser.getBlog());
        mCompanyView.setText(AuthUser.getCompany());
        mLocationView.setText(AuthUser.getLocation());
        mBioView.setText(AuthUser.getBio());
    }

    private void Update() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ApiService Service = ((RestClient) getApplicationContext()).getService();

        UpdateUserBody UpdateUserBody = new UpdateUserBody();
        UpdateUserBody.setName(String.valueOf(mNameView.getText()));
        UpdateUserBody.setBlog(String.valueOf(mBlogView.getText()));
        UpdateUserBody.setCompany(String.valueOf(mCompanyView.getText()));
        UpdateUserBody.setLocation(String.valueOf(mLocationView.getText()));
        UpdateUserBody.setBio(String.valueOf(mBioView.getText()));

        Call<AutorizationUserModel> Update = Service.update(UpdateUserBody);
        Update.enqueue(new Callback<AutorizationUserModel>() {
            @Override
            public void onResponse(@NonNull Call<AutorizationUserModel> call, @NonNull Response<AutorizationUserModel> response) {
                if (response.isSuccessful()) {
                    mAuthUser = response.body();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("AutorizationUserModel", mAuthUser);
                    setResult(1, resultIntent);
                    finish();
                }
                else
                {
                    Toast.makeText(EditActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AutorizationUserModel> call, @NonNull Throwable t) {
                Toast.makeText(EditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}