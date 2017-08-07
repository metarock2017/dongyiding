import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

public class RandomWords implements Readable{
    private Random rand=new Random(47);
    //将字符串转换为字符数组
    private static final char[] capitals="ABCDEFGHIJKLMOPQRSTUVWXYZ".toCharArray();
    private static final char[] lowers="ABCDEFGHIJKLMOPQRSTUVWXYZ".toLowerCase().toCharArray();
    private static final char[] vowels="一二三四".toCharArray();
    private int count;
    public RandomWords(int count) {
        this.count=count;
    }

    @Override
    //方法返回-1时候Scanner类停止读取
    public int read(CharBuffer cb) throws IOException {
        if (count--==0) {
            return -1;
        }
        //读取一个字符
        cb.append(capitals[rand.nextInt(capitals.length)]);
        //循环读取三个字符
        for (int i = 0; i < 4; i++) {
            cb.append(vowels[rand.nextInt(vowels.length)]);
            cb.append(lowers[rand.nextInt(lowers.length)]);
        }
        cb.append(" ");
        return 10;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(new RandomWords(10));
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        scanner.close();
    }
}