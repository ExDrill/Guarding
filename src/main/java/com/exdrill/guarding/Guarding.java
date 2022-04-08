package com.exdrill.guarding;

import net.fabricmc.api.ModInitializer;

public class Guarding implements ModInitializer {
    public static String NAMESPACE = "guarding";


    @Override
    public void onInitialize() {
        System.out.println("Guarding is loaded");
    }
}
