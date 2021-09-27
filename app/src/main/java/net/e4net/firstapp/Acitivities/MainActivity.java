package net.e4net.firstapp.Acitivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.e4net.firstapp.Constants.Constants;
import net.e4net.firstapp.R;
import net.e4net.firstapp.Utils.QRUtils;

public class MainActivity extends AppCompatActivity {

    
    private TextView basicTV;
    private EditText basicET;
    private Button basicBtn;
    private ImageView basicIV;

    final int RC_SIGN_IN = 777;

    private GoogleSignInClient mGoogleSignInClient;

    private Button googleLoginBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("48925873851-fnjdpgqbp2inlo0fc7qhplojn356bi42.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        basicTV = findViewById(R.id.basicTV);
        basicET = findViewById(R.id.basicET);
        basicBtn = findViewById(R.id.basicBtn);
        basicIV = findViewById(R.id.basicIV);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);

        basicTV.setText("변경된 문구");

        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicTV.setText(basicET.getText().toString());
            }
        });

        basicIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRUtils.startQRScan(MainActivity.this);
            }
        });

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleSignIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(googleSignIntent, RC_SIGN_IN);
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(Constants.LOG_STRING, "Google get Id : " + account.getId());
                Log.d(Constants.LOG_STRING, "Google get Email : " + account.getEmail());

                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){

            }
            Log.e(Constants.LOG_STRING, "Login callback 호출됨");
        } else if(requestCode == IntentIntegrator.REQUEST_CODE){
            IntentResult ir = IntentIntegrator.parseActivityResult(requestCode, requestCode, data);

            if(data == null){
                Log.e(Constants.LOG_STRING, "QR SCAN ERROR!");
            } else {
                basicTV.setText(ir.getContents());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){

                }
                FirebaseUser user = mAuth.getCurrentUser();
                user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        Log.d(Constants.LOG_STRING, "ID TOKEN : " + task.getResult().getToken());
                    }
                });
            }
        });
    }
}