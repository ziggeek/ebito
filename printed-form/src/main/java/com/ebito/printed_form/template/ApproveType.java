package com.ebito.printed_form.template;

import lombok.Getter;

public enum ApproveType {

    APPROVE("☑"), DISAPPROVE("☐");

    @Getter
    private final String label;

    ApproveType(String label) {

        this.label = label;
    }
}
