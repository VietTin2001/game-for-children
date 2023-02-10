package com.example.final_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.final_project.database.AccountDB;
import com.example.final_project.entities.Account;
import com.example.final_project.fragment.Age_fragment;
import com.example.final_project.fragment.Home_fragment;
import com.example.final_project.fragment.Profile_Fragment;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public final static int MY_REQUEST_CODE = 10 ;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_AGE = 1;
    private static final int FRAGMENT_PROFILE = 2;
    private int FRAGMENT_CURRENT = FRAGMENT_HOME;
    private final Profile_Fragment profileFragment = new Profile_Fragment();
    private Account account;
    private AccountDB accountDB;
    private String Email,Password,Name;
    private ImageView avatar;
    private TextView tvEmail,tvFullName;
    public Bitmap bitmap;
    private View header;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
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
                    profileFragment.setBitmapImageView(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.nav_drawer_open,R.string.nav_drawer_close);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

        accountDB = new AccountDB(getApplicationContext());
        if (getIntent().getExtras() != null){
            account = (Account) getIntent().getExtras().get("account");
            Email = account.getEmail();
            Password = account.getPassword();
        }
        String email = Email;
        String password = Password;
        account = accountDB.login(email, password);
        Name= account.getFullName();
        initUi();
        showInformation();

        replaceFragment(new Home_fragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);


    }


    private void initUi(){
        avatar = header.findViewById(R.id.img_avatar);
        tvEmail = header.findViewById(R.id.tv_email);
        tvFullName = header.findViewById(R.id.tv_name);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home){
            if(FRAGMENT_CURRENT != FRAGMENT_HOME){
                replaceFragment(new Home_fragment());
                FRAGMENT_CURRENT =FRAGMENT_HOME;
            }
        }else if (id == R.id.nav_age){
            if(FRAGMENT_CURRENT != FRAGMENT_AGE){
                replaceFragment(new Age_fragment());
                FRAGMENT_CURRENT =FRAGMENT_AGE;
            }
        }else if(id == R.id.nav_profile){
            if(FRAGMENT_CURRENT != FRAGMENT_PROFILE){
                sendDataToFragment(profileFragment);
                //replaceFragment(new Profile_Fragment());
                FRAGMENT_CURRENT =FRAGMENT_PROFILE;
            }
        }else if(id ==R.id.nav_sign_out){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.authenticate);
            builder.setMessage(R.string.string_authenticate);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPress(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    private void sendDataToFragment(Fragment fragment){
        String email = Email;
        String password = Password;

        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        bundle.putString("password",password);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();

    }

    public void showInformation(){
        if(account == null){
            return;
        }else{
            tvFullName.setText(Name);
            tvEmail.setText(Email);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else{

            }
        }
    }

    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("img/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
}
