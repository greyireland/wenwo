package com.finalx.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.math.DoubleMath;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



/**
 * 头像图标生成
 * 示例：
 *    AvatarGen identicon = new AvatarGen();
     Hasher hasher = Hashing.md5().newHasher();
     hasher.putString("wang jingwen", Charsets.UTF_8);
     String md5 = hasher.hash().toString();
     BufferedImage image = identicon.create(md5, 200);
     ImageIO.write(image, "PNG", new File("test.png"));
 */
public class AvatarGen {

    public static void main(String[] args) {
        AvatarGen a = new AvatarGen();
        String filename = a.gen();
        System.out.println(filename);
    }
    private DefaultGenerator genartor=new DefaultGenerator();
    private String username = "";

    public AvatarGen() {
        this.username=genRandomStr(10);
    }
    public AvatarGen(String username) {
        this.username=username;
    }

    public String gen()  {
        try {
            AvatarGen identicon = new AvatarGen();
            Hasher hasher = Hashing.md5().newHasher();
            hasher.putString(username, Charsets.UTF_8);
            String md5 = hasher.hash().toString();
            BufferedImage image = identicon.genarate(md5, 200);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddHHyyyyMM");
            String nowTimestamp = sdf.format(date);
            String filename = nowTimestamp + md5 + ".png";
            ImageIO.write(image, "PNG", new File("static/avatar/"+filename));
            return filename;
        } catch (IOException e) {
            //todo
            System.out.println("生成头像失败"+e.getMessage());

        }
        return "";
    }

    public BufferedImage genarate(String hash, int size) {
        Preconditions.checkArgument(size > 0 && StringUtils.isNotBlank(hash));

        boolean[][] array = genartor.getBooleanValueArray(hash);


        int ratio = DoubleMath.roundToInt(size / 5.0, RoundingMode.HALF_UP);

        BufferedImage identicon = new BufferedImage(ratio * 5, ratio * 5, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = identicon.getGraphics();

        graphics.setColor(genartor.getBackgroundColor()); // 背景色
        graphics.fillRect(0, 0, identicon.getWidth(), identicon.getHeight());

        graphics.setColor(genartor.getForegroundColor()); // 图案前景色
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (array[i][j]) {
                    graphics.fillRect(j * ratio, i * ratio, ratio, ratio);
                }
            }
        }
        return identicon;
    }

    private String genRandomStr(int length) {
        char[] chars={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
                'o','p','q','r','s','t','u','v','w','x','y','z',
                'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random r = new Random();
            sb.append(chars[r.nextInt(chars.length-1)]);
        }
        return sb.toString();
    }
    class DefaultGenerator {
        private String hash;
        private boolean[][] booleanValueArray;
        public boolean[][] getBooleanValueArray(String hash) {
            Preconditions.checkArgument(StringUtils.isNotBlank(hash) && hash.length() >= 16,
                    "illegal argument hash:not null and size >= 16");

            this.hash = hash;

            boolean[][] array = new boolean[6][5];

            //初始化字符串
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    array[i][j] = false;
                }
            }

            for (int i = 0; i < hash.length(); i += 2) {
                int s = i / 2; //只取hash字符串偶数编号（从0开始）的字符

                boolean v =
                        DoubleMath.roundToInt(Integer.parseInt(hash.charAt(i) + "", 16) / 10.0, RoundingMode.HALF_UP) > 0 ? true : false;
                if (s % 3 == 0) {
                    array[s / 3][0] = v;
                    array[s / 3][4] = v;
                } else if (s % 3 == 1) {
                    array[s / 3][1] = v;
                    array[s / 3][3] = v;
                } else {
                    array[s / 3][2] = v;
                }
            }

            this.booleanValueArray = array;

            return this.booleanValueArray;
        }


        public Color getBackgroundColor() {
//        int r = Integer.parseInt(String.valueOf(this.hash.charAt(0)), 16) * 16;
//        int g = Integer.parseInt(String.valueOf(this.hash.charAt(1)), 16) * 16;
//        int b = Integer.parseInt(String.valueOf(this.hash.charAt(2)), 16) * 16;
//
//        return new Color(r, g, b);
            return new Color(255, 255, 255);
        }


        public Color getForegroundColor() {
            int r = Integer.parseInt(String.valueOf(hash.charAt(hash.length() - 1)), 16) * 16;
            int g = Integer.parseInt(String.valueOf(hash.charAt(hash.length() - 2)), 16) * 16;
            int b = Integer.parseInt(String.valueOf(hash.charAt(hash.length() - 3)), 16) * 16;

            return new Color(r, g, b);
        }
    }
}
