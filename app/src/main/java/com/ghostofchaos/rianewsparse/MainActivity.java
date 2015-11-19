package com.ghostofchaos.rianewsparse;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.ghostofchaos.rianewsparse.Fragments.Fragment1;
import com.ghostofchaos.rianewsparse.Fragments.Fragment10;
import com.ghostofchaos.rianewsparse.Fragments.Fragment11;
import com.ghostofchaos.rianewsparse.Fragments.Fragment2;
import com.ghostofchaos.rianewsparse.Fragments.Fragment3;
import com.ghostofchaos.rianewsparse.Fragments.Fragment4;
import com.ghostofchaos.rianewsparse.Fragments.Fragment5;
import com.ghostofchaos.rianewsparse.Fragments.Fragment6;
import com.ghostofchaos.rianewsparse.Fragments.Fragment7;
import com.ghostofchaos.rianewsparse.Fragments.Fragment8;
import com.ghostofchaos.rianewsparse.Fragments.Fragment9;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 1:
                fragment = new Fragment2();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 2:
                fragment = new Fragment3();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 3:
                fragment = new Fragment4();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 4:
                fragment = new Fragment5();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 5:
                fragment = new Fragment6();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 6:
                fragment = new Fragment7();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 7:
                fragment = new Fragment8();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 8:
                fragment = new Fragment9();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 9:
                fragment = new Fragment10();
                ft.replace(R.id.container, fragment).commit();
                break;
            case 10:
                fragment = new Fragment11();
                ft.replace(R.id.container, fragment).commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_section9);
                break;
            case 10:
                mTitle = getString(R.string.title_section10);
                break;
            case 11:
                mTitle = getString(R.string.title_section11);
                break;
        }
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
