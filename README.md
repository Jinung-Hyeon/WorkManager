WorkManager 간단한 예제

### dependencies 추가(Build.gradle)
```
dependencies {
    def work_version = "2.8.0"

    // (Java only)
    implementation "androidx.work:work-runtime:$work_version"
}
```

### Worker
Worker를 상속받은 클래스를 생성하고 Context와 WorkerParameters를 생성자에 넘긴다.

doWork() 메소드를 오버라이드하고, 하고자하는 작업 코드를 작성한다.

작업을 완료하고 결과에 따라 Worker클래스 내에 정의된 enum인 Result의 값중 하나를 리턴해야 한다
SUCCESS, FAILURE, RETRY의 3개 값이 있으며 리턴되는 이 값에 따라 WorkManager는 해당 작업을 마무리 할것인지, 재시도 할것인지, 실패로 정의하고 중단할 것인지를 결정하게 된다

```
public class SendWorker extends Worker {

    public SendWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        try {
            Log.d("Worker", "작업중입니다.");
            Thread.sleep(10000);
            Log.d("Worker", "작업이끝났습니다.");
    
            return Result.success();
        }
        catch (Exception e) {
            return Result.failure();
        }
    
    }
}
```

### WorkRequest
- WorkManager를 통해 실제 요청하게 될 개별 작업이다
- 처리해야 할 작업인 Work와 작업 반복 여부 및 작업 실행 조건, 제약 사항등 이 작업을 어떻게 처리할 것인지에 대한 정보가 담겨있다
- 반복여부에 따라 onTimeWorkRequest, PeriodicWorkRequest로 나뉜다
- onTimeWorkRequest
  - 반복하지 않을 작업, 즉 한번만 실행할 작업의 요청을 나타내는 클래스
- PeriodicWorkRequest
  - 여러번 실행할 작업의 요청을 나타내는 클래스
 

```
// 한번만 실행할 때
WorkRequest sendWorkRequest = new OneTimeWorkRequest.Builder(SendWorker.class)
        .build();


// 주기적으로 실행할 때
WorkRequest sendWorkRequest = new PeriodicWorkRequest.Builder(SendWorker.class, 15, TimeUnit.MINUTES)
        .build();

WorkManager.getInstance(this)
        .enqueue(sendWorkRequest);
```

### 제약사항
- 제약사항을 추가해 제약 사항에 따라 WorkManager를 작동시킬 수 있음
- 자세한 제약 사항 종류는 공식 문서 확인
- https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work?hl=ko#work-constraints
```
Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

WorkRequest sendWorkRequest = new OneTimeWorkRequest.Builder(SendWorker.class)
        .setConstraints(constraints)
        .build();
```
