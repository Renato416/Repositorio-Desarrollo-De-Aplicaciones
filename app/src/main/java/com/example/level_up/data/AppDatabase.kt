package com.example.level_up.data.local
/*AppDatabase*/
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.level_up.data.local.Usuario // Asegúrate que Usuario tmb esté en data.local

@Database(
    entities = [Usuario::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "level_up_database" // Nombre del archivo de la BD
                )
                    // --- CORRECCIÓN AÑADIDA AQUÍ ---
                    // Esto soluciona el crash "IllegalStateException"
                    // porque tu LoginScreen llama a la BD desde el hilo principal.
                    .allowMainThreadQueries()
                    // --- FIN DE LA CORRECCIÓN ---
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}