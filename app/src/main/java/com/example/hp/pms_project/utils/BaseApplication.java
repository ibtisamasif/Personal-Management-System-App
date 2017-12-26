package com.example.hp.pms_project.utils;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by HP on 12/22/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
