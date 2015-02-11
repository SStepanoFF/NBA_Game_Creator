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

    public static int taskCount(String gameID){
        int result=-1;
        try{
            statement = conn.createStatement();//Готовим запрос
            resultSets = statement.executeQuery("SELECT COUNT(*) as C FROM GAMES JOIN TASKS ON GAMES.GAME_ID=TASKS.GAME_ID WHERE EXTERNAL_ID="+gameID);
            while(resultSets.next()){
                result=resultSets.getInt("C");
            }
            return result;
        } catch(Exception e){
            e.printStackTrace();
            return result;
        }
        finally{
            try {
                resultSets.close();
                statement.close();
               // conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int gamesCount(String date){
        int result=-1;
        try{
            statement = conn.createStatement();//Готовим запрос
            resultSets = statement.executeQuery("SELECT COUNT(*) AS C FROM GAMES WHERE  STARTUTC LIKE '"+date+"%' AND HOME_TEAM_ID=117");
            while(resultSets.next()){
                result= resultSets.getInt("C");
            }
            return result;
        } catch(Exception e){
            e.printStackTrace();
            return result;
        }
        finally{
            try {
                resultSets.close();
                statement.close();
               // conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTaskExtId(String gameId,String taskName){
        String result="Error Task ExtId";
        try{
            statement = conn.createStatement();//Готовим запрос
            resultSets = statement.executeQuery("SELECT TASK_EXT_ID FROM GAMES JOIN TASKS ON GAMES.GAME_ID=TASKS.GAME_ID  " +
                    "WHERE EXTERNAL_ID="+gameId+" AND TASKSECTIONTITLE LIKE\""+taskName+"%\"");
            while(resultSets.next()){
                result= resultSets.getString("TASK_EXT_ID");
            }
            return result;
        } catch(Exception e){
            e.printStackTrace();
            return result;
        }
        finally{
            try {
                resultSets.close();
                statement.close();
                // conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
