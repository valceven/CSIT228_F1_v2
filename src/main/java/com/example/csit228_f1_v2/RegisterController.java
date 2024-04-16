package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.*;

public class RegisterController {
    public Button btnCreate,btnDelete,btnUpdate,getBtnDelete;
    public TextField textFieldId;

    public RegisterController() throws IOException {
    }

    @FXML
    protected void btnCreateClick(){
        Connection c = MySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS accounts (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "email VARCHAR(100) NOT NULL)," +
                "address VARCHAR(100) NOT NULL," +
                "password VARCHAR(20) NOT NULL";
        try{
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table created successfully");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                c.close();
            }catch (SQLException e){
                throw new RuntimeException(e);

            }
        }
    }

    @FXML
    protected void btnUpdateClick(){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE users SET id = ? WHERE id = ?"
            )){
            int newId = 2;
            int id = 9;
            statement.setInt(1,newId);
            statement.setInt(2,id);
            int rows = statement.executeUpdate();
            System.out.println("Rows Updated: " + rows);
            ResultSet res = statement.getResultSet();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void btnReadClick() {
        try (Connection c = MySQLConnection.getConnection();) {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String email = res.getString(3);
                System.out.println("\nID: " + id + "\nName: " + name + "\nEmail: " + email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void btnDeleteClick() {

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM users WHERE id < ?"
             )) {
            int startId = 2;
            statement.setInt(1,startId);
            int rows = statement.executeUpdate();
            System.out.println("Rows deleted: " + rows);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void btnSignInClick() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO users (name, email) VALUES (? , ?)"
             )){
            String name = "Savel";
            String email = "savelbolante@gmail.com";
            statement.setString(1, name);
            statement.setString(2, email);
            int rows = statement.executeUpdate();
            System.out.println("Rows Inserted: " + rows);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
