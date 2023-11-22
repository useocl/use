package org.tzi.use.autocompletion;

import java.util.List;

/**
 * Represents the result of an autocompletion suggestion.
 * It contains a list of suggested strings and the prefix (can be null) used for generating these suggestions.
 */
public class SuggestionResult {
    public List<String> suggestions;
    public String prefix;
}
