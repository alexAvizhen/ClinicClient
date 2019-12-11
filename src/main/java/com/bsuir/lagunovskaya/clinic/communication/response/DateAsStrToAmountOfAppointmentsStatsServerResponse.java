package com.bsuir.lagunovskaya.clinic.communication.response;

import java.util.Map;

public class DateAsStrToAmountOfAppointmentsStatsServerResponse extends ServerResponse {

    private static final long serialVersionUID = 2855994636381507403L;

    private Map<String, Integer> dateAsStrToAmountOfAppointmentsStats;

    public DateAsStrToAmountOfAppointmentsStatsServerResponse(Map<String, Integer> dateAsStrToAmountOfAppointmentsStats) {
        this.dateAsStrToAmountOfAppointmentsStats = dateAsStrToAmountOfAppointmentsStats;
    }

    public Map<String, Integer> getDateAsStrToAmountOfAppointmentsStats() {
        return dateAsStrToAmountOfAppointmentsStats;
    }
}
