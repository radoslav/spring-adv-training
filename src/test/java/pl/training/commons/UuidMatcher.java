package pl.training.commons;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class UuidMatcher extends TypeSafeMatcher<String> {

    private static final String UUID_PATTERN = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";

    public static UuidMatcher isUuid() {
        return new UuidMatcher();
    }

    @Override
    protected boolean matchesSafely(String uuid) {
        return uuid.matches(UUID_PATTERN);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("invalid uuid");
    }

}
