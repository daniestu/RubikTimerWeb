package utils;

import models.TemaConfig;

public class TemaHelper {
    public static TemaConfig getConfig(int tema) {
        TemaConfig config = new TemaConfig();

        switch (tema) {
            case 4:
                config.setColorTextoJS("#2c2118");
                break;
            case 2:
                config.setColorTextoJS("#e6edf3");
                break;
            case 5:
                config.setColorTextoJS("#1c2530");
                break;
            case 1:
            case 3:
            default:
                config.setColorTextoJS("#ecf0f1");
                break;
        }

        return config;
    }
}