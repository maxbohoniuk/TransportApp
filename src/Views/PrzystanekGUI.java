package Views;

import Models.Przystanek;
import Models.Trasa;
import Services.Main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class PrzystanekGUI extends JFrame implements Runnable{
    JPanel upPanel,centerPanel;
    JLabel usernameLbl;
    JButton logoutBtn,addPrzystanekBtn,anulujBtn,wybierzBtn;
    JTextField searchField;
    JList<Przystanek> przystanekList;
    List<Przystanek> przystanki;

    DefaultListModel<Przystanek> model;

    AddTrasaGUI parent;

    public PrzystanekGUI(List<Przystanek> przystanki,AddTrasaGUI parent){
        this.parent = parent;

        this.przystanki = przystanki;
        if(this.przystanki == null){
            this.przystanki = new ArrayList<>();
        }
    }

    @Override
    public void run() {
        setVisible(true);
        setTitle("Wybierz przystanek");
        setPreferredSize(new Dimension(330,400));
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

        JLabel trasyLbl = new JLabel("Dostępne przystanki");
        trasyLbl.setFont(new Font(null,Font.PLAIN,30));
        trasyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        przystanekList = new JList<Przystanek>();
        model = new DefaultListModel<>();
        przystanki.stream().forEach(trasa -> model.addElement(trasa));
        przystanekList.setModel(model);
        przystanekList.setPreferredSize(new Dimension(50,80));
        przystanekList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        przystanekList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


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
                        wyszukaj((DefaultListModel<Przystanek>) (przystanekList.getModel()),searchField.getText());
                    }
                }
                else{
                    model.removeAllElements();
                    przystanki.stream().forEach(p -> model.addElement(p));
                }


            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(!searchField.getText().isEmpty()){
                    if(!searchField.getText().equals("Wyszukaj")){
                        wyszukaj((DefaultListModel<Przystanek>) przystanekList.getModel(),searchField.getText());
                    }
                }
                else{
                    model.removeAllElements();
                    przystanki.stream().forEach(p -> model.addElement(p));
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



        addPrzystanekBtn = new JButton("Dodaj nowy przystanek");
        addPrzystanekBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addPrzystanekBtn.addActionListener(l ->dodajClicked() );

        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            parent.setEnabled(true);
        });
        wybierzBtn = new JButton("Wybierz");
        wybierzBtn.addActionListener(l->wybierzClicked());
        downPanel.add(anulujBtn);
        downPanel.add(wybierzBtn);




        centerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerPanel,BoxLayout.Y_AXIS);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        centerPanel.setLayout(boxLayout);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);
        centerPanel.add(trasyLbl);
        centerPanel.add(searchField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        JScrollPane scrollPane = new JScrollPane(przystanekList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollPane);
        centerPanel.add(addPrzystanekBtn);




        this.add(centerPanel,BorderLayout.CENTER);
        this.add(downPanel,BorderLayout.SOUTH);






        this.pack();
    }

    public void wyszukaj(DefaultListModel<Przystanek> defaultListModel,String target){

        for (Przystanek e : przystanki) {
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
        if(przystanekList.isSelectionEmpty()){
            JOptionPane.showMessageDialog(this,"Nie wybrano żadnego przystanku","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else{
            if(przystanekList.getSelectedValuesList().size() < 5){
                JOptionPane.showMessageDialog(this,"Wybierz co najmniej 5 przystanków","Błąd",JOptionPane.ERROR_MESSAGE);
            }
            else{
                parent.przystankiWybrane(przystanekList.getSelectedValuesList());
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }


    }

    public void dodajClicked(){
        setEnabled(false);
        AddPrzystanekGUI addPrzystanekGUI = new AddPrzystanekGUI(this);
        SwingUtilities.invokeLater(addPrzystanekGUI);
    }

    public void addPrzystanekGUIFinished(){
        setEnabled(true);
        przystanki = Przystanek.przystanki;
        model.removeAllElements();
        przystanki.stream().forEach(p->model.addElement(p));
    }
}
