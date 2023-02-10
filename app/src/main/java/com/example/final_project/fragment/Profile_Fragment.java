package com.example.final_project.fragment;

import static com.example.final_project.MainActivity.MY_REQUEST_CODE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.BidiRun;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.final_project.MainActivity;
import com.example.final_project.R;
import com.example.final_project.database.AccountDB;
import com.example.final_project.entities.Account;

public class Profile_Fragment extends Fragment {


    private View mView;
    private boolean passwordShowing = false;
    private boolean passwordConfirmShowing = false;
    private EditText edtFullName, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnUpdate;
    private ImageView imgPasswordIcon, imgConfirmIcon, imgAvatar;
    private Account account;
    private MainActivity mainActivity;
    private String Email;
    private String Password;
    private AccountDB accountDB;
    private Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        mainActivity = (MainActivity) getActivity();
        Email = getArguments().getString("email");
        Password = getArguments().getString("password");
        accountDB = new AccountDB(mainActivity.getApplicationContext());
        String email = Email;
        String password = Password;
        account = accountDB.login(email, password);
        initUI();
        initListener();
        return mView;
    }

    private void initUI() {

        edtFullName = mView.findViewById(R.id.edt_full_name);
        edtEmail = mView.findViewById(R.id.edt_email);
        edtPassword = mView.findViewById(R.id.edt_password);
        edtConfirmPassword = mView.findViewById(R.id.edt_confirm_password);

        edtEmail.setText(account.getEmail());
        edtFullName.setText(account.getFullName());
        imgAvatar = mView.findViewById(R.id.img_avatar);
        imgPasswordIcon = mView.findViewById(R.id.img_password_icon);
        imgConfirmIcon = mView.findViewById(R.id.img_confirm_password_icon);
        btnUpdate = mView.findViewById(R.id.btn_update_profile);
    }

    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        imgPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordShowing) {
                    passwordShowing = false;
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    passwordShowing = true;
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                edtPassword.setSelection(edtPassword.length());
            }
        });

        imgConfirmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordConfirmShowing) {
                    passwordConfirmShowing = false;
                    edtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    passwordConfirmShowing = true;
                    edtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                edtConfirmPassword.setSelection(edtConfirmPassword.length());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(view);
            }
        });
    }

    public void onClickRequestPermission(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity ==null){
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mainActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mainActivity.openGallery();
        }else{
            String[] permissons = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissons,MY_REQUEST_CODE);

        }
    }

    private void updateProfile(View view) {
        try {
            Account accountCurrent = accountDB.find(account.getId());
            String newEmail = edtEmail.getText().toString().trim();
            Account temp = accountDB.checkUser(newEmail);
            if (!newEmail.equalsIgnoreCase(accountCurrent.getEmail()) && temp !=null) {
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
                return;

            }
                accountCurrent.setEmail(edtEmail.getText().toString().trim());
                accountCurrent.setFullName(edtFullName.getText().toString().trim());
                //accountCurrent.setAvatar(mainActivity.bitmap);
                String password = edtPassword.getText().toString().trim();
                String confirm = edtConfirmPassword.getText().toString().trim();
                if (!password.isEmpty() && password.equals(confirm)) {
                    accountCurrent.setPassword(edtPassword.getText().toString().trim());
                }
                if (accountDB.update(accountCurrent)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.notification);
                    builder.setMessage(R.string.update_success);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.error);
                    builder.setMessage(R.string.update_failed);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }

        } catch (Exception e) {
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
        }
        mainActivity.showInformation();
    }
    public void setBitmapImageView(Bitmap bitmap){
        imgAvatar.setImageBitmap(bitmap);
    }
}

