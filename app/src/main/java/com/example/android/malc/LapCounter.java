package com.example.android.malc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class LapCounter extends AppCompatActivity {

    Athlete[] athletesList;
    int activeAthletes;
    LinearLayout[] athleteView;
    TextView[] lapButtons;
    TextView[] lapCountText;
    TextView[] deltaTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_counter);

        LinearLayout rootLapCounter = (LinearLayout) findViewById(R.id.rootLapCounter);

        LinearLayout.LayoutParams paramsMWn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        /*Grab variables from intent*/
        Intent receivedIntent = getIntent();
        int lapCount = Integer.parseInt(receivedIntent.getStringExtra("lapCount"));
        ArrayList<String> athletesBibs = receivedIntent.getStringArrayListExtra("athletesBibs");
        activeAthletes = athletesBibs.size();
        final int noOfAthletes = athletesBibs.size();
        final Vibrator hapticFeedBack = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        athletesList = new Athlete[noOfAthletes];
        athleteView = new LinearLayout[noOfAthletes];
        lapButtons = new TextView[noOfAthletes];
        lapCountText = new TextView[noOfAthletes];
        deltaTimeText = new TextView[noOfAthletes];

        /*Create static arrays of views*/
        final LinearLayout[] athleteCapsuleView = new LinearLayout[noOfAthletes];
//        final TextView[] cumulativeTimeText = new TextView[noOfAthletes];

        /*LinearLayout with headers*/
        LinearLayout headersTexts = new LinearLayout(this);
        headersTexts.setOrientation(LinearLayout.HORIZONTAL);
        headersTexts.setGravity(Gravity.CENTER);
        headersTexts.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        /*Header TextViews*/
        TextView bibNoText = new TextView(this);
        bibNoText.setText(R.string.bibNoHeader);
        bibNoText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
//        bibNoText.setBackgroundColor(Color.RED);
        bibNoText.setGravity(Gravity.CENTER);
        TextView remainingLapsText = new TextView(this);
        remainingLapsText.setText(R.string.lapsToGoHeader);
        remainingLapsText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
//        remainingLapsText.setBackgroundColor(Color.GREEN);
        remainingLapsText.setGravity(Gravity.CENTER);
        TextView lapTimeText = new TextView(this);
        lapTimeText.setText(R.string.lapTimeHeader);
        lapTimeText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2));
//        lapTimeText.setBackgroundColor(Color.YELLOW);
        lapTimeText.setGravity(Gravity.CENTER);

        /*Adding headers to linearlayout*/
        headersTexts.addView(bibNoText);
        headersTexts.addView(remainingLapsText);
        headersTexts.addView(lapTimeText);

        /*Add headers to root view*/
        rootLapCounter.addView(headersTexts);

        for (int i=0;i<noOfAthletes;i++) {
            /*Creating atletes*/
            athletesList[i] = new Athlete(lapCount,athletesBibs.get(i));

            /*Creating capsules for athleteViews*/
            athleteCapsuleView[i] = new LinearLayout(this);
            athleteCapsuleView[i].setOrientation(LinearLayout.VERTICAL);
            paramsMWn.setMargins(0,0,0,8);
            paramsMWn.weight = 1;
            paramsMWn.height = 0;
            athleteCapsuleView[i].setLayoutParams(paramsMWn);
            athleteCapsuleView[i].setGravity(Gravity.CENTER);

            /*setting properties of single athlete view*/
            athleteView[i] = new LinearLayout(this);
            athleteView[i].setOrientation(LinearLayout.HORIZONTAL);
            athleteView[i].setGravity(Gravity.CENTER);
            athleteView[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            athleteView[i].setEnabled(false);
            athleteView[i].setId(R.id.athleteIDs + i);

            /*Context menu*/
            registerForContextMenu(athleteView[i]);


            /*setting properties on lap text views*/
            lapCountText[i] = new TextView(this);
            lapCountText[i].setText(String.valueOf(athletesList[i].getmLapCount()));
            lapCountText[i].setTextSize(32);
            lapCountText[i].setGravity(Gravity.CENTER);
            lapCountText[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1));
            lapCountText[i].setEnabled(false);

            /*Setting timer views*/
//            timersView[i] = new LinearLayout(this);
//            timersView[i].setOrientation(LinearLayout.VERTICAL);
//            timersView[i].setGravity(Gravity.CENTER);
//            timersView[i].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2));

            /*setting properties on timers text views*/
            final String deltaTimeString =athletesList[i].getmDeltaTime();
//            String cumulativeTimeString = getString(R.string.cumulativeTextAddition) + athletesList[i].getmCumulativeTime();
            deltaTimeText[i] = new TextView(this);
            deltaTimeText[i].setText(deltaTimeString);
            deltaTimeText[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,2));
            deltaTimeText[i].setGravity(Gravity.CENTER);
            deltaTimeText[i].setTextSize(24);
            deltaTimeText[i].setEnabled(false);
//            cumulativeTimeText[i] = new TextView(this);
//            cumulativeTimeText[i].setText(cumulativeTimeString);

            /*Adding timers to timersView*/
