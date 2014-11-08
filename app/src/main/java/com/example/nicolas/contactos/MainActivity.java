package com.example.nicolas.contactos;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import util.DatabaseHelper;
import util.TabsPagerAdapter;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    //control de fichas (tabs)
    private ViewPager viewPager;
    private TabsPagerAdapter adapter;
    private ActionBar actionBar;
    //titulos de las fichas
    private String[] titulos= {"Crear contactos", "Lista contactos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarTabs();
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void inicializarTabs() {
       viewPager= (ViewPager) findViewById(R.id.pager);
        actionBar= getActionBar();
        adapter= new TabsPagerAdapter(getFragmentManager());

        viewPager.setAdapter(adapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Agregando las fichas (tabs)
        for(String nombre: titulos){
            ActionBar.Tab tab= actionBar.newTab().setText(nombre);
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
        viewPager.setOnPageChangeListener(this);
    }


    //<editor-fold desc="METODOS TAB CHANGE LISTENER">
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
    //</editor-fold>


    //<editor-fold desc="METODOS VIEW CHANGE LISTENER">
    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    //</editor-fold>
}
