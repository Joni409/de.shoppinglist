package de.datev.services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySql ist verantwortlich für alle Zugriffe in eine MySQL Datenbank.
 *
 * @author Rogalski, Lars
 * @version 1.0
 */
public class MySql {

    /**
     * Datenbank NAME mit der eine Verbindung aufgebaut werden soll.
     */
    private final String db_db;
    private final String db_userid;
    private final String db_password;
    
    /**
     *  Name des Servers mit eingerichteter MSSQLDB.
     */
    private final String db_connect_string;
    
    /**
     * Verbindung zur Datenbank
     */
    private Connection conn = null;

    /**
     * Speichert informationen zum Aufbau einer DB Verbindung in MSSQL - Klasse.
     *
     * @version 1.0
     * @param db_ip_server_name Name des Servers mit eingerichteter MSSQLDB.
     * @param db_userid Benutzername für die MYSQL DB.
     * @param db_password Passwort für Benutzername.
     * @param db_db_name Name der Datenbank.
     */
    public MySql(String db_ip_server_name, String db_userid, String db_password, String db_db_name) {
        this.db_connect_string = db_ip_server_name;
        this.db_userid = db_userid;
        this.db_password = db_password;
        this.db_db = db_db_name;

    }
   
    //--------------------------------------[ VERBINDUNG ]--------------------------------------\\
    /**
     * Baut eine Verbindung zu einem MySql Server auf.
     *
     * @return den Status der aktuell aufgebauten Verbindung.
     */
    public boolean Connect() {
        try {

            Class.forName("com.mysql.jdbc.Driver"); // Lade JDBC Treiber
            conn =DriverManager.getConnection("jdbc:mysql://" + db_connect_string + "/" + db_db , db_userid ,db_password);
//Baut Verbindung mit Parametern auf.
            Statement statement = conn.createStatement();
            return this.conn != null/*sollte nicht eintreten*/ && !this.conn.isClosed();
        } catch (ClassNotFoundException e) {
            return false; //Verbindung konnte nicht aufgebaut werden.
        } catch (SQLException e){
            return false;
        }

    }

