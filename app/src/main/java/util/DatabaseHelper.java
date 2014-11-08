package util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nicolas.contactos.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Nicolas on 01/11/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "agenda.db";
    private static final int DATABASE_VERSION = 1;

    //Objeto DAO que se utilizara para acceder a la tabla contacto
    private Dao<Contacto, Integer> contactoDAO = null;
    private RuntimeExceptionDao<Contacto, Integer> contactoRuntimeDAO = null;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }
    /**
     *
     * @param db
     * @param source
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource source) {

        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onCreate");
            TableUtils.createTable(source, Contacto.class);
        } catch (SQLException ex) {
            Log.e(DatabaseHelper.class.getSimpleName(), "imposible crear la base de datos", ex);
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onUpgrade()");
            TableUtils.dropTable(source, Contacto.class, true);
            // Despues de eliminar las tablas anteriores, creamos nuevamente la base de datos
            onCreate(db, source);
        }catch (SQLException e){
            Log.e(DatabaseHelper.class.getSimpleName(), "Imposible eliminar la base de datos", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Contacto, Integer> getContactoDAO() throws SQLException {
        if(contactoDAO==null) contactoDAO= getDao(Contacto.class);
        return contactoDAO;
    }

    public RuntimeExceptionDao<Contacto, Integer> getContactoRuntimeDAO() {
        if(contactoRuntimeDAO == null) contactoRuntimeDAO= getRuntimeExceptionDao(Contacto.class);
        return contactoRuntimeDAO;
    }


    /**
     * Cierra las conexiones a la base de datos
     */
    @Override
    public void close() {
        super.close();
        contactoDAO= null;
        contactoRuntimeDAO = null;
    }
}
