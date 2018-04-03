package com.mindinventory.PieChart;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mind on 11/9/17.
 */

public class AppClass extends Application {
    public static String REALM_DATABASE_NAME;
    public static String DBFLOW_DATABASE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();

        REALM_DATABASE_NAME = AppClass.class.getCanonicalName();
        DBFLOW_DATABASE_NAME = AppClass.class.getCanonicalName();

        /*calligraphy initialization*/
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());


        Realm.init(this);

        // The RealmConfiguration is created using the builder pattern.
        // The Realm file will be located in Context.getFilesDir() with name "Mospur.realm"
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_DATABASE_NAME)
                .schemaVersion(0)
                .build();

        // Use the config
        Realm.setDefaultConfiguration(config);
    }
}
