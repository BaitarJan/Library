package Library;

public enum YesNo {
    YES,
    NO;

    // Převede text "ano"/"ne" na YES/NO
    public static YesNo parse(String input) {
        if (input == null) return null;

        input = input.trim().toLowerCase();

        return switch (input) {
            case "ano", "a", "yes", "y" -> YES;
            case "ne", "n", "no" -> NO;
            default -> null;
        };
    }
}