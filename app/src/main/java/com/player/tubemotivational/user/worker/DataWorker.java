package com.player.tubemotivational.user.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.player.tubemotivational.util.AppConstants.USER_NAME_KEY;
import static com.player.tubemotivational.util.AppConstants.USER_URL;


public class DataWorker extends Worker {
    private static final String TAG = "DataWorker";

    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.e(TAG, "doWork: start ");

        Data data = getInputData();
        String mUrl = data.getString(USER_URL);
        String userName = data.getString(USER_NAME_KEY);


        Log.e(TAG, "doWork: " + mUrl + "----" + userName);
        Data outputData = new Data.Builder()
                .putString("KEY_MESSAGE", "This is output message")
                .build();


        return Result.success(outputData);
    }
}
