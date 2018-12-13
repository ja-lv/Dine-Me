package com.me.dine.dineme.ViewModel.Reminder.Notif;

import android.app.IntentService;
import android.content.Intent;

public class DineMeReminderIntentService extends IntentService {

    public DineMeReminderIntentService() {
        super("NewsReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}
