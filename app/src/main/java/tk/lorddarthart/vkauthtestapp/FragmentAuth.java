package tk.lorddarthart.vkauthtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentAuth extends Fragment {
    private View view;
    private String[] strings = new String[]{VKScope.FRIENDS};
    private Fragment fr1;
    private FragmentTransaction transaction;
    private VKList list;
    private ArrayList<String> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auth, container, false);
        Button button = view.findViewById(R.id.btnAuth);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 VKSdk.login(FragmentAuth.this, strings);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                arrayList = new ArrayList<>();
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
                        for (int i = 0; i<arrayList.size(); i++) {
                            if (i>=5) {
                                arrayList.remove(i-deleted);
                                deleted++;
                            }
                        }
                        fr1 = new FragmentFriendslist();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("friendslist", arrayList);
                        fr1.setArguments(bundle);
                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainFragment, fr1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null);
                        transaction.commit();
                    }
                });
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getActivity(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
            }
        }))
        super.onActivityResult(requestCode, resultCode, data);
    }
}
