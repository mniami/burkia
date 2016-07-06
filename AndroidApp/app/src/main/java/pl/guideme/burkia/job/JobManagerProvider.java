package pl.guideme.burkia.job;

import android.content.Context;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class JobManagerProvider {
    private JobManager jobManager;

    public JobManagerProvider(Context context){
        jobManager = new JobManager(context);
    }
    public JobManager get(){
        return jobManager;
    }
}
