import framework.Loader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sergii.stepanov on 11.02.2015.
 */
public class GameCreator {

    private String gameName;
    private File outputDir = new File("..\\Games\\");
    private File game;
    private FileWriter fileWriter = null;

    public void createGame() {
        String gameName=takeCurrentDate("YYYMMdd")+"0001_nba_todays_schedule";
        game = new File(outputDir, gameName+".xml");
        writeToFile("<Msg_file LeagueID=\"00\" League=\"NBA\" Season=\"2014-15\" SeasonType=\"Regular Season\">\n" +
                "  <Game Number=\"0\">\n" +
                "\t<Msg_game_info>\n" +
                "\t\t<Game_info Game_id=\""+ Loader.loadProperty("gameID")+"\" Game_date=\""+takeCurrentDate("MM/dd/YYYY")+"\" Game_time=\"10:30 AM\" Home_time=\"10:30 AM\" Visitor_time=\"10:30 AM\" Arena_name=\"Forbes Road Pavilion\" Location=\"Braintree, MA\" PPD_Status=\"I\" Game_date_UTC=\""+takeCurrentDate("YYYY-MM-dd")+"\" Game_time_UTC=\"15:30\" />\n" +
                "\t\t<Home_team Team_id=\"7818498118\" Team_city=\"Braintree\" Team_name=\"Quality\" Team_abr=\"WA_HOME\" />\n" +
                "\t\t<Visitor_team Team_id=\"7818498119\" Team_city=\"\" Team_name=\"Assurance\" Team_abr=\"WA_AWAY\" />\n" +
                "\t\t<TV_Info Home=\"NA_QA\" Away=\"NA_QA2\" Natnl=\"NA_QA3\" Canadian=\"NA_QA4\" />\n" +
                "\t</Msg_game_info>\n" +
                "  </Game>\n" +
                "</Msg_file>");
        Loader.updateProperty("gameID","111111130");
    }

    private void writeToFile(String text) {
        try {
            fileWriter = new FileWriter(game);
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
}
