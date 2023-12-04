import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;


public class UI extends JPanel implements Runnable {
    int pixel; // TO CHANGE CELL SIZE
    int delay; // delay within the run method
    volatile boolean state = false; //false = idle, true = start
    KeyListener keyL = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(keyCode == KeyEvent.VK_SPACE) {
                state = !state;
            } else if (keyCode == KeyEvent.VK_R) {
                for(int i=0; i<width/pixel; i++) {
                    for(int j=0; j<height/pixel; j++) {
                        map[i][j].alive = false;
                    }
                }
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    };
    MouseAdapter mouseA = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            map[mouseX/pixel][mouseY/pixel].alive = true;
            repaint();
        }
    };
    MouseMotionListener mouseL = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            map[mouseX/pixel][mouseY/pixel].alive = true;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //does nothing
        }
    };

    int width, height;

    Tile[][] map;
    Tile[][] tempMap;
    Thread thread;

    Color backgroundGray = new Color(45, 52, 54);


    public UI(int width, int height, int pixel, int delay) {

        this.width = width;
        this.height = height;
        this.pixel = pixel;
        this.delay = delay;

        addKeyListener(keyL);
        setFocusable(true);
        addMouseListener(mouseA);
        addMouseMotionListener(mouseL);

        setBackground(backgroundGray);
        setup();
        startThread();
    }

    public void setup() {
        map = new Tile[width/pixel][height/pixel];
        tempMap = new Tile[width/pixel][height/pixel];

        for(int i=0; i<width/pixel; i++) {
            for(int j=0; j<height/pixel; j++) {
                map[i][j] = new Tile();
            }
        }

        for(int i=0; i<width/pixel; i++) {
            for(int j=0; j<height/pixel; j++) {
                tempMap[i][j] = new Tile();
            }
        }

    }

    public void update() {
        // Create a temporary map to store the new state
        Tile[][] newMap = new Tile[width / pixel][height / pixel];

        for (int i = 0; i < width / pixel; i++) {
            for (int j = 0; j < height / pixel; j++) {
                int liveNeighbors = countLiveNeighbors(i, j); // Count live neighbors
                newMap[i][j] = new Tile(); // Create a new Tile in the temporary map

                if (map[i][j].alive) {
                    // If the cell is alive
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newMap[i][j].alive = false; // Dies due to underpopulation or overpopulation
                    } else {
                        newMap[i][j].alive = true; // Survives
                    }
                } else {
                    // If the cell is dead
                    if (liveNeighbors == 3) {
                        newMap[i][j].alive = true; // Reproduction
                    } else {
                        newMap[i][j].alive = false; // Stays dead
                    }
                }
            }
        }

        // Update the map with the new state
        map = newMap;
    }

    private int countLiveNeighbors(int x, int y) {
        int liveNeighbors = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue; // Skip the cell itself
                }
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < width / pixel && ny >= 0 && ny < height / pixel) {
                    if (map[nx][ny].alive) {
                        liveNeighbors++;
                    }
                }
            }
        }
        return liveNeighbors;
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        for(int i=0; i<width/pixel; i++) {
            for(int j=0; j<height/pixel; j++) {
                if(map[i][j].alive) {
                    g.setColor(Color.WHITE);
                    g.fillRect(i*pixel, j*pixel, pixel, pixel);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(i*pixel, j*pixel, pixel, pixel);
                }
            }
        }


        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        g.drawString("Space: Play", 10, 20);
        g.drawString("R: Clear", 10, 40);

        if(!state) {
            g.drawString("Pause", 10, 60);
        } else {
            g.drawString("Play", 10, 60);
        }
    }



    @Override
    public void run() {
        while(true) {
            if(state) {
                try {
                    //wait every few miliseconds, then update and repaint
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    System.out.println("interrupred exception idk lmao");
                }
                update();
                repaint();
            }
        }
    }


}