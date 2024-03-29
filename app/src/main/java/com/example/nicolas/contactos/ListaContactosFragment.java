package com.example.nicolas.contactos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;

import util.ContactListAdapter;
import util.ContactReceiver;
import util.Contacto;
import util.DatabaseHelper;

/**
 * Created by Nicolas on 16/10/2014.
 */
public class ListaContactosFragment extends Fragment {

    private ArrayAdapter<Contacto> adapter;
    private ListView contactsListView;
    private ContactReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_contactos, container, false);
        inicializarComponentes(rootView);
        setHasOptionsMenu(true);//habilita el action bar de este fragment para tener botones
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new ContactReceiver(adapter, getOrmLiteBaseActivity());
        getActivity().registerReceiver(receiver, new IntentFilter("listacontactos"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private void inicializarComponentes(View view){
        contactsListView = (ListView) view.findViewById(R.id.listView);
        adapter = new ContactListAdapter(getActivity(), new ArrayList<Contacto>());
       OrmLiteBaseActivity<DatabaseHelper> activity = getOrmLiteBaseActivity();
        if(activity!=null){
            DatabaseHelper helper = activity.getHelper();
            RuntimeExceptionDao<Contacto, Integer> dao = helper.getContactoRuntimeDAO();
            adapter.addAll(dao.queryForAll());
        }
        //se configura para que el adapter notifique cambios en el dataset automaticamente
        adapter.setNotifyOnChange(true);
        contactsListView.setAdapter(adapter);
    }

    private OrmLiteBaseActivity<DatabaseHelper> getOrmLiteBaseActivity(){
        Activity activity = getActivity();
        if(activity instanceof OrmLiteBaseActivity)
            return (OrmLiteBaseActivity<DatabaseHelper>) activity;
        return null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_eliminar_contacto: eliminarContacto(item); return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void eliminarContacto(MenuItem item) {
        SparseBooleanArray array = contactsListView.getCheckedItemPositions();
        ArrayList<Contacto> seleccion= new ArrayList<Contacto>();
        for(int i=0; i < array.size(); i++){
            //posicion del contacto en el adapter
            int posicion = array.keyAt(i);
            if(array.valueAt(i)) seleccion.add(adapter.getItem(posicion));
            Intent intent = new Intent("listacontactos");
            intent.putExtra("operacion", ContactReceiver.CONTACTO_ELIMINADO);
            intent.putExtra("datos", seleccion);
            getActivity().sendBroadcast(intent);
            contactsListView.clearChoices();
        }
    }
}
