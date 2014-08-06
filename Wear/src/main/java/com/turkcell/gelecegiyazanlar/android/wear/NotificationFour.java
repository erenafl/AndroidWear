package com.turkcell.gelecegiyazanlar.android.wear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.webkit.WebView;

/**
 * Created by Turkcell on 24.07.2014.
 */
public class NotificationFour extends Activity  {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_four);
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        if(getMessageText(getIntent()).toString().equals("Google Maps"))
            webview.loadUrl("https://maps.google.com");

        else if(getMessageText(getIntent()).toString().equals("Google"))
            webview.loadUrl("https://google.com.tr");

        else
            webview.loadUrl("https://www.youtube.com");

    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(MainActivity.EXTRA_VOICE_REPLY);
        }

        return null;
    }

}
