package cn.newbeedaly.springnative.config.lock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author newbeedaly
 * @date 2022-04-26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    //锁key
    String lockKey() default "";

    //等待时间
    int waitTime() default 3;

    //释放时间
    int releaseTime() default 3;

    //时间单位
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
