import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

/**
 * Created by sergii.stepanov on 11.02.2015.
 */
public class GameCreationTest {
    private GameCreator gameCreator;

    @BeforeClass
    public void setup(){
        gameCreator=new GameCreator();
    }

    @Test
    private void createGameTest(){
        gameCreator.createGame();
    }

    @Test
    private void downloadTestGameToFTPTest() throws NoSuchAlgorithmException {
        gameCreator.downloadTestGameToSFTP();
    }

    @Test
    private void downloadProdGameToFTPTest() throws NoSuchAlgorithmException {
        gameCreator.downloadProdGameToSFTP();
    }

    @Test
    private void updatePropertiesTest(){
        gameCreator.updateProperty();
    }
}
