public class Developer extends Person {
    Developer(String _name) {
        super(_name);
    }

    @Override
    void Update(Game new_game) {
        System.out.print("Developer " + name + " <- Technical data: " + new_game.getTechnicalData() + ".\n\n");
    }
}
