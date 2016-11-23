package com.example.android.toolate;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BetActivity extends AppCompatActivity {
    boolean countDownFinished;
    boolean teamWin;
    long bet;
    long seconds;
    CountDownTimer countDownTimer;

    @BindView(R.id.round_countdown)
    TextView roundCountdown;
    @BindView(R.id.team_win)
    TextView teamWon;
    @BindView(R.id.red_square)
    TextView redSquare;
    @BindView(R.id.green_square)
    TextView greenSquare;
    @BindView(R.id.timer)
    TextView timer;

    @OnClick(R.id.red_square)
    public void red_bet() {
        bet++;
        if (seconds < 3000) {
            startTimer(seconds + 3000);
        }
        redSquare.setText(String.valueOf(bet));
        teamWin = true;
    }

    @OnClick(R.id.green_square)
    public void green_bet() {
        bet++;
        if (seconds < 3000) {
            startTimer(seconds + 3000);
        }
        greenSquare.setText(String.valueOf(bet));
        teamWin = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        teamWon.setVisibility(View.GONE);
        bet = 0;
        startTimer(0);
    }

    public void startTimer(long timeInMilis) {
        if (!countDownFinished) {
            if (!(countDownTimer == null) & bet > 0) {
                countDownTimer.cancel();
            }
            countDownTimer = new CountDownTimer(timeInMilis, 500) {

                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                    seconds = millisUntilFinished;
                }

                public void onFinish() {
                    countDownFinished = true;
                    redSquare.setClickable(false);
                    greenSquare.setClickable(false);
                    afterCountDown();
                }
            }.start();
        }
    }

    public void afterCountDown() {
        if (teamWin) {
            teamWon.setText(getResources().getString(R.string.red_won_bet));
        } else {
            teamWon.setText(getResources().getString(R.string.green_won_bet));
        }
        redSquare.setVisibility(View.GONE);
        greenSquare.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);
        teamWon.setVisibility(View.VISIBLE);
        new CountDownTimer(0, 500) {

            public void onTick(long millisUntilFinished) {
                roundCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                Intent roundIntent = new Intent(getApplicationContext(),RoundActivity.class);
                roundIntent.putExtra("TEAM",teamWin);
                startActivity(roundIntent);

            }
        }.start();

    }
}


