package app.audio.Collections.Utilities.forArtist;

public class Merch {
    private String name;
    private Integer price;
    private String description;

    public Merch(final String name, final Integer price, final String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /**
     * Gets name of the merch.
     * @return name of the merch
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the merch.
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Gets the merch description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Verify if the price of the new merch is valid.
     * @param price the price of new album
     * @return boolean
     */
    public static boolean priceIsValid(final Integer price) {
        return price >= 0;
    }
}
