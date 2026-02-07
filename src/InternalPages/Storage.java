package InternalPages;

import java.io.File;

public class Storage {
    public static File getUsersFile(){
        return new File("users.txt");
    }

    public static File getDbDir(){
        File d = new File("db");
        if(!d.exists()) d.mkdirs();
        return d;
    }

    public static File getItemsFile(){
        return new File(getDbDir(), "items.csv");
    }

    public static File getReportsDir(){
        File r = new File("reports");
        if(!r.exists()) r.mkdirs();
        return r;
    }
}
