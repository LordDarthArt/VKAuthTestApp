package tk.lorddarthart.vkauthtestapp.app.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.core.KoinComponent
import tk.lorddarthart.vkauthtestapp.R
import tk.lorddarthart.vkauthtestapp.databinding.SplashFragmentBinding
import javax.xml.namespace.NamespaceContext

abstract class BaseFragment: Fragment(), KoinComponent {
    protected lateinit var fragmentBinding: ViewDataBinding
    protected val navController: NavController?
        get() { return requireActivity().findNavController(R.id.container_base) }

    abstract val fragmentViewModel: ViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater, container)
        initialization()

        return fragmentBinding.root
    }

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?)

    fun initialization() {
        hangObservers()
        initListeners()
        start()
    }

    abstract fun hangObservers()
    abstract fun initListeners()
    abstract fun start()
}