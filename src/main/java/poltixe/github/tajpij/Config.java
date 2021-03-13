package poltixe.github.tajpij;

import java.io.Serializable;

public class Config implements Serializable {
    private int enabledOnStartup = 1;

    public int getEnabledOnStartup() {
        return this.enabledOnStartup;
    }

    public void setEnabledOnStartup(int value) {
        this.enabledOnStartup = value;
    }

    public Config() {
    }
}
