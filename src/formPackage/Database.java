package formPackage;

import javax.swing.*;
import java.sql.*;

public class Database {
    private Connection conn;

    String name;
    String host;
    String port;

    public Database(String host, String port, String DBName)  {
        this.host = host;
        this.port = port;
        this.name = DBName;
    }

    public String Connect(String username, String password){
        String connection = "jdbc:mysql://"+host+":"+port+"/"+name;

        try {
            conn = DriverManager.getConnection(connection, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(conn!=null)
            return "Connessione con il database avvenuta";

        return "Connessione con il database fallita";
    }

    public void Registrazione(String username, String password) {

        try {
            if(!conn.isValid(5)){
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO utenti VALUES(?,?)";

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean Login(String username, String password){
        try {
            if(!conn.isValid(5)){
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "SELECT username, password FROM utenti WHERE username = ? AND password = ?";

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                JOptionPane.showMessageDialog(
                        null,
                        "Accesso effettuato",
                        "Login effettuato",
                        JOptionPane.WARNING_MESSAGE
                );

                return true;
            }
            else{
                JOptionPane.showMessageDialog(
                        null,
                        "Credenziali non valide",
                        "Login non effettuato",
                        JOptionPane.WARNING_MESSAGE
                );

                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
