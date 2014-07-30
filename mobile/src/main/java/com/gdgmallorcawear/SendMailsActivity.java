package com.gdgmallorcawear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SendMailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mails);

        Intent intent = getIntent();

        Log.d("SEND_MAILS_ACTIVITY", "I'm in");

        // Si tiene mensaje de voz utilizalo sino default
        SpannableString body = (SpannableString)getMessageText(intent);
        if (body == null) body = SpannableString.valueOf(getString(R.string.notify_people));

        ((TextView)findViewById(R.id.mail_body)).setText(body);

        if (intent.hasExtra(Utils.EXTRA_EVENT_ID)) {
            Log.d("SEND_MAILS_ACTIVITY", "EXTRA_ID: " + intent.getLongExtra(Utils.EXTRA_EVENT_ID, 0));

            ArrayList<String> mails = new ArrayList<String>();
            Event event = CalendarUtils.getSingleEvent(
                    this, intent.getLongExtra(Utils.EXTRA_EVENT_ID, 0));
            ListView listitems=(ListView)findViewById(R.id.mail_list);

            for (String mail : event.attendeesMail)
                mails.add(mail);

            ArrayAdapter adapter =
                    new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, mails);
            listitems.setAdapter(adapter);

            // TODO: ¿Se deberían enviar mails de verdad? Creo que para la demo mejor una activity

        } else setContentView(R.layout.send_mails_fail);
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(Utils.EXTRA_VOICE_REPLY);
        }
        return null;
    }
}
