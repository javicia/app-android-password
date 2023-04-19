package com.javier.passlive.Encryption

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import net.sqlcipher.database.SupportFactory
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class Controller (val service: ControllerApp) {
    data class ControllerApp (val nameController: String)

    class SessionApp (val sessionName: String, var numUser: Int)


    val moduleApp = module {
        single { Controller(get()) }

        single {
            Room.databaseBuilder(
                androidContext(),
                Database::class.java,
                BuildConfig.DB_NAME
            )
                .openHelperFactory(get<SupportFactory>())
                .build()
        }

        single {
            // importado desde net.sqlcipher.database.SupportFactory
            SupportFactory(get<Passphrase>().getPassphrase())
        }

        single {
            Passphrase(
                androidContext()
                    .getSharedPreferences(
                        "sharedPreferencesRoomSqlCypher",
                        Context.MODE_PRIVATE
                    )
            )
        }
    }
}