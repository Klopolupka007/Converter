import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Customizer;
import java.io.File;
import java.io.IOException;

public class App extends JPanel {
    App(){
        setSize(1080,720);
        setBackground(new Color(244, 249, 244));
        add(new AddFile());
        setLayout(null);
        setVisible(true);
    }
}

class AddFile extends JButton implements ActionListener{

    JFileChooser fc;
    File SelFile;

    AddFile(){
        //Создаем объект класса библиотеки пользовательских шрифтов
        customFontColor fontsLib = new customFontColor();

        setSize(300, 60);
        setLocation(50, 50);
        setText("Выберите файл");

        setFont(fontsLib.FiraSans_Bold);
        setForeground(new Color(70, 70, 70));
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        setBackground(new Color(138, 174, 146));

        fc = new JFileChooser();
        setFocusPainted(false);
        addActionListener(this);
    }


    //Метод, который возвращает путь к выбранному файлу
    String Select(){
        SelFile = fc.getSelectedFile();
        setVisible(false);
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
            FiraSans_Bold = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/FiraSans-Bold.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FiraSans_Bold);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }
}
