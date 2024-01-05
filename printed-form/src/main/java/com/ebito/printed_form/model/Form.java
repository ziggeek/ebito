package com.ebito.printed_form.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Form {
    REFERENCE_001_BRANCH("reference001/reference-001-branch-template.docx",
            "Выписка по начислениям абонента"),
    REFERENCE_001_MOBILE("reference001/reference-001-mobile-template.docx",
            "Выписка по начислениям абонента"),
    REFERENCE_001_ONLINE("reference001/reference-001-online-template.docx",
            "Выписка по начислениям абонента")
    ;

    private final String path;
    private final String documentName;
}
