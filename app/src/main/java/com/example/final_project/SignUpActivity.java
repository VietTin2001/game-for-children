package com.example.final_project;

import static com.example.final_project.MainActivity.MY_REQUEST_CODE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
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

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private boolean passwordShowing = false;
    private boolean passwordConfirmShowing = false;
    private LinearLayout layoutSignIn;
    private EditText edtEmail, edtPassword, edtConfirmPassword, edtFullName;
    private Button btnSignup;
    private ProgressDialog progressDialog;
    private ImageView imgPasswordIcon;
    private ImageView imgConfirmPasswordIcon, imgAvatar;
    private final Profile_Fragment profileFragment = new Profile_Fragment();
    private MainActivity mainActivity;
    private static final int PICK_IMAGE_REQUEST = 10;
    private Uri uri;
    private Bitmap bitmap;


    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if (intent == null){
                            return;
                        }
                        Uri uri = intent.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initUi();
        initListener();
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        edtFullName = findViewById(R.id.edt_full_name);
        btnSignup = findViewById(R.id.btn_sign_up);
        layoutSignIn = findViewById(R.id.layout_sign_in);
        imgPasswordIcon = findViewById(R.id.img_password_icon);
        imgConfirmPasswordIcon = findViewById(R.id.img_confirm_password_icon);
        imgAvatar = findViewById(R.id.img_avatar);
        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignup(view);
            }
        });
        layoutSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
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

        imgConfirmPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordConfirmShowing){
                    passwordConfirmShowing = false;
                    edtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    passwordConfirmShowing = true;
                    edtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                edtConfirmPassword.setSelection(edtConfirmPassword.length());
            }
        });
    }

    public void onClickRequestPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String[] permissons = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissons,MY_REQUEST_CODE);

        }
    }

    private void onClickSignup(View view) {


    try {
        AccountDB accountDB = new AccountDB(getApplicationContext());
        Account account = new Account();
        account.setEmail(edtEmail.getText().toString().trim());
        //account.setAvatar(bitmap);
        String temp1 = edtPassword.getText().toString().trim();
        String temp2 = edtConfirmPassword.getText().toString().trim();
        if(temp2.equals(temp1)){
            account.setPassword(edtPassword.getText().toString().trim());
        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.error_confirm_pass);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        account.setFullName(edtFullName.getText().toString().trim());
        Account temp = accountDB.checkUser(edtEmail.getText().toString().trim());
        if (temp == null) {
            if (accountDB.create(account)) {

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                Toast.makeText(SignUpActivity.this,"Sign Up Success !!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finishAffinity();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.can_not_create_account);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.username_exists);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
    }catch (Exception e){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.can_not_create_account);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
    }

    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data!= null && data.getData() !=null){
//            uri = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                imgAvatar.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("img/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
}