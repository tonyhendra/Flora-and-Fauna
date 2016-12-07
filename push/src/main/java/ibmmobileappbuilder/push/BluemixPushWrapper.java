package ibmmobileappbuilder.push;

import android.content.Context;
import android.util.Log;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;

import java.net.MalformedURLException;

public class BluemixPushWrapper implements MFPPushResponseListener<String> {

    private static final String TAG = BluemixPushWrapper.class.getSimpleName();

    public void register(Context appContext, String bluemixRegion, String bluemixAppGuid, String clientSecret) {
        try {
            BMSClient.getInstance().initialize(appContext, bluemixRegion);
            // Initialize Push client
            MFPPush.getInstance().initialize(appContext, bluemixAppGuid, clientSecret);
            MFPPush.getInstance().registerDevice(this);
        } catch (Exception e) {
            Log.e(TAG, "Please, check push configuration and try again", e);
        }
    }

    @Override
    public void onSuccess(String response) {
        Log.d(TAG, String.format("Registered: %s", response));
    }

    @Override
    public void onFailure(MFPPushException exception) {
        Log.e(TAG, "Failure", exception);
    }
}
