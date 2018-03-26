package com.okayama.shop.di.component;

import com.okayama.shop.di.module.DaggerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DaggerModule.class)
public interface DaggerComponent {
}
