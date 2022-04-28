package pl.training.examples;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Template {

    private static final String EXPRESSION_START = "\\$\\{";
    private static final String EXPRESSION_END = "}";
    private static final Pattern EXPRESSION = Pattern.compile(EXPRESSION_START + "\\w+" + EXPRESSION_END);
    private static final String INVALID_VALUE = ".*\\W+.*";

    private final String textWithExpressions;

    public Template(String textWithExpressions) {
        this.textWithExpressions = textWithExpressions;
    }

    public String evaluate(Map<String, String> values) {
        if (!isDataComplete(values) || !isDataValid(values)) {
            throw new IllegalArgumentException();
        }
        return substitute(values, textWithExpressions);
    }

    private boolean isDataComplete(Map<String, String> values) {
        return values.size() == getExpressions().count();
    }

    private boolean isDataValid(Map<String, String> values) {
        return values.values().stream().noneMatch(value -> value.matches(INVALID_VALUE));
    }

    private Stream<MatchResult> getExpressions() {
        return EXPRESSION.matcher(textWithExpressions).results();
    }

    private String substitute(Map<String, String> values, String text) {
        for (Entry<String, String> entry : values.entrySet()) {
            var expression = createExpression(entry.getKey());
            text = text.replaceAll(expression, entry.getValue());
        }
        return text;
    }

    private String createExpression(String key) {
        return EXPRESSION_START + key + EXPRESSION_END;
    }

}
