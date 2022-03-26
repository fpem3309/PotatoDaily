package com.redoc.potatodaily.db

import android.content.Context
import androidx.room.*

@Database(
    entities = [BoardEntity::class],
    version = 11,
)
@TypeConverters(Converters::class)
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