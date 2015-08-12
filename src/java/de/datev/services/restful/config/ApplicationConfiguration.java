package de.datev.services.restful.config;
import de.datev.services.database.MySql;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author TG00030
 */
@ApplicationPath("/api/")
public class ApplicationConfiguration extends Application{
    
    public static MySql Sql = new MySql("192.168.1.69:3306", "root", "root", "shoppinglist");
    
}
