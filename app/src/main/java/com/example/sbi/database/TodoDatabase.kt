package com.example.sbi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DeviceItem::class], version = 1, exportSchema = false)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDatabaseDao

    companion object {

        private var INSTANCE: DeviceDatabase? = null

        fun getInstance(context: Context): DeviceDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DeviceDatabase::class.java,
                        "device_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
