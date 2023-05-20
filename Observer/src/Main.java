public class Main {
    public static void main(String[] args) {
        GameChannel channel = new GameChannel();
        channel.Subscribe(new Developer("Vlad"));
        channel.Subscribe(new Gamer("John"));
        channel.Subscribe(new Journalist("Bill"));
        channel.PublishNewGame(new Game("Coolest Game", "No achievements",
                "This is the coolest game of our time", "You need the most powerful computer to play"));
        channel.NotifyAllSubscribers();
    }
}