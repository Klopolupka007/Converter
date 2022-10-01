import javax.swing.*;
import java.awt.*;

public class App extends JPanel {
    App(){
        setSize(1080,720);
        setBackground(new Color(131, 179, 179));
        add(new AddFile());
        add(new AddField());
        setLayout(null);
        setVisible(true);
    }
}

class AddFile extends JButton{
    AddFile(){
        setSize(100, 25);
        setLocation(10, 10);
        setText("Добавить");
        setForeground(new Color(190, 220, 210));
        setOpaque(true);

        setBackground(new Color(50, 90, 100));

        setFocusPainted(false);

        setVisible(true);
    }

}

class AddField extends JTextField{
    AddField(){
        setSize(300, 25);
        setLocation(130, 10);
        setBackground(new Color(190, 220, 210));
    }
}
