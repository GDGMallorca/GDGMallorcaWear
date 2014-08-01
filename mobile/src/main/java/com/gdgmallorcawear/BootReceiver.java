package com.gdgmallorcawear;

/**
 * Created by jmliras on 13/07/14.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gdgmallorcawear.utils.Utils;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Utils.startService(context);
    }
}

