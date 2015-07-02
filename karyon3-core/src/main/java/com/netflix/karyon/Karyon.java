package com.netflix.karyon;

import java.util.Collection;

import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;
import com.netflix.governator.Governator;
import com.netflix.governator.LifecycleInjector;
import com.netflix.governator.ModuleListProvider;
import com.netflix.governator.auto.AutoModuleBuilder;
import com.netflix.governator.auto.annotations.ConditionalOnProfile;

public class Karyon {
    private final Stage stage = Stage.DEVELOPMENT;
    private final AutoModuleBuilder builder;

    private Karyon(Module module) {
        builder = new AutoModuleBuilder(module);
    }
    
    public static Karyon forModule(Module module) {
        return new Karyon(module);
    }
    
    public static Karyon forModules(Module... modules) {
        return new Karyon(Modules.combine(modules));
    }
    
    /**
     * Add a module finder such as a ServiceLoaderModuleFinder or ClassPathScannerModuleFinder
     * @param finder
     * @return
     */
    public Karyon withModuleFinder(ModuleListProvider finder) {
        builder.withModuleFinder(finder);
        return this;
    }
    
    public Karyon withBootstrap(Module bootstrapModule) {
        builder.withBootstrap(bootstrapModule);
        return this;
    }
    
    public Karyon withBootstrap(Module ... bootstrapModule) {
        builder.withBootstrap(Modules.combine(bootstrapModule));
        return this;
    }

    /**
     * Add a runtime profile.  @see {@link ConditionalOnProfile}
     * 
     * @param profile
     */
    public Karyon withProfile(String profile) {
        builder.withProfile(profile);
        return this;
    }

    /**
     * Add a runtime profiles.  @see {@link ConditionalOnProfile}
     * 
     * @param profile
     */
    public Karyon withProfiles(String... profiles) {
        builder.withProfiles(profiles);
        return this;
    }

    /**
     * Add a runtime profiles.  @see {@link ConditionalOnProfile}
     * 
     * @param profile
     */
    public Karyon withProfiles(Collection<String> profiles) {
        builder.withProfiles(profiles);
        return this;
    }
    
    public LifecycleInjector create() {
        return Governator.createInjector(stage, builder.build());
    }
}
