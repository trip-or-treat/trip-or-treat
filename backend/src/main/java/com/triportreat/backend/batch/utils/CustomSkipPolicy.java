package com.triportreat.backend.batch.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class CustomSkipPolicy implements SkipPolicy {

    private final Map<Class<? extends Throwable>, Integer> exceptionClass = new HashMap<>();
    private final Map<Class<? extends Throwable>, Integer> exceptionCount = new HashMap<>();

    public void setExceptionClassMap(Class<? extends Throwable> clazz, int limit){
        exceptionClass.put(clazz,limit);
    }

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (exceptionClass.containsKey(t.getClass())) {
            int limit = exceptionClass.get(t.getClass());
            int count = exceptionClass.getOrDefault(t.getClass(), 0);

            if (count + 1 > limit) {
                throw new SkipLimitExceededException(limit, t);
            }

            exceptionCount.put(t.getClass(), count + 1);
            return true;
        }
        return false;
    }
}
