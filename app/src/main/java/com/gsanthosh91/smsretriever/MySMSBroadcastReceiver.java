package com.gsanthosh91.smsretriever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySMSBroadcastReceiver extends BroadcastReceiver {

    public static String INTENT_OTP = "INTENT_OTP";
    private String TAG = "MySMS";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    Log.d(TAG, message);
                    Pattern pattern = Pattern.compile("(\\d{4})");
                    Matcher matcher = pattern.matcher(message);
                    if (matcher.find()) {
                        String val = matcher.group(0);  // 4 digit number
                        Intent myIntent = new Intent(INTENT_OTP);
                        myIntent.putExtra("otp", val);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    break;
            }
        }
    }

}
