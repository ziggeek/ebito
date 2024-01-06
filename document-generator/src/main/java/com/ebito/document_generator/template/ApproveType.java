package com.ebito.document_generator.template;

import lombok.Getter;

public enum ApproveType {

    APPROVE("☑"), DISAPPROVE("☐");

    @Getter
    private final String label;

    ApproveType(String label) {

        this.label = label;
    }
}
