package ru.eu.flights.gui;

import ru.eu.flights.callback.ListenerType;
import ru.eu.flights.callback.Receptionist;
import ru.eu.flights.callback.WsResultListener;
import ru.eu.flights.client.FlightWSClient;
import ru.eu.flights.client.generated.Flight;
import ru.eu.flights.client.generated.InvalidArgumentMN;
import ru.eu.flights.client.generated.Passenger;
import ru.eu.flights.client.generated.Place;
import ru.eu.flights.gui.models.BoxModel;
import ru.eu.flights.object.ExtPlace;
import ru.eu.flights.utils.MessageManager;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DialogBuyTicket extends JDialog implements WsResultListener {

    private FlightWSClient flightWSClient = FlightWSClient.getInstance();
    private Flight flight;

    private JPanel contentPane;
    private JButton btnBuyTicket;
    private JButton btnCancel;
    private JTextField txtFamilyName;
    private JTextField txtDocNum;
    private JTextField txtName;
    private JTextField txtMiddleName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtCardNum;
    private JTextArea txtAreaAddInfo;
    private JComboBox comboBoxPlaces;
    private JProgressBar progressBar;

    public DialogBuyTicket(JFrame parent, boolean modal) {
        super(parent, modal);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnBuyTicket);
        btnBuyTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuyTicketActionPerformed();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setTitle("Покупка билета");
        pack();
        showOrHideProgressBar(false);
        Receptionist.getInstance().addListener(this, ListenerType.BUY_TICKET);
    }

    private void btnBuyTicketActionPerformed() {
        Passenger passenger = new Passenger();
        passenger.setGivenName(txtName.getText());
        passenger.setFamilyName(txtFamilyName.getText());
        passenger.setMiddleName(txtMiddleName.getText());
        passenger.setDocumentNumber(txtDocNum.getText());
        passenger.setEmail(txtEmail.getText());
        passenger.setPhone(txtPhone.getText());
        Place place = (Place) comboBoxPlaces.getSelectedItem();
        showOrHideProgressBar(true);
        try {
            flightWSClient.buyTicket(flight, place, passenger, txtAreaAddInfo.getText());
        } catch (InvalidArgumentMN e) {
            Logger.getLogger(DialogBuyTicket.class.getName()).log(Level.SEVERE, null, e);
            MessageManager.showErrorMessage(this, "Ошибка", e.getMessage());
        }
    }

    private void onCancel() {
        dispose();
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        fillPlaces();
    }

    private void fillPlaces() {
        List<ExtPlace> freePlaces = new ArrayList<>();

        for (Place place : flight.getAircraft().getFreePlaceList()) {
            ExtPlace ep = new ExtPlace();
            ep.setId(place.getId());
            ep.setBusy(place.isBusy());
            ep.setFlightClass(place.getFlightClass());
            ep.setSeatLetter(place.getSeatLetter());
            ep.setSeatNumber(place.getSeatNumber());
            freePlaces.add(ep);
        }
        comboBoxPlaces.setModel(new BoxModel<>(freePlaces));
    }

    private void showOrHideProgressBar(boolean b) {
        progressBar.setVisible(b);
    }

    @Override
    public void notify(Object o, ListenerType listenerType) {
        showOrHideProgressBar(false);
        Boolean result = (Boolean) o;
        if (result) {
            MessageManager.showInformMessage(DialogBuyTicket.this, "Покупка билета", "Все хорошо. Куплен.");
            dispose();
        } else {
            MessageManager.showErrorMessage(DialogBuyTicket.this, "Покупка билета", "Ошибка. Не куплен.");
        }
    }
}
