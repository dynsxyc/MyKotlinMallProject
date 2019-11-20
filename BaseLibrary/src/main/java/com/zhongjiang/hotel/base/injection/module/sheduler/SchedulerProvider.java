package com.zhongjiang.hotel.base.injection.module.sheduler;

import io.reactivex.Scheduler;

/**
 * Created by QingMei on 2017/11/13.
 * desc:
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();

}
