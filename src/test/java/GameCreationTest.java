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
    private void creatGameTest(){
        gameCreator.createGame();
    }

    @Test
    private void downloadToFTPTest() throws NoSuchAlgorithmException {
        gameCreator.downloadGameToSFTP();
    }

    @Test
    private void updatePropertiesTest(){
        gameCreator.updateProperty();
    }
}
