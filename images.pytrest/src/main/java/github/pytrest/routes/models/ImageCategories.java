package github.pytrest.routes.models;

public enum ImageCategories {
    NATURE("Nature"),
    ANIMALS("Animals"),
    PORTRAIT("Portrait"),
    ARCHITECTURE("Architecture"),
    TECHNOLOGY("Technology"),
    FOOD("Food"),
    FASHION("Fashion"),
    SPORT("Sport"),
    TRAVEL("Travel"),
    ART("Art");

    private final String category;

    ImageCategories(String category) {
        this.category = category;
    }

    public static ImageCategories toImageCategories(String category) {
        return ImageCategories.valueOf(category.toUpperCase());
    }

    @Override
    public String toString() {
        return category;
    }
}
