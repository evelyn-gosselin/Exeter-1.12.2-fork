package me.friendly.exeter.gui.screens.accountmanager;
import me.friendly.api.interfaces.Labeled;
import me.friendly.exeter.gui.screens.accountmanager.AccountException;

public class Account
implements Labeled {
    private final String label;
    private final String password;
    private final boolean premium;

    public Account(String label, String password) {
        this.premium = true;
        this.label = label;
        this.password = password;
    }

    public Account(String label) {
        this.premium = false;
        this.label = label;
        this.password = "N/A";
    }

    public String getFileLine() {
        return this.premium ? this.label.concat(":").concat(this.password) : this.label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getPassword() throws AccountException {
        if (this.premium) {
            return this.password;
        }
        throw new AccountException("Non-Premium accounts do not have passwords!");
    }

    public boolean isPremium() {
        return this.premium;
    }
}

