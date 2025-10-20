package com.example.level_up.data
/*AppDatabase*/
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Usuario::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun usuarioDao(): UsuarioDao
}