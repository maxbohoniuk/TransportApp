package Views;

import Models.Przystanek;
import Models.PrzystanekNaTrasie;
import Models.Trasa;
import Services.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class AddTrasaGUI extends JFrame implements Runnable{
    JPanel upPanel,centerPanel;
    JLabel usernameLbl,kierunekLbl,kierunekZwrLbl,nrTrasyLbl,przystankLbl,kolejnoscLbl;
    JButton logoutBtn,anulujBtn,zatwierdzBtn,addBtn,upBtn,downBtn;
    JTextField kierunekField,kierunekZwrField,nrTrasyField;
    JCheckBox autoGenCheckBox;
    JList<Przystanek> przystankiList;
    List<Przystanek> przystanki = new ArrayList<>();
    DefaultListModel<Przystanek> model;

    TrasaGUI parent;

    public AddTrasaGUI(TrasaGUI parent){
        this.parent = parent;
    }


    @Override
    public void run() {
        setVisible(true);
        setTitle("Dodaj trasę");
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


        kierunekLbl = new JLabel("Kierunek");
        kierunekLbl.setFont(new Font(null,Font.PLAIN,13));


        kierunekZwrLbl = new JLabel("Kierunek zwrotny");
        kierunekZwrLbl.setFont(new Font(null,Font.PLAIN,13));

        nrTrasyLbl = new JLabel("Numer trasy");
        nrTrasyLbl.setFont(new Font(null,Font.PLAIN,13));

        autoGenCheckBox = new JCheckBox();
        autoGenCheckBox.setText("Wygeneruj\nautomatycznie");

        kierunekField = new JTextField();
        kierunekField.setPreferredSize(new Dimension(200,35));
        kierunekField.setMinimumSize(kierunekField.getPreferredSize());
        kierunekField.setMaximumSize(kierunekField.getPreferredSize());
        kierunekLbl.setLabelFor(kierunekField);


        kierunekZwrField = new JTextField();
        kierunekZwrField.setPreferredSize(new Dimension(200,35));
        kierunekZwrField.setMinimumSize(kierunekZwrField.getPreferredSize());
        kierunekZwrField.setMaximumSize(kierunekZwrField.getPreferredSize());

        nrTrasyField = new JTextField();
        nrTrasyField.setPreferredSize(new Dimension(100,35));
        nrTrasyField.setMinimumSize(nrTrasyField.getPreferredSize());
        nrTrasyField.setMaximumSize(nrTrasyField.getPreferredSize());


        JPanel leftCenterPanel = new JPanel();
        leftCenterPanel.setLayout(new GridLayout(7,2));
        leftCenterPanel.setBackground(Color.WHITE);

        leftCenterPanel.add(kierunekLbl);
        leftCenterPanel.add(kierunekField,Box.LEFT_ALIGNMENT);
        leftCenterPanel.add(kierunekZwrLbl);
        leftCenterPanel.add(kierunekZwrField,Box.LEFT_ALIGNMENT);
        leftCenterPanel.add(nrTrasyLbl);
        JPanel autoGenPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,7));
        autoGenPanel.setBackground(Color.WHITE);
        autoGenPanel.add(nrTrasyField);
        autoGenPanel.add(autoGenCheckBox);

        leftCenterPanel.add(autoGenPanel);
        leftCenterPanel.setBorder(BorderFactory.createEmptyBorder(0,0,40,0));

        //Center panel

        przystankLbl = new JLabel("Przystanki na trasie");
        przystankLbl.setFont(new Font(null,Font.PLAIN,20));
        przystankLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        przystankiList = new JList<Przystanek>();
        model = new DefaultListModel<>();
        przystankiList.setModel(model);
        przystankiList.setPreferredSize(new Dimension(50,80));
        przystankiList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        przystankiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addBtn = new JButton("Dodaj");
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.addActionListener(l->dodajClicked());

        JPanel centerCenterPanel = new JPanel();
        BoxLayout centerCenterBoxLayout = new BoxLayout(centerCenterPanel,BoxLayout.Y_AXIS);
        centerCenterPanel.setBorder(BorderFactory.createEmptyBorder(0,25,0,15));
        centerCenterPanel.setLayout(centerCenterBoxLayout);
        centerCenterPanel.setBackground(Color.WHITE);
        centerCenterPanel.setOpaque(true);

        centerCenterPanel.add(przystankLbl);
        JScrollPane trasyScrollPane = new JScrollPane(przystankiList);
        trasyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerCenterPanel.add(trasyScrollPane);
        centerCenterPanel.add(addBtn);

        //Right panel

        kolejnoscLbl = new JLabel("Zmień kolejność");
        kolejnoscLbl.setFont(new Font(null,Font.PLAIN,15));
        kolejnoscLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        upBtn = new JButton("Wgórę");
        upBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        upBtn.addActionListener(l->{
            int indx = przystankiList.getSelectedIndex();
            if(indx > 0) {
                Przystanek p = przystanki.get(indx);
                przystanki.remove(p);
                przystanki.add(indx-1,p);

                model.removeAllElements();
                przystanki.stream().forEach(przystanek -> model.addElement(przystanek));

                przystankiList.setSelectedIndex(indx-1);
            }
        });

        downBtn = new JButton("Wdół");
        downBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        downBtn.addActionListener(l->{
            int indx = przystankiList.getSelectedIndex();
            if(indx < przystanki.size()-1) {
                Przystanek p = przystanki.get(indx);
                przystanki.remove(p);
                przystanki.add(indx+1,p);

                model.removeAllElements();
                przystanki.stream().forEach(przystanek -> model.addElement(przystanek));

                przystankiList.setSelectedIndex(indx+1);
            }
        });

        JPanel rightCenterPanel = new JPanel();
        BoxLayout rightCenterBoxLayout = new BoxLayout(rightCenterPanel,BoxLayout.Y_AXIS);
        rightCenterPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,15));
        rightCenterPanel.setLayout(rightCenterBoxLayout);
        rightCenterPanel.setBackground(Color.WHITE);
        rightCenterPanel.setOpaque(true);

        rightCenterPanel.add(Box.createRigidArea(new Dimension(50,50)));
        rightCenterPanel.add(kolejnoscLbl);
        rightCenterPanel.add(upBtn);
        rightCenterPanel.add(downBtn);
        rightCenterPanel.add(Box.createRigidArea(new Dimension(50,50)));







        //down panel
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            parent.setEnabled(true);
        });
        zatwierdzBtn = new JButton("Zatwierdź");
        zatwierdzBtn.addActionListener(l->zatwierdzClicked());
        downPanel.add(anulujBtn);
        downPanel.add(zatwierdzBtn);



        //Services.Main center panel
        centerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerPanel,BoxLayout.X_AXIS);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        centerPanel.setLayout(boxLayout);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);

        centerPanel.add(leftCenterPanel);
        centerPanel.add(centerCenterPanel);
        centerPanel.add(rightCenterPanel);



        this.add(centerPanel,BorderLayout.CENTER);
        this.add(downPanel,BorderLayout.SOUTH);







        this.pack();
    }

    public void zatwierdzClicked(){

        if(validateForms()){
            int n;
            if(autoGenCheckBox.isSelected()) {
                n = randNumerTrasy();
            }
            else{
                n = Integer.parseInt(nrTrasyField.getText());
            }
            try {
                Trasa trasa = new Trasa(kierunekField.getText(),kierunekZwrField.getText(),n,false);
                for(Przystanek przystanek:przystanki){
                    Main.dbService.addPrzystanekNaTrasieToDb(new PrzystanekNaTrasie(przystanek,trasa));
                }
                Main.dbService.addTrasaToDb(trasa);
                parent.addTrasaGUIFinished();
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

    }

    public void dodajClicked(){
        PrzystanekGUI przystanekGUI = new PrzystanekGUI(Przystanek.przystanki,this);
        SwingUtilities.invokeLater(przystanekGUI);
        setEnabled(false);
    }

    public void przystankiWybrane(List<Przystanek> przystanki){
        this.przystanki = przystanki;

        setEnabled(true);
        przystanki.stream().forEach(p -> model.addElement(p));


    }

    public boolean validateForms(){
        if(kierunekField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Proszę wprowadzić kierunek trasy","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(kierunekZwrField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Proszę wprowadzić kierunek zwrotny","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!autoGenCheckBox.isSelected()){
            if(nrTrasyField.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Proszę wprowadzić numer trasy","Błąd",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else{
                try{
                    Integer.parseInt(nrTrasyField.getText());
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this,"Proszę wprowadzić poprawny numer trasy","Błąd",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        if(przystanki.size() < 5){
            JOptionPane.showMessageDialog(this,"Wybierz co najmniej 5 przystanków","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public int randNumerTrasy(){
        int res = (int)(Math.random()*1000);
        while (Trasa.numery.containsKey(res)){
            res = (int)(Math.random()*1000);
        }
        return res;
    }


}
