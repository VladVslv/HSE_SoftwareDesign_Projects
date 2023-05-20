import java.util.Date;

public class Gamer extends Person {
    Gamer(String _name) {
        super(_name);
    }

    @Override
    void Update(Game new_game) {
        System.out.print("Gamer " + name + " <- Title: \"" + new_game.getTitle() +
                "\". Achievements: " + new_game.getAchievements() + ".\n\n");
    }
}
