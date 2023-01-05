package br.com.alura.ceep.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.alura.ceep.database.dao.NoteDao
import br.com.alura.ceep.migrations.MIGRATION_1_2
import br.com.alura.ceep.migrations.MIGRATION_2_3
import br.com.alura.ceep.migrations.MIGRATION_3_4
import br.com.alura.ceep.model.Note

@Database(
    version = 4,
    entities = [Note::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ceep.db"
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4
            )
                .build()
        }
    }

}