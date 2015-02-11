import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
    private void creatGame(){
        gameCreator.createGame();
    }
}
