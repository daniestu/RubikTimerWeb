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
}