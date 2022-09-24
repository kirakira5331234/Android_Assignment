package com.example.truonghongtham.lab04_1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pbFirst, pbSecond;
    private TextView tvMsgWorking, tvMsgReturned;
    private boolean isRunning;
    private int MAX_SEC;
    private int intTest;
    private Thread bgThread;
    private Handler handler;
    private Button btnStart;

    private void findViewByIds(){
        pbFirst=(ProgressBar) findViewById(R.id.pb_first);
        pbSecond= (ProgressBar) findViewById(R.id.pb_second);
        tvMsgWorking= (TextView) findViewById(R.id.tv_working);
        tvMsgWorking = (TextView) findViewById(R.id.tv_return);
        btnStart = (Button) findViewById(R.id.btn_start);
    }
    private void initVariables(){
        isRunning = false;
        MAX_SEC=20;
        intTest =1;
        pbFirst.setMax(MAX_SEC);
        pbFirst.setProgress(0);

        //Init Views
        pbFirst.setVisibility(View.GONE);
        pbSecond.setVisibility(View.GONE);

        handler = new Handler(){
            @Override
            public void handleMessage (Message msg){
                super.handleMessage(msg);

                String returnedValue = (String) msg.obj;

                //Don something with the value sent by backgroud thread here...
                tvMsgReturned.setText(getString(R.string.return_by_bg_thread)+returnedValue);
                pbFirst.incrementProgressBy(2);

                // Testing thread's termination
                if (pbFirst.getProgress()== MAX_SEC) {
                    tvMsgReturned.setText(getString(R.string.done_background_thread_has_bên_stopped));
                    tvMsgWorking.setText(getString(R.string.done));
                    pbFirst.setVisibility(View.GONE);
                    pbSecond.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    isRunning = false;
                }else{
                    tvMsgWorking.setText(getString(R.string.working)+pbFirst.getProgress());
                }
            }
        };
    }

    private void initBgThread(){
        bgThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for (int i=0;i<MAX_SEC && isRunning;i++){
                        // Sleep one second
                        Thread.sleep(1000);

                        Random rnd = new Random();
                        // This is a locally generated value
                        String data = "Thread value: "+ (int) rnd.nextInt(101);
                        // We can see change (global) class variables
                        data+= getString(R.string.global_value_seen)+" "+intTest;
                        intTest++;
                        // If thread still alive send the meggage
                        if (isRunning){
                            // Request a message token and put some data in it
                            Message msg = handler.obtainMessage(1,(String) data);
                        }

                    }
                } catch (Throwable t) {
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        initVariables();

        // Handle clickListenner
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isRunning=true;
                pbFirst.setVisibility(View.VISIBLE);
                pbSecond.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.GONE);
                bgThread.start();
             }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        initBgThread();
    }


    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }


}
