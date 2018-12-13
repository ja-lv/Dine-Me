package com.me.dine.dineme.ViewModel.Reminder.Notif;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.me.dine.dineme.ViewModel.DineMeRepository;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class DineMeJob extends JobService {
    static AsyncTask mBackgroundTask;

    private DineMeRepository mRepository;

    @Override
    public boolean onStartJob(final JobParameters job) {
        NotificationsUtils.remindUsers(DineMeJob.this);

        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(DineMeJob.this, "DineMe refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                mRepository = new DineMeRepository(DineMeJob.this.getApplication());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                super.onPostExecute(o);

            }
        };


        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        if (mBackgroundTask != null) mBackgroundTask.cancel(false);

        return true;
    }
}
