package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText userN , passW;
    Button btn_click ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userN = findViewById(R.id.user);
        passW =findViewById(R.id.pass);
        btn_click =findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user ,pass ;
                user = userN.getText().toString();
                pass =passW.getText().toString();
                Intent intent=new Intent(MainActivity.this,Target.class);
                intent.putExtra("pass",pass);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });

    }
}
