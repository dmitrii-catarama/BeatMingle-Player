package app.pageSystem;

import lombok.Getter;

public abstract class BasePage {
    @Getter
    private String owner;

    public BasePage(String owner) {
        this.owner = owner;
    }

}
