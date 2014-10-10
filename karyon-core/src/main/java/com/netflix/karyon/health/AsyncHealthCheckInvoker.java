package com.netflix.karyon.health;

import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import com.netflix.karyon.health.HealthCheck.Status;

/**
 * Invocation strategy that processes the health check in a separate thread 
 * so that the call to invoke does not block or can timeout.
 * @author elandau
 *
 */
@Singleton
public class AsyncHealthCheckInvoker implements HealthCheckInvoker {

    @Override
    public List<Status> invoke(Collection<HealthCheck> healthChecks) {
        // TODO Auto-generated method stub
        return null;
    }

}
