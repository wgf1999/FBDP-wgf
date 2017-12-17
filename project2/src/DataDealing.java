import org.apache.commons.lang3.StringUtils;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
* 将project2的TrainingData进行处理
* 先分词，再去掉多余的数字、符号、空格
* 得到Data1
* author：wgf
*/
public class DataDealing {

    private static String[] data;
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
        DataDealing news = new DataDealing();
        int k = 0;
        news.iteratorPath("/Users/macbook/Documents/FBDP/project2/TrainingData/negative/");
        for (String title : news.pathName) {
            String READ_PATH = ("/Users/macbook/Documents/FBDP/project2/TrainingData/negative/" + title);
            String Out_PATH = "/Users/macbook/Downloads/negative/" + title;
            //需要先在Downloads新建negative文件夹
            ArrayList<String> list = new ArrayList<String>();
            File file = new File(READ_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            BufferedWriter bw = new BufferedWriter(new FileWriter(Out_PATH, true));
            String temp;
            while ((temp = br.readLine()) != null) {
                temp.trim();
                list.add(temp);
            }
            br.close();

            for (int i = 0; i < list.size(); i++) {
                List<Word> tempseg = WordSegmenter.seg(list.toString());
                String titles = StringUtils.strip(tempseg.toString().replaceAll("[,.|‰%/(A-Za-z0-9)]", ""), "[]");
                titles.trim();
                System.out.print(1);
                System.out.println(titles);
                titles = titles.replaceAll("\\s+", " ");
                bw.write(titles);
                bw.newLine();
            }
            bw.close();
        }
    }
}
