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
    ONLINE("Личный кабинет"),

    /**
     * Мобильное приложение
     */
    MOBILE("Мобильное приложение"),

    /**
     * Отделение
     */
    BRANCH("Отделение");

    private final String channelName;
}
