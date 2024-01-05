package com.ebito.printed_form.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Канал формирования документа
 */
@Getter
@RequiredArgsConstructor
public enum Channel {

    /**
     * Личный кабинет на сайте
     */
    ONLINE("Личный кабинет", "Личном кабинете"),

    /**
     * Мобильное приложение
     */
    MOBILE("Мобильное приложение", "Мобильном приложении"),

    /**
     * Отделение
     */
    BRANCH("Отделение","Отделении");

    private final String channelName;
    private final String channelNameForForm;
}
