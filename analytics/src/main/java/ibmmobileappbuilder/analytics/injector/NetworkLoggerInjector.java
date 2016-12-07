package ibmmobileappbuilder.analytics.injector;

import ibmmobileappbuilder.analytics.bms.BluemixNetworkLogger;
import ibmmobileappbuilder.analytics.network.NetworkLogger;

public class NetworkLoggerInjector {

    private static NetworkLogger instance;

    public static NetworkLogger networkLogger() {
        if (instance != null) {
            return instance;
        }
        instance = new BluemixNetworkLogger();
        return instance;
    }
}
