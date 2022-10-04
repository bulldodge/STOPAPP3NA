// Natalie Arner
// Stopwatch code

package com.example.stopwatchna;

import androidx.appcompat.app.AppCompatActivity;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Chronometer stopwatch; // the stop watch
    private boolean running= false; // checks if running
    private long offset= 0;

    // Bundle Strings
    public static final String OFFSET_KEY= "offset";
    public static final String RUNNING_KEY= "running";
    public static final String BASE_KEY= "base";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ref stop watch
        stopwatch = findViewById(R.id.stopwatch);
        Button buttonStart = findViewById(R.id.start_button);
        Button buttonPause = findViewById(R.id.pause_button);
        Button buttonReset = findViewById(R.id.reset_button);

        //restorer

        if (savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY);
            running = savedInstanceState.getBoolean(RUNNING_KEY);
            if (running){
                stopwatch.setBase(savedInstanceState.getLong(BASE_KEY));
                stopwatch.start();
            }else{
                setBaseTime();
            }
        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running){
                    setBaseTime();
                    stopwatch.start();
                    running = true;
                }
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running){
                    saveOffset();
                    stopwatch.stop();
                    running = false;
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = 0;
                setBaseTime();
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong(OFFSET_KEY, offset);
        savedInstanceState.putBoolean(RUNNING_KEY, running);
        savedInstanceState.putLong(BASE_KEY, stopwatch.getBase());
    }

    //helper methods

    public void setBaseTime(){
        stopwatch.setBase(SystemClock.elapsedRealtime()-offset);
    }
    public void saveOffset(){
        offset = SystemClock.elapsedRealtime()-stopwatch.getBase();
    }
}