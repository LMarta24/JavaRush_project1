import java.io.*;
import java.util.Scanner;

public class Crypto {
    public static final String letters = "абвгдеёжзийклмнопрстуфхцчшщьъэюя0123456789,.! ";
    public static final char[] symbols = letters.toCharArray();

    public static void main(String[] args) throws IOException {

        printMenu();
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                System.out.println("Введите полный путь к исходному файлу:");
                String src1 = requestEncryptedUrl();
                int key1 = requestKey();
                String path1 = requestDecryptedUrl();
                toEncrypt(src1, path1, key1, false);
                break;

            case 2:
                String src2 = requestEncryptedUrl();
                int key2 = requestKey();
                String path2 = requestDecryptedUrl();
                toEncrypt(src2, path2, key2, true);
                break;

            case 4: //   стат анализ
                System.out.println("Введите полный путь к файлу для сбора статистики:");
                String srcStat = requestEncryptedUrl();
                //StatAnalyze.useStatMethod(srcStat);
                System.out.println("Введите полный путь к зашифрованному файлу:");
                String src3 = requestEncryptedUrl(); //файл, который надо расшифровать
                //StatAnalyze.useStatMethod(src3);
                // StatAnalyze.compare(srcStat, src3);
                System.out.println("Введите полный путь к файлу, в который хотите записать расшифрованный текст:");
                String src4 = requestEncryptedUrl();
                StatAnalyze.decipher(srcStat, src3, src4);


                break;

        }
    }

    private static void printMenu() {
        System.out.println("1. Зашифровать текст в файле с помощью ключа");
        System.out.println("2. Расшифровать текст в файле с помощью ключа");
        System.out.println("3. Взломать текст в файле методом перебора ключа (брут-форс)");
        System.out.println("4. Расшифровать текст в файле методом статистического анализа");
    }

    /**
     * Метод получения пути к файлу считыванием с консоли, из которого будет произведено чтение
     *
     * @return
     */
    private static String requestEncryptedUrl() throws IOException {
        //System.out.println("Введите полный путь к исходному файлу:");
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String s = buff.readLine();
        return s;
    }

    /**
     * Метод получения пути к файлу считыванием с консоли, в который будет произведена запись
     *
     * @return
     */
    //C:\Users\denis\IdeaProjects\Test\file1.txt
    private static String requestDecryptedUrl() throws IOException {
        System.out.println("Введите полный путь к файлу, в который будет осуществлена запись:");
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String s = buff.readLine();
        return s;
    }

    /**
     * Метод получения ключа из консоли ввода
     *
     * @return - ключ
     */
    private static int requestKey() {
        System.out.println("Введи ключ шифрования");
        int key = 0;
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            key = Integer.valueOf(buff.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Это было не число!");
        }
        return key;
    }

    /**
     * Метод кодирования и декодирования
     *
     * @param src          - путь к считываемому файлу
     * @param path         - путь к файлу, в который будет произведена запись
     * @param key          - ключ сдвига
     * @param isDecryption - указатель на декодирование, если false, то кодирование, если true, то декодирование
     */
    private static void toEncrypt(String src, String path, int key, boolean isDecryption) {
        try (FileReader in = new FileReader(src);
             BufferedReader reader = new BufferedReader(in);
             FileWriter writer = new FileWriter(path)) {
            while (reader.ready()) {
                String line = reader.readLine();
                char[] arrayChars = line.toLowerCase().toCharArray();
                for (int j = 0; j < arrayChars.length; j++) { // проходимся по тексту
                    for (int k = 0; k < symbols.length; k++) { // прохожусь по словарю
                        if (arrayChars[j] == symbols[k]) {
                            if (!" ".equals(String.valueOf(arrayChars[j]))) { // если символ не пробел
                                if (!isDecryption) { //прямое кодирование
                                    arrayChars[j] = symbols[(k + key) % symbols.length];
                                } else { //декодирование
                                    arrayChars[j] = symbols[(k - key) % symbols.length];
                                }
                            }
                            break;
                        }
                    }
                }
                writer.write(String.valueOf(arrayChars)); //запись по строчно в новый файл
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
