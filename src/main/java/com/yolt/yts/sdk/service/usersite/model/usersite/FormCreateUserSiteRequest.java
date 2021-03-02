package com.yolt.yts.sdk.service.usersite.model.usersite;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;



@Getter
@Setter
public class FormCreateUserSiteRequest extends CreateUserSiteRequest {
    private UUID stateId;
    private List<FilledInFormValue> filledInFormValues;

    public FormCreateUserSiteRequest() {
        super(LoginType.FORM);
    }

    public FormCreateUserSiteRequest(UUID stateId, List<FilledInFormValue> filledInFormValues) {
        super(LoginType.FORM);
        this.stateId = stateId;
        this.filledInFormValues = filledInFormValues;
    }
}
