package utils;

import java.util.Locale;

public class IdiomaHelper {
    public static Locale getLocale(int idioma) {
        switch (idioma) {
            case 1:
            default:
                return Locale.ENGLISH;
            case 2:
                return new Locale("es", "ES");
            case 3:
                return Locale.FRENCH;
            case 4:
                return Locale.GERMAN;
            case 5:
                return Locale.ITALIAN;
            case 6:
                return new Locale("pt", "PT");
            case 7:
                return Locale.SIMPLIFIED_CHINESE;
            case 8:
                return new Locale("ar", "SA");
            case 9:
                return new Locale("ru", "RU");
            case 10:
                return Locale.JAPANESE;
            case 11:
                return Locale.KOREAN;
        }
    }

    public static int getIdioma(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return 1;
        } else if (locale.equals(new Locale("es", "ES"))) {
            return 2;
        } else if (locale.equals(Locale.FRENCH)) {
            return 3;
        } else if (locale.equals(Locale.GERMAN)) {
            return 4;
        } else if (locale.equals(Locale.ITALIAN)) {
            return 5;
        } else if (locale.equals(new Locale("pt", "PT"))) {
            return 6;
        } else if (locale.equals(Locale.SIMPLIFIED_CHINESE)) {
            return 7;
        } else if (locale.equals(new Locale("ar", "SA"))) {
            return 8;
        } else if (locale.equals(new Locale("ru", "RU"))) {
            return 9;
        } else if (locale.equals(Locale.JAPANESE)) {
            return 10;
        } else if (locale.equals(Locale.KOREAN)) {
            return 11;
        } else {
            return 1;
        }
    }
}