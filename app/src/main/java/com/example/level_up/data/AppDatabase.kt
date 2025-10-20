package com.example.level_up.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Usuario::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Método abstracto que devuelve el DAO
    abstract fun usuarioDao(): UsuarioDao
}