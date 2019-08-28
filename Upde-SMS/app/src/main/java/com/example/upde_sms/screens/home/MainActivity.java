package com.example.upde_sms.screens.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.upde_sms.R;

public class MainActivity extends AppCompatActivity {

    public static Intent getInstance(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
