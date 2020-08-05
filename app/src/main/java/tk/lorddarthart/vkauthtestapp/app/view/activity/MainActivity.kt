package tk.lorddarthart.vkauthtestapp.app.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vk.sdk.VKSdk
import tk.lorddarthart.vkauthtestapp.R
import tk.lorddarthart.vkauthtestapp.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.vkauthtestapp.app.view.fragment.friends_list.FriendsListFragment
import tk.lorddarthart.vkauthtestapp.util.IOnBackPressable
import tk.lorddarthart.vkauthtestapp.util.isOnBackPressable

class MainActivity : AppCompatActivity() {
    val currentBaseFragment: Fragment?
        get() {
            var fragment: Fragment? = null
            if (supportFragmentManager.findFragmentById(R.id.container_base) != null) {
                fragment = supportFragmentManager.findFragmentById(R.id.container_base)
            }
            return fragment
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onBackPressed() {
        if (currentBaseFragment?.isOnBackPressable == true) {
            (currentBaseFragment as IOnBackPressable).onBackPressed()
        } else {
            finishAffinity()
        }
    }
}