package server.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConfiguration {
    private static DBConfiguration instance;

    public static DBConfiguration getInstance() {
        if(instance == null){
            instance = new DBConfiguration();
        }
        return instance;
    }

    private static SessionFactory factory;

    public DBConfiguration(){setup();}

    private void setup(){
        Configuration configuration = new Configuration();
        configuration.configure();

        factory = configuration.buildSessionFactory();
    }

    public Session getSession(){return factory.openSession();}
}
