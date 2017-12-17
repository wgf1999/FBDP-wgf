
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

//
public class MyBayes {
    static Vector<String> indata = new Vector<>();//读入数据
    static Vector<String> testdata = new Vector<>();//存储待分类数据
    static Vector<int[]> catagory_R = new Vector<>();//存储类别R的所有数据  
    static Vector<int[]> catagory_L = new Vector<>();//存储类别L的所有数据  
    static Vector<int[]> catagory_B = new Vector<>();//存储类别B的所有数据  


    public static boolean loadData(String url) {//加载测试的数据文件  y
        try {
            Scanner in = new Scanner(new File(url));//读入文件  
            while (in.hasNextLine()) {
                String str = in.nextLine();//将文件的每一行存到str的临时变量中  
                indata.add(str);//将每一个样本点的数据追加到Vector 中  
            }
            return true;
        } catch (Exception e) { //如果出错返回false  
            return false;
        }
    }

    public static boolean loadtestData(String url) {//加载测试的数据文件  y
        try {
            Scanner in = new Scanner(new File(url));//读入文件
            while (in.hasNextLine()) {
                String str = in.nextLine();//将文件的每一行存到str的临时变量中
                testdata.add(str);//将每一个样本点的数据追加到Vector 中
            }
            return true;
        } catch (Exception e) { //如果出错返回false
            return false;
        }
    }

    public static void pretreatment(Vector<String> indata) {   //数据预处理，将原始数据中的每一个属性值提取出来存放到Vector<double[]>  data中
        int i = 0;
        String t;
        while (i < indata.size()) {//取出indata中的每一行值  
            int[] tem = new int[1000];
            t = indata.get(i);
            t.trim();
            String[] sourceStrArray = t.split(" ", 1001);//使用字符串分割函数提取出各属性值
            switch (sourceStrArray[0]) {
                case "R": {
                    for (int j = 1; j < 1001; j++) {
                        tem[j - 1] = Integer.parseInt(sourceStrArray[j].trim());
                    }
                    catagory_R.add(tem);
                    break;
                }
                case "L": {
                    for (int j = 1; j < 1001; j++) {
                        tem[j - 1] = Integer.parseInt(sourceStrArray[j].trim());
                    }
                    catagory_L.add(tem);
                    break;
                }
                case "B": {
                    for (int j = 1; j < 1001; j++) {
                        tem[j - 1] = Integer.parseInt(sourceStrArray[j].trim());
                    }
                    catagory_B.add(tem);
                    break;
                }
            }
            i++;
        }

    }

    public static double bayes(int[] x, Vector<int[]> catagory) {
        double[] ai_y = new double[1000];
        int[] sum_ai = new int[1000];
        for (int i = 0; i < 1000; i++) {

            for (int j = 0; j < catagory.size(); j++) {
                if (x[i] == catagory.get(j)[i])
                    sum_ai[i]++;
            }
        }
        for (int i = 0; i < 1000; i++) {
            ai_y[i] = (double) sum_ai[i] / (double) catagory.size();
        }
        return ai_y[0] * ai_y[1] * ai_y[2] * ai_y[3];
    }

    public static void main(String[] args) throws Exception {


        loadData("bayes_train.txt");
        loadtestData("bayes_test.txt");
        String OUT_PATH = "bayes_out";
        String INPUT_PATH = ("/Users/macbook/Documents/FBDP/project2/股票编号.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUT_PATH, true));
        File file1 = new File(INPUT_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file1));
        pretreatment(indata);
        double p_yR = (double) catagory_R.size() / (double) (indata.size());//表示概率p（R）  
        double p_yB = (double) catagory_B.size() / (double) (indata.size());//表示概率p（B）  
        double p_yL = (double) catagory_L.size() / (double) (indata.size());//表示概率p（L）  

        int[] x = new int[1000];
        double x_in_R, x_in_L, x_in_B;

        int sumR = 0, sumL = 0, sumB = 0;
        double correct = 0;
        int r = 0;
        while (r < testdata.size()) {

            for (int i = 0; i < 1000; i++) {
                //读取数字放入数组的第i个元素  
                x[i] = Integer.parseInt(testdata.get(r).split(" ", 1000)[i].trim());
            }

            x_in_B = bayes(x, catagory_B) * p_yB;
            x_in_L = bayes(x, catagory_L) * p_yL;
            x_in_R = bayes(x, catagory_R) * p_yR;
            String temp0;
            temp0 = br.readLine();
            if (x_in_B == Math.max(Math.max(x_in_B, x_in_L), x_in_R)) {
                bw.write(temp0 + " " + "属于类别neg\n");
                System.out.println("输入的第" + r + "样本属于类别：B");
                sumB++;
//                if (indata.get(r).split(" ", 1001)[0].equals("B"))
//                    correct++;
            } else if (x_in_L == Math.max(Math.max(x_in_B, x_in_L), x_in_R)) {
                System.out.println("输入的第" + r + "样本属于类别：L");
                bw.write(temp0 + " " + "属于类别neu\n");
                sumL++;
//                if (indata.get(r).split(" ", 1001)[0].equals("L"))
//                    correct++;
            } else if (x_in_R == Math.max(Math.max(x_in_B, x_in_L), x_in_R)) {
                bw.write(temp0 + " " + "属于类别pos\n");
                System.out.println("输入的第" + r + "样本属于类别：R");
                sumR++;
//                if (indata.get(r).split(" ", 1001)[0].equals("R"))
//                    correct++;
            }

            r++;


        }
        br.close();
        bw.close();

//        System.out.println("使用训练样本进行分类器检验得到结果统计如下：");
//        System.out.println("R类有：" + sumR + "    实际有R类样本" + catagory_R.size() + "个");
//        System.out.println("L类有：" + sumL + "    实际有L类样本" + catagory_L.size() + "个");
//        System.out.println("B类有：" + sumB + "      实际有B类样本" + catagory_B.size() + "个");
//        System.out.println("分类的正确率为" + correct * 1.0 / indata.size() * 100 + "%");

    }
} 