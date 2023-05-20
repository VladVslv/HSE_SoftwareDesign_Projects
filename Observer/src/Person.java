abstract public class Person {
    final String name;

    Person(String _name) {
        name = _name;
    }
    abstract void Update(Game new_game);
}