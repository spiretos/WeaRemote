package communication;

import android.content.Intent;
import android.os.Vibrator;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.spiretos.spaceremote.SpaceRemoteActivity;

/**
 * Created by spiretos on 26/3/2016.
 */
public class AppListenerService123 extends WearableListenerService
{

    public static final String START_ACTIVITY_PATH = "/start/MainActivity";


    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        super.onMessageReceived(messageEvent);

        if (messageEvent.getPath().equals(START_ACTIVITY_PATH))
        {
            Intent intent = new Intent(this, SpaceRemoteActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            long[] vibrationPattern = {0, 75, 50, 75};
            vibrator.vibrate(vibrationPattern, -1);
        }
    }

}
