package com.example.level_up.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    // Insertar un nuevo usuario
    @Insert
    suspend fun insertar(usuario: Usuario)

    // Buscar un usuario por correo
    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun obtenerPorCorreo(correo: String): Usuario?

    // Obtener todos los usuarios (como Flow para observar cambios)
    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): Flow<List<Usuario>>
}