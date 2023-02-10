package com.example.final_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.example.final_project.entities.Account;

import java.io.ByteArrayOutputStream;

public class AccountDB extends SQLiteOpenHelper {

    private Context context;
    private static String dbName ="contactDB";
    private static String tableName ="account";
    private static  String idColum = "id";
    private static String emailColum ="email";
    private static String passwordColum = "password";
    private static  String fullNameColum = "fullName";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imgByte;



    public AccountDB(Context context){
        super(context,dbName,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("create table " + tableName +"(" +
               idColum + "integer primary key autoincrement,"+
               emailColum + "text, "+
               passwordColum +"text,"+
               fullNameColum +"text" +")");

        final String Insert_Data ="INSERT INTO account VALUES(9,'admin@gmail.com','123','admin')";
        sqLiteDatabase.execSQL(Insert_Data);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public boolean create(Account account){
        boolean result = true;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//            Bitmap imgToStore = account.getAvatar();
//            byteArrayOutputStream = new ByteArrayOutputStream();
//            imgToStore.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//
//            imgByte = byteArrayOutputStream.toByteArray();

            ContentValues contentValues = new ContentValues();
            contentValues.put(emailColum, account.getEmail());
            contentValues.put(passwordColum,account.getPassword());
            contentValues.put(fullNameColum,account.getFullName());
            //contentValues.put("avatar",imgByte);


            long check = sqLiteDatabase.insert(tableName,null,contentValues);
            long in = check;
            if(check > -1){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            result= false;
        }
        return result;
    }
    public Account checkUser(String email){
        Account account = null;
        try{

            SQLiteDatabase sqLiteDatabase= getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ tableName+" where email = ?", new String[]{email});
            if (cursor.moveToFirst()){
                account = new Account();
                account.setId(cursor.getInt(0));
                account.setEmail(cursor.getString(1));
                account.setPassword(cursor.getString(2));
                account.setFullName(cursor.getString(3));
//                byte[]  imgByte = cursor.getBlob(4);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
               // account.setAvatar(bitmap);
            }
        }catch(Exception e){
            account = null;
        }
        return account;

    }

    public Account login(String email,String password){
        Account account = null;
        try{

            SQLiteDatabase sqLiteDatabase= getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ tableName+" where email = ? and password = ?", new String[]{email,password});
            if (cursor.moveToFirst()){
                account = new Account();
                account.setId(cursor.getInt(0));
                account.setEmail(cursor.getString(1));
                account.setPassword(cursor.getString(2));
                account.setFullName(cursor.getString(3));
//                byte[]  imgByte = cursor.getBlob(4);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
//                account.setAvatar(bitmap);
            }
        }catch(Exception e){
            account = null;
        }
        return account;

    }

    public boolean update(Account account){
        boolean result = true;
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//            Bitmap imgToStore = account.getAvatar();
//            byteArrayOutputStream = new ByteArrayOutputStream();
//            imgToStore.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

 //           imgByte = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put(emailColum, account.getEmail());
            contentValues.put(passwordColum,account.getPassword());
            contentValues.put(fullNameColum,account.getFullName());
//            contentValues.put(avatarColum,imgByte);
            long check = sqLiteDatabase.update(tableName, contentValues, idColum +"= ?",new String []{ String.valueOf(account.getId())});
            if(check > -1){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            result= false;
        }
        return result;
    }

    public Account find(int id){
        Account account = null;
        try{

            SQLiteDatabase sqLiteDatabase= getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ tableName+" where id = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()){
                account = new Account();
                account.setId(cursor.getInt(0));
                account.setEmail(cursor.getString(1));
                account.setPassword(cursor.getString(2));
                account.setFullName(cursor.getString(3));
//                byte[]  imgByte = cursor.getBlob(4);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
//                account.setAvatar(bitmap);
            }
        }catch(Exception e){
            account = null;
        }
        return account;

    }
}
