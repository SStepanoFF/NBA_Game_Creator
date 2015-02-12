package framework;


import java.sql.*;

public class DataBase {
    private static Connection conn;
    private static Statement statement;
    private static ResultSet resultSets;

    public DataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");//Регистрируем драйвер
            conn = DriverManager.getConnection("jdbc:mysql://mysql-kstest2.t1.tenet:3306/nba_t1_ssstest_com_PRR_3227",
                    "member", "1234");//Установка соединения с БД
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
            Loader.logWritter("ERROR! Can't connect to DB");
        }
    }

    public String getGameID(String gameID){
        String result="Err";
        try{
            statement = conn.createStatement();//Готовим запрос
            while (!result.contains("0")) {
                resultSets = statement.executeQuery("SELECT count(*) as C FROM GAMES WHERE EXTERNAL_ID=" + gameID);
                while (resultSets.next()) {
                    result = resultSets.getString("C");
                }
                if(!result.contains("0")) gameID=Integer.toString(Integer.parseInt(gameID) + 1);
            }
            return gameID;
        } catch(Exception e){
            e.printStackTrace();
            return result="Error";
        }
        finally{
            try {
                resultSets.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
