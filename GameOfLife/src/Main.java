import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Main {

    JFrame mainFrame = new JFrame();
    JPanel mainPanel = new JPanel(new GridBagLayout());

    Font font = new Font("Arial", Font.BOLD, 16);
    JLabel widthLabel = new JLabel("Width");
    JLabel heightLabel = new JLabel("Height");
    JLabel pixelLabel = new JLabel("Pixel Size");
    JLabel delayLabel = new JLabel("Delay (MS)");
    JLabel errorLabel = new JLabel("");


    JTextField widthField = new JTextField();
    JTextField heightField = new JTextField();
    JTextField pixelField = new JTextField();
    JTextField delayField = new JTextField();

    JButton startButton = new JButton("Start");


    public Main() {

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400,400);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setResizable(false);
        mainFrame.setTitle("Conway's Game Of Life - © Sam");

        mainPanel.setBackground(Color.BLACK);


        widthLabel.setForeground(Color.WHITE);
        widthLabel.setFont(font);
        heightLabel.setForeground(Color.WHITE);
        heightLabel.setFont(font);
        pixelLabel.setForeground(Color.WHITE);
        pixelLabel.setFont(font);
        delayLabel.setForeground(Color.WHITE);
        delayLabel.setFont(font);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(font);


        widthField.setPreferredSize(new Dimension(80, 30));
        widthField.setText("800");
        heightField.setPreferredSize(new Dimension(80, 30));
        heightField.setText("800");
        pixelField.setPreferredSize(new Dimension(80, 30));
        pixelField.setText("30");
        startButton.setPreferredSize(new Dimension(225, 30));
        delayField.setPreferredSize(new Dimension(80, 30));
        delayField.setText("100");



        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 15, 15, 15); // Set insets for spacing


        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(widthLabel,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(heightLabel,gbc);


        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(widthField,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        mainPanel.add(heightField,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(pixelLabel,gbc);

        gbc.gridx = 2;
        gbc.gridy  = 3;
        mainPanel.add(delayLabel,gbc);


        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(pixelField,gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        mainPanel.add(delayField, gbc);






        GridBagConstraints gbcButton = new GridBagConstraints(); // Create another new GridBagConstraints
        gbcButton.gridy = 5;
        gbcButton.gridwidth = 4; // Span 3 columns
        mainPanel.add(startButton,gbcButton);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(errorLabel, gbc);


        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String width = widthField.getText();
                String height = heightField.getText();
                String pixel = pixelField.getText();
                String delay = delayField.getText();

                if(width.matches("\\d+") && height.matches("\\d+") && pixel.matches("\\d+")) {
                    errorLabel.setText("");
                    int widthConvert = Integer.parseInt(width);
                    int heightConvert = Integer.parseInt(height);
                    int pixelConvert = Integer.parseInt(pixel);
                    int delayConvert = Integer.parseInt(delay);

                    Frame frame = new Frame(widthConvert, heightConvert, pixelConvert, delayConvert);

                } else {
                    errorLabel.setText("Error only enter numbers");
                }

            }

        });
    }

    public static void main(String[] args) {
        Main main = new Main();
    }


}
