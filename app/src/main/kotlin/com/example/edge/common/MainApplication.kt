package com.example.edge.common

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MainApplication : Application() {
    val services: MutableMap<String, Any> = mutableMapOf()

    override fun onCreate() {
        super.onCreate()

        val realmConfig = RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfig)
        val realm = Realm.getInstance(realmConfig)
        realm.close()
    }

    override fun getSystemService(name: String?): Any? {
        if (DaggerService.SERVICE_NAME.equals(name)) {
            return services;
        }

        return super.getSystemService(name)
    }
}
