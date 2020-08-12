package tk.lorddarthart.vkauthtestapp.app.view.fragment.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vk.sdk.VKSdk
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.lorddarthart.vkauthtestapp.R
import tk.lorddarthart.vkauthtestapp.app.view.base.BaseFragment
import tk.lorddarthart.vkauthtestapp.databinding.SplashFragmentBinding
import tk.lorddarthart.vkauthtestapp.util.helper.launchPostponed

class SplashFragment : BaseFragment() {
    override val fragmentViewModel: SplashViewModel by viewModel()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = SplashFragmentBinding.inflate(inflater, container, false)
    }

    override fun hangObservers() {
        // do nothing
    }

    override fun initListeners() {
        // do nothing
    }

    override fun start() {
        launchPostponed(2000) {
            if (VKSdk.isLoggedIn()) {
                navController?.navigate(R.id.action_splashFragment_to_friendsListFragment)
            } else {
                navController?.navigate(R.id.action_splashFragment_to_authFragment)
            }
        }
    }
}
