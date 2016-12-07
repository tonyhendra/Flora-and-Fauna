

package com.tonyhendra.florafauna.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tonyhendra.florafauna.R;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.*;
import com.ibm.mobilefirstplatform.clientsdk.android.analytics.api.*;
import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * FloraandFaunaActivity list activity
 */
public class FloraandFaunaActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_US_SOUTH); // You can change the region

        Analytics.init(getApplication(), "Flora and Fauna", "34cb3ca6-50cb-4b2e-8144-53d161004f13", false, Analytics.DeviceEvent.ALL);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.floraandFaunaActivity));
        Analytics.send();
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return FloraandFaunaFragment.class;
    }

}
