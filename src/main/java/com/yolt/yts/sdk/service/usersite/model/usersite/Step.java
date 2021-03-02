package com.yolt.yts.sdk.service.usersite.model.usersite;

import com.yolt.yts.sdk.service.usersite.model.login.FormStep;
import com.yolt.yts.sdk.service.usersite.model.login.RedirectStep;
import lombok.Data;

@Data
public class Step {
    private FormStep formStep;
    private RedirectStep redirectStep;

    public Step(FormStep formStep) {
        this.formStep = formStep;
    }

    public Step(RedirectStep redirectStep) {
        this.redirectStep = redirectStep;
    }
}
