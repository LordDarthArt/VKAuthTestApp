package tk.lorddarthart.vkauthtestapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vk.sdk.VKSdk;

public class MainActivity extends AppCompatActivity {
    Fragment fr1;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VKSdk.initialize(getApplicationContext());
        if (true) {
            fr1 = new FragmentFriendslist();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFragment, fr1);
            transaction.commit();
        } else {
            fr1 = new FragmentAuth();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFragment, fr1);
            transaction.commit();
        }
    }
}
