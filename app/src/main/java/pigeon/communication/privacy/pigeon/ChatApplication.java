package pigeon.communication.privacy.pigeon;

import android.app.Application;




public class ChatApplication extends Application {
    private static ChatApplication instance;

    public static ChatApplication getInstance()
    {
        return instance;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

}
