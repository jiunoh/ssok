package com.example.jiun.sookpam

import io.realm.DynamicRealm
import io.realm.RealmMigration
import io.realm.RealmSchema

class ContactMigration : RealmMigration {
    var oldVersion : Long =0

    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        var schema: RealmSchema = realm!!.getSchema()

        if (oldVersion.toInt() == 0) {
            schema.create("ContactVO")
                    .addField("class1", String::class.java)
                    .addField("class2", String::class.java)
                    .addField("phone", String::class.java)
            this.oldVersion++
        }
    }
}