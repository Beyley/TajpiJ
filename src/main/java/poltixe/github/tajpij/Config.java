package poltixe.github.tajpij;

import java.io.Serializable;

public class Config implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5809678581736027451L;

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
