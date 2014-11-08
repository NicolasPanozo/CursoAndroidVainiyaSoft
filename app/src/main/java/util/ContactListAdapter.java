package util;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicolas.contactos.R;

import java.util.List;

/**
 * Created by Nicolas on 13/10/2014.
 */
public class ContactListAdapter extends ArrayAdapter<Contacto> {

    private Activity ctx;

    public ContactListAdapter(Activity context, List<Contacto> contactos){
        super(context, R.layout.listview_item, contactos);
        this.ctx=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null) {
            view = ctx.getLayoutInflater().inflate(R.layout.listview_item, parent, false);
        }
            Contacto actual= this.getItem(position);
            inicializarCamposDeTexto(view, actual);
            return view;
    }

    private void inicializarCamposDeTexto(View view, Contacto actual) {
        TextView textView= (TextView) view.findViewById(R.id.view_nombre);
        textView.setText(actual.getNombre());

        textView= (TextView) view.findViewById(R.id.view_telefono);
        textView.setText(actual.getTelefono());

        textView= (TextView) view.findViewById(R.id.view_email);
        textView.setText(actual.getEmail());

        textView= (TextView) view.findViewById(R.id.view_direccion);
        textView.setText(actual.getDireccion());

        ImageView imageViewContacto = (ImageView) view.findViewById(R.id.imageViewContacto);
        imageViewContacto.setImageURI(Uri.parse(actual.getImageUri()));
    }
}
