package com.example.android.malc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button endConfigButton = (Button) findViewById(R.id.buttonEndConfig);
        final EditText lapCountEdit = (EditText) findViewById(R.id.editLapCount);

        final ArrayList<String> athletesBibs = new ArrayList<String>();

        final ArrayList<EditText> athleteEditTexts = new ArrayList<EditText>();
        athleteEditTexts.add((EditText) findViewById(R.id.editAthlete01));
        athleteEditTexts.add((EditText) findViewById(R.id.editAthlete02));
        athleteEditTexts.add((EditText) findViewById(R.id.editAthlete03));
        athleteEditTexts.add((EditText) findViewById(R.id.editAthlete04));
//        athleteEditTexts.add((EditText) findViewById(R.id.editAthlete05));
//        athleteEditTexts.add((EditText) findViewById(R.id.editAthlete06));
        
        endConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noLaps = true;
                boolean noAthletes = true;
                boolean duplicateAthletes = false;
                final Set<String> checkDuplicate = new HashSet<String>();
                if (lapCountEdit.getText().toString().trim().length() > 0) noLaps = false;
                for (EditText athleteEdit : athleteEditTexts) {
                    if (athleteEdit.getText().toString().trim().length() > 0) {
                        noAthletes = false;
                        if (!checkDuplicate.add(athleteEdit.getText().toString())) {
                            duplicateAthletes = true;
                            checkDuplicate.clear();
                        }
                    }
                }
                if (noLaps) Toast.makeText(getApplicationContext(), getString(R.string.noLapsToast), Toast.LENGTH_SHORT).show();
                else if (noAthletes) Toast.makeText(getApplicationContext(), R.string.noAthletesToast, Toast.LENGTH_SHORT).show();
                else if (duplicateAthletes) {
                    Toast.makeText(getApplicationContext(), R.string.duplicateAthletesToast, Toast.LENGTH_SHORT).show();
                }
                else {
                    for (EditText athleteEdit : athleteEditTexts) {
                        if (athleteEdit.getText().toString().trim().length() > 0) athletesBibs.add(athleteEdit.getText().toString());
                    }
                    String lapCount = lapCountEdit.getText().toString();
                    Intent lapCountIntent = new Intent(MainActivity.this, LapCounter.class);
                    lapCountIntent.putExtra("lapCount", lapCount);
                    lapCountIntent.putExtra("athletesBibs", athletesBibs);
                    startActivity(lapCountIntent);
                    athletesBibs.clear();
                }
            }
        });
    }
}
