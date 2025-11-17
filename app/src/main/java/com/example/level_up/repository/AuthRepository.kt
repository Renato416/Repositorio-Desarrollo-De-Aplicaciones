package com.example.level_up.repository

import com.example.level_up.data.local.Usuario
import com.example.level_up.data.local.UsuarioDao

/**
 * Esta clase es el "puente" entre el ViewModel y el DAO (Base de Datos).
 * El ViewModel le pide datos al Repositorio, no directamente al DAO.
 */
class AuthRepository(private val usuarioDao: UsuarioDao) {

    /**
     * El ViewModel llamará a esta función para buscar al usuario
     */
    suspend fun buscarUsuarioPorCorreo(correo: String): Usuario? {
        return usuarioDao.obtenerPorCorreo(correo)
    }

    // (Aquí podríamos agregar más funciones, como registrar, pero
    // por ahora solo necesitamos la de login)
}