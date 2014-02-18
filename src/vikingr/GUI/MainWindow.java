/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vikingr.GUI;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import vikingr.Generator;

/**
 *
 * @author saljack
 */
public class MainWindow extends JFrame {
    
    private JButton btnGenerate;
    private JSpinner rounds;
    private JSpinner players;
    private JFileChooser fileSaver;
    private JButton saveFile;
    private boolean isSetFile;
    
    public MainWindow() throws HeadlessException {
        super("Vikingr");
        setSize(300, 200);
        isSetFile = false;
        initGUI();
    }
    
    private void initGUI() {
        setLayout(new GridLayout(4, 2));
        fileSaver = new JFileChooser();
        fileSaver.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        add(new JLabel("Počet hráčů"));
        players = new JSpinner(new SpinnerNumberModel(40, 4, 120, 4));
        add(players);
        
        add(new JLabel("Počet kol"));
        rounds = new JSpinner(new SpinnerNumberModel(8, 1, 100, 1));
        add(rounds);
        
        add(new JLabel(""));
        saveFile = new JButton("Select save file");
        saveFile.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret = fileSaver.showSaveDialog(MainWindow.this);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    System.out.println(fileSaver.getSelectedFile().getPath());
                    isSetFile = true;
                } else {
                    isSetFile = false;
                }
            }
        });
        add(saveFile);
        
        add(new JLabel(""));
        btnGenerate = new JButton("Generuj");
        btnGenerate.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSetFile) {
                    generateAndSave();
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this, "Nenastaven soubor pro ulozeni.\nNastavte soubor pro ulozeni",
                            "Nenastaven soubor pro ulozeni", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        add(btnGenerate);
        
    }
    
    private void generateAndSave() {
        Generator gen = new Generator((int) players.getValue());
        try{
        boolean ok = gen.generateAndWrite(fileSaver.getSelectedFile().getPath(), (int) rounds.getValue());
        if(ok){
            JOptionPane.showMessageDialog(MainWindow.this, "Vse bylo uspesne vygenerovano",
                            "Uspesne vygenerovano", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(MainWindow.this, "Nastala nejaka chyba nejspis je prilis mnoho kol na maly pocet hracu.",
                            "Chyba pri generovani", JOptionPane.INFORMATION_MESSAGE);
        }
        }catch(IOException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainWindow.this, "Nastala chyba se zapisem do souboru: \n"+fileSaver.getSelectedFile().getPath()+"\nZkuste zvolit jiny soubor.",
                            "Problem se zapisem souboru", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
}
