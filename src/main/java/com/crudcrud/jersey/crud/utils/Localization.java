package com.crudcrud.jersey.crud.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    public static final String BUNDLE = "messages";

    private final ResourceBundle resourceBundle;

    public Localization(String language) {
        this(new Locale(language));
    }

    public Localization(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE, locale);
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public String getLanguage() {
        return resourceBundle.getLocale().getLanguage();
    }
}
