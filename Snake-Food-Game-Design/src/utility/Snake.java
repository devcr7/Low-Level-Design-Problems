package utility;

import java.util.*;

public class Snake {
    private Deque<Pair> body;
    private Set<Pair> positionSet; // O(1) computation as iterating deque is not efficient

    public Snake() {
        this.body = new LinkedList<>();
        this.positionSet = new HashSet<>();

        Pair initialPosition = new Pair(0, 0);
        this.body.offerFirst(initialPosition);
        this.positionSet.add(initialPosition);
    }

    public Deque<Pair> getBody() {
        return body;
    }

    public Set<Pair> getPositionSet() {
        return positionSet;
    }
}
