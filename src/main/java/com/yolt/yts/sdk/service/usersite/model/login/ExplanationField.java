package com.yolt.yts.sdk.service.usersite.model.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExplanationField extends FormField {
    private String id;
    private String displayName;
    private String explanation;
}
