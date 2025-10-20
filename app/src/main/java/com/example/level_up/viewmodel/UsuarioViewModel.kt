package com.example.level_up.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.Usuario
import com.example.level_up.data.UsuarioDao
import com.example.level_up.navigation.UsuarioErrores
import com.example.level_up.navigation.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onEdadChange(valor: String) {
        _estado.update { it.copy(edad = valor, errores = it.errores.copy(edad = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

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
            else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion,
            errores.edad
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


            val usuario = Usuario(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo,
                clave = estadoActual.clave,
                direccion = estadoActual.direccion,
                edad = estadoActual.edad.toInt(),
                aceptaTerminos = estadoActual.aceptaTerminos,
                tieneDescuento = estadoActual.tieneDescuento
            )

            
        }
    }
    fun buscarUsuarioPorCorreo(correo: String, dao: UsuarioDao, onResult: (Usuario?) -> Unit) {
        viewModelScope.launch {
            val usuario = dao.obtenerPorCorreo(correo)
            onResult(usuario)
        }
    }

}
