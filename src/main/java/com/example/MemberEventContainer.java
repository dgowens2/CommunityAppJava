package com.example;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class MemberEventContainer {
    List<MemberEvent> eventList;
    String errorMessage;


    public List<MemberEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<MemberEvent> eventList) {
        this.eventList = eventList;
    }

    public MemberEventContainer() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
