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

    public static String getCodigoUrl(int idioma) {
        switch (idioma) {
            case 2:
                return "es";
            case 3:
                return "fr";
            case 4:
                return "de";
            case 5:
                return "it";
            case 6:
                return "pt";
            case 7:
                return "zh";
            case 8:
                return "ar";
            case 9:
                return "ru";
            case 10:
                return "ja";
            case 11:
                return "ko";
            case 1:
            default:
                return "en";
        }
    }

    public static int getIdiomaDesdeCodigoUrl(String codigo) {
        if (codigo == null) {
            return 1;
        }

        switch (codigo.toLowerCase()) {
            case "es":
                return 2;
            case "fr":
                return 3;
            case "de":
                return 4;
            case "it":
                return 5;
            case "pt":
                return 6;
            case "zh":
            case "zh-hans":
                return 7;
            case "ar":
                return 8;
            case "ru":
                return 9;
            case "ja":
                return 10;
            case "ko":
                return 11;
            case "en":
            default:
                return 1;
        }
    }
}