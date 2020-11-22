package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.AppDatabase
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.MyApi
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.NetworkInterceptorOkHttp
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.preferences.PreferenceProvider
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.QuotesRepository
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.UserRepository
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.auth.AuthViewModelFactory
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.profile.ProfileViewModelFactory
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

// To use the dependency injection we need to bind all the required classes here.
class MVVMApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule((this@MVVMApplication)))

        // Bind all the objects which are required.
        bind() from singleton { NetworkInterceptorOkHttp(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        // If we don't want single instance user provider instead of singleton
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        AndroidThreeTen.init(this)
    }
}