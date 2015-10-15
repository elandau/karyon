package com.netflix.karyon;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import com.netflix.governator.AbstractLifecycleFeature;
import com.netflix.governator.LifecycleAction;

/**
 * Special AbstractLifecycleFeature to support @PostConstruct annotation processing.
 * Note that this feature is implicit in LifecycleModule and therefore does not need 
 * to be added using the LifecycleFeature multibinding.
 * 
 * @author elandau
 */
final class PostConstructLifecycleActions extends AbstractLifecycleFeature {

    static PostConstructLifecycleActions INSTANCE = new PostConstructLifecycleActions();
    
    @Override
    protected LifecycleAction getMethodAction(final Class<?> type, final Method method) {
        if (null != method.getAnnotation(PostConstruct.class)) {
            method.setAccessible(true);
            return new LifecycleAction() {
                @Override
                public void call(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    method.invoke(obj);
                }
                
                @Override
                public String toString() {
                    return new StringBuilder()
                        .append("PostConstruct[")
                        .append(type.getName())
                        .append("#")
                        .append(method.getName())
                        .append("]")
                        .toString();
                }
            };
        }
        return NONE;
    }
    
    @Override
    public String toString() {
        return "PostConstruct";
    }

}
