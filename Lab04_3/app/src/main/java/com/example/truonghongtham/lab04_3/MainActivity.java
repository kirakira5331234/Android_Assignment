package com.example.truonghongtham.lab04_3;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnQuickJob, btnSlowJob;
    private TextView tvStatus;
    private SlowTask slowTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();

        // Init slowtask
        slowTask=new SlowTask(MainActivity.this, tvStatus);

        // Handle onClickListenner
        btnQuickJob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                tvStatus.setText((new Date()).toString());
            }
        });
        btnSlowJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slowTask.execute();
            }
        });
    }

    private void findViewByIds() {
        btnQuickJob = (Button) findViewById(R.id.btn_quick_job);
        btnSlowJob = (Button) findViewById(R.id.btn_slow_job);
        tvStatus = (TextView) findViewById(R.id.tv_status);
    }
}

