package cricbuzz.match;

public class OneDayMatchType implements MatchType{
    @Override
    public int noOfOvers() {
        return 50;
    }

    @Override
    public int oversPerBowler() {
        return 10;
    }
}
