package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.repository.AuthRepository
import com.example.level_up.data.util.Result
import com.example.level_up.data.local.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Este es el "cerebro" que usa LoginScreen.kt.
 * Obtiene la lógica del AuthRepository.
 */
class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    // Estado para el email (privado)
    private val _email = MutableStateFlow("")

    // Estado para la contraseña (privado)
    private val _password = MutableStateFlow("")

    // Estado para el resultado del login (público, para que la UI lo observe)
    private val _loginState = MutableStateFlow<Result<Usuario>?>(null)
    val loginState: StateFlow<Result<Usuario>?> = _loginState.asStateFlow()

    /**
     * Lo llama el OutlinedTextField del email
     */
    fun onEmailChange(email: String) {
        _email.update { email }
    }

    /**
     * Lo llama el OutlinedTextField de la contraseña
     */
    fun onPasswordChange(password: String) {
        _password.update { password }
    }

    /**
     * Lo llama el botón "Iniciar Sesión"
     */
    fun login() {
        viewModelScope.launch {
            // 1. Poner estado en "Loading"
            _loginState.value = Result.Loading

            val email = _email.value
            val password = _password.value

            // Validación simple
            if (email.isBlank() || password.isBlank()) {
                _loginState.value = Result.Error("Correo y contraseña no pueden estar vacíos")
                return@launch
            }

            try {
                // 2. Buscar en el repositorio
                val user = repository.buscarUsuarioPorCorreo(email)

                // 3. Validar
                if (user == null) {
                    _loginState.value = Result.Error("Usuario no encontrado")
                } else if (user.clave != password) {
                    _loginState.value = Result.Error("Contraseña incorrecta")
                } else {
                    // ¡Éxito!
                    _loginState.value = Result.Success(user)
                }
            } catch (e: Exception) {
                // Manejar cualquier error de la base de datos
                _loginState.value = Result.Error("Error: ${e.message}")
            }
        }
    }

    /**
     * Lo llama la UI cuando el login es exitoso, para limpiar el estado
     */
    fun resetState() {
        _loginState.value = null
    }
}