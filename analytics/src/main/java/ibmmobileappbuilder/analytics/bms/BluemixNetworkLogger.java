package ibmmobileappbuilder.analytics.bms;

import com.ibm.mobilefirstplatform.clientsdk.android.analytics.api.Analytics;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ibmmobileappbuilder.analytics.NetworkResponse;
import ibmmobileappbuilder.analytics.network.NetworkLogger;

public class BluemixNetworkLogger implements NetworkLogger {

    private static final String NETWORK_RESPONSE = "network_response";
    private static final String NETWORK_REQUEST = "network_request";

    @Override
    public void logRequest(String url, String httpMethod) {
        Map<String, String> paramsMap = new HashMap<>(2);
        paramsMap.put(NETWORK_REQUEST, url);
        paramsMap.put("http_method", httpMethod);
        Analytics.log(new JSONObject(paramsMap));
    }

    @Override
    public void logResponse(NetworkResponse networkResponse) {
        Map<String, String> paramsMap = new HashMap<>(3);
        paramsMap.put(NETWORK_RESPONSE, networkResponse.getUrl());
        paramsMap.put("response_code", networkResponse.getStatusCode());
        paramsMap.put("response_body", networkResponse.getBody());
        Analytics.log(new JSONObject(paramsMap));
    }
}
