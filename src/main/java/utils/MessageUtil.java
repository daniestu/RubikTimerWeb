package utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Locale;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageUtil {
    private static final Logger _log = LogManager.getLogger(MessageUtil.class);

    private static ResourceBundle getBundleForLocale(Locale locale) {
        return ResourceBundle.getBundle("Language", locale, new UTF8Control());
    }

    public static String getFormattedMessage(Locale locale, String key, Object... params) {
        String message = key;
        try {
            String pattern = getBundleForLocale(locale).getString(key);
            message = MessageFormat.format(pattern, params);
        } catch (MissingResourceException e) {
            _log.error("Can't find resource for bundle language_" + locale + ".properties, key " + key, e);
        }
        return message;
    }

    public static String getMessage(Locale locale, String key) {
        String message = key;
        try {
            message = getBundleForLocale(locale).getString(key);
        } catch (MissingResourceException e) {
            _log.error("Can't find resource for bundle language_" + locale + ".properties, key " + key, e);
        }
        return message;
    }

    public static class UTF8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws java.io.IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            try (InputStream stream = loader.getResourceAsStream(resourceName)) {
                if (stream != null) {
                    try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                        return new PropertyResourceBundle(reader);
                    }
                }
            }
            return null;
        }
    }
}