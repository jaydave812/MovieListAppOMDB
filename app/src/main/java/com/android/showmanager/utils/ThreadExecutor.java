package com.android.showmanager.utils;

import java.util.concurrent.Executor;

import android.os.Handler;
import android.os.Looper;

/**
 * Executor class to pass to pagedlist
 */
public class ThreadExecutor implements Executor
{
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(Runnable command)
    {
        mHandler.post(command);
    }
}
