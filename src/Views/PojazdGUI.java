package Views;

import Models.Pojazd;
import Models.PojazdNaTrasie;
import Models.Status;
import Models.Trasa;
import Services.Main;
import datechooser.beans.DateChooserPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PojazdGUI extends JFrame implements Runnable{
    JPanel upPanel,rightPanel;
    JLabel usernameLbl;
    JButton logoutBtn,anulujBtn,wybierzBtn;
    JTextField searchField;
    JList<Pojazd> pojazdList;
    List<Pojazd> pojazdy;
    DefaultListModel<Pojazd> model;
    DateChooserPanel dateChooserPanel;

    public PojazdGUI(List<Pojazd> pojazdy){
        this.pojazdy = pojazdy;
    }

    @Override
    public void run() {
        setVisible(true);
        setTitle("Wybierz pojazd");
        setPreferredSize(new Dimension(580,400));
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

        //Left panel
        JLabel dataLbl = new JLabel("Wybierz\ndatę");
        dataLbl.setFont(new Font(null,Font.PLAIN,30));
        dataLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

         dateChooserPanel = new DateChooserPanel();
        dateChooserPanel.setLocale(Locale.forLanguageTag("pl"));
        dateChooserPanel.setBackground(Color.WHITE);
        dateChooserPanel.setOpaque(true);







        JPanel leftPanel = new JPanel();
        BoxLayout leftBoxLayout = new BoxLayout(leftPanel,BoxLayout.Y_AXIS);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,5));
        leftPanel.setLayout(leftBoxLayout);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setOpaque(true);

        leftPanel.add(dataLbl);
        leftPanel.add(dateChooserPanel);


        //Right panel

        JLabel pojazdyLbl = new JLabel("Dostępne pojazdy");
        pojazdyLbl.setFont(new Font(null,Font.PLAIN,30));
        pojazdyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        pojazdList = new JList<Pojazd>();
        model = new DefaultListModel<>();
        pojazdy.stream().filter(p -> p.getStatus() == Status.WOLNY).forEach(p -> model.addElement(p));
        pojazdList.setModel(model);
        pojazdList.setPreferredSize(new Dimension(50,80));
        pojazdList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pojazdList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        searchField = new JTextField("Wyszukaj");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Wyszukaj")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Wyszukaj");

                }
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!searchField.getText().isEmpty()){
                    if(!searchField.getText().equals("Wyszukaj")){
                        wyszukaj((DefaultListModel<Pojazd>) (pojazdList.getModel()),searchField.getText());
                    }
                }
                else{
                    model.removeAllElements();
                    pojazdy.stream().forEach(p -> model.addElement(p));
                }


            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(!searchField.getText().isEmpty()){
                    if(!searchField.getText().equals("Wyszukaj")){
                        wyszukaj((DefaultListModel<Pojazd>) pojazdList.getModel(),searchField.getText());
                    }
                }
                else{
                    model.removeAllElements();
                    pojazdy.stream().forEach(p -> model.addElement(p));
                }

            }

            @Override
            public void changedUpdate(DocumentEvent e) {


            }
        });
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setPreferredSize(new Dimension(180,35));
        searchField.setMinimumSize(searchField.getPreferredSize());
        searchField.setMaximumSize(searchField.getPreferredSize());



        rightPanel = new JPanel();
        BoxLayout rightBoxLayout = new BoxLayout(rightPanel,BoxLayout.Y_AXIS);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,15));
        rightPanel.setLayout(rightBoxLayout);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setOpaque(true);
        rightPanel.add(pojazdyLbl);
        rightPanel.add(searchField);
        rightPanel.add(Box.createRigidArea(new Dimension(0,20)));
        JScrollPane scrollPane = new JScrollPane(pojazdList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollPane);


        //Down panel
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });
        wybierzBtn = new JButton("Wybierz");
        wybierzBtn.addActionListener(l -> wybierzClicked());

        downPanel.add(anulujBtn);
        downPanel.add(wybierzBtn);



        //Center panel
        JPanel centerPanel = new JPanel();
        BoxLayout centerBoxLayout = new BoxLayout(centerPanel,BoxLayout.X_AXIS);
        centerPanel.setLayout(centerBoxLayout);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        this.add(centerPanel,BorderLayout.CENTER);
        this.add(downPanel,BorderLayout.SOUTH);






        this.pack();
    }

    public void wyszukaj(DefaultListModel<Pojazd> defaultListModel,String target){

        for (Pojazd e : pojazdy) {
            if (!e.toString().contains(target)) {
                if (defaultListModel.contains(e)) {
                    defaultListModel.removeElement(e);
                }
            } else {
                if (!defaultListModel.contains(e)) {
                    defaultListModel.addElement(e);
                }
            }
        }


    }

    public void wybierzClicked(){
        if(validateForms()){
            System.out.println(dateChooserPanel.getSelectedDate().get(Calendar.DAY_OF_MONTH));
            System.out.println(dateChooserPanel.getSelectedDate().get(Calendar.MONTH));
            System.out.println(dateChooserPanel.getSelectedDate().get(Calendar.YEAR));

            Pojazd p = pojazdList.getSelectedValue();

            try {
                PojazdNaTrasie pojazdNaTrasie = new PojazdNaTrasie(p, Main.t);

                pojazdNaTrasie.setData(LocalDate.of(dateChooserPanel.getSelectedDate().get(Calendar.YEAR),dateChooserPanel.getSelectedDate().get(Calendar.MONTH)+1,dateChooserPanel.getSelectedDate().get(Calendar.DAY_OF_MONTH)));

                Main.pojazdWybrany(pojazdNaTrasie);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,e.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();

            }
        }


    }

    public boolean validateForms(){
        if(dateChooserPanel.getSelectedDate() == null){
            JOptionPane.showMessageDialog(this,"Proszę wybrać datę","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(pojazdList.getSelectedValuesList().size() == 0){
            JOptionPane.showMessageDialog(this,"Nie wybrano żadnego pojazdu","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(LocalDate.now().isAfter(LocalDate.of(dateChooserPanel.getSelectedDate().get(Calendar.YEAR),dateChooserPanel.getSelectedDate().get(Calendar.MONTH)+1,dateChooserPanel.getSelectedDate().get(Calendar.DAY_OF_MONTH)))){
            JOptionPane.showMessageDialog(this,"Proszę wybrać poprawną datę","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
