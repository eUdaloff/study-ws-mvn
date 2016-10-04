package ru.eu.flights.gui;


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import ru.eu.flights.callback.ListenerType;
import ru.eu.flights.callback.Receptionist;
import ru.eu.flights.callback.WsResultListener;
import ru.eu.flights.client.FlightRS_Client;
import ru.eu.flights.gui.models.BoxModel;
import ru.eu.flights.gui.models.FlightTableModel;
import ru.eu.flights.object.ExtCity;
import ru.eu.flights.utils.MessageManager;
import ru.eu.flights.ws.proxy.CustomProxySelector;
import ru.eu.webservices.generated.City;
import ru.eu.webservices.generated.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    private FlightRS_Client flightWSClient = FlightRS_Client.getInstance();
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
                btnSearchFlightsActionPerformed(e);
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
            public void actionPerformed(ActionEvent e) {
                comboBoxFromChanged();
            }
        });
        comboBoxTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxToChanged();
            }
        });
        showOrHideProgressBar(false);
        Receptionist.getInstance().addListener(this, ListenerType.ALL_CITIES);
        Receptionist.getInstance().addListener(this, ListenerType.SEARCH_FLIGHT);
        updateCities(flightWSClient.getAllCities());
    }

    private void updateCities(List<ExtCity> allCities) {
        comboBoxFrom.setModel(new BoxModel(allCities));
        comboBoxTo.setModel(new BoxModel(allCities));
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

    private void btnSearchFlightsActionPerformed(ActionEvent e) {
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
        List<Flight> list = flightWSClient.searchFlights(dateDepartMilliseconds, from, to);
        if (list == null || list.isEmpty()) {
            MessageManager.showInformMessage(this, "Результаты поиска", "Ничего не найдено");
        } else {
            flights.clear();
            flights.addAll(list);
            ((FlightTableModel) tableFlights.getModel()).fireTableDataChanged();
        }
        showOrHideProgressBar(false);
    }

    private void showOrHideProgressBar(boolean b) {
        progressBar.setVisible(b);
    }

    @Override
    public void notify(Object o, ListenerType listenerType) {
//        switch (listenerType) {
//            case ALL_CITIES:
//                comboBoxFrom.setModel(new BoxModel<>((List<City>) o));
//                comboBoxTo.setModel(new BoxModel<>((List<City>) o));
//                showOrHideProgressBar(false);
//                break;
//            case SEARCH_FLIGHT:
//                showOrHideProgressBar(false);
//                List<Flight> list = (List<Flight>) o;
//                if (list == null || list.isEmpty()) {
//                    MessageManager.showInformMessage(this, "Результаты поиска", "Ничего не найдено");
//                } else {
//                    flights.clear();
//                    flights.addAll(list);
//                    ((FlightTableModel) tableFlights.getModel()).fireTableDataChanged();
//                }
//                break;
//        }
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


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Дата вылета");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateFlight = new JXDatePicker();
        panel2.add(dateFlight, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btSearchFlights = new JButton();
        btSearchFlights.setText("Найти");
        panel2.add(btSearchFlights, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(286, 57), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Куда");
        panel3.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Откуда");
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxFrom = new JComboBox();
        panel3.add(comboBoxFrom, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxTo = new JComboBox();
        panel3.add(comboBoxTo, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelCityFromFlag = new JLabel();
        labelCityFromFlag.setText("");
        panel3.add(labelCityFromFlag, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelCityToFlag = new JLabel();
        labelCityToFlag.setText("");
        panel3.add(labelCityToFlag, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel4.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableFlights = new JXTable();
        tableFlights.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        scrollPane1.setViewportView(tableFlights);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnBuyTicket = new JButton();
        btnBuyTicket.setText("Купить");
        panel5.add(btnBuyTicket, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCheckReservation = new JButton();
        btnCheckReservation.setText("Проверить");
        panel5.add(btnCheckReservation, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel5.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMaximum(50);
        progressBar.setOrientation(0);
        panel5.add(progressBar, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}