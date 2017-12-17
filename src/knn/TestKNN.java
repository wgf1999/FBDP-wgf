package knn;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestKNN {

    /**
     * 从数据文件中读取数据
     *
     * @param datas 存储数据的集合对象
     * @param path  数据文件的路径
     */
    public void read(List<List<Double>> datas, String path) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String data = br.readLine();
            List<Double> l = null;
            while (data != null) {
                String t[] = data.split(" ");
                l = new ArrayList<Double>();

                for (int i = 0; i < t.length; i++) {

                    l.add(Double.parseDouble(t[i]));
                }
                datas.add(l);
                data = br.readLine();

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序执行入口
     *
     * @param args
     */
    public static void main(String[] args) {
        TestKNN t = new TestKNN();
        String datafile = new File("").getAbsolutePath() + File.separator + "knntrain.txt";
        String testfile = new File("").getAbsolutePath() + File.separator + "knndata.txt";
        String INPUT_PATH = ("/Users/macbook/Documents/FBDP/project2/股票编号.txt");
        File file1 = new File(INPUT_PATH);
        try {
            List<List<Double>> datas = new ArrayList<List<Double>>();
            List<List<Double>> testDatas = new ArrayList<List<Double>>();
            t.read(datas, datafile);
            t.read(testDatas, testfile);
            KNN knn = new KNN();
            //写文件
            BufferedReader bw1 = new BufferedReader(new FileReader(file1));
            String temp0;
            for (int i = 0; i < testDatas.size(); i++) {
                List<Double> test = testDatas.get(i);
                System.out.print(temp0 = bw1.readLine());
                System.out.print(" (测试组: " + (i + 1) + ") ");
                for (int j = 0; j < test.size(); j++) {
                    //System.out.print(test.get(j) + " ");
                }
                System.out.print("类别为: ");
                System.out.println(Math.round(Float.parseFloat((knn.knn(datas, test, 3)))));
                Integer a = Math.round(Float.parseFloat((knn.knn(datas, test, 3))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}