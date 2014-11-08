package util;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Nicolas on 13/10/2014.
 */

@DatabaseTable(tableName = "contacto")
public class Contacto implements Serializable{

    @DatabaseField(generatedId = true)
    private int id; //Primary key

    @DatabaseField(index = true, canBeNull = false)
    private String nombre;

    @DatabaseField
    private String telefono;

    @DatabaseField
    private String email;

    @DatabaseField
    private String direccion;

    @DatabaseField
    private String imageUri; //no es posible serializar objetos Uri

    // lo exige ORMLITE este contructor predeterminado
    public Contacto(){

    }
    //***************

    public Contacto(String nombre, String telefono, String email, String direccion, String imageUri){
        this.nombre=nombre;
        this.telefono=telefono;
        this.email=email;
        this.direccion=direccion;
        this.imageUri= imageUri;
    }

    public int getId() {
        return id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacto)) return false;

        Contacto contacto = (Contacto) o;

        if (!direccion.equals(contacto.direccion)) return false;
        if (!email.equals(contacto.email)) return false;
        if (!imageUri.equals(contacto.imageUri)) return false;
        if (!nombre.equals(contacto.nombre)) return false;
        if (!telefono.equals(contacto.telefono)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result = 31 * result + telefono.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + direccion.hashCode();
        result = 31 * result + imageUri.hashCode();
        return result;
    }
}
