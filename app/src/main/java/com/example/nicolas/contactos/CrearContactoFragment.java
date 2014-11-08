package com.example.nicolas.contactos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import util.ContactReceiver;
import util.Contacto;
import util.TextChangedListener;

/**
 * Created by Nicolas on 16/10/2014.
 */
public class CrearContactoFragment extends Fragment implements View.OnClickListener {

    private ImageView imgViewContacto;
    private EditText txt_nombre;
    private EditText txt_telefono;
    private EditText txt_email;
    private EditText txt_direccion;
    private Button btn_agregar;

    private int request_code=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crear_contacto, container, false);
        inicializarComponentes(rootView);
        return rootView;

    }

    private void inicializarComponentes(final View view) {
        txt_nombre= (EditText) view.findViewById(R.id.txt_nombre);
        txt_telefono= (EditText) view.findViewById(R.id.txt_telefono);
        txt_email= (EditText) view.findViewById(R.id.txt_email);
        txt_direccion= (EditText) view.findViewById(R.id.txt_direccion);
        imgViewContacto= (ImageView) view.findViewById(R.id.imgViewContacto);
        imgViewContacto.setOnClickListener(this);
        txt_nombre.addTextChangedListener(new TextChangedListener(){
            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                btn_agregar.setEnabled(!seq.toString().trim().isEmpty());
            }
        });
        btn_agregar= (Button) view.findViewById(R.id.btn_agregar);
        btn_agregar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view == btn_agregar){
            agregarNuevoContacto(
                    txt_nombre.getText().toString(),
                    txt_telefono.getText().toString(),
                    txt_email.getText().toString(),
                    txt_direccion.getText().toString(),
                    String.valueOf(imgViewContacto.getTag()));
            String text= "A sido agregado el contacto "+ txt_nombre.getText().toString();
            Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
            limpiarCampos();
            btn_agregar.setEnabled(false);
        }else{
            if(view == imgViewContacto){
                Intent intent=null;
                if(Build.VERSION.SDK_INT <19){
                    // Android JellyBean 4.3 y anteriores
                    intent= new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                }else{
                    // Android KitKat 4.4 o superior
                    intent= new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }

                intent.setType("image/*");
                startActivityForResult(intent, request_code);
            }
        }

    }

    private void agregarNuevoContacto(String nombre, String telefono, String email, String direccion, String imageUri) {
        Contacto nuevo= new Contacto(nombre, telefono, email, direccion, imageUri);
        Intent intent= new Intent("listacontactos");
        intent.putExtra("operacion", ContactReceiver.CONTACTO_AGREGADO);
        intent.putExtra("datos", nuevo);
        getActivity().sendBroadcast(intent);
    }

    private void limpiarCampos() {
        txt_nombre.getText().clear();
        txt_telefono.getText().clear();
        txt_email.getText().clear();
        txt_direccion.getText().clear();
        imgViewContacto.setImageResource(R.drawable.person_icon);
        txt_nombre.requestFocus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == request_code){
            imgViewContacto.setImageURI(data.getData());
            imgViewContacto.setTag(data.getData());
        }
    }
}
