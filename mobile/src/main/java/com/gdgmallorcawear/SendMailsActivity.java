package com.gdgmallorcawear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SendMailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        Log.d("SEND_MAILS_ACTIVITY", "I'm in");

        // Si tiene mensaje de voz utilizalo sino default
        String body = (String)getMessageText(intent);
        if (body == null) body = getString(R.string.notify_people);

        if (intent.hasExtra(Utils.EXTRA_EVENT_ID)) {
            setContentView(R.layout.send_mails);

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
