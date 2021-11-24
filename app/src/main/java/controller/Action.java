package controller;

public class Action {
    String source;
    String target;

    public Action(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String toString() {
        return source + " " + target;
    }
}
