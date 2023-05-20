import java.util.ArrayList;
import java.time.LocalDate;

public class GameChannel {
    private Game lastGame = null;
    private ArrayList<Person> subscribers = new ArrayList<Person>();

    GameChannel(Game _game, ArrayList<Person> _subscribers) {
        lastGame = _game;
        subscribers = new ArrayList<Person>(_subscribers);
    }

    GameChannel(Game game) {
        this(game, new ArrayList<Person>());
    }

    GameChannel(ArrayList<Person> _subscribers) {
        this(null, _subscribers);
    }

    public GameChannel() {
    }

    void Subscribe(Person person) {
        subscribers.add(person);
    }

    void Unsubscribe(Person person) {
        subscribers.remove(person);
    }

    void PublishNewGame(Game newGame) {
        lastGame = newGame;
        System.out.print("Game \"" + lastGame.getTitle() + "\" was published. Date: " +
                LocalDate.now() + ".\n\n");
    }

    void NotifyAllSubscribers() {
        if (lastGame != null) {
            for (Person person : subscribers) {
                person.Update(lastGame);
            }
        } else {
            System.out.print("There are no published games!\n");
        }
    }
}
