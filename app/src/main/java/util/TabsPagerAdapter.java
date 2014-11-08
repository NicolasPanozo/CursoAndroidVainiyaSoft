package util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.nicolas.contactos.CrearContactoFragment;
import com.example.nicolas.contactos.ListaContactosFragment;

/**
 * Created by Nicolas on 18/10/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int posicion) {
        switch (posicion){
            case 0: return new CrearContactoFragment();
            case 1: return new ListaContactosFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
