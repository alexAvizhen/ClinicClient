package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.frames.UserFrame;
import com.bsuir.lagunovskaya.clinic.communication.command.GetDateAsStrToAmountOfAppointmentsStatsClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.response.DateAsStrToAmountOfAppointmentsStatsServerResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;

public class AppointmentsStatsDialog extends JDialog {

    private JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel());
    private JSpinner endDateSpinner = new JSpinner(new SpinnerDateModel());
    private String datesFormatPattern = "yyyy-MM-dd";
    private DefaultCategoryDataset barChartDataModel;
    private ChartPanel barChartPanel;

    public AppointmentsStatsDialog(final UserFrame userFrame) {
        super(userFrame);
        setTitle("Статистика приёмов по поликлиннке");
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.add(new JLabel("Начальная дата"));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startDateSpinner, datesFormatPattern);
        startDateSpinner.setEditor(timeEditor);
        startDateSpinner.setValue(DateUtils.addDays(new Date(), -30));
        northPanel.add(startDateSpinner);

        northPanel.add(new JLabel("Конечная дата"));
        timeEditor = new JSpinner.DateEditor(endDateSpinner, datesFormatPattern);
        endDateSpinner.setEditor(timeEditor);
        endDateSpinner.setValue(DateUtils.addDays(new Date(), 30));
        northPanel.add(endDateSpinner);

        JButton showStatsBtn = new JButton("Показать статистику приёмов");
        addActionListenerToShowStatsBtn(showStatsBtn);
        northPanel.add(showStatsBtn);
        add(northPanel, BorderLayout.NORTH);

        barChartDataModel = new DefaultCategoryDataset();
        JFreeChart barChart = ChartFactory.createBarChart("Статистика приёмов по датам", "Дата", "Количество приёмов",
                barChartDataModel, PlotOrientation.VERTICAL, true, true, false);
        barChartPanel = new ChartPanel( barChart );
        barChartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        barChartPanel.setVisible(false);
        add(barChartPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addActionListenerToShowStatsBtn(JButton showStatsBtn) {
        showStatsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date startDate = (Date) startDateSpinner.getValue();
                Date endDate = (Date) endDateSpinner.getValue();
                GetDateAsStrToAmountOfAppointmentsStatsClientCommand getDateAsStrToAmountOfAppointmentsStatsCommand =
                        new GetDateAsStrToAmountOfAppointmentsStatsClientCommand("getDateAsStrToAmountOfAppointmentsStats", startDate, endDate);

                DateAsStrToAmountOfAppointmentsStatsServerResponse dateAsStrToAmountOfAppointmentsStatsServerResponse =
                        (DateAsStrToAmountOfAppointmentsStatsServerResponse) ClientCommandSender.sendClientCommand(getDateAsStrToAmountOfAppointmentsStatsCommand);
                barChartDataModel.clear();
                Map<String, Integer> dateAsStrToAmountOfAppointmentsStats = dateAsStrToAmountOfAppointmentsStatsServerResponse.getDateAsStrToAmountOfAppointmentsStats();
                final String fiat = "Приёмы";
                for (Map.Entry<String, Integer> dateAsStrToAmountOfAppointments : dateAsStrToAmountOfAppointmentsStats.entrySet()) {
                    barChartDataModel.addValue(dateAsStrToAmountOfAppointments.getValue() , fiat , dateAsStrToAmountOfAppointments.getKey());
                }

                barChartPanel.setVisible(true);
            }
        });
    }


}
