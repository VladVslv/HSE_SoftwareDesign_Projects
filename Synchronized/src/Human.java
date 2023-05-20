public abstract class Human implements Runnable{
    final String name_;
    final Data buffer_;

    Human(Data buffer, String name){
        name_=name;
        buffer_=buffer;
    }

    public abstract void run();
}
