package com.example.hi_locationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private String photoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        final Button button = (Button) findViewById(R.id.googleLoginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google_giris_butonu(v);
                System.out.println("_________________________________________2  onClick google_giris_butonu________________________________________________");
            }
        });

    }

    public void google_giris_butonu(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //System.out.println("_______________________________________________1  google_giris_butonu________________________________________________");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //System.out.println("_________________________________________3   onActivityResult________________________________________________");

        if (requestCode == RC_SIGN_IN) {
            data.getExtras();
            //System.out.println("_________________________________________4   onActivityResult if if if________________________________________________");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //System.out.println("_________________________________________"+task.isSuccessful()+"________________________________________________");

            try {
                System.out.println("_________________________________________5   onActivityResult try try try________________________________________________");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                photoUri = account.getPhotoUrl().toString();

                //task.addOnCompleteListener(new MapsActivity(), addContentView(mStatusTextView));
                //System.out.println("_________________________________________"+account+"6   onActivityResult try try try________________________________________________");
                //setContentView(R.layout.activity_maps);
                System.out.println("_________________________________________ setContentView i yaptım________________________________________________");
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                intent.putExtra("PHOTOURI", photoUri);
                //intent.putExtra("FULLNAME", mGoogleSignInClient.getApiOptions().getAccount().name);

                MainActivity.this.startActivity(intent);
                System.out.println("_________________________________________ intent mintent _______________________________________________");

            }
            catch (Exception e){}
            finally {
                System.out.println("_________________________________________ finallllyyyy  _______________________________________________");

                mGoogleSignInClient.revokeAccess();
            }
        }
    }



}

