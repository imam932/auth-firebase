package id.nawawi.fierbaseapp;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageAssets extends AppCompatActivity {

    private static List<Integer> body = new ArrayList<Integer>(){{
        add(R.drawable.b11);
        add(R.drawable.b12);
        add(R.drawable.b13);
        add(R.drawable.b14);
        add(R.drawable.b15);

    }};

    private static List<Integer> eye = new ArrayList<Integer>(){{
        add(R.drawable.e32);
        add(R.drawable.e33);
        add(R.drawable.e34);
        add(R.drawable.e35);
        add(R.drawable.e37);

    }};

    private static List<Integer> blush = new ArrayList<Integer>(){{
        add(R.drawable.n211);
        add(R.drawable.n212);
        add(R.drawable.n213);

    }};

    private static List<Integer> mouth = new ArrayList<Integer>(){{
        add(R.drawable.m414);
        add(R.drawable.m416);
        add(R.drawable.m424);
        add(R.drawable.m427);
        add(R.drawable.m428);
        add(R.drawable.m430);

    }};

    private static List<Integer> hair = new ArrayList<Integer>(){{
        add(R.drawable.h11);
        add(R.drawable.h12);
        add(R.drawable.h13);
        add(R.drawable.h116);
        add(R.drawable.h122);

    }};

    private static List<Integer> cloth = new ArrayList<Integer>(){{
        add(R.drawable.c10);
        add(R.drawable.c12);
        add(R.drawable.c14);
        add(R.drawable.c18);
        add(R.drawable.c28);

    }};

    public static List<Integer> getBody() {
        return body;
    }

    public static List<Integer> getEye() {
        return eye;
    }

    public static List<Integer> getBlush() {
        return blush;
    }

    public static List<Integer> getMouth() {
        return mouth;
    }

    public static List<Integer> getCloth() {
        return cloth;
    }

    public static List<Integer> getHair() {
        return hair;
    }
}
