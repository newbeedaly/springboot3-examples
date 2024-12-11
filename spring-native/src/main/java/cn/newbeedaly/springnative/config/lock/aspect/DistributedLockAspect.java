package cn.newbeedaly.springnative.config.lock.aspect;

import cn.newbeedaly.springnative.config.lock.DistributedLocker;
import cn.newbeedaly.springnative.config.lock.annotation.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author newbeedaly
 * @date 2022-04-26
 */
@Slf4j
@Component
@Aspect
public class DistributedLockAspect {

    @Autowired
    private DistributedLocker distributedLocker;

    @Pointcut("@annotation(cn.newbeedaly.springnative.config.lock.annotation.DistributedLock)")
    public void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        String uuid = UUID.randomUUID().toString();
        log.info("distributed lock aspect exec start：{}", uuid);
        Object object = null;
        // 获取锁的注解
        DistributedLock distributedLock = getDistributedLock(proceedingJoinPoint);
        String lockKey = getLocalKey(proceedingJoinPoint, distributedLock);
        try {
            boolean acquire = distributedLocker.tryLock(lockKey, distributedLock.timeUnit()
                    , distributedLock.waitTime(), distributedLock.releaseTime());
            if (acquire) {
                try {
                    object = proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else {
                log.error("cannot get distributed lock：{}", lockKey);
                throw new RuntimeException("业务繁忙");
            }
        } finally {
            distributedLocker.unlock(lockKey);
        }
        log.info("distributed lock aspect complete：{}", uuid);
        return object;
    }

    /**
     * 获取分布式锁的注解
     */
    public DistributedLock getDistributedLock(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(DistributedLock.class);
    }

    /**
     * 获取redis的lockKey
     */
    public String getLocalKey(ProceedingJoinPoint proceedingJoinPoint, DistributedLock distributedLock) {
        StringBuilder lockKey;
        if (StringUtils.isNotBlank(distributedLock.lockKey())) {
            lockKey = new StringBuilder(distributedLock.lockKey());
        } else {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Class<?>[] parameters = methodSignature.getParameterTypes();
            lockKey = new StringBuilder(methodSignature.getMethod().getName());
            if (parameters != null) {
                for (Class<?> parameter : parameters) {
                    lockKey.append(parameter.getSimpleName());
                }
            }
        }
        return lockKey.toString();
    }

}
