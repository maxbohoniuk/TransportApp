package Views;

import Models.Kierowca;
import Models.Trasa;
import Services.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TrasaInfoGUI extends JFrame implements Runnable{
    JPanel upPanel,centerPanel;
    JLabel usernameLbl;
    JButton logoutBtn,anulujBtn,zatwierdzBtn;
    JTable infoTable;
    Trasa trasa;

    DefaultTableModel tableModel;

    public TrasaInfoGUI(Trasa trasa) {
        this.trasa = trasa;
    }

    @Override
    public void run() {
        setVisible(true);
        setTitle("Informacja o trasie");
        setPreferredSize(new Dimension(730,400));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocation(100,120);
        setBackground(Color.WHITE);


        //Up panel
        usernameLbl = new JLabel();
        usernameLbl.setIcon(new ImageIcon("images/user_logo.png"));
        usernameLbl.setText("Administrator");
        usernameLbl.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

        logoutBtn = new JButton();
        logoutBtn.setText("Wyloguj");

        upPanel = new JPanel(new BorderLayout(5,80));
        upPanel.setBackground(Color.WHITE);
        upPanel.setOpaque(true);
        upPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        upPanel.add(usernameLbl,BorderLayout.WEST);
        upPanel.add(logoutBtn,BorderLayout.EAST);

        this.add(upPanel,BorderLayout.NORTH);

        //Center panel

        JLabel trasaLbl = new JLabel(trasa.toString());
        trasaLbl.setFont(new Font(null,Font.PLAIN,30));
        trasaLbl.setAlignmentX(Component.CENTER_ALIGNMENT);


        infoTable = new JTable();
        infoTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoTable.setOpaque(true);
        infoTable.setShowHorizontalLines(true);
        infoTable.setShowVerticalLines(true);

        infoTable.setFont(new Font(null,Font.PLAIN,14));
        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new String[]{"Data","Kierowcy","Pojazd"});

        trasa.getPojazdy().stream().forEach(k -> {
            tableModel.addRow(new Object[]{k.getData(),k.getKierowcy().get(0).osoba.getImieNazwisko() + " | " + k.getKierowcy().get(1).osoba.getImieNazwisko(),k.getPojazd()});
        });

        infoTable.setModel(tableModel);








        centerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerPanel,BoxLayout.Y_AXIS);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));
        centerPanel.setLayout(boxLayout);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);
        centerPanel.add(trasaLbl);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        JScrollPane scrollPane = new JScrollPane(infoTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollPane);





        this.add(centerPanel,BorderLayout.CENTER);






        this.pack();
    }



}
