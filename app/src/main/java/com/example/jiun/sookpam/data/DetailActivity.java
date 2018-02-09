package com.example.jiun.sookpam.data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jiun.sookpam.R;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        TextView bodyView = (TextView) findViewById(R.id.detail_body);

        titleView.setText(title);
        bodyView.setText(body);
    }
}
