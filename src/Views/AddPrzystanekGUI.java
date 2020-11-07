package Views;

import Models.Przystanek;
import Services.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class AddPrzystanekGUI extends JFrame implements Runnable{
    JPanel upPanel;
    JLabel usernameLbl,nazwaLbl,adresLbl,nrLbl;
    JButton logoutBtn,anulujBtn,zatwierdzBtn;
    JTextField nazwaField,adresField,nrField;
    JCheckBox autoGenCheckBox;

    PrzystanekGUI parent;

    public AddPrzystanekGUI(PrzystanekGUI parent){
        this.parent = parent;
    }




    @Override
    public void run() {
        setVisible(true);
        setTitle("Dodaj przystanek");
        setPreferredSize(new Dimension(300,370));
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


        nazwaLbl = new JLabel("Nazwa");
        nazwaLbl.setFont(new Font(null,Font.PLAIN,13));


        adresLbl = new JLabel("Adres");
        adresLbl.setFont(new Font(null,Font.PLAIN,13));

        nrLbl = new JLabel("Numer przystanku");
        nrLbl.setFont(new Font(null,Font.PLAIN,13));

        autoGenCheckBox = new JCheckBox();
        autoGenCheckBox.setText("Wygeneruj\nautomatycznie");

        nazwaField = new JTextField();

        adresField = new JTextField();

        nrField = new JTextField();



        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7,2));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(nazwaLbl);
        centerPanel.add(nazwaField);
        centerPanel.add(adresLbl);
        centerPanel.add(adresField);
        centerPanel.add(nrLbl);
        JPanel autoGenPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(autoGenPanel,BoxLayout.X_AXIS);
        autoGenPanel.setLayout(boxLayout);
        autoGenPanel.setBackground(Color.WHITE);
        autoGenPanel.add(nrField);
        autoGenPanel.add(autoGenCheckBox);

        centerPanel.add(autoGenPanel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));



        //down panel
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            parent.setEnabled(true);
        });
        zatwierdzBtn = new JButton("Zatwierdź");
        zatwierdzBtn.addActionListener(l -> zatwierdzClicked());
        downPanel.add(anulujBtn);
        downPanel.add(zatwierdzBtn);


        this.add(downPanel,BorderLayout.SOUTH);
        this.add(centerPanel,BorderLayout.CENTER);







        this.pack();
    }

    public void zatwierdzClicked(){
        if(validateForms()){
            int n;
            if(autoGenCheckBox.isSelected()) {
                n = randNumerPrzystanku();
            }
            else{
                n = Integer.parseInt(nrField.getText());
            }
            try {
                Przystanek przystanek = new Przystanek(nazwaField.getText(), adresField.getText(), n);
                Main.dbService.addPrzystanekToDb(przystanek);
                parent.addPrzystanekGUIFinished();
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public boolean validateForms(){
        if(nazwaField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Proszę wprowadzić nazwę przystanku","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(adresField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Proszę wprowadzić adres","Błąd",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!autoGenCheckBox.isSelected()){
            if(nrField.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Proszę wprowadzić numer przystanku","Błąd",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else{
                try{
                    Integer.parseInt(nrField.getText());
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this,"Proszę wprowadzić poprawny numer przystanku","Błąd",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    public int randNumerPrzystanku(){
        int res = (int)(Math.random()*16);
        while (Przystanek.numery.containsKey(res)){
            res = (int)(Math.random()*16);
        }
        return res;
    }


}
