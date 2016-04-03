package com.pharmacy.exceptions.type;

import com.pharmacy.exceptions.loglevel.LogLevel;

/**
 * Created by apopow on 25.12.2015.
 */
public enum ExceptionType {
    PE_0000(LogLevel.INFO, "Beschreibung für die GUI", "Beschreibung für die Log-Ausgabe"),
    LOGIN_0001(LogLevel.ERROR, "Ihr Benutzername oder Ihr Passwort ist falsch.", "Could not found user in DB. Check email {} by user."),
    LOGIN_0002(LogLevel.ERROR, "Ihr Benutzername oder Ihr Passwort ist falsch.", "Could not found user in DB. Check email {} by user."),
    LOGIN_0003(LogLevel.ERROR, "Ihr Benutzername oder Ihr Passwort ist falsch.", "Could not found user in DB. Check email {} by user."),
    LOGIN_0004(LogLevel.ERROR, "Ihr Benutzername oder Ihr Passwort ist falsch.", "Could not found user in DB. Check email {} by user."),
    LOGIN_0005(LogLevel.ERROR, "Ihr Benutzername oder Ihr Passwort ist falsch.", "Could not found user in DB. Check email {} by user."),

    //Search
    SEARCH_0001(LogLevel.ERROR, "Die Suche konnte nicht ausgeführt werden.", "Could not create the Seach."),

    //Change password
    RESET_PASSWORD_0001(LogLevel.ERROR, "Diese E-Mail-Adresse existiert nicht. Bitte geben Sie eine andere E-Mail-Adresse ein.", "User {} was not found in the database");

    private LogLevel logLevel;
    private String resourceKey;
    private String description;

    ExceptionType(LogLevel logLevel, String resourceKey, String description) {
        this.logLevel = logLevel;
        this.resourceKey = resourceKey;
        this.description = description;
    }

    /**
     * @return the logLevel
     */
    public LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * @param logLevel the logLevel to set
     */
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * @return the resourceKey
     */
    public String getResourceKey() {
        return resourceKey;
    }

    /**
     * @param resourceKey the resourceKey to set
     */
    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
