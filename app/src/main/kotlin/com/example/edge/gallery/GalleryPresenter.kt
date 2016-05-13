package com.example.edge.gallery

import android.util.Log
import android.view.View
import com.example.edge.common.Presenter
import com.example.edge.common.ScopeSingleton
import io.realm.Realm
import javax.inject.Inject
import kotlin.properties.Delegates

@ScopeSingleton(GalleryComponent::class)
class GalleryPresenter @Inject constructor() : Presenter<View> {
    private var realm: Realm by Delegates.notNull()

    override fun onEnter() {
        Log.e("EDGE", "GalleryPresenter.onEnter")

        checkRealmCrud();
    }

    private fun checkRealmCrud() {
        realm = Realm.getDefaultInstance();

        realm.executeTransaction {
            realm.allObjects(Person::class.java).deleteAllFromRealm()
        }

        realm.executeTransaction {
            // Add a person
            var person = realm.createObject(Person::class.java)
            person.id = 1
            person.name = "Young Person"
            person.age = 14
        }

        // Find the first person (no query conditions) and read a field
        var person = realm.where(Person::class.java).findFirst()
        Log.e("REALM", person.name + ": " + person.age)

        // Update person in a transaction
        realm.executeTransaction {
            person.name = "Senior Person"
            person.age = 99
            Log.e("REALM", person.name + " got older: " + person.age)
        }

        realm?.close()
    }

    private var view: View? = null

    override fun attach(view: View) {
        this.view = view
    }

    override fun detach(view: View) {
        this.view = null
    }

    override fun onExit() {
        Log.e("EDGE", "GalleryPresenter.onExit")
    }
}
