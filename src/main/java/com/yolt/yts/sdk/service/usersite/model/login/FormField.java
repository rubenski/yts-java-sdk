package com.yolt.yts.sdk.service.usersite.model.login;

import lombok.Data;

@Data
public abstract class FormField {
    String displayName;
    boolean optional;
    String fieldType;
}
