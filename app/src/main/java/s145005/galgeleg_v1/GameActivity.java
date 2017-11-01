package s145005.galgeleg_v1;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView hangman;

    TextView wordView;
    TextView usedView;
    TextView attempCount;

    EditText input;

    Button check;
    Button newGame;

    Galgelogik gamelogic; //Galgelogik object

    String word;
    String used;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gamelogic = new Galgelogik(); //new Galgelogik object

        //buttons on click listener
        check = (Button) findViewById(R.id.CheckButton); //check letter input
        check.setOnClickListener(this);
        newGame = (Button) findViewById(R.id.NewGameButton); //start new game
        newGame.setOnClickListener(this);

        //text view/edit
        wordView = (TextView) findViewById(R.id.WordTextView); //word
        usedView = (TextView) findViewById(R.id.UsedLettersView); //used letters
        attempCount = (TextView) findViewById(R.id.AttempCount); //count of used letters
        input = (EditText) findViewById(R.id.LetterInputTextEdit); //letter input

        //image
        hangman = (ImageView) findViewById(R.id.imageView);

        update(); //run update on create
    }

    @Override
    public void onClick(View v) {

        if (v == newGame){ //start new game
            gamelogic.nulstil(); //reset everything and set a new word
            update(); //update user interface / graphics
            System.out.println("starting new game done");
        }
        else if (v == check){ //check if the letter input is part of the word
            word = input.getText().toString(); //input to a string
            gamelogic.gætBogstav(word); //check input
            update(); //update user interface / graphics
            System.out.println("checking input done");
        }
    }

    public void update(){ //updates the user interface

        System.out.println("running update");
        used = gamelogic.getBrugteBogstaver().toString(); //array of used letters to a string

        wordView.setText(gamelogic.getSynligtOrd()); //update wordView with viewable letters
        attempCount.setText("Antal forsøg: " + gamelogic.getBrugteBogstaver().size()); //update count of wrongs
        usedView.setText("Brugte bogstaver: " + used); //update used letters
        input.setText(""); //reset input field
        updateImage(); //update image

        if (gamelogic.erSpilletVundet()){ //show this alert dialog if the game is won
            /** source: AndroidElementer / Nordfalk **/
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setTitle("Spillet er slut");
            alert1.setMessage("Tillykke du har vundet! ordet var: " + gamelogic.getOrdet());
            alert1.show();
            System.out.println("the game is won");
        }
        else if (gamelogic.erSpilletTabt()){ //show this alert dialog if the game is lost
            /** source: AndroidElementer / Nordfalk **/
            AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
            alert2.setTitle("Spillet er slut");
            alert2.setMessage("Surt.. du har tabt :( ordet var: " + gamelogic.getOrdet() );
            alert2.show();
            System.out.println("the game is lost");
        }

        System.out.println("update done");
    }

    public void updateImage(){ //updates gallow graphic

        System.out.println("updating graphics");

        switch(gamelogic.getAntalForkerteBogstaver()){ //takes count of wrongs
            case 0:
                hangman.setImageResource(R.drawable.galge);
                break;
            case 1:
                hangman.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                hangman.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                hangman.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                hangman.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                hangman.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                hangman.setImageResource(R.drawable.forkert6);
                break;
        }
    }
}