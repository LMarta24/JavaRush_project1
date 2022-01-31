import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public static List<String> list1 = new ArrayList<>();
    public static List<String> list2 = new ArrayList<>();

    public static void main(String[] args) {
        ReadFiles thread1 = new ReadFiles("thread1");
        ReadFiles thread2 = new ReadFiles("thread2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list1);
        System.out.println(list2);
    }
}




    class ReadFiles extends Thread {
    List<String> list;
    String data;

    ReadFiles(String name) {
        super(name);
    }

    @Override
    public void run() {
        try (BufferedReader fileName = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader file = new BufferedReader(new FileReader(fileName.readLine()))) {

            if (getName().equals("thread1")) {
                list = Solution.list1;
            } else if (getName().equals("thread2")) {
                list = Solution.list2;
            }

            while((data = file.readLine()) != null) {
                list.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}