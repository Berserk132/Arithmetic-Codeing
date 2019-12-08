import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.out;

public class GUI extends Container {
    private JTextField character;
    private JTextField probability;
    private JButton insertChar;
    private JPanel ArithmeticCoding;
    private JButton saveTable;
    private JTextArea textArea1;
    private JButton compressButton;
    private JTextArea textArea2;
    private JTextField seq;
    private JTextField numOfSymbols;
    Arithmetic call = null;
    ArrayList<Char> probTable = new ArrayList<>();

    public GUI() {
        insertChar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!character.getText().trim().isEmpty() && !probability.getText().trim().isEmpty()) {
                    String chr = character.getText();
                    double prop = Double.parseDouble(probability.getText());

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter("C:\\Users\\ahmed\\Desktop\\ArithmeticCoding\\seqInput.txt",true));

                        if (character.getText().equals("space")){

                            writer.append(" ");
                            writer.newLine();
                            writer.append(String.valueOf(prop));
                        }
                        else {
                            writer.append(chr);
                            writer.newLine();
                            writer.append(String.valueOf(prop));
                            writer.newLine();
                            writer.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    out.println(chr);
                    out.println(prop);
                }
            }
        });
        saveTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent m) {


                try {
                    call = new Arithmetic();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File file = new File("seqInput.txt");
                Scanner input = null;
                try {
                    input = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                input.useLocale( Locale.US );



                try {
                    probTable = call.takeProbabilityInput(file);
                    out.println(probTable);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    call.print(probTable);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textArea1.setText(probTable.toString());
            }
        });
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sequence = seq.getText();
                double result1 = 0.0;
                String result2,result3 = null;

                int numOfSym = Integer.parseInt(numOfSymbols.getText());
                try {
                    result1 = call.compress(sequence,probTable);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    call.writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                result2 = call.decompress(probTable, result1, numOfSym);
                try {
                    result3 = call.generateCode(call.lower,call.higher);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    call.writer.write(result3);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    call.writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    call.writer.write(result2+" ");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    call.writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }



                try {
                       call.writer.close();
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("answer.txt"));
                    StringBuilder content = new StringBuilder();
                    String line = "";
                    while (( line = reader.readLine()) != null) {

                        content.append(line + "\n");
                        out.println(content);
                    }

                    String con = content.toString();
                    textArea2.setText(con);

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }



        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arithmetic Coding");
        frame.setContentPane(new GUI().ArithmeticCoding);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(new Dimension(1200,600));
        frame.setVisible(true);


    }


}
