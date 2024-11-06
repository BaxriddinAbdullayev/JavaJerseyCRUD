package com.crudcrud.jersey.crud.factory;

import com.crudcrud.jersey.crud.utils.Localization;
import org.glassfish.hk2.api.Factory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LocalizationFactory implements Factory<Localization> {

    public static final String DEFAULT_LANGUAGE = "en";

    @Context
    HttpHeaders httpHeaders;

    @Override
    public Localization provide() {
        List<Locale> languages = httpHeaders.getAcceptableLanguages();
        if (languages.isEmpty()) {
            return new Localization(DEFAULT_LANGUAGE);
        }
        Locale locale = languages.get(0);
        if (Objects.equals(locale.getLanguage(), "*")){
            return new Localization(DEFAULT_LANGUAGE);
        }
        return new Localization(locale);
    }

    @Override
    public void dispose(Localization instance) {

    }
}
