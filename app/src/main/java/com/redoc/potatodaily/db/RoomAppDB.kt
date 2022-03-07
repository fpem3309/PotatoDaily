package com.redoc.potatodaily.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BoardEntity::class],
    version = 3,
)
abstract class RoomAppDB : RoomDatabase() {

    abstract fun BoardDao(): BoardDao?

    companion object {
        private var INSTANCE: RoomAppDB? = null

        fun getAppDatabase(context: Context): RoomAppDB? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<RoomAppDB>(
                    context.applicationContext,
                    RoomAppDB::class.java,
                    "AppDB"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }
    }
}