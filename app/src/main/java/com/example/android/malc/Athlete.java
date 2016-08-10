package com.example.android.malc;

import android.content.Context;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by islavchev on 22.07.16.
 */
public class Athlete {
    private int mLapCount = 0;
    private String mBibNo;
    private long mStartTime = 0;
    long mDeltaTime = 0;
    long mCumulativeTime = 0;
    private ArrayList<String> mLapTimes = new ArrayList<String>();
    private ArrayList<String> mCumulativeTimes = new ArrayList<String>();

    public Athlete (int LapCount, String BibNo) {
        mLapCount = LapCount;
        mBibNo = BibNo;
    }

    public void markLap(){
        if (mLapCount > 0) {
            mLapCount = mLapCount - 1;
            mDeltaTime = System.currentTimeMillis() - (mCumulativeTime+mStartTime);
            mCumulativeTime = System.currentTimeMillis() - mStartTime;
            mCumulativeTimes.add(millisecondsToHMSM(mCumulativeTime));
            mLapTimes.add(millisecondsToHMSM(mDeltaTime));
        }
    }

    public void start(){
        mStartTime = System.currentTimeMillis();
    }
    public void setDNSorDNF (String mDNSorDNF){
        mLapCount = 0;
        mCumulativeTimes.add(mDNSorDNF);
    }

    public ArrayList<String> getmLapTimes(){
        return mLapTimes;
    }

    public ArrayList<String> getmCumulativeTimes(){
        return mCumulativeTimes;
    }

    public int getmLapCount(){
        return mLapCount;
    }

    public String getmBibNo(){
        return mBibNo;
    }

    public String getmDeltaTime(){
        return millisecondsToHMSM(mDeltaTime);
    }

    public String getmCumulativeTime(){
        return millisecondsToHMSM(mCumulativeTime);
    }

    private static String millisecondsToHMSM(long timeInMillis){
        final long hr = TimeUnit.MILLISECONDS.toHours(timeInMillis);
        final long min = TimeUnit.MILLISECONDS.toMinutes(timeInMillis - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(timeInMillis - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(timeInMillis - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        String timeInHMSM;
        if (hr==0){
            if (min==0) {
                if (sec==0){
                    if (ms==0){
                        timeInHMSM = "00.00";
                    }
                    else timeInHMSM = String.format("%02d.%02d", sec, ms/10);
                }
				else timeInHMSM = String.format("%02d.%02d", sec, ms/10);
            }
			else timeInHMSM = String.format("%02d:%02d.%02d", min, sec, ms/10);
        }
		else timeInHMSM = String.format("%02d:%02d:%02d.%02d", hr, min, sec, ms/10);

        return timeInHMSM;
    }

}
