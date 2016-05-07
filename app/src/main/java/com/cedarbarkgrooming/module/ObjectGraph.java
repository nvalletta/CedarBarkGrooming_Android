package com.cedarbarkgrooming.module;


import android.support.annotation.NonNull;

/**
 * Created by Nora on 5/7/2016.
 */
public class ObjectGraph {

    private static ObjectComponent sObjectComponent;

    public static Injector getInjector() {
        return sObjectComponent;
    }

    public static void setObjectComponent(@NonNull ObjectComponent component) {
        sObjectComponent = component;
    }

}
