import framework.DataBase;
import framework.Loader;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.LocalSourceFile;
import net.schmizz.sshj.xfer.scp.SCPDownloadClient;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.Assert;

import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sergii.stepanov on 11.02.2015.
 */
public class GameCreator {

    private String gameName;
    private File outputDir= new File("../Games/"); //System.getProperty("user.dir")+
    private File gameFile;
    private FileWriter fileWriter = null;
    private DataBase dataBase=new DataBase();
    private String gameID=Loader.loadProperty("gameID");

    public void createGame() {
        gameID=dataBase.getGameID(gameID);
        if (gameID.contains("Err")){throw new RuntimeException("ERROR! Can't create Game file.");}
        gameName=takeCurrentDate("YYYMMdd")+"0001_nba_todays_schedule";
        gameFile = new File(outputDir, gameName+".xml");
        writeToFile("<Msg_file LeagueID=\"00\" League=\"NBA\" Season=\"2014-15\" SeasonType=\"Regular Season\">\n" +
                "  <Game Number=\"0\">\n" +
                "\t<Msg_game_info>\n" +
                "\t\t<Game_info Game_id=\""+ gameID+"\" Game_date=\""+takeCurrentDate("MM/dd/YYYY")+"\" Game_time=\"05:30 AM\" Home_time=\"05:30 AM\" Visitor_time=\"05:30 AM\" Arena_name=\"Forbes Road Pavilion\" Location=\"Braintree, MA\" PPD_Status=\"I\" Game_date_UTC=\""+takeCurrentDate("YYYY-MM-dd")+"\" Game_time_UTC=\"10:30\" />\n" +
                "\t\t<Home_team Team_id=\"7818498118\" Team_city=\"Braintree\" Team_name=\"Quality\" Team_abr=\"WA_HOME\" />\n" +
                "\t\t<Visitor_team Team_id=\"7818498119\" Team_city=\"\" Team_name=\"Assurance\" Team_abr=\"WA_AWAY\" />\n" +
                "\t\t<TV_Info Home=\"NA_QA\" Away=\"NA_QA2\" Natnl=\"NA_QA3\" Canadian=\"NA_QA4\" />\n" +
                "\t</Msg_game_info>\n" +
                "  </Game>\n" +
                "</Msg_file>");
    }

    private void writeToFile(String text) {
        try {
            fileWriter = new FileWriter(gameFile);
            fileWriter.append(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR! Can't write to Game file.");
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
            while (!findGameId) {
                if (dataBase.getGameID(gameID) == null) {
                    findGameId = true;
                } else {
                    gameID = Integer.toString(Integer.parseInt(gameID) + 1);
                }
            }
    }

    public void downloadGameToSFTP() {
        final SSHClient sshClient = new SSHClient();
        try {
            sshClient.addHostKeyVerifier("9b:f1:0d:f0:1c:39:8d:0e:fc:c7:7f:45:7a:25:0e:2d");
            sshClient.connect("sftp.keysurvey.com",22);
            sshClient.authPassword("nba", "Hd8kfc4xzx");
            final SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.put(gameFile.getPath(),"/test/");
            sftpClient.close();
            sshClient.disconnect();
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("ERROR! Can't download Game file to SFTP.");
        }
    }

    public void updateProperty(){
        Loader.updateProperty("gameID",gameID);
        String date=takeCurrentDate("YYYY-MM-dd");
        Loader.updateProperty("date",date);
        try{
            Assert.assertEquals(Loader.loadProperty("gameID"),gameID);
            Assert.assertEquals(Loader.loadProperty("date"),date);
        }catch (AssertionError e){
            e.printStackTrace();
            throw new RuntimeException("ERROR! NBA Properties was not updated");
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
