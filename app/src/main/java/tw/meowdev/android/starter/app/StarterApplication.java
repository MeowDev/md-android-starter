package tw.meowdev.android.starter.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class StarterApplication extends Application {
    private static final String TAG = "application";
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(activityLifeCycleCallbacks);
    }

    static class State_ {
        private Activity currentActivity;
        public Activity getCurrentActivity() {
            return currentActivity;
        }

        private boolean _isForeground = false;
        public boolean isForeground() { return _isForeground; }
    }

    private final static State_ State = new State_();

    private final static Application.ActivityLifecycleCallbacks activityLifeCycleCallbacks = new Application.ActivityLifecycleCallbacks() {

        private void logActivityEvent(Activity activity, String event) {
            Log.d(TAG, "Activity:" + activity.getLocalClassName() + " " + event);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            logActivityEvent(activity, "created");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            logActivityEvent(activity, "started");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            logActivityEvent(activity, "resumed");

            State._isForeground = true;
            State.currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            logActivityEvent(activity, "paused");

            State._isForeground = false;
            State.currentActivity = null;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            logActivityEvent(activity, "stopped");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            logActivityEvent(activity, "saveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            logActivityEvent(activity, "destroyed");
        }
    };
}
