package Views;

import Models.Kierowca;
import Models.Trasa;
import Services.Main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class KierowcaGUI extends JFrame implements Runnable{
    JPanel upPanel,centerPanel;
    JLabel usernameLbl;
    JButton logoutBtn,anulujBtn,zatwierdzBtn;
    JTable kierowcyTable;
    List<Kierowca> kierowcy;

    DefaultTableModel tableModel;

    public KierowcaGUI(List<Kierowca> kierowcy) {
        this.kierowcy = kierowcy;
    }

    @Override
    public void run() {
        setVisible(true);
        setTitle("Wybierz kierowcę");
        setPreferredSize(new Dimension(530,400));
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

        JLabel kierowcyLbl = new JLabel("Dostępne kierowcy");
        kierowcyLbl.setFont(new Font(null,Font.PLAIN,30));
        kierowcyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);


        kierowcyTable = new JTable();
        kierowcyTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        kierowcyTable.setOpaque(true);
        kierowcyTable.setShowHorizontalLines(true);
        kierowcyTable.setShowVerticalLines(true);
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0){
                    return true;
                }
                else{
                    return false;
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 0){
                    return Boolean.class;
                }
                else{
                    return String.class;
                }
            }
        };
        kierowcyTable.setFont(new Font(null,Font.PLAIN,14));

        tableModel.setColumnIdentifiers(new String[]{"Wybrano","Imie Nazwisko","Doświadczenie", "Data ważności badań"});

        kierowcy.stream().filter(k -> k.getPojazdNaTrasie() == null).forEach(k -> {
            tableModel.addRow(new Object[]{false,k.osoba.getImieNazwisko(),k.getDoswiadczenie()+ "",k.getDataWaznosciBadan() + ""});
        });

        kierowcyTable.setModel(tableModel);





        //Down panel
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });
        zatwierdzBtn = new JButton("Zatwiedz");
        downPanel.add(anulujBtn);
        downPanel.add(zatwierdzBtn);

        zatwierdzBtn.addActionListener(l -> zatwierdzClicked());




        centerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerPanel,BoxLayout.Y_AXIS);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        centerPanel.setLayout(boxLayout);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);
        centerPanel.add(kierowcyLbl);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        JScrollPane scrollPane = new JScrollPane(kierowcyTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollPane);





        this.add(centerPanel,BorderLayout.CENTER);
        this.add(downPanel,BorderLayout.SOUTH);






        this.pack();
    }

    public void zatwierdzClicked() {
        List<Kierowca> wybraniKierowcy = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((boolean) (tableModel.getValueAt(i, 0))) {
                wybraniKierowcy.add(kierowcy.get(i));
            }
        }

        if(wybraniKierowcy.size() != 2){
            JOptionPane.showMessageDialog(this,"Proszę wybrać 2 kierowcy","Błąd",JOptionPane.ERROR_MESSAGE);

        }
        else{
            Main.kierowcyWybrani(wybraniKierowcy);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }


}
