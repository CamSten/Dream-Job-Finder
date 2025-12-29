package strategy;

public class MatchingStrategyFactory {

    // Factory method to get the strategy
    public static MatchingStrategy getStrategy(StrategyType type) {
        if (type == null) {
            return new FlexibleMatchingStrategy(); // default to flexible
        }

        switch (type) {
            case STRICT:
                return new StrictMatchingStrategy();
            case FLEXIBLE:
                return new FlexibleMatchingStrategy();
            case EDUCATION_FOCUSED:
                return new EducationFocusedMatchingStrategy();
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + type);
        }
    }
}
