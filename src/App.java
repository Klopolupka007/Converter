import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.Customizer;
import java.io.File;
import java.io.IOException;

public class App extends JPanel {

    ImageIcon picture = new ImageIcon(new ImageIcon("src/StandartImage.png").getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT));
    JLabel img;
    App(){
        setSize(1080,720);
        setBackground(new Color(191, 80, 60));
        View();

        add(new AddFile(img, picture));        add(new pan());
        setLayout(null);
        setVisible(true);
    }


    //Основной дизайн и вид страницы приложения
    void View(){
        img = new JLabel(picture);
        img.setBackground(Color.gray);
        img.setBounds(50, 50, 600, 400);
        add(img);
    }

}

class pan extends JPanel{
    boolean state = true;
    pan(){
        setSize(1080, 220);
        setLocation(0, 500);
        setVisible(true);
    }
    public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(191, 80, 60),
                    0, 220, new Color(161, 50, 30));
            g2.setPaint(gradientPaint);
            g2.fill(new Rectangle2D.Double(0, 0, 1080, 220));
    }
}

class AddFile extends JButton implements ActionListener{

    JFileChooser fc;
    File SelFile;
    ImageIcon picture;
    JLabel img;

    AddFile(JLabel img, ImageIcon picture){

        this.img = img;
        this.picture = picture;
        //Создаем объект класса библиотеки пользовательских шрифтов
        customFontColor fontsLib = new customFontColor();

        setSize(150, 40);
        setLocation(275, 470);
        setText("Выберите файл");

        setFont(fontsLib.FiraSans_Bold);
        setForeground(new Color(240, 240, 240));
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        setBackground(new Color(166, 30, 30));

        fc = new JFileChooser();
        setFocusPainted(false);
        addActionListener(this);
    }

    //Метод, который возвращает путь к выбранному файлу
    String Select(){
        SelFile = fc.getSelectedFile();
        ImageIcon pic = new ImageIcon(new ImageIcon(SelFile.getPath()).getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT));
        img.setIcon(pic);
        img.setLocation(50, 50);

        return SelFile.getPath();
    }

    //Метод выбора файла из файловой локальной системы
    @Override
    public void actionPerformed(ActionEvent e) {
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fc.showOpenDialog(AddFile.this);
        System.out.println(Select());
    }
}

//Класс пользовательских шрифтов
class customFontColor{
    Font FiraSans_Black, FiraSans_BlackItalic,
            FiraSans_Bold, FiraSans_BoldItalic,
            FiraSans_ExtraBold, FiraSans_ExtraBoldItalic, FiraSans_ExtraLight, FiraSans_ExtraLightItalic,
            FiraSans_Italic, FiraSans_Light, FiraSans_LightItalic,
            FiraSans_Medium, FiraSans_MediumItalic,
            FiraSans_Regular, FiraSans_SemiBold, FiraSans_SemiBoldItalic,
            FiraSans_Thin, FiraSans_ThinItalic;
    customFontColor(){
        try {
            FiraSans_Bold = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/FiraSans-Bold.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FiraSans_Bold);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }
}
