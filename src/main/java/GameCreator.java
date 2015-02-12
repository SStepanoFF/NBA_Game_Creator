import framework.DataBase;
import framework.Loader;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sergii.stepanov on 11.02.2015.
 */
public class GameCreator {

    private String gameName;
    private File outputDir = new File("..\\Games\\");
    private File gameFile;
    private FileWriter fileWriter = null;
    private DataBase dataBase=new DataBase();
    private String gameID=Loader.loadProperty("gameID");

    public void createGame() {
        gameID=dataBase.getGameID(gameID);
        gameName=takeCurrentDate("YYYMMdd")+"0001_nba_todays_schedule";
        gameFile = new File(outputDir, gameName+".xml");
        writeToFile("<Msg_file LeagueID=\"00\" League=\"NBA\" Season=\"2014-15\" SeasonType=\"Regular Season\">\n" +
                "  <Game Number=\"0\">\n" +
                "\t<Msg_game_info>\n" +
                "\t\t<Game_info Game_id=\""+ gameID+"\" Game_date=\""+takeCurrentDate("MM/dd/YYYY")+"\" Game_time=\"10:30 AM\" Home_time=\"10:30 AM\" Visitor_time=\"10:30 AM\" Arena_name=\"Forbes Road Pavilion\" Location=\"Braintree, MA\" PPD_Status=\"I\" Game_date_UTC=\""+takeCurrentDate("YYYY-MM-dd")+"\" Game_time_UTC=\"15:30\" />\n" +
                "\t\t<Home_team Team_id=\"7818498118\" Team_city=\"Braintree\" Team_name=\"Quality\" Team_abr=\"WA_HOME\" />\n" +
                "\t\t<Visitor_team Team_id=\"7818498119\" Team_city=\"\" Team_name=\"Assurance\" Team_abr=\"WA_AWAY\" />\n" +
                "\t\t<TV_Info Home=\"NA_QA\" Away=\"NA_QA2\" Natnl=\"NA_QA3\" Canadian=\"NA_QA4\" />\n" +
                "\t</Msg_game_info>\n" +
                "  </Game>\n" +
                "</Msg_file>");
        Loader.updateProperty("gameID",gameID);
    }

    private void writeToFile(String text) {
        try {
            fileWriter = new FileWriter(gameFile);
            fileWriter.append(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String takeCurrentDate(String formatDate){
        DateTime dt = new DateTime();
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(formatDate);
        return dateFormat.print(dt);
    }

    private void findGameID(){
        gameID=Integer.toString(Integer.parseInt(gameID)+1);
        boolean findGameId=false;
        while (!findGameId){
            if (dataBase.getGameID(gameID)==null) {
                findGameId = true;
            }else {
                gameID=Integer.toString(Integer.parseInt(gameID)+1);
            }
        }
    }

    public void downloadGameToSFTP() {
        SSHClient sshClient = new SSHClient();
        try {
            sshClient.loadKnownHosts();
            sshClient.connect("sftp.keysurvey.com");
            sshClient.authPassword("nbaс", "Hd8kfc4xzx");
            SFTPClient sftpClient=sshClient.newSFTPClient();
            sftpClient.put(new FileSystemFile(gameFile),"/test");
            sftpClient.close();
            sshClient.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//    public void downloadGameToFTP() throws NoSuchAlgorithmException {
//        FTPClient ftpClient = new FTPClient();
//        FileInputStream fileInputStream = null;
//        try {
//            ftpClient.connect("sftp.keysurvey.com");
//            ftpClient.login("nbaс", "Hd8kfc4xzx");
//            //ftpClient.changeWorkingDirectory("/test");
//            fileInputStream= new FileInputStream(gameFile);
//            ftpClient.storeFile("/test/201502120001_nba_todays_schedule.xml",fileInputStream);
//            ftpClient.logout();
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if (fileInputStream != null) {
//                    fileInputStream.close();
//                }
//                ftpClient.disconnect();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
}
