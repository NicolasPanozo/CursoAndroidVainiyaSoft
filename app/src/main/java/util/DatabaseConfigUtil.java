package util;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Nicolas on 01/11/2014.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[]args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt");
    }
}
