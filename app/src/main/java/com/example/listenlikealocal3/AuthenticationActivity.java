package com.example.listenlikealocal3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.listenlikealocal3.Services.SpotifyClient;
import com.example.listenlikealocal3.Model.User;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import static com.spotify.sdk.android.auth.AuthorizationResponse.Type.TOKEN;

public class AuthenticationActivity extends AppCompatActivity {

    public static final String TAG = "AuthenticationActivity";
    private static final String CLIENT_ID  = "12b41bf4bae9497bb882c55db18c0c0e";
    private static final int REQUEST_CODE = 1337;
    public static final String REDIRECT_URI = "com.listenlikealocal://callback";

    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivityAuthentication();
        SharedPreferences msharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        RequestQueue queue = Volley.newRequestQueue(this);
    }

    protected void LoginActivityAuthentication(){
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming user-top-read, user-read-recently-played,user-library-modify,user-read-email,user-read-private"});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                case TOKEN:
                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("START", "This is the auth token");
                    editor.apply();
                    waitForUserInfo();
                    break;

                case ERROR:
                    Log.e(TAG, "Error");
                    break;

                default:
                    Log.e(TAG, "Auth flow cancelled");
            }
        }
    }

    private void waitForUserInfo() {
        SpotifyClient userService = new SpotifyClient(getApplicationContext());
        userService.get(() -> {
            User user = userService.getUser();
            editor = getSharedPreferences("SPOTIFY", 0).edit();

            editor.putString("userid", user.id);
            editor.putString("name", user.display_name);

            editor.apply();
            startMainActivity();
        });
    }


    private void startMainActivity() {
        Intent intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}




