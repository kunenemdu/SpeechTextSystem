package com.example.rember;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.rember.ui.dashboard.VoiceText;
import com.example.rember.ui.home.HomeFragment;
import com.example.rember.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NavigationActivity extends AppCompatActivity {
    final Fragment dashboard = new VoiceText();
    final Fragment home = new HomeFragment();
    final public static Fragment noti = new NotificationsFragment();
    BottomNavigationView bnav;
    public static FragmentManager fm;
    public static Fragment active;
    NavigationView nv;
    Toolbar toolbar;

    @SuppressWarnings("Deprecated")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bnav = findViewById(R.id.nav_view);

        //set launch activity
        bnav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = this.getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frag_container, home, "1").commit();
        active = home;
        fm.beginTransaction().add(R.id.frag_container, dashboard, "2").hide(dashboard).commit();
        fm.beginTransaction().add(R.id.frag_container, noti, "3").hide(noti).commit();
        fm.executePendingTransactions();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    fm.beginTransaction()
                            .hide(active)
                            .show(home)
                            .commit();
                    active = home;
                    return true;

                case R.id.nav_extra:
                    fm.beginTransaction()
                            .hide(active)
                            .show(dashboard)
                            .commit();
                    active = dashboard;
                    return true;

                case R.id.nav_notifications:
                    fm.beginTransaction()
                            .hide(active)
                            .show(noti)
                            .commit();
                    active = noti;
                    return true;
            }
            return false;
        }
    };
}