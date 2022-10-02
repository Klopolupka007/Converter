import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    Main(){
        setSize (1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new App());
        ImageIcon img = new ImageIcon("src\\Logo.png");
        setIconImage(img.getImage());
        setTitle("Конвертер графических приложений");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}