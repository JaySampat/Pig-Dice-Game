import org.newdawn.slick.*;
import java.lang.Math;
import java.util.concurrent.TimeUnit;


public class pig extends BasicGame {
    Image one;
    Image two;
    Image three;
    Image four;
    Image five;
    Image six;
    Image displayDice;
    Image redtriangle;
    Image bluetriangle;
    Image turnarrow;
    Image youwin;
    Image titlescreen;
    Image screen;
    boolean turn;
    int scoreA;
    int scoreB;
    int turnPoints;
    int maxPoints;
    int maxRoll;
    int capRoll;
    int rollCount;
    boolean computerTurn;
    public pig () {
        super("pic dice game");
    }

    public static void main(String [] arguments){
        try
        {
            AppGameContainer app = new AppGameContainer(new pig ());
            app.setDisplayMode(500,400, false);
            app.setShowFPS(false);
            app.start();

        }
        catch (SlickException e){
            e.printStackTrace();
        }


    }
    private void roll(){
        int roll = (int) (Math.random() * 6) + 1;
        setDiceImage(roll);
        if(roll == 1){
            bust();
            return;
        }
        else {
            turnPoints = turnPoints + roll;
            
        }
    }


    private void Switch(){
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        turn = !turn;
        turnPoints = 0;
        displayDice = null;

    }

    private void bust(){
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        turn = !turn;
        turnPoints = 0;
        setDiceImage(1);
        maxRoll = (int) (Math.random() * 3) + 1;
        rollCount = 0;
    }

    private void setDiceImage(int roll){
        switch (roll){
            case 1:
                displayDice = one;
                break;
            case 2:
                displayDice = two;
                break;
            case 3:
                displayDice = three;
                break;
            case 4:
                displayDice = four;
                break;
            case 5:
                displayDice = five;
                break;
            case 6:
                displayDice = six;
                break;
        }
    }

    @Override
    public void init (GameContainer container) throws SlickException{
        one = new Image("media/dice1.png");
        one.setName("one");
        two = new Image ("media/dice2.png");
        two.setName("two");
        three = new Image ("media/dice3.png");
        three.setName("three");
        four = new Image ("media/dice4.png");
        four.setName("four");
        five = new Image ("media/dice5.png");
        five.setName("five");
        six = new Image ("media/dice6.png");
        six.setName("six");
        redtriangle = new Image ("media/redtriangle.png");
        bluetriangle = new Image ("media/bluetriangle.png");
        youwin = new Image ("media/youWin.png");
        titlescreen = new Image ("media/titlescreen.png");
        turn = true;
        screen = titlescreen;
        maxPoints = 15;
        maxRoll = (int) (Math.random() * 3) + 1;
        capRoll = 10;
        rollCount = 0;
        computerTurn = false;

    }
    @Override
    public void update (GameContainer container, int delta) throws SlickException{
        if (container.getInput().isKeyPressed(Input.KEY_A) && !(computerTurn && !turn)){
           roll();

        }
        else if(computerTurn && !turn){
          if(maxRoll > 0 && turnPoints + scoreB < 100){
              roll();
              rollCount = rollCount + 1;
              maxRoll = maxRoll - 1;
            }
            else if(turnPoints < maxPoints + scoreA - scoreB && turnPoints + scoreB < 100 && rollCount < capRoll){
                roll();
                rollCount = rollCount + 1;
                System.out.println(displayDice);
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
            else{
                scoreB = turnPoints + scoreB;
                Switch();
                maxRoll = (int) (Math.random() * 3) + 1;
                rollCount = 0;
            }
        }
        else if (container.getInput().isKeyPressed(Input.KEY_SPACE) && !(computerTurn && !turn)){
            if(turn == false){
                scoreB = turnPoints + scoreB;
            }
            else{
                scoreA = turnPoints + scoreA;
            }
            Switch ();
        }
        else if(container.getInput().isKeyPressed(Input.KEY_E) && screen != null){
            screen = null;
            computerTurn = true;
            maxPoints = 5;

        }
        else if(container.getInput().isKeyPressed(Input.KEY_M) && screen != null){
            screen = null;
            computerTurn = true;
            maxPoints = 10;

        }
        else if(container.getInput().isKeyPressed(Input.KEY_H) && screen != null){
            screen = null;
            computerTurn = true;
            maxPoints = 15;

        }
        else if(container.getInput().isKeyPressed(Input.KEY_2) && screen != null){
            screen = null;
        }


    }

    public void render (GameContainer container, Graphics g ) throws SlickException{
       if(displayDice != null){
           displayDice.draw(250,200, 0.8f);
       }


        g.drawString("Score: " + scoreA, 20.0f, 20.0f) ;
        g.drawString("Score: " + scoreB, 390.0f, 20.0f);
        g.drawString("Turn Points: " + turnPoints, 200f, 0f);
        if (turn == true){
            turnarrow = redtriangle;
            turnarrow. draw (20, 30, 0.1f);
        }
        else{
            turnarrow = bluetriangle;
            turnarrow. draw (390, 30, 0.1f);
        }
        if (scoreA >= 100 || scoreB >= 100){
            youwin.draw(200, 200, 0.9f);
        }
        if(screen!=null){
            screen.draw(0, 0, 0.5f);
        }


    }
}
