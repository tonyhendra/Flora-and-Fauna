package ibmmobileappbuilder.analytics.bms;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.ibm.mobilefirstplatform.clientsdk.android.analytics.api.Analytics;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.logger.api.Logger;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

import ibmmobileappbuilder.analytics.AnalyticsReporter;

public class BluemixAnalyticsReporter implements AnalyticsReporter {

    private static final String PAGE_VIEW = "page_view";
    private static final String EVENT = "event";
    private static final Logger logger = Logger.getLogger("main");

    @Override
    public void init(Application app, String accessId, String region, String appName) {
        try {
            BMSClient.getInstance().initialize(app, region);
            Analytics.init(app, appName, accessId, false, Analytics.DeviceEvent.ALL);
        } catch (Exception e) {
            Log.e(appName, "Bluemix analytics initialization error:  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void sendView(String screenName) {
        Analytics.log(new JSONObject(Collections.singletonMap(PAGE_VIEW, screenName)));
        Analytics.send();
    }

    @Override
    public void sendEvent(Map<String, String> paramsMap) {
        Analytics.log(new JSONObject(paramsMap));
        Analytics.send();
    }

    @Override
    public void sendHandledException(String tag, String message, Throwable exception) {
        logger.error(message, exception);
        logger.send();
    }
}
