package com.example;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class InvitationContainer {
    String errorMessage;
    String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public InvitationContainer() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
