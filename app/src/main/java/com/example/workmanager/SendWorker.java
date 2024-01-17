package com.example.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SendWorker extends Worker {

    private int i = 0;
    public SendWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * OneTimeWorkRequest 로 한번만 실행할 때
     * 반복적인 작업을 하려면 Result.retry()로 다시 실행할 수 있음
     * */
//    @Override
//    public Result doWork() {
//        try {
//            Log.d("Worker", "I'm hard worker, but i will sleep");
//            Thread.sleep(10000);
//            Log.d("Worker", "Who woke up. I'm tired.");
//            if(i == 0) {
//                return Result.retry();
//            }
//            return Result.success();
//        }
//        catch (Exception e) {
//            return Result.failure();
//        }
//
//    }

    /**
     * PeriodicWorkRequest로 주기적으로 실행할 때
     * 반복 주기는 최소 15분 이상이어야함
     * */
    @Override
    public Result doWork() {
        try {
            return Result.success();
        }
        catch (Exception e) {
            return Result.failure();
        }

    }


}
