package tk.lorddarthart.vkauthtestapp.app.view.fragment.friends_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest.VKRequestListener
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKList
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.lorddarthart.vkauthtestapp.R
import tk.lorddarthart.vkauthtestapp.app.view.base.BaseFragment
import tk.lorddarthart.vkauthtestapp.databinding.FriendsListFragmentBinding
import java.util.*

class FriendsListFragment : BaseFragment() {
    private var listView: ListView? = null
    private var arrayList: ArrayList<String>? = null

    override val fragmentViewModel: FriendsListViewModel by viewModel()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FriendsListFragmentBinding.inflate(inflater, container, false)
    }

    override fun hangObservers() {
        // do nothing
    }

    override fun initListeners() {
        // do nothing
    }

    override fun start() {
        val bundle = this.arguments
        arrayList = ArrayList()
        if (bundle != null) {
            arrayList = bundle.getStringArrayList("friendslist")
            listView = requireActivity().findViewById(R.id.friendsList)
            val friendsList = ArrayAdapter(requireActivity(), android.R.layout.simple_expandable_list_item_1, arrayList!!)
            listView?.adapter = friendsList
        } else {
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
                    listView = activity!!.findViewById(R.id.friendsList)
                    val friendsList = ArrayAdapter(activity!!, android.R.layout.simple_expandable_list_item_1, arrayList!!)
                    listView?.adapter = friendsList
                }
            })
        }
    }
}