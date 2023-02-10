package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.final_project.database.AccountDB;
import com.example.final_project.entities.Account;
import com.example.final_project.fragment.Profile_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private boolean passwordShowing = false;
    private LinearLayout layoutSignUp;
    private EditText edtUser, edtPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    private ImageView imgPasswordIcon;
    private String mEmail,mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initUi();
        initListener();
    }

    private void initUi(){
        progressDialog = new ProgressDialog(this);
        edtUser = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        imgPasswordIcon = findViewById(R.id.img_password_icon);
        layoutSignUp = findViewById(R.id.layout_sign_up);
    }

    private void initListener() {
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        imgPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordShowing){
                    passwordShowing = false;
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    passwordShowing = true;
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                edtPassword.setSelection(edtPassword.length());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn(view);
            }
        });
//
//        LayoutForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickForgotPassword();
//            }
//        });
    }

    private void onClickSignIn(View view) {
        progressDialog.show();

        AccountDB accountDB = new AccountDB(getApplicationContext());
        String username = edtUser.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        Account account = accountDB.login(username,password);
        if (account == null){
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.invalid_account);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }else{
            progressDialog.dismiss();
            Intent intent= new Intent(SignInActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("account",account);
            intent.putExtras(bundle);
            startActivity(intent);
        }


//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String strEmail = edtEmail.getText().toString().trim();
//        String strPassword = edtPassword.getText().toString().trim();
//        progressDialog.show();
//        auth.signInWithEmailAndPassword(strEmail, strPassword)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//
//                        } else {
//                            Toast.makeText(SignInActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }
    public String getEmail() {
        return mEmail ;
    }
    public String getPassword(){
        return mPassword;
    }

//
//    private void onClickForgotPassword(){
//        progressDialog.show();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String emailAddress = "viettin.250801@gmail.com";
//        // làm 1 layout để người dùng nhập email vào
//
//        auth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SignInActivity.this,"Email sent !",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}