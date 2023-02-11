import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    static JFrame frame = new JFrame();
    static JTextField textBox = new JTextField();
    static String n1=""; // Первое число
    static String n2=""; // Второе число
    static String op=""; // Операция

    private static double caclulate() { // Метод вычисления
        return switch (op) { // Обрабатываем знак операции и возвращаем результат
            case "+" -> Double.parseDouble(n1) + Double.parseDouble(n2);
            case "-" -> Double.parseDouble(n1) - Double.parseDouble(n2);
            case "*" -> Double.parseDouble(n1) * Double.parseDouble(n2);
            case "/" -> Double.parseDouble(n1) / Double.parseDouble(n2);
            default -> 0;
        };
    }

    public static void main(String[] args) {

        frame.setLayout(new BorderLayout()); // Задаем менеджер расположения объектов, чтобы потом добавить 2 панели
        frame.setResizable(false); // Отключаем возможность изменения размеров
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Калькулятор");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 400, height = 300;
        frame.setBounds(dim.width / 2 - width / 2, dim.height / 2 - height / 2, width, height);
        JPanel panel1 = new JPanel (), panel2 = new JPanel (); // Создаем 2 панели
        panel1.setLayout(new BorderLayout()); // Устанавливаем им менеджеров расположения
        panel2.setLayout(new GridLayout(4,4,1,1));
        panel1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); // Устанавливаем им границы
        panel2.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));
        textBox.setEnabled(false); // Отключаем у него ввод
        textBox.setFont(new Font("Font", Font.PLAIN, 20));
        textBox.setDisabledTextColor(Color.BLACK);
        panel1.add(textBox);
        var buttons = new ArrayList<JButton>(); // Создаем список для хранения кнопок
        String [] nameButtons = new String[] {"1","2","3","+","4","5","6","-","7","8","9","*","C","0","=","/"}; // Создаем массив с названиями кнопок
        Font fontButton = new Font("Font", Font.BOLD, 35); // Указываем шрифт для кнопок
        for (String name: nameButtons) {
            buttons.add(new JButton(name)); // Создаем кнопки и добавляем их в список
            JButton newButton =  buttons.get(buttons.size() - 1); // Вытаскиваем название объекта кнопки
            newButton.setFont(fontButton); // Меняем шрифт
            panel2.add(newButton); // Добавляем на вторую панель
            newButton.addActionListener(e -> {
                String text = textBox.getText();
                String b = newButton.getText();
                switch (b) {
                    case "C": // Обнуление
                        textBox.setText("");
                        n1 = "";
                        n2 = "";
                        op = "";
                        break;
                    case "=":
                        if (n1.length() > 0 && n2.length() > 0 && op.length() > 0){ // Если есть данные для расчета
                            double result = caclulate(); // Получаем результат
                            textBox.setText(result % 1 == 0 ? Integer.toString((int) result): String.valueOf(result)); // Переводим в строку, и если число целое, то без дробной части, и отображаем
                            n1 = textBox.getText(); // После вычисления полученное значение пишем в n1, чтобы сразу можно было продолжить дальнейшее вычисление, остальное обнуляем
                            n2 = "";
                            op = "";
                        }
                        break;
                    case "+","-","*","/":
                        if (n1.length() > 0 && op.length() == 0){ // Если есть первое число, но нет знака, то пишем знак
                            textBox.setText(text+b);
                            op = b;
                        }
                        break;
                    case "0":
                        if (op.equals("/")&&n2.length() == 0) // Если деление на ноль, то выходим
                            break;
                    default:
                        if (op.length() == 0 & !n1.equals("0")) { // Если вводим цифру до знака и перед ней нет нуля
                            n1 = n1 + b; // Обновляем первое число
                            textBox.setText(text+b); // Обновляем текстбокс
                        }
                        if (op.length() != 0 & !n2.equals("0")) { // Если вводим цифры после знака и перед ней нет нуля
                            n2 = n2 + b; // Обновляем второе число
                            textBox.setText(text+b); // Обновляем текстбокс
                        }
                }
            });
        }

        frame.add(panel1,BorderLayout.NORTH);
        frame.add(panel2,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}