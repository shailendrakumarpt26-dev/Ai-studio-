package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserTeamEntity::class,
        JoinedContestEntity::class,
        WalletEntity::class,
        WalletTransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Fan11Database : RoomDatabase() {
    abstract fun fan11Dao(): Fan11Dao

    companion object {
        @Volatile
        private var INSTANCE: Fan11Database? = null

        fun getDatabase(context: Context): Fan11Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Fan11Database::class.java,
                    "fan11_fantasy.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
