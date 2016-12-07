

package com.tonyhendra.florafauna;

import android.app.Application;
import ibmmobileappbuilder.injectors.ApplicationInjector;
import android.support.multidex.MultiDexApplication;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;
import ibmmobileappbuilder.push.BluemixPushWrapper;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import ibmmobileappbuilder.cloudant.factory.CloudantDatabaseSyncerFactory;
import java.net.URI;


/**
 * You can use this as a global place to keep application-level resources
 * such as singletons, services, etc.
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationInjector.setApplicationContext(this);
        AnalyticsReporterInjector.analyticsReporter(this).init(this,
                getString(R.string.analyticsApiKey),
                getString(R.string.bmdBluemixRegion),
                getString(R.string.app_name));
        BluemixPushWrapper bluemixPushWrapper = new BluemixPushWrapper();
        bluemixPushWrapper.register(this,
            getString(R.string.bmdBluemixRegion),
            getString(R.string.pushAppGuid),
            getString(R.string.pushClientSecret)
        );
        //Syncing cloudant ds
        CloudantDatabaseSyncerFactory.instanceFor(
            "data_flora_fauna",
            URI.create("https://1ae7e3be-a478-4387-afcc-434e53aeb810-bluemix:716244766aba7e12cd8302b3263bf4fa939ec97f235c5de06dc0eeff3b2abcac@1ae7e3be-a478-4387-afcc-434e53aeb810-bluemix.cloudant.com/data_flora_fauna")
        ).sync(null);
      }
}
