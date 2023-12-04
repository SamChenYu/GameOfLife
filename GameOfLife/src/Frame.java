import javax.swing.JFrame;


public class Frame extends JFrame {

    UI ui;

    public Frame(int width, int height, int pixel, int delay) {

        this.setSize(width,height);
        ui = new UI(width, height, pixel, delay);
        setSize(width,height);
        setLocationRelativeTo(null);
        setVisible(true);
        add(ui);
        setResizable(false);




    }

}