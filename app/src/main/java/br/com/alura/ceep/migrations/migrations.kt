package br.com.alura.ceep.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

val MIGRATION_1_2 = object : Migration(1, 2){

    override fun migrate(database: SupportSQLiteDatabase) {
        val newTable = "Note_new"
        val currentTable = "Note"

        //criar tabela nova com todos os campos esperados

        database.execSQL(
            """CREATE TABLE IF NOT EXISTS $newTable (
        `id` TEXT PRIMARY KEY NOT NULL, 
        `title` TEXT NOT NULL, 
        `description` TEXT NOT NULL, 
        `image` TEXT)"""
        )

        //copiar dados da tabela atual para a tabela nova

        database.execSQL(
            """INSERT INTO $newTable (id, title, description, image) 
        SELECT id, title, description, image FROM $currentTable
    """
        )

        //gerar ids diferentes e novos

        val cursor = database.query("SELECT * FROM $newTable")
        while (cursor.moveToNext()) {
            val id = cursor.getString(
                cursor.getColumnIndex("id")
            )
            database.execSQL(
                """
        UPDATE $newTable 
            SET id = '${UUID.randomUUID()}' 
            WHERE id = $id"""
            )
        }

        //apagar tabela atual
        database.execSQL("DROP TABLE $currentTable")

        //renomear tabela nova com o nome da tabela atual
        database.execSQL("ALTER TABLE $newTable RENAME TO $currentTable")
    }

}

val MIGRATION_2_3 = object  : Migration(2,3){
    override fun migrate( database: SupportSQLiteDatabase){
        database.execSQL("ALTER TABLE Note ADD synchronize INTEGER  NOT NULL DEFAULT 0")
    }
}

val MIGRATION_3_4 = object  : Migration(3,4){
    override fun migrate( database: SupportSQLiteDatabase){
        database.execSQL("ALTER TABLE Note ADD disabled INTEGER  NOT NULL DEFAULT 0")
    }
}