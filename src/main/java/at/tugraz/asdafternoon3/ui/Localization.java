package at.tugraz.asdafternoon3.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    private static Localization instance;
    private final Locale currentLocale;
    private final ResourceBundle currentMessages;

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
