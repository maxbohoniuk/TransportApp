package Views;

import Models.Przystanek;
import Models.PrzystanekNaTrasie;
import Models.Trasa;
import Services.Main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TrasaGUI extends JFrame implements Runnable {

    JPanel upPanel,leftPanel,mainPanel;
    JLabel usernameLbl,przystankLbl;
    public JButton logoutBtn,addTrasaBtn,anulujBtn,wybierzBtn;
    JTextField searchField;
    JList<Trasa> trasaList;
    List<Trasa> trasy;
    JList<Przystanek> przystankiList;
    DefaultListModel<Trasa> model;
    DefaultListModel<Przystanek> przystanekModel;

    public Trasa wybranaTrasa;

    public TrasaGUI(List<Trasa> trasy){
        this.trasy = trasy;
        if(this.trasy == null){
            this.trasy = new ArrayList<>();
        }
    }


    @Override
    public void run() {
        setVisible(true);
        setTitle("Wybierz trasę");
        setPreferredSize(new Dimension(730,400));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocation(200,120);
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

        JLabel trasyLbl = new JLabel("Dostępne trasy");
        trasyLbl.setFont(new Font(null,Font.PLAIN,30));
        trasyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        trasaList = new JList<Trasa>();
        model = new DefaultListModel<>();
        trasy.stream().forEach(trasa -> model.addElement(trasa));
        trasaList.setModel(model);
        trasaList.setPreferredSize(new Dimension(50,80));
        trasaList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        trasaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        trasaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    if(trasaList.getSelectedValue() != null){
                        SwingUtilities.invokeLater(new TrasaInfoGUI(trasaList.getSelectedValue()));
                    }
                }
            }
        });




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
                        wyszukaj((DefaultListModel<Trasa>) (trasaList.getModel()),searchField.getText());
                    }
                }
                else{
                    model.removeAllElements();
                    trasy.stream().forEach(trasa -> model.addElement(trasa));
                }


            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(!searchField.getText().isEmpty()){
                    if(!searchField.getText().equals("Wyszukaj")){
                        wyszukaj((DefaultListModel<Trasa>) trasaList.getModel(),searchField.getText());
                    }
                }
                else{
                    model.removeAllElements();
                    trasy.stream().forEach(trasa -> model.addElement(trasa));
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



        addTrasaBtn = new JButton("Dodaj nową trasę");
        addTrasaBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTrasaBtn.addActionListener(l->{
            dodajClicked();
        });





        leftPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(leftPanel,BoxLayout.Y_AXIS);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        leftPanel.setLayout(boxLayout);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setOpaque(true);
        leftPanel.add(trasyLbl);
        leftPanel.add(searchField);
        leftPanel.add(Box.createRigidArea(new Dimension(0,20)));
        JScrollPane trasyScrollPane = new JScrollPane(trasaList);
        trasyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(trasyScrollPane);
        leftPanel.add(addTrasaBtn);



        //Center panel

        przystankLbl = new JLabel("Przystanki na trasie");
        przystankLbl.setFont(new Font(null,Font.PLAIN,20));
        przystankLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        przystankiList = new JList<Przystanek>();
        przystanekModel = new DefaultListModel<>();
        przystankiList.setModel(przystanekModel);
        przystankiList.setPreferredSize(new Dimension(50,80));
        przystankiList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        przystankiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        trasaList.addListSelectionListener(e -> {
            przystanekModel.clear();
            if(trasaList.getSelectedValue() != null) {
                List<PrzystanekNaTrasie> tmp = trasaList.getSelectedValue().getPrzystanki();
                for (PrzystanekNaTrasie p : tmp) {
                    przystanekModel.addElement(p.getPrzystanek());
                }
            }
        });



        JPanel centerPanel = new JPanel();
        BoxLayout centerCenterBoxLayout = new BoxLayout(centerPanel,BoxLayout.Y_AXIS);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,25,0,15));
        centerPanel.setLayout(centerCenterBoxLayout);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);

        centerPanel.add(przystankLbl);
        JScrollPane przystanekScrollPane = new JScrollPane(przystankiList);
        trasyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(przystanekScrollPane);







        //down panel
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });
        wybierzBtn = new JButton("Wybierz");
        wybierzBtn.addActionListener(l->{
            wybierzClicked();
        });
        downPanel.add(anulujBtn);
        downPanel.add(wybierzBtn);



        //Services.Main center panel
        mainPanel = new JPanel();
        BoxLayout boxLayout2 = new BoxLayout(mainPanel,BoxLayout.X_AXIS);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        mainPanel.setLayout(boxLayout2);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setOpaque(true);

        mainPanel.add(leftPanel);
        mainPanel.add(centerPanel);



        this.add(mainPanel,BorderLayout.CENTER);
        this.add(downPanel,BorderLayout.SOUTH);



        this.pack();
    }

    public void wyszukaj(DefaultListModel<Trasa> defaultListModel,String target){

        for (Trasa e : trasy) {
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
        if(trasaList.isSelectionEmpty()){
            JOptionPane.showMessageDialog(this,"Nie wybrano żadnej trasy","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else{
            wybranaTrasa = trasaList.getSelectedValuesList().get(0);
            Main.trasaWybrana(wybranaTrasa);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void dodajClicked(){
        setEnabled(false);
        AddTrasaGUI addTrasaGUI = new AddTrasaGUI(this);
        SwingUtilities.invokeLater(addTrasaGUI);
    }

    public void addTrasaGUIFinished(){
        setEnabled(true);
        trasy = Trasa.trasy;
        model.removeAllElements();
        trasy.stream().forEach(t -> model.addElement(t));
    }



}
