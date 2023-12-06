package app.audio.Collections.Utilities.forArtist;

public class Merch {
    private String name;
    private Integer price;
    private String description;

    public Merch(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    public static boolean priceIsValid(Integer price) {
        if (price < 0) {
            return false;
        }
        return true;
    }
}
