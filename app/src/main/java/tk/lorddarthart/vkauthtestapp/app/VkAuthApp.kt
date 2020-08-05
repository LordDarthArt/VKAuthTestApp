package tk.lorddarthart.vkauthtestapp.app

import android.app.Application
import android.content.Intent
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tk.lorddarthart.vkauthtestapp.app.model.authModule
import tk.lorddarthart.vkauthtestapp.app.model.friendsListModule
import tk.lorddarthart.vkauthtestapp.app.model.splashModule
import tk.lorddarthart.vkauthtestapp.app.view.activity.MainActivity

class VkAuthApp : Application() {
    private var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                val intent = Intent(this@VkAuthApp, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@VkAuthApp)
            // modules
            modules(
                    splashModule,
                    authModule,
                    friendsListModule
            )
        }

        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
    }
}