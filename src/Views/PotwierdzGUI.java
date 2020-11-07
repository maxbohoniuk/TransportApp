package Views;

import Models.Kierowca;
import Models.Pojazd;
import Models.PojazdNaTrasie;
import Models.Trasa;
import Services.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PotwierdzGUI extends JFrame implements Runnable{
    JPanel upPanel;
    JLabel usernameLbl;
    JButton logoutBtn,anulujBtn,zatwierdzBtn,zmienTrasaBtn,zmienPojazdBtn,zmienKierowcyBtn;
    Trasa trasa;
    PojazdNaTrasie pojazdNaTrasie;

    public PotwierdzGUI(Trasa trasa, PojazdNaTrasie pojazdNaTrasie){
        this.trasa = trasa;
        this.pojazdNaTrasie = pojazdNaTrasie;
    }


    @Override
    public void run() {
        setVisible(true);
        setTitle("Potwierdź przydzielanie");
        setPreferredSize(new Dimension(780,400));
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
        JLabel trasaLbl = new JLabel("Wybrana trasa");
        trasaLbl.setFont(new Font(null,Font.PLAIN,30));
        trasaLbl.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));

        JLabel trasaInfoLbl = new JLabel(trasa.toString());
        trasaInfoLbl.setFont(new Font(null,Font.PLAIN,15));

        zmienTrasaBtn = new JButton("Zmień");
        zmienTrasaBtn.addActionListener(l -> {
            SwingUtilities.invokeLater(new TrasaGUI(Trasa.trasy));
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });


        JLabel pojazdLbl = new JLabel("Wybrany pojazd");
        pojazdLbl.setFont(new Font(null,Font.PLAIN,30));
        pojazdLbl.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));

        JLabel pojazdInfoLbl = new JLabel(pojazdNaTrasie.getPojazd().toString());
        pojazdInfoLbl.setFont(new Font(null,Font.PLAIN,15));

        JLabel dataLbl = new JLabel(pojazdNaTrasie.getData()+"");
        dataLbl.setFont(new Font(null,Font.PLAIN,15));



        zmienPojazdBtn = new JButton("Zmień");
        zmienPojazdBtn.addActionListener(l -> {
            SwingUtilities.invokeLater(new PojazdGUI(Pojazd.pojazdy));
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });


        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,5));
        BoxLayout leftBoxLayout = new BoxLayout(leftPanel,BoxLayout.Y_AXIS);
        leftPanel.setLayout(leftBoxLayout);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setOpaque(true);

        leftPanel.add(trasaLbl);
        leftPanel.add(Box.createRigidArea(new Dimension(20,10)));
        leftPanel.add(trasaInfoLbl);
        leftPanel.add(Box.createRigidArea(new Dimension(20,20)));
        leftPanel.add(zmienTrasaBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(20,20)));

        leftPanel.add(pojazdLbl);
        leftPanel.add(Box.createRigidArea(new Dimension(20,20)));
        leftPanel.add(pojazdInfoLbl);
        leftPanel.add(Box.createRigidArea(new Dimension(20,20)));
        leftPanel.add(dataLbl);
        leftPanel.add(Box.createRigidArea(new Dimension(20,20)));
        leftPanel.add(zmienPojazdBtn);


        //Right panel

        JLabel kierowcyLbl = new JLabel("Wybrani kierowcy");
        kierowcyLbl.setFont(new Font(null,Font.PLAIN,30));
        kierowcyLbl.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));
        kierowcyLbl.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel kierowca1Lbl = new JLabel(pojazdNaTrasie.getKierowcy().get(0).toString());
        kierowca1Lbl.setFont(new Font(null,Font.PLAIN,15));

        JLabel kierowca2Lbl = new JLabel(pojazdNaTrasie.getKierowcy().get(1).toString());
        kierowca2Lbl.setFont(new Font(null,Font.PLAIN,15));


        zmienKierowcyBtn = new JButton("Zmień");
        zmienKierowcyBtn.addActionListener(l -> {
            SwingUtilities.invokeLater(new KierowcaGUI(Kierowca.kierowcy));
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });



        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        BoxLayout rightBoxLayout = new BoxLayout(rightPanel,BoxLayout.Y_AXIS);
        rightPanel.setLayout(rightBoxLayout);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setOpaque(true);

        rightPanel.add(kierowcyLbl);
        rightPanel.add(Box.createRigidArea(new Dimension(20,20)));
        rightPanel.add(kierowca1Lbl);
        rightPanel.add(Box.createRigidArea(new Dimension(20,20)));
        rightPanel.add(kierowca2Lbl);
        rightPanel.add(Box.createRigidArea(new Dimension(20,20)));
        rightPanel.add(zmienKierowcyBtn);




        //Down panel
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,1));
        downPanel.setBackground(Color.WHITE);
        anulujBtn = new JButton("Anuluj");
        anulujBtn.addActionListener(l -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });
        zatwierdzBtn = new JButton("Zatwierdź");
        zatwierdzBtn.addActionListener(l -> zatwierzClicked());



        downPanel.add(anulujBtn);
        downPanel.add(zatwierdzBtn);



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

    public void zatwierzClicked(){
        Main.potwierdzenieNadane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }


}
