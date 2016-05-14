package com.pharmacy.config;

/**
 * Application constants.
 */
public final class Constants {

    // Spring profile for development, production and "fast", see http://jhipster.github.io/profiles.html
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_FAST = "fast";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String SECURITY_REMERBERME_KEY = "4d0d1e0cad915f2075e65279bbc20a952bfe11d7";

    //E-mail addresses for send the notification
    public static final String SENDER_E_MAIL = "noname@gmail.com";
    public static final String RECIPIENT_E_MAIL = "info@medizin-finder.de";

    public static final String BASE_URL = "http://medizin-finder.de/";

    private Constants() {
    }
}
