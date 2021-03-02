package com.yolt.yts.sdk.service.usersite.model.login;


import lombok.Data;

import java.util.List;

/**
 * The encryption fields from Site Management's FormStepEncryptionDetailsDTO are intentionally missing here.
 * We will get to that when we implement our first bank that provides embedded flow or when scraping must be supported.
 */
@Data
public class FormStep {
    private List<FormField> formComponents;
    private ExplanationField explanationField;
    private String stateId;
}
