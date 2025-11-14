package com.example.level_up.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.level_up.data.local.Usuario // <-- Ruta actualizada
import com.example.level_up.data.local.UsuarioDao // <-- Ruta actualizada
import com.example.level_up.navigation.UsuarioErrores
import com.example.level_up.navigation.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow() // (Mejor usar asStateFlow() para exponerlo)

    // --- NUEVO ESTADO PARA ERROR DE LOGIN ---
    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()
    // --- FIN NUEVO ESTADO ---

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
        _loginError.value = null // Limpia el error de login al escribir
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
        _loginError.value = null // Limpia el error de login al escribir
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onEdadChange(valor: String) {
        _estado.update { it.copy(edad = valor, errores = it.errores.copy(edad = null)) }
    }

    fun onTelefonoChange(valor: String) {
        _estado.update { it.copy(telefono = valor, errores = it.errores.copy(telefono = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    // --- NUEVA FUNCIÓN PARA LIMPIAR CAMPOS ---
    /**
     * Limpia el estado de la UI, útil antes de navegar a Login o Registro
     */
    fun limpiarEstado() {
        _estado.value = UsuarioUiState()
        _loginError.value = null
    }
    // --- FIN NUEVA FUNCIÓN ---

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))) {
                "Correo inválido"
            } else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null,
            edad = if (estadoActual.edad.toIntOrNull() == null || estadoActual.edad.toInt() < 18)
                "Debe ser mayor de 18 años"
            else null,
            telefono = if (estadoActual.telefono.toLongOrNull() == null || estadoActual.telefono.length != 9)
                "Debe ser un número de 9 dígitos"
            else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion,
            errores.edad,
            errores.telefono
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        if (errores.correo == null) {
            val esDuoc = estadoActual.correo.endsWith("@duoc.cl")
            _estado.update { it.copy(tieneDescuento = esDuoc) }
        }

        return !hayErrores
    }

    // guardar el usuario en la base de datos
    fun registrarUsuario(context: Context) {
        val estadoActual = _estado.value

        if (!validarFormulario()) return

        viewModelScope.launch {
            // (Aquí iría tu lógica de inserción en la BD, que parece estar
            // manejada fuera del ViewModel o inyectada)

            val usuario = Usuario(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo,
                clave = estadoActual.clave,
                direccion = estadoActual.direccion,
                edad = estadoActual.edad.toInt(),
                telefono = estadoActual.telefono.toLong(),
                aceptaTerminos = estadoActual.aceptaTerminos,
                tieneDescuento = estadoActual.tieneDescuento
            )

            // Ejemplo: dao.insertar(usuario)
        }
    }

    // (Tu función original de búsqueda)
    fun buscarUsuarioPorCorreo(correo: String, dao: UsuarioDao, onResult: (Usuario?) -> Unit) {
        viewModelScope.launch {
            val usuario = dao.obtenerPorCorreo(correo)
            onResult(usuario)
        }
    }

    // --- NUEVA FUNCIÓN DE INICIO DE SESIÓN ---
    /**
     * Intenta iniciar sesión verificando correo y clave.
     * @param dao El objeto de acceso a datos para buscar al usuario.
     * @param onLoginSuccess Callback que se ejecuta si el inicio de sesión es exitoso.
     */
    fun iniciarSesion(dao: UsuarioDao, onLoginSuccess: () -> Unit) {
        val estadoActual = _estado.value
        _loginError.value = null // Limpia errores anteriores

        // Validación simple de campos vacíos
        if (estadoActual.correo.isBlank() || estadoActual.clave.isBlank()) {
            _loginError.value = "Correo y contraseña no pueden estar vacíos"
            return
        }

        viewModelScope.launch {
            // Buscamos al usuario por el correo ingresado
            val usuario = dao.obtenerPorCorreo(estadoActual.correo)

            if (usuario == null) {
                // Error: Usuario no existe
                _loginError.value = "Usuario no encontrado"
            } else if (usuario.clave != estadoActual.clave) {
                // Error: La contraseña no coincide
                _loginError.value = "Contraseña incorrecta"
            } else {
                // ¡Éxito!
                // Limpiamos el estado y ejecutamos el callback
                limpiarEstado()
                onLoginSuccess()
            }
        }
    }
    // --- FIN NUEVA FUNCIÓN ---
}