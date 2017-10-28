package com.example.myles.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar seekBar;
    Button washerButton;
    Button dryerButton;
    Button startButton;
    boolean timerIsOn;
    int minutes;
    int seconds;
    String secondsString;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        timerIsOn = false;

        //sets maximum slider (in seconds)
        seekBar.setMax(3600);

        //sets current slider progress
        seekBar.setProgress(600);
        timerUpdate(600);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                timerUpdate(i);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });




    }

    public void setTimeDryer(View view) {

        //set timer to 48 min
        seekBar.setProgress(2880);




    }
    public void setTimeWasher(View view){


        //set timer to 35 min
        seekBar.setProgress(2100);

    }

    public void startTimer(View view){



        if(timerIsOn == false) {

            //set UI
            timerIsOn = true;
            seekBar.setEnabled(false);
            washerButton.setEnabled(false);
            dryerButton.setEnabled(false);
            startButton.setText("Stop");


            //seekbar is in miliseconds so you must multiply by 1000, countdown by 1 sec at a time
            //add 100 to have an extra 1/10 of a second
            new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    if(timerIsOn == true) {

                        timerUpdate((int) l / 1000);
                    }else{

                        //just cancels the countdown all together
                        cancel();
                    }
                }

                @Override
                public void onFinish() {

                    //cant use this for context because "this" will refer to countdowntimer function
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);

                    mediaPlayer.start();




                    //reset UI


                    //trying to loop mediaplayer while stop isn't pressed
                    //Note use two buttons in same position make them visible/invisible
                    timerTextView.setText("00:00");

                    while(mediaPlayer.isPlaying()){

                        //loop the sound until user clicks stop
                        mediaPlayer.setLooping(true);
                    }



                    startButton.setText("Start");
                    timerIsOn = false;
                    seekBar.setEnabled(true);
                    washerButton.setEnabled(true);
                    dryerButton.setEnabled(true);




                }
            }.start();

        }else{

            //reset UI

            if(mediaPlayer != null){

                mediaPlayer.stop();
            }

            mediaPlayer.reset();
            startButton.setText("Start");
            timerIsOn = false;
            seekBar.setEnabled(true);
            washerButton.setEnabled(true);
            dryerButton.setEnabled(true);



        }



    }

    public void timerUpdate(int timeRemaining){

        //cast to int to round down
        minutes = (int) timeRemaining / 60;
        seconds = timeRemaining - minutes * 60;
        secondsString = Integer.toString(seconds);

        //if seconds is an even number add an extra 0 i.e. 10:0 to 10:00
        if(secondsString == "0"){

            secondsString = "00";

            //if seconds is less than 10 add 0 to front i.e. 9:3 to 9:03
        }else if (seconds < 10){

            secondsString = "0" + secondsString;
        }


        timerTextView.setText(minutes + " : " + secondsString);


    }

    public void findViews(){

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        washerButton = (Button)findViewById(R.id.washerButton);
        dryerButton = (Button)findViewById(R.id.dryerButton);
        startButton = (Button)findViewById(R.id.startButton);




    }
}
