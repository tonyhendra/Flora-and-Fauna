package ibmmobileappbuilder.analytics.injector;

import android.content.Context;
import android.util.Log;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;

import java.net.MalformedURLException;

import ibmmobileappbuilder.analytics.bms.BluemixAnalyticsReporter;
import ibmmobileappbuilder.analytics.AnalyticsReporter;

public class AnalyticsReporterInjector {

    private static AnalyticsReporter instance;

    public static AnalyticsReporter analyticsReporter(Context context) {
        if (instance != null) {
            return instance;
        }
        instance = new BluemixAnalyticsReporter();
        return instance;
    }

    public static AnalyticsReporter analyticsReporter() {
        if (instance == null) {
            throw new IllegalStateException("You must call analyticsReporter(Context context) first");
        }
        return instance;
    }
}
