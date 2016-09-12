package nickebbitt;

/**
 * Created by NEbbitt on 11/09/2016.
 */
class Language {

    private String name;
    private String type;
    private int yearCreated;

    private Language() {}

    Language(String name, String type, int yearCreated) {
        this.name = name;
        this.type = type;
        this.yearCreated = yearCreated;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getYearCreated() {
        return yearCreated;
    }
}
