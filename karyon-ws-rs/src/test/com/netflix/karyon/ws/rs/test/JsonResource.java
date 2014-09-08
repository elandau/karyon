package com.netflix.karyon.ws.rs.test;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import rx.Observable;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

@Path("/json")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class JsonResource {
    @GET
    @Path("/observable/user") 
    public Observable<UserName> getUserObservable(
            @QueryParam("first") @DefaultValue("defaultFirst") String first,
            @QueryParam("last") @DefaultValue("defaultLast") String last) {
        return Observable.just(new UserName(first, last));
    }
    
    @GET
    @Path("/user") 
    public UserName getUser(
            @QueryParam("first") @DefaultValue("defaultFirst") String first,
            @QueryParam("last") @DefaultValue("defaultLast") String last) {
        return new UserName(first, last);
    }
    
    @GET
    @Path("/observable/users") 
    public Observable<List<UserName>> getUsersObservables() {
        return Observable.just((List<UserName>)Lists.newArrayList(
                new UserName("John", "Doe"),
                new UserName("Jane", "Doe")
                ));
    }
    
    @GET
    @Path("/users") 
    public List<UserName> getUsers() {
        return (List<UserName>)Lists.newArrayList(
                new UserName("John", "Doe"),
                new UserName("Jane", "Doe")
                );
    }
    
    @POST
    @Path("/users")
    public String addUser(UserName user) {
        
        return Joiner.on(":").join(user.getFirst(), user.getLast());
    }
}