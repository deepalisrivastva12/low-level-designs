package cricbuzz.match;

public class T20MatchType implements MatchType{
    @Override
    public int noOfOvers() {
        return 20;
    }

    @Override
    public int oversPerBowler() {
        return 5;
    }
}
