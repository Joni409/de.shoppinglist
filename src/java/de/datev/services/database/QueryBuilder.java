package de.datev.services.database;

/**
 * Baut aus Parametern passende SQL - Strings
 *
 * @author T09151A
 */
public class QueryBuilder {

    public String getInsertQuery(String TableName, String[] Values) {
        String QuerySend = "Insert into " + TableName + " values (";
        int length = Values.length - 1;
        for (int i = 0; i < length; i++) {
            QuerySend = QuerySend + "'" + Values[i] + "',";
        }
        QuerySend = QuerySend + "'" + Values[length] + "')";
        System.out.println(QuerySend);
        return QuerySend;
    }

    public String getSelectQuery(String TableName) {
        String QuerySend = "select * from " + TableName;
        return QuerySend;
    }

    public String getSelectSortQuery(String TableName, String Spalte) {
        String QuerySend = "select * from " + TableName + " ORDER BY " + Spalte;
        System.out.print(QuerySend);
        return QuerySend;
    }

    public String getSelectSortQuery(String TableName, String Spalte, String Values, String sort) {
        String QuerySend = "select * from " + TableName + " where " + Spalte + "='" + Values + "' " + " ORDER BY " + sort;
        System.out.print(QuerySend);
        return QuerySend;
    }

    public String getSelectQuery(String TableName, String Spalte, String Values) {
        String QuerySend = "select * from " + TableName + " where " + Spalte + "='" + Values + "';";
        System.out.print(QuerySend);
        return QuerySend;
    }

    public String getSelectNotQuery(String TableName, String Spalte, String Values) {
        String QuerySend = "select * from " + TableName + " where " + Spalte + " not like '" + Values + "'";
        System.out.print(QuerySend);
        return QuerySend;
    }

    public String getSelectIDQuery(String TableName, String Spalte, String Values, String Spalte2, String Value2) {
        String QuerySend = "select * from " + TableName + " where " + Spalte + "='" + Values + "' and " + Spalte2 + " = '" + Value2 + "'";
        System.out.print(QuerySend);
        return QuerySend;
    }

    public String getDeleteQuery(String TableName) {
        String QuerySend = "delete from " + TableName;
        return QuerySend;
    }

    public String getDeleteQuery(String TableName, String Spalte, String Value) {
        String QuerySend = "DELETE FROM " + TableName + " WHERE " + Spalte + "='" + Value + "'";
        return QuerySend;
    }

    public String getDeleteQuery(String TableName, String Spalte1, String Value1, String Spalte2, String Value2) {
        String QuerySend = "DELETE FROM " + TableName + " WHERE " + Spalte1 + "='" + Value1 + "' AND "+ Spalte2+ " ='" + Value2;
        return QuerySend;
    }

    public String getUpdateQuery(String TableName, String Spalte, String Value, String Spalteneu, String Valueneu) {
        String QuerySend = "UPDATE " + TableName + " SET " + Spalteneu + " = '" + Valueneu + "' WHERE " + Spalte + " = '" + Value + "'";
        return QuerySend;
    }

    public String getUpdateQuery(String TableName, String Spalte1, String Value1, String Spalte2, String Value2, String Spalteneu, String Valueneu) {
        String QuerySend = "UPDATE " + TableName + " SET " + Spalteneu + " = '" + Valueneu + "' WHERE " + Spalte1 + " = '" + Value1 + "' and " + Spalte2 + " = '" + Value2 + "'";
        return QuerySend;
    }

    public String getUpdateQuery(String TableName, String[] Spaltenname, String[] Werteneu, String SpalteBedingung, String WertBedigung) {
        String QuerySend = "UPDATE " + TableName + " SET ";
        if (Spaltenname.length == Werteneu.length) {
            int length = Werteneu.length - 1;
            for (int i = 0; i < length; i++) {
                QuerySend = QuerySend + "" + Spaltenname[i] + " = '" + Werteneu[i] + "', ";
            }
            QuerySend = QuerySend + "" + Spaltenname[length] + " = '" + Werteneu[length] + "' WHERE " + SpalteBedingung + " ='" + WertBedigung + "';";
        }
        return QuerySend;
    }
}