    /**
     * Überprüft aktuelle Verbindung zur DB, sollte Verbindung getennt sein wird
     * {@link #Connect()} aufgerufen.
     *
     * @return den Status der aktuell aufgebauten Verbindung.
     */
    public boolean checkconn() {
        try {
            //Ist eine geschlossen?
            if (this.conn == null || this.conn.isClosed()) {
                return Connect();//Verbindung aufbauen
            } else {
                //Verbindung ist aufgebaut
            } 
            return true;
        } catch (SQLException e) {
            return false;
        }

    }
    //--------------------------------------[ EINFÜGEN ]--------------------------------------\\
    /**
     * Fügt einen neuen Wert in eine SQL Tabelle ein.
     *
     * @param Tabellen_Name den Name der Tabelle in den Werte eingefügt werden
     * sollen.
     * @param Values die Werte die in die Tabelle eingefügt werden sollen. Werte
     * müssen in der reienfolge der Spaltennamen sein.
     * @return den Status ob das Einfügen erfolgreich war
     */
    public boolean insert(String Tabellen_Name, String[] Values) {
        try {
            //Prüfe verbindung zur Datenbank 
            if (checkconn()) {
                //Verbunden oder neu Verbunden
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder(); //Erstellen eines Objektes vom Typ QueryBuilder // Baut aus den Parametern SQL String
                statement.execute(QB.getInsertQuery(Tabellen_Name, Values));
                return true;
            } else {
                //Achtung: Keine Verbindung zur DB 
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    //--------------------------------------[ AUSWÄHLEN ]--------------------------------------\\
    /**
     * Liest alle werte aus einer SQL Tabelle aus. (Select * )
     *
     * @param Tabellen_Name den Name der Tabelle aus der Werte ausgelesen werden
     * sollen.
     * @return alle Werte aus der Tabelle
     */
    public ResultSet select(String Tabellen_Name) {
        try {
            //Prüfe verbindung zur Datenbank 
            if (checkconn()) {
                //Verbunden oder neu Verbunden
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder(); //Erstellen eines Objektes vom Typ QueryBuilder / Baut aus den Parametern SQL String
                ResultSet rs = statement.executeQuery(QB.getSelectQuery(Tabellen_Name));
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            //Achtung: Keine Verbindung zur DB oder Falscher SQL String
            return null;
        }
    }

    /**
     * Liest werte aus SQL Tabelle mit überprüfung eines wertes.
     *
     * @param Tabellen_Name den Name der Tabelle aus der Werte ausgelesen werden
     * sollen.
     * @param Spalte den Name der Spalte aus der der Wert vergleicht werden
     * soll.
     * @param VergleichsWert der Wert der überprüft werden soll.
     * @return alle Werte aus der Tabelle die in einer Spalte bestimmten Wert
     * haben.
     */
    public ResultSet select(String Tabellen_Name, String Spalte, String VergleichsWert) {
        try {
            //Prüfe verbindung zur Datenbank 
            if (checkconn()) {
                //Verbunden oder neu Verbunden
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                ResultSet rs = statement.executeQuery(QB.getSelectQuery(Tabellen_Name, Spalte, VergleichsWert));
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Liest werte aus SQL Tabelle mit negierter überprüfung eines wertes.
     *
     * @param Tabellen_Name den Name der Tabelle aus der Werte ausgelesen werden
     * sollen.
     * @param Spalte den Name der Spalte aus der der Wert vergleicht werden
     * soll.
     * @param Wert der Wert der überprüft werden soll.
     * @return alle Werte aus der Tabelle die nicht in einer Spalte bestimmten
     * Wert haben.
     */
    public ResultSet selectNot(String Tabellen_Name, String Spalte, String Wert) {
        try {
            //Prüfe verbindung zur Datenbank 
            if (checkconn()) {
                //Verbunden oder neu Verbunden
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                ResultSet rs = statement.executeQuery(QB.getSelectNotQuery(Tabellen_Name, Spalte, Wert));
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Liest werte aus SQL Tabelle mit überprüfung zweier Werte.
     *
     * @version 1.0
     * @param Tabellen_Name den Name der Tabelle aus der Werte ausgelesen werden
     * sollen.
     * @param Spalte1 den Name der Spalte aus der der Wert vergleicht werden
     * soll.
     * @param Wert1 der Wert der überprüft werden soll.
     * @param Spalte2 den Name der Spalte aus der der Wert vergleicht werden
     * soll.
     * @param Wert2 der Wert der überprüft werden soll.
     * @return alle Werte aus der Tabelle die in zwei Spalten bestimmte Werte
     * haben.
     */
    public ResultSet select(String Tabellen_Name, String Spalte1, String Wert1, String Spalte2, String Wert2) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                ResultSet rs = statement.executeQuery(QB.getSelectIDQuery(Tabellen_Name, Spalte1, Wert1, Spalte2, Wert2));
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }


    /**
     * Liest alle Werte aus SQL Tabelle aus, mit Sortierung nach einer Spalte.
     *
     * @version 1.0
     * @param Tabellen_Name den Name der Tabelle aus der Werte ausgelesen werden
     * sollen.
     * @param SortSpalte den Name der Spalte nach dem die Werte Sortiert werden
     * sollen.
     * @return alle Werte aus der Tabelle sortiert nach einer Spalte.
     */
    public ResultSet selectSort(String Tabellen_Name, String SortSpalte) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                ResultSet rs = statement.executeQuery(QB.getSelectSortQuery(Tabellen_Name, SortSpalte));
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Liest alle Werte aus SQL Tabelle aus, bei den ein Wert mit dem
     * Vergleichswert übereinstimmt.
     *
     * @version 1.0
     * @param Tabellen_Name den Name der Tabelle aus der Werte ausgelesen werden
     * sollen.
     * @param SortSpalte den Name der Spalte nach dem die Werte Sortiert werden
     * sollen.
     * @param VergleichsSpalte den Name der Spalte aus der Werte verglichen
     * werden sollen.
     * @param VergleichsWert den Wert der in einer Spalte verglichen werden
     * soll.
     * @return verglichene Werte aus der Tabelle sortiert nach einer Spalte.
     */
    public ResultSet selectSort(String Tabellen_Name, String VergleichsSpalte, String VergleichsWert, String SortSpalte) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                ResultSet rs = statement.executeQuery(QB.getSelectSortQuery(Tabellen_Name, VergleichsSpalte, VergleichsWert, SortSpalte));
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
    //--------------------------------------[ LÖSCHEN ]--------------------------------------\\
    /**
     * Löscht alle Werte aus einer Tabelle.
     *
     * @param Tabellen_Name den Namen der Tabelle aus der alle Einträge gelöscht
     * werden sollen.
     * @return den Status ob das Löschen erfolgreich war.
     */
    public boolean delete(String Tabellen_Name) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                statement.executeQuery(QB.getDeleteQuery(Tabellen_Name));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Löscht alle Werte aus SQL Tabelle aus, bei den ein Wert mit dem
     * Vergleichswert übereinstimmt.
     *
     * @param Tabellen_Name den Namen der Tabelle aus der Einträge gelöscht
     * werden sollen.
     * @param VergleichsSpalte den Name der Spalte aus der Werte verglichen
     * werden sollen.
     * @param VergleichsWert den Wert der in einer Spalte verglichen werden
     * soll.
     * @return den Status ob das Löschen erfolgreich war.
     */
    public boolean delete(String Tabellen_Name, String VergleichsSpalte, String VergleichsWert) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                String query = QB.getDeleteQuery(Tabellen_Name, VergleichsSpalte, VergleichsWert);
                statement.execute(query);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
/**
     * Löscht alle Werte aus SQL Tabelle aus, bei den ein Wert mit dem
     * Vergleichswert übereinstimmt.
     *
     * @param Tabellen_Name den Namen der Tabelle aus der Einträge gelöscht
     * werden sollen.
     * @param VergleichsSpalte1 den Name der Spalte aus der Werte verglichen
     * werden sollen.
     * @param VergleichsWert1 den Wert der in einer Spalte verglichen werden
     * soll.
     * @param VergleichsSpalte2 den Name der Spalte aus der Werte verglichen
     * werden sollen.
     * @param VergleichsWert2 den Wert der in einer Spalte verglichen werden
     * soll.
     * @return den Status ob das Löschen erfolgreich war.
     */
    public boolean delete(String Tabellen_Name, String VergleichsSpalte1, String VergleichsWert1,String VergleichsSpalte2, String VergleichsWert2) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                String query = QB.getDeleteQuery(Tabellen_Name, VergleichsSpalte1, VergleichsWert1, VergleichsSpalte2, VergleichsWert2);
                statement.execute(query);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    //--------------------------------------[ AKTUALISIEREN ]--------------------------------------\\
    /**
     * Aktualisiert einen Wert aus einer SQL Tabelle, bei den ein Wert mit dem
     * Vergleichswert übereinstimmt.
     *
     * @param Tabellen_Name den Name der Tabelle aus der Werte aktualisiert
     * werden sollen.
     * @param VergleichsSpalte den Name der Spalte aus der Werte verglichen
     * werden sollen.
     * @param VergleichsWert den Wert der in einer Spalte verglichen werden
     * soll.
     * @param Spalteneu den Namen der Spalte in den der neue Wert eingetragen
     * werden soll.
     * @param Valueneu den neuen Wert.
     * @return den Status ob das Aktualisieren erfolgreich war.
     */
    public boolean update(String Tabellen_Name, String VergleichsSpalte, String VergleichsWert, String Spalteneu, String Valueneu) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                statement.execute(QB.getUpdateQuery(Tabellen_Name, VergleichsSpalte, VergleichsWert, Spalteneu, Valueneu));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Aktualisiert einen Wert aus einer SQL Tabelle, bei den zwei Werte mit den
     * Vergleichswerten übereinstimmt.
     *
     * @param Tabellen_Name den Name der Tabelle aus der Werte aktualisiert
     * werden sollen.
     * @param Spalte1 den Name der Spalte aus der Werte verglichen werden
     * sollen.
     * @param Value1 den Wert der in einer Spalte verglichen werden soll.
     * @param Spalte2 den Name der Spalte aus der Werte verglichen werden
     * sollen.
     * @param Value2 den Wert der in einer Spalte verglichen werden soll.
     * @param Spalteneu den Namen der Spalte in den der neue Wert eingetragen
     * werden soll.
     * @param Valueneu den neuen Wert.
     * @return den Status ob das Aktualisieren erfolgreich war.
     */
    public boolean update(String Tabellen_Name, String Spalte1, String Value1, String Spalte2, String Value2, String Spalteneu, String Valueneu) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                statement.execute(QB.getUpdateQuery(Tabellen_Name, Spalte1, Value1, Spalte2, Value2, Spalteneu, Valueneu));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Aktualisiert Werte aus einer SQL Tabelle, bei den ein Wert mit dem
     * Vergleichswert übereinstimmt.
     *
     * @param Tabellen_Name den Name der Tabelle aus der Werte aktualisiert
     * werden sollen.
     * @param Spaltenname die Namen aller Spalten
     * @param Werteneu alle Werte die aktualiesiert werden sollen. Achtung: Werte werden den Spaltennamen der Reienfolge nach zugeordnet.
     * @param SpalteBedingung Name der Spalte die mit Werte Bedingung verglichen werden soll.
     * @param WertBedigung den Wert der Verglichen werden soll.
     * @return ob inhalt geupdatet wurde
     */
    public boolean update(String Tabellen_Name ,String Spaltenname[], String[] Werteneu, String SpalteBedingung, String WertBedigung) {
        try {
            if (checkconn()) {
                Statement statement = this.conn.createStatement();
                QueryBuilder QB = new QueryBuilder();
                statement.execute(QB.getUpdateQuery(Tabellen_Name, Spaltenname, Werteneu, SpalteBedingung, WertBedigung));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
