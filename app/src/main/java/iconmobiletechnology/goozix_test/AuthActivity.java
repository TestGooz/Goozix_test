package iconmobiletechnology.goozix_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import iconmobiletechnology.goozix_test.Model.AutorizationUserModel;
import iconmobiletechnology.goozix_test.RestClient.ApiService;
import iconmobiletechnology.goozix_test.RestClient.RestClient;


public class AuthActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
        mLoginView = (AutoCompleteTextView) findViewById(R.id.login);
        mLoginView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        mLoginView.setError(null);
        mPasswordView.setError(null);

        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        } else if (!isLoginValid(login)) {
            mLoginView.setError(getString(R.string.error_invalid_login));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) focusView.requestFocus();
        else showProgress(login,password);
    }

    private boolean isLoginValid(String login) {
        return login.length() > 1;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(String login, String password) {
        mProgressView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        RestClient Client = ((RestClient) getApplicationContext());
        Client.CreateAuthRetrofit(login,password);
        ApiService Service = ((RestClient) getApplicationContext()).getService();

        Call<AutorizationUserModel> m = Service.AuthUser();
        m.enqueue(new Callback<AutorizationUserModel>() {
            @Override
            public void onResponse(@NonNull Call<AutorizationUserModel> call, @NonNull Response<AutorizationUserModel> response) {
                if (response.isSuccessful()) {
                    showResult(response.body());
                }
                else
                {
                    showProgressError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AutorizationUserModel> call, @NonNull Throwable t) {
                Toast.makeText(AuthActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showResult(AutorizationUserModel AuthUser) {
        Intent in = new Intent(this, MainActivity.class);
        in.putExtra("AutorizationUserModel", AuthUser);
        startActivity(in);
        mProgressView.setVisibility(View.GONE);
    }

    private void showProgressError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сообщение об ошибке")
                .setMessage("Не удалось авторизироваться. Проверьте точность вводимых данных")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mLoginView.getText().clear();
                                mLoginView.requestFocus();
                                mPasswordView.getText().clear();
                                mProgressView.setVisibility(View.GONE);
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
