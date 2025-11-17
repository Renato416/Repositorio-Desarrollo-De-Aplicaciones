package com.example.level_up.data.local
/*Usuario*/
import androidx.room.Entity
import androidx.room.PrimaryKey

// Movido a com.example.level_up.data.local
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val correo: String,
    val clave: String,
    val direccion: String,
    val edad: Int,
    val telefono: Long,
    val aceptaTerminos: Boolean,
    val tieneDescuento: Boolean
)