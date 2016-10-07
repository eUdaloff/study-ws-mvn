package ru.eu.flights.gui;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import ru.eu.flights.callback.ListenerType;
import ru.eu.flights.callback.Receptionist;
import ru.eu.flights.callback.WsResultListener;
import ru.eu.flights.client.FlightWSClient;
import ru.eu.flights.client.generated.City;
import ru.eu.flights.client.generated.Flight;
import ru.eu.flights.client.generated.InvalidArgumentMN;
import ru.eu.flights.gui.models.BoxModel;
import ru.eu.flights.gui.models.FlightTableModel;
import ru.eu.flights.proxy.CustomProxySelector;
import ru.eu.flights.utils.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrameMain extends JFrame implements WsResultListener {

    private JButton btSearchFlights;
    private JComboBox comboBoxFrom;
    private JComboBox comboBoxTo;
    private JXDatePicker dateFlight;
    private JPanel rootPanel;
    private JXTable tableFlights;
    private JButton btnBuyTicket;
    private JButton btnCheckReservation;
    private JLabel labelCityFromFlag;
    private JLabel labelCityToFlag;
    private JProgressBar progressBar;

    private FlightWSClient flightWSClient = FlightWSClient.getInstance();
    private List<City> cities;
    private List<Flight> flights = new ArrayList<>();

    public FrameMain() {
        super("Авиабилеты");
        tableFlights.setModel(new FlightTableModel(flights));
        dateFlight.setTimeZone(TimeZone.getTimeZone("GMT"));
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        btSearchFlights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSearchActionPerformed(e);
            }
        });
        btnBuyTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuyTicketActionPerformed(e);
            }
        });
        btnCheckReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCheckReservationActionPerformed();
            }
        });
        comboBoxFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxFromChanged();
            }
        });
        comboBoxTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxToChanged();
            }
        });
        showOrHideProgressBar(false);
        Receptionist.getInstance().addListener(this, ListenerType.ALL_CITIES);
        Receptionist.getInstance().addListener(this, ListenerType.SEARCH_FLIGHT);
        flightWSClient.getAllCities();
    }

    private void comboBoxToChanged() {
        City city = (City) comboBoxTo.getSelectedItem();
        byte[] flag = city.getCountry().getFlag();
        if (flag != null) {
            labelCityToFlag.setIcon(new ImageIcon(flag));
        } else {
            labelCityToFlag.setIcon(null);
        }
    }

    private void comboBoxFromChanged() {
        City city = (City) comboBoxFrom.getSelectedItem();
        byte[] flag = city.getCountry().getFlag();
        if (flag != null) {
            labelCityFromFlag.setIcon(new ImageIcon(flag));
        } else {
            labelCityFromFlag.setIcon(null);
        }
    }

    private void btnCheckReservationActionPerformed() {
        DialogCheckReserveCode dialog = new DialogCheckReserveCode(this, true);
        dialog.setVisible(true);
    }

    private void btnSearchActionPerformed(ActionEvent e) {
        searchFlights();
    }

    private void btnBuyTicketActionPerformed(ActionEvent e) {
        if (tableFlights.getSelectedRow() >= 0) {
            DialogBuyTicket dialog = new DialogBuyTicket(this, true);
            Flight flight = flights.get(tableFlights.getSelectedRow());
            dialog.setFlight(flight);
            dialog.setVisible(true);
            searchFlights();
        } else {
            MessageManager.showInformMessage(this, "Внимание", "Выберите рейс");
        }
    }

    private void searchFlights() {
        City from = (City) comboBoxFrom.getSelectedItem();
        City to = (City) comboBoxTo.getSelectedItem();
        Date dateDepart = dateFlight.getDate();
        long dateDepartMilliseconds = dateDepart == null ? 0 : dateDepart.getTime();
        showOrHideProgressBar(true);
        try {
            flightWSClient.searchFlights(dateDepartMilliseconds, from, to, 1);
        } catch (InvalidArgumentMN e) {
            Logger.getLogger(FrameMain.class.getName()).log(Level.SEVERE, null, e);
            MessageManager.showErrorMessage(FrameMain.this, "Ошибка", e.getMessage());
            showOrHideProgressBar(false);
        }
    }

    private void showOrHideProgressBar(boolean b) {
        progressBar.setVisible(b);
    }

    @Override
    public void notify(Object o, ListenerType listenerType) {
        switch (listenerType) {
            case ALL_CITIES:
                comboBoxFrom.setModel(new BoxModel<>((List<City>) o));
                comboBoxTo.setModel(new BoxModel<>((List<City>) o));
                showOrHideProgressBar(false);
                break;
            case SEARCH_FLIGHT:
                showOrHideProgressBar(false);
                List<Flight> list = (List<Flight>) o;
                if (list == null || list.isEmpty()) {
                    MessageManager.showInformMessage(this, "Результаты поиска", "Ничего не найдено");
                } else {
                    flights.clear();
                    flights.addAll(list);
                    ((FlightTableModel) tableFlights.getModel()).fireTableDataChanged();
                }
                break;
        }
    }

    public static void main(String[] args) {
        ProxySelector.setDefault(new CustomProxySelector());
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (UnsupportedLookAndFeelException ignore) {
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameMain().setVisible(true);
            }
        });
    }
}