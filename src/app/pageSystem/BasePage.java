package app.pageSystem;

import app.users.User;
import lombok.Getter;

public abstract class BasePage {
    @Getter
    private String owner;

    public BasePage(String owner) {
        this.owner = owner;
    }

    public abstract String printPage(User user);

}
