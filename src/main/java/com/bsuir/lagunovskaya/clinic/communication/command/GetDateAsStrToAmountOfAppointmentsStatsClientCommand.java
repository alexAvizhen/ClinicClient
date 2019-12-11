package com.bsuir.lagunovskaya.clinic.communication.command;

import java.util.Date;

public class GetDateAsStrToAmountOfAppointmentsStatsClientCommand extends ClientCommand {

    private Date startDate;
    private Date endDate;

    public GetDateAsStrToAmountOfAppointmentsStatsClientCommand(String commandName, Date startDate, Date endDate) {
        super(commandName);
        this.startDate = startDate;

        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
