import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StatAnalyze {

    private static HashMap<Character, Integer> useStatMethod(String srcStat) {
        //пробегаюсь по тексту для сбора статистики
        //всю статистику записываю в HashMap
        Map<Character, Integer> mapStat = new HashMap<>(); // map для сбора статистики
        try (FileReader in = new FileReader(srcStat);
             BufferedReader reader = new BufferedReader(in)) {
            while (reader.ready()) {
                String line = reader.readLine();
                char[] arrayChars = line.toLowerCase().toCharArray();

                for (int j = 0; j < arrayChars.length; j++) { // проходимся по каждому символу строки
                    if (!mapStat.containsKey(arrayChars[j])) { // если этого элемента всё еще нет в mapStat
                        mapStat.put(arrayChars[j], 1);
                    } else if (mapStat.containsKey(arrayChars[j])) {
                        mapStat.put(arrayChars[j], mapStat.get(arrayChars[j]) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Создадим список из элементов HashMap
        List<Map.Entry<Character, Integer>> list = new LinkedList<Map.Entry<Character, Integer>>(mapStat.entrySet());

        // Сортируем, используя лямбду-функцию
        Collections.sort(
                list,
                (i1,
                 i2) -> i2.getValue().compareTo(i1.getValue()));
        // Добавим данные из сортированного списка в Hashmap
        HashMap<Character, Integer> temp = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        System.out.println(temp);
        return temp;
    }

    // сопоставить два хэш-мэпа
    public static HashMap<Character, Character> compare(String s1, String s2) { // здесь нужно созать 3ий хэшмэп?
        HashMap<Character, Integer> map1 = useStatMethod(s1); // мэп для статистики
        HashMap<Character, Integer> map2 = useStatMethod(s2); // мэп зашифрованного файла
        HashMap<Character, Character> map3 = new HashMap<>(); // хэшмэп для сопоставления

        int i = 0;
        Iterator iter = map2.keySet().iterator();
        for (Character mark1 : map1.keySet()) {
            if (iter.hasNext()) {
                Character ch = (Character) iter.next();
                map3.put(ch, mark1);
            }
        }
        return map3;
    }

    // нужно заново считать файл, найти символ в map3
    public static void decipher(String s1, String s2, String s3) {

        HashMap<Character, Character> map3 = compare(s1, s2);
        try (FileReader in = new FileReader(s2);
             BufferedReader reader = new BufferedReader(in);
             FileWriter writer = new FileWriter(s3)) {
            while (reader.ready()) {
                String line = reader.readLine();
                char[] arrayChars = line.toLowerCase().toCharArray();

                for (int j = 0; j < arrayChars.length; j++) { // проходимся по каждому символу строки
                    for (Character ch : map3.keySet()) {
                        if (arrayChars[j] == ch) {
                            arrayChars[j] = map3.get(ch);
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

