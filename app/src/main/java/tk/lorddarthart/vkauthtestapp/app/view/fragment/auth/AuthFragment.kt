package tk.lorddarthart.vkauthtestapp.app.view.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.VKRequest.VKRequestListener
import com.vk.sdk.api.model.VKList
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.lorddarthart.vkauthtestapp.BR
import tk.lorddarthart.vkauthtestapp.R
import tk.lorddarthart.vkauthtestapp.app.view.activity.MainActivity
import tk.lorddarthart.vkauthtestapp.app.view.base.BaseFragment
import tk.lorddarthart.vkauthtestapp.app.view.fragment.friends_list.FriendsListFragment
import tk.lorddarthart.vkauthtestapp.databinding.AuthFragmentBinding
import java.util.*

class AuthFragment : BaseFragment() {
    private val strings = arrayOf(VKScope.FRIENDS)
    private var fr1: Fragment? = null
    private var transaction: FragmentTransaction? = null
    private val list: VKList<*>? = null
    private var arrayList: ArrayList<String>? = null

    override val fragmentViewModel: AuthViewModel by viewModel()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = AuthFragmentBinding.inflate(inflater, container, false)
        fragmentBinding.setVariable(BR.auth_fragment, this)
    }

    override fun hangObservers() {
        // do something
    }

    override fun initListeners() {
        // do nothing
    }

    fun onAuthClick() {
        VKSdk.login(this@AuthFragment, *strings)
    }

    override fun start() {
        with (fragmentBinding as AuthFragmentBinding) {
            (activity as MainActivity?)?.setSupportActionBar(authToolbar)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(
                        requestCode,
                        resultCode,
                        data,
                        object : VKCallback<VKAccessToken?> {
                            override fun onResult(res: VKAccessToken?) {
                                arrayList = ArrayList()
                                val request = VKApi.friends()[VKParameters.from(VKApiConst.FIELDS, "first_name, last_name")]
                                request.executeWithListener(object : VKRequestListener() {
                                    override fun onComplete(response: VKResponse) {
                                        super.onComplete(response)
                                        var deleted = 0
                                        val list = response.parsedModel as VKList<*>
                                        for (i in list.indices) {
                                            arrayList!!.add(list[i].toString())
                                        }
                                        arrayList?.sort()
                                        for (i in arrayList!!.indices) {
                                            if (i >= 5) {
                                                arrayList!!.removeAt(i - deleted)
                                                deleted++
                                            }
                                        }
                                        fr1 = FriendsListFragment()
                                        val bundle = Bundle()
                                        bundle.putStringArrayList("friendslist", arrayList)
                                        fr1!!.arguments = bundle
                                        transaction = activity!!.supportFragmentManager.beginTransaction()
                                        transaction!!.replace(R.id.container_base, fr1!!).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
                                        transaction!!.commit()
                                    }
                                })
                            }

                            override fun onError(error: VKError) {
                                Toast.makeText(activity, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                            }
                        }
                )
        )
            super.onActivityResult(requestCode, resultCode, data)
    }
}