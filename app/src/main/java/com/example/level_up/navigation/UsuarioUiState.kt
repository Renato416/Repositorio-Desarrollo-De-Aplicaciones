package com.example.level_up.navigation
/*UsuarioUiState.kt*/
data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val edad: String = "",
    val tieneDescuento: Boolean = false,
    val aceptaTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)

/* Crear UsuarioErrores para manejar los errores de validación. */
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null,
    val edad: String? = null
)