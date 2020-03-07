package com.example.myapplication;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.DB.DBadapter;
import com.example.myapplication.DB.phone_msg_detiles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Target extends AppCompatActivity {
TextView userN , passW ;
Button writePrf ,readPrf , internalWrite ,internalRead ,sqlWrite ,sqlqlRead ;
SharedPreferences preferences ;
FileOutputStream fos ;
DataOutputStream dos ;//to deal with high level data
    FileInputStream FINS ;
    DataInputStream DINS ;
    private final static String filename ="file msg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        userN =findViewById(R.id.getUser);
        passW =findViewById(R.id.getPass);
        writePrf =findViewById(R.id.write);
        readPrf =findViewById(R.id.Read);
        internalWrite =findViewById(R.id.internal_write);
        internalRead =findViewById(R.id.internal_Read);
        sqlWrite =findViewById(R.id.sqlite_write);
        sqlqlRead =findViewById(R.id.sqlite_Read);

        Intent intent =getIntent();
        final String user ,pass ;
        user =intent.getStringExtra("user");
        pass =intent.getStringExtra("pass");
        userN.setText(user);
        passW.setText(pass);
        writePrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences=getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(user,userN.getText().toString());
                editor.putString(pass,passW.getText().toString());
                editor.commit();
                userN.setText("");
                passW.setText("");

            }

        });

        readPrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences =getPreferences(Context.MODE_PRIVATE);
                userN.setText(preferences.getString(user,"NONAV"));
                passW.setText(preferences.getString(pass,"NONAVA"));
            }
        });
        internalWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //low level fos
                    //take file name , Mod ,return file output stream
                    //to store privet non access this file
                    fos = openFileOutput(Target.filename, Context.MODE_PRIVATE);
                    //u can use it direct and write as dataoutput Stream
                    // new obj from dos this take REF from low level like Fos
                    dos =new DataOutputStream(fos);
                    //then i can write
                    dos.writeUTF(userN.getText().toString());//should add catch exe
                    dos.writeUTF(passW.getText().toString());
                    //then should close evTH cause Ram storage
                    fos.close();
                    dos.close();
                    //clear Text
                    userN.setText("");
                    passW.setText("");


                }catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        internalRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FINS = openFileInput(filename);
                    DINS = new DataInputStream(FINS);
                    userN.setText(DINS.readUTF());
                    passW.setText(DINS.readUTF());
                }
                catch (FileNotFoundException e)
                {e.printStackTrace();}
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        sqlWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBadapter adapter =new DBadapter(Target.this);
                adapter.addEntery(new phone_msg_detiles(passW.getText().toString()
                        ,userN.getText().toString()));
                passW.setText("");
                userN.setText("");
                Log.d("WRITE" ,"WITE ERRor ");
            }
        });
        sqlqlRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBadapter adapter =new DBadapter(Target.this);
                phone_msg_detiles result = adapter.getentry();
                passW.setText(result.getPhone());
                userN.setText(result.getMsg());
                Log.d("Read" ,"Read  ERRor ");


            }
        });
    }
}
