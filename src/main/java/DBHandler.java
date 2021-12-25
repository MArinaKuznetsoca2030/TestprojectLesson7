import org.sqlite.JDBC;

import  java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

public class DBHandler {
        private final String PATH_TO_DB = "jdbc:sqlite:src/main/resources/weatherForCity.db";
        private Connection connection;

    public DBHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(PATH_TO_DB);

    }

    // Добавляем запись о погоде
    public  void addSWeather(String city, String localdate, String weathertext, float  temperatureMin, float temperatureMax) throws SQLException {
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(
                "INSERT INTO weatherCity2('nameCity', 'localdate', 'weatherText', 'temperatureMin', 'temperatureMax') VALUES(?, ?, ?, ?, ?)"
        )){

            preparedStatement.setObject(1,city);
            preparedStatement.setObject(2,localdate);
            preparedStatement.setObject(3, weathertext);
            preparedStatement.setObject(4, temperatureMin);
            preparedStatement.setObject(5, temperatureMax);
            preparedStatement.execute();

        }catch (SQLDataException e){
            e.printStackTrace();
        }

    }

    // показываем все строки из таблицы
    public void selectAllTableWeatherCity() throws SQLException {

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM weatherCity2");

            while (resultSet.next()) {
                        System.out.println("[nameCity: "+resultSet.getString("nameCity") +
                        " localdate: " + resultSet.getString("localdate")+
                                " weatherText: " + resultSet.getString("weatherText")+
                                " temperatureMin: " + resultSet.getString("temperatureMin")+
                                " temperatureMin: " +resultSet.getString("temperatureMax")+"]"
                );
            }

        } catch (SQLDataException e) {
            e.printStackTrace();
        }
    }

    }
