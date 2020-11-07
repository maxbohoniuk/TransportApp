package Services;

import Models.Trasa;
import Views.TrasaGUI;

import javax.swing.*;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new TrasaGUI(new ArrayList<Trasa>()));

    }
}
