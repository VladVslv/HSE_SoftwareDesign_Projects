public class Journalist extends Person {
    Journalist(String _name) {
        super(_name);
    }

    @Override
    void Update(Game new_game) {
        System.out.print("Journalist " + name + " <- Information about the game: " + new_game.getInfo() + ".\n\n");
    }
}
