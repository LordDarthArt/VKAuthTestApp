package tk.lorddarthart.vkauthtestapp.app.model

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tk.lorddarthart.vkauthtestapp.app.view.fragment.auth.AuthViewModel
import tk.lorddarthart.vkauthtestapp.app.view.fragment.friends_list.FriendsListViewModel
import tk.lorddarthart.vkauthtestapp.app.view.fragment.splash.SplashViewModel

/** Module for injecting the [SplashViewModel]. */
val splashModule = module {
    viewModel { SplashViewModel() }
}

/** Module for injecting the [FriendsListViewModel]. */
val friendsListModule = module {
    viewModel { FriendsListViewModel() }
}

/** Module for injecting the [AuthViewModel]. */
val authModule = module {
    viewModel { AuthViewModel() }
}