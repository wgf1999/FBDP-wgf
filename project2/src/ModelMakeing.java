import java.io.*;
import java.util.ArrayList;

/**
 * Created by macbook on 2017/12/16.
 * 得到模型model.txt
 */
public class ModelMakeing {
    public static void main(String[] args) throws Exception {
        String INPUT_PATH = ("/Users/macbook/Downloads/dic.txt");
        String OUT_PATH = "model.txt";
        //首先读取文件
        File file1 = new File(INPUT_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file1));
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUT_PATH, true));
        String temp;
        ArrayList<String> dic = new ArrayList<String>();

        while ((temp = br.readLine()) != null) {
            temp.trim();
            String[] temp0 = temp.split("\t");
            if (!dic.contains(temp0[1])) {
                dic.add(temp0[1]);
            }
        }

        for (String a : dic) {
            bw.write(a);
            bw.newLine();
        }
        br.close();
        bw.close();
    }

}
