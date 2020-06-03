package at.tugraz.asdafternoon3.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    private static Localization instance;
    private  Locale currentLocale;
    private  ResourceBundle currentMessages;

    private Localization() {
        // Probably not the best idea to init stuff here, but who cares
        currentLocale = tryGetLocale();
        currentMessages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }

    public Locale tryGetLocale() {
        Locale locale = null;

        try {
            // Format: "de-AT"
            String overwritePath = ".locale";
            File file = new File(overwritePath);
            if(file.exists()) {
                String content = Files.readString(Paths.get(overwritePath));
                String[] arguments = content.split("-");
                locale = new Locale(arguments[0], arguments[1]);
            }
        }
        catch(IOException ex) {
            // proceed
        }

        if(locale == null) {
            locale = Locale.getDefault();
        }

        return locale;
    }

    public void setLocale(String locale)
    {
        if (locale == null)
            return;

        switch (locale) {
            case "Chinese":
                currentMessages = ResourceBundle.getBundle("MessagesBundle", new Locale("zh", "CN"));
                break;
            case "German":
                currentMessages = ResourceBundle.getBundle("MessagesBundle", new Locale("de", "AT"));
                break;
            case "English":
                currentMessages = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "US"));
                break;
            case "Italian":
                currentMessages = ResourceBundle.getBundle("MessagesBundle", new Locale("it", "IT"));
                break;
            case "Russian":
                currentMessages = ResourceBundle.getBundle("MessagesBundle", new Locale("ru", "RU"));
                break;
            case "Spanish":
                currentMessages = ResourceBundle.getBundle("MessagesBundle", new Locale("es", "ES"));
                break;

            default:
                return;
        }
    }

    public static Localization getInstance() {
        if(instance == null) {
            instance = new Localization();
        }
        return instance;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public ResourceBundle getCurrent() {
        return currentMessages;
    }
}