//            timersView[i].addView(deltaTimeText[i]);
//            timersView[i].addView(cumulativeTimeText[i]);

            /*setting properties on buttons*/
            lapButtons[i] = new TextView(this);
            lapButtons[i].setTextSize(32);
            lapButtons[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1));
            lapButtons[i].setGravity(Gravity.CENTER);
            lapButtons[i].setText(athletesList[i].getmBibNo());
            lapButtons[i].setEnabled(false);

            final int finalI = i;
            athleteView[i].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   /*Mark lap*/
                   athletesList[finalI].markLap();
                   /*Haptic feed back*/
                   hapticFeedBack.vibrate(30);
                   /*Set color for last laps*/
                   String deltaTimeStrOnClick = athletesList[finalI].getmDeltaTime();
                   int lapCountNumber = athletesList[finalI].getmLapCount();
                   if (lapCountNumber == 2) {
                       athleteView[finalI].setBackgroundColor(Color.YELLOW);
                       deltaTimeStrOnClick = athletesList[finalI].getmDeltaTime();
                   }
                   else if (lapCountNumber == 1) {
                       athleteView[finalI].setBackgroundColor(Color.RED);
                       deltaTimeStrOnClick = athletesList[finalI].getmDeltaTime();
                   }
                   else if (lapCountNumber == 0) {
                       deltaTimeStrOnClick = athletesList[finalI].getmCumulativeTime();
                       athleteView[finalI].setBackgroundColor(Color.TRANSPARENT);
                       athleteView[finalI].setEnabled(false);
                       lapButtons[finalI].setEnabled(false);
                       lapCountText[finalI].setEnabled(false);
                       deltaTimeText[finalI].setEnabled(false);
                       if (activeAthletes > 1) {
                           activeAthletes -= 1;
                       }
                       else if (activeAthletes == 1){
                           activeAthletes -= 1;
                           shareResults();
                       }

                   }
                   /*set Text of laps and time*/
                   lapCountText[finalI].setText(String.valueOf(lapCountNumber));
//                   String cumulativeTimeString = getString(R.string.cumulativeTextAddition) + athletesList[finalI].getmCumulativeTime();
                   deltaTimeText[finalI].setText(deltaTimeStrOnClick);
//				   cumulativeTimeText[finalI].setText(cumulativeTimeString);
               }
            });

            /*adding to single athlete view*/
            athleteView[i].addView(lapButtons[i]);
            athleteView[i].addView(lapCountText[i]);
            athleteView[i].addView(deltaTimeText[i]);

            /*encapsulating*/
            athleteCapsuleView[i].addView(athleteView[i]);

            /*adding to root view*/
            rootLapCounter.addView(athleteCapsuleView[i]);
        }

        final Button startButton = new Button(this);
        startButton.setText(getString(R.string.buttonStartText));
        startButton.setId(R.id.athleteIDs+noOfAthletes);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < noOfAthletes; i++) {
                    athleteView[i].setEnabled(true);
                    lapCountText[i].setEnabled(true);
                    deltaTimeText[i].setEnabled(true);
                    lapButtons[i].setEnabled(true);
                    athletesList[i].start();
                }
                hapticFeedBack.vibrate(30);
                startButton.setEnabled(false);
            }
        });
        rootLapCounter.addView(startButton);
    }
    private void shareResults() {
        Button shareButton = (Button) findViewById(R.id.athleteIDs+athletesList.length);
        shareButton.setText(R.string.shareResultsText);
        shareButton.setEnabled(true);

/*
        for (int i = 0; i < athletesList.length; i++){
            ArrayList<String>
        }
*/

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Create intent to share*/

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        int athleteID = v.getId()- R.id.athleteIDs;
        menu.add(0, athleteID+10, 0, "DNS");
        menu.add(0, athleteID+100, 0, "DNF");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Log.v("ContextMenu: ", "activeAthletes lowered");
        int athleteID = item.getItemId();
        Log.v("ContextMenu: ", "itemID = " + item.getItemId());
        if (athleteID<100){
            int athID = athleteID - 10;
            athletesList[athID].setDNSorDNF("DNS");
            lapCountText[athID].setText(String.valueOf(athletesList[athID].getmLapCount()));
            lapCountText[athID].setEnabled(false);
            deltaTimeText[athID].setText(R.string.DNS);
            deltaTimeText[athID].setEnabled(false);
            lapButtons[athID].setEnabled(false);
        }
        else if (athleteID>99){
            int athID = athleteID - 100;
            athletesList[athID].setDNSorDNF("DNF");
            lapCountText[athID].setText(String.valueOf(athletesList[athID].getmLapCount()));
            lapCountText[athID].setEnabled(false);
            deltaTimeText[athID].setText(R.string.DNF);
            deltaTimeText[athID].setEnabled(false);
            lapButtons[athID].setEnabled(false);
        }
        if (activeAthletes > 1) {
            activeAthletes -= 1;
        }
        else if (activeAthletes == 1){
            activeAthletes -=1;
            shareResults();
        }
        return super.onContextItemSelected(item);
    }
}
