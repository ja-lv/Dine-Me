package com.me.dine.dineme.ViewModel;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

import com.me.dine.dineme.R;

public class NotificationsUtils {
    private static final String News_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    public static void clearAllNotifications (Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUsers(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    News_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,News_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.dineme_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.news_reminder_notification_title)) // new user join
                .setContentText(context.getString(R.string.news_reminder_notification_body)) // someone joined
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.news_reminder_notification_body))) //someone joined
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

/*

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,News_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.dineme_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.news_reminder_notification_title)) // new event
                .setContentText(context.getString(R.string.news_reminder_notification_body)) //  new event
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.news_reminder_notification_body))) //new event
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);



 */


    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.dineme_notification);
        return largeIcon;
    }
}
