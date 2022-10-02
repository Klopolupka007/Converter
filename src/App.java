import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.Customizer;
import java.io.File;
import java.io.IOException;

public class App extends JPanel {

    ImageIcon picture = new ImageIcon(new ImageIcon("src/StandartImage.png").getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT));
    JLabel img;
    AddFile FileOp;
    JComboBox comboBox;

    App(){
        setSize(1080,720);
        setBackground(new Color(191, 80, 60));
        //Добавление картинки по-умолчанию
        img = new JLabel(picture);
        img.setBackground(Color.gray);
        img.setBounds(50, 50, 600, 400);
        add(img);
        JLabel addFormat = new JLabel("Выберите расширение файла");
        addFormat.setBounds(670, 50, 300, 30);
        customFontColor fontsLib = new customFontColor();
        addFormat.setFont(fontsLib.FiraSans_Bold);
        add(addFormat);

        //Добавление функционала выбора файла
        FileOp = new AddFile(img, picture);
        add(FileOp);
        //Добавление выпадающего списка расширений
        String[] components = {"psd", "tiff", "bmp", "jpeg", "gif", "eps", "png", "pict", "pdf", "pcs", "ico", "cdr",
                "ai", "raw", "svg", "avif"};
        comboBox = new JComboBox(components);
        comboBox.setBounds(670, 80, 200, 30);
        comboBox.setBackground(new Color(231, 120, 100));
        add(comboBox);
        //Добавление функционала кнопки старта
        add(new SaveFiles(FileOp, comboBox));

        //Добавление градиента
        add(new pan());

        setLayout(null);
        setVisible(true);
    }
}

//Класс визуального дизайна приложения
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

//Класс стартовой кнопки и сохранения файла
class SaveFiles extends JButton implements ActionListener{
    AddFile FileOp;
    JComboBox comboBox;

    SaveFiles(AddFile FileOp, JComboBox comboBox){
        this.FileOp = FileOp; this.comboBox = comboBox;
        setBounds(210, 470, 40, 40);
        setOpaque(true);
        setBackground(new Color(166, 30, 30));
        setFocusPainted(false);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        setBorder(emptyBorder);
        setIcon(new ImageIcon(new ImageIcon("src/Start.png").getImage().getScaledInstance(20, 25, Image.SCALE_DEFAULT)));

        addActionListener(this);
    }

    //Получение пути выбранного файла
    public String getFilePath() {
        return FileOp.Select();
    }

    //Получение выбранного расширения
    public String getComboBox() {
        return (String)comboBox.getSelectedItem();
    }

    //Метод получения строки типа JSON для обозначения пути выбранного файла и формата преобразования
    public JSONObject startBut(){
        JSONObject json = new JSONObject();
        try {
            json.put("path", getFilePath());
            json.put("format", getComboBox());
        } catch (NullPointerException e){
            System.err.println("Путь/формат файла пустой.");
        }
        return json;
    }

    //Обработчик нажатия кнопки старта
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(startBut());
        //Здесь может быть ваша реклама!
    }
}

//Класс выбора файла из каталога
class AddFile extends JButton implements ActionListener{

    JFileChooser fc;
    File SelFile;
    ImageIcon picture;
    JLabel img;
    String[] components = {"psd", "tiff", "bmp", "jpg", "jpeg", "gif", "eps", "png", "pict", "pdf", "pcs", "ico", "cdr",
            "ai", "raw", "svg", "avif"};

    AddFile(JLabel img, ImageIcon picture){

        this.img = img;
        this.picture = picture;
        //Создаем объект класса библиотеки пользовательских шрифтов
        customFontColor fontsLib = new customFontColor();

        setSize(150, 40);
        setLocation(50, 470);
        setText("Выберите файл");

        setFont(fontsLib.FiraSans_Bold);
        setForeground(new Color(240, 240, 240));
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBackground(new Color(166, 30, 30));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        setBorder(emptyBorder);


        fc = new JFileChooser();
        setFocusPainted(false);
        addActionListener(this);
    }

    //Метод, который возвращает путь к выбранному файлу
    String Select(){
        SelFile = fc.getSelectedFile();
        boolean state = true; StringBuilder format = new StringBuilder();
        String Path = SelFile.getPath();

        //Проверка формата файла
        //Срез строки пути файла до расширения
        for (char ch : Path.toCharArray()) {
            if (ch != '.' && state) {
                continue;
            }
            else {
                state = false;
                if (ch != '.') format.append(ch);
            }
        }

        if (format.toString().equals("pdf") || format.toString().equals("psd")){
            BufferedImage bimg = null;
            try {
                bimg = ImageIO.read(new File("src/doc.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer[] picSize = sizeImg(bimg.getWidth(), bimg.getHeight());
            ImageIcon pic = new ImageIcon(new ImageIcon("src/doc.png").getImage().getScaledInstance(picSize[0], picSize[1], Image.SCALE_DEFAULT));
            img.setIcon(pic);
            return SelFile.getPath();
        }else {
            //Сверка среза с шаблонами форматов
            for (String str : components) {
                if (str.equals(format.toString()) && !format.toString().equals("")) {
                    state = true;
                    break;
                }
            }
        }
        //Результат сверки
        if (state) {
            BufferedImage bimg = null;
            try {
                bimg = ImageIO.read(new File(SelFile.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer[] picSize = sizeImg(bimg.getWidth(), bimg.getHeight());
            ImageIcon pic = new ImageIcon(new ImageIcon(SelFile.getPath()).getImage().getScaledInstance(picSize[0], picSize[1], Image.SCALE_DEFAULT));
            img.setIcon(pic);
            img.setLocation(50, 50);
            return SelFile.getPath();
        }
        else {
            JOptionPane.showMessageDialog(this,"Invalid format of selected file. Choose it again.","Error: Invalid format", JOptionPane.ERROR_MESSAGE);
            img.setIcon(picture);
            return "Error: Invalid format of selected file.";
        }
    }

    //Подбор значений размера изображения
    Integer[] sizeImg(int x, int y){
        Integer[] size = new Integer[2];
        double prop, y1=y, x1=x;
        if (x>y){
            prop = (double) y/(double)x;
            while (x1 < 600) x1++;
            while (x1 > 600) x1--;
            y1 = (int) (x1*prop);
        } else {
            prop = (double) x/(double)y;
            while (y1 < 400) y1++;
            while (y1 > 400) y1--;
            x1 = (int) (y1*prop);
        }

        size[0] = (int)x1;size[1] = (int)y1;
        return size;
    }

    //Метод выбора файла из файловой локальной системы
    @Override
    public void actionPerformed(ActionEvent e) {
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fc.showOpenDialog(AddFile.this);
        try {
            Select();
        } catch (NullPointerException Exception){
            System.err.println("Не выбран файл");
        }

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
