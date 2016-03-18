package test.owen.com.myapplication.assist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import test.owen.com.myapplication.LogUtils;

/**
 * Created by owen on 2016/1/19.
 */
public class AssistService extends Service {

    public static final String ASSIST_FROM_PACKAGE = "AssistFromAPPPackage";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFromAssist(intent)) {
            String from = intent.getStringExtra(ASSIST_FROM_PACKAGE);
            LogUtils.log("owen", "AssistService->onStartCommand from " + from);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private boolean isFromAssist(Intent intent) {
        if (intent == null) {
            return false;
        }
        return !TextUtils.isEmpty(intent.getStringExtra(ASSIST_FROM_PACKAGE));
    }
}
