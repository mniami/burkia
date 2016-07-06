package pl.guideme.burkia.compat;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class NotificationManagerCompatProvider {

    private NotificationManagerCompat notificationManagerCompat;

    public NotificationManagerCompatProvider(Context context){
        notificationManagerCompat = NotificationManagerCompat.from(context);
    }
    public NotificationManagerCompat getNotificationManagerCompat(){
        return notificationManagerCompat;
    }
}
