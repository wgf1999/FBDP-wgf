import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 2017/12/13.
 * 输入：/Users/macbook/Downloads/training_tfidfout/
 * 输出：training.txt
 */
public class TxtVec {

    private static final int lenth = 1000;
    private File stock;
    private File[] files;
    private List<String> pathName = new ArrayList<String>();

    public void iteratorPath(String dir) {
        stock = new File(dir);
        files = stock.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    pathName.add(file.getName());
                } else if (file.isDirectory()) {
                    iteratorPath(file.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //用list记录模型的词
        String READ_PATH = ("model.txt");
        ArrayList<String> dic = new ArrayList<String>();
        File file = new File(READ_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp;
        while ((temp = br.readLine()) != null) {
            dic.add(temp);
        }
        br.close();
        String[] vec = new String[lenth];

        Integer id = 0;
        TxtVec news = new TxtVec();
        news.iteratorPath("/Users/macbook/Documents/FBDP/project2/stock_tfidfout/");
        for (String title : news.pathName) {
            String INPUT_PATH = ("/Users/macbook/Documents/FBDP/project2/stock_tfidfout/" + title);
            String OUT_PATH = "bayes_test.txt";
            //首先读取文件
            File file1 = new File(INPUT_PATH);
            for (int j = 0; j < vec.length; j++) {
                vec[j] = "0";
            }
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(OUT_PATH, true));
            String temp0;
            String[] temp1 = new String[2];
            int k = 0;
            while ((temp0 = br1.readLine()) != null) {
                temp1 = temp0.split(" ");
                k = dic.indexOf(temp1[0]);
                if (k != -1) {
                    vec[k] = temp1[1];
                }
            }
            br1.close();

            id=id+1;
            //bw1.write(id+" ");
            //System.out.println(title+" "+id);
            Integer tem=0;
            if (title.contains("neg")) {
                bw1.write("B ");
            }
            if (title.contains("pos")) {
                bw1.write("R ");
            }
            if (title.contains("neu")) {
                bw1.write("L ");
            }
            for (int i = 0; i < vec.length; i++) {
                if((vec[i].indexOf("."))!=-1) {
                    String str = vec[i].substring(0, vec[i].indexOf("."));
                    tem=Integer.parseInt(str);
                }
                else
                    tem=Integer.parseInt(vec[i]);
                bw1.write(tem.toString() + " ");
            }

            bw1.newLine();
            bw1.close();

        }
    }
}
