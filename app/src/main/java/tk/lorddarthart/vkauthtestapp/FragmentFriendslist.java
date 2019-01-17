package tk.lorddarthart.vkauthtestapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentFriendslist extends Fragment {
    private View view;
    private ListView listView;
    private ArrayList<String> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friendslist, container, false);
        container.removeAllViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = this.getArguments();
        arrayList = new ArrayList<>();
        if (bundle!=null) {
            arrayList = bundle.getStringArrayList("friendslist");
            listView = getActivity().findViewById(R.id.friendsList);
            ArrayAdapter<String> friendsList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
            listView.setAdapter(friendsList);
        } else {
            VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    int deleted = 0;

                    VKList list = (VKList) response.parsedModel;
                    for (int i = 0; i<list.size(); i++) {
                        arrayList.add(String.valueOf(list.get(i)));
                    }
                    Collections.sort(arrayList);
                    for (int i = 0; i<list.size(); i++) {
                        if (i>=5) {
                            arrayList.remove(i-deleted);
                            deleted++;
                        }
                    }
                    listView = getActivity().findViewById(R.id.friendsList);
                    ArrayAdapter<String> friendsList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
                    listView.setAdapter(friendsList);
                }
            });
        }
    }


}
