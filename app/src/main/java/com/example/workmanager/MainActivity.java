package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * 제약사항을 추가해 제약 사항에 따라 WorkManager를 작동시킬 수 있음
         * 자세한 제약 종류 조건은 공식 문서 확인
         * https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work?hl=ko#work-constraints
         * */
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // 한번만 실행할 때
//        WorkRequest sendWorkRequest = new OneTimeWorkRequest.Builder(SendWorker.class)
//                .setConstraints(constraints)
//                .build();


        // 주기적으로 실행할 때
        WorkRequest sendWorkRequest = new PeriodicWorkRequest.Builder(SendWorker.class, 5, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this)
                .enqueue(sendWorkRequest);
    }
}