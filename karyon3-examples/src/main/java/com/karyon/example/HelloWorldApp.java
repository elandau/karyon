package com.karyon.example;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Provides;
import com.google.inject.util.Modules;
import com.netflix.archaius.ConfigProxyFactory;
import com.netflix.archaius.inject.ApplicationLayer;
import com.netflix.governator.DefaultLifecycleListener;
import com.netflix.governator.DefaultModule;
import com.netflix.governator.guice.jetty.JettyConfig;
import com.netflix.governator.guice.jetty.JettyModule;
import com.netflix.karyon.Karyon;
import com.netflix.karyon.archaius.ArchaiusBootstrapModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

@Path("/")
public class HelloWorldApp extends DefaultLifecycleListener {
    public static void main(String[] args) throws InterruptedException {
        Karyon
            .forModules(
                Modules
                    .override(new JettyModule())
                    .with(new DefaultModule() {
                        @Provides
                        @Singleton
                        private JettyConfig getDefaultConfig(ConfigProxyFactory factory) {
                            return factory.newProxy(AnnotatedJettyConfig.class);
                        }
                    }),
                 new JerseyServletModule() {
                    @Override
                    protected void configureServlets() {
                        serve("/*").with(GuiceContainer.class);
                        bind(GuiceContainer.class).asEagerSingleton();
                        bind(HelloWorldApp.class).asEagerSingleton();
                    }  
                }
            )
            .withBootstrap(
                Modules
                    .override(new ArchaiusBootstrapModule())
                    .with(new DefaultModule() {
                        @Provides
                        @Singleton
                        @ApplicationLayer
                        String getConfigName() {
                            return "helloworld";
                        }
                    })
            )
            .create()
            .awaitTermination();
    }
    
    @GET
    public String sayHello() {
        return "hello world";
    }

    @Override
    public void onStarted() {
        System.out.println("Started ***** ");
    }
}
