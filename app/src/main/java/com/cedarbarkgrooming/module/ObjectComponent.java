package com.cedarbarkgrooming.module;

import com.cedarbarkgrooming.module.modules.ApplicationModule;
import com.cedarbarkgrooming.module.modules.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nora on 5/7/2016.
 */

@Component(
        modules = {
                ApplicationModule.class,
                PresenterModule.class
        }
)

@Singleton
public interface ObjectComponent extends Injector {
}
