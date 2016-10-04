package ru.eu.flights.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.eu.flights.callback.ListenerType;
import ru.eu.flights.callback.Receptionist;
import ru.eu.flights.callback.WsResultListener;
import ru.eu.flights.client.FlightRS_Client;
import ru.eu.flights.object.ExtPlace;
import ru.eu.flights.utils.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DialogCheckReserveCode extends JDialog implements WsResultListener {

    private JPanel contentPane;
    private JButton btnCheckReservation;
    private JButton btnCancel;
    private JTextField txtReservationCode;
    private JProgressBar progressBar;

    private FlightRS_Client flightWSClient = FlightRS_Client.getInstance();

    public DialogCheckReserveCode(Frame owner, boolean modal) {
        super(owner, modal);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnCheckReservation);
        btnCheckReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCheckReservationActionPerformed();
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
        setTitle("Проверка брони");
        pack();
        showOrHideProgressBar(false);
        Receptionist.getInstance().addListener(this, ListenerType.CHECK_RESERVATION);
    }

    private void btnCheckReservationActionPerformed() {
//        String code = txtReservationCode.getText();
//        if (code == null || code.isEmpty()) {
//            MessageManager.showInformMessage(this, "Внимание", "Укажите код брони");
//            return;
//        }
//        showOrHideProgressBar(true);
//        try {
//            flightWSClient.checkReservationByCode(code);
//        } catch (InvalidArgumentMN e) {
//            Logger.getLogger(DialogCheckReserveCode.class.getName()).log(Level.SEVERE, null, e);
//            MessageManager.showErrorMessage(this, "Ошибка", e.getMessage());
//        }
    }

//        flightWSClient.checkReservationByCodeAsyncCallback(code, res -> {
//            try {
//                String msg;
//                Reservation reservation = res.get().getReturn();
//                if (reservation != null) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("Рейс: ").append(reservation.getFlight().getCode()).append("\n");
//                    sb.append("Самолет: ").append(reservation.getFlight().getAircraft().getName()).append("\n");
//                    sb.append("Дата вылета: ").append(reservation.getFlight().getDateDepart()).append("\n");
//                    sb.append("Дата прилета: ").append(reservation.getFlight().getDateArrive()).append("\n");
//                    sb.append("Место: ").append(transformToExtPlace(reservation.getPlace())).append("\n");
//                    msg = sb.toString();
//                } else {
//                    msg = "Бронь не найдена";
//                }
//                MessageManager.showInformMessage(DialogCheckReserveCode.this, "Результат проверки", msg);
//            } catch (InterruptedException | ExecutionException e) {
//                Logger.getLogger(DialogCheckReserveCode.class.getName()).log(Level.SEVERE, null, e);
//                MessageManager.showErrorMessage(this, "Ошибка", e.getMessage());
//            }
//            showOrHideProgressBar(false);
//        });


//    private ExtPlace transformToExtPlace(Place place) {
//        ExtPlace ep = new ExtPlace();
//        ep.setId(place.getId());
//        ep.setBusy(place.isBusy());
//        ep.setFlightClass(place.getFlightClass());
//        ep.setSeatLetter(place.getSeatLetter());
//        ep.setSeatNumber(place.getSeatNumber());
//        return ep;
//    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        DialogCheckReserveCode dialog = new DialogCheckReserveCode(null, false);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void showOrHideProgressBar(boolean b) {
        progressBar.setVisible(b);
    }

    @Override
    public void notify(Object o, ListenerType listenerType) {
//        showOrHideProgressBar(false);
//        Reservation reservation = (Reservation) o;
//        String msg;
//        if (reservation != null) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Рейс: ").append(reservation.getFlight().getCode()).append("\n");
//            sb.append("Самолет: ").append(reservation.getFlight().getAircraft().getName()).append("\n");
//            sb.append("Дата вылета: ").append(reservation.getFlight().getDateDepart()).append("\n");
//            sb.append("Дата прилета: ").append(reservation.getFlight().getDateArrive()).append("\n");
//            sb.append("Место: ").append(transformToExtPlace(reservation.getPlace())).append("\n");
//            msg = sb.toString();
//        } else {
//            msg = "Бронь не найдена";
//        }
//        MessageManager.showInformMessage(DialogCheckReserveCode.this, "Результат проверки", msg);
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnCheckReservation = new JButton();
        btnCheckReservation.setText("Проверить");
        panel2.add(btnCheckReservation, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCancel = new JButton();
        btnCancel.setText("Отмена");
        panel2.add(btnCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Код брони");
        panel3.add(label1, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtReservationCode = new JTextField();
        panel3.add(txtReservationCode, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        contentPane.add(progressBar, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
