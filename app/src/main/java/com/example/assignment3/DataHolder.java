package com.example.assignment3;

import java.util.HashMap;

public class DataHolder {

    public static DataHolder instance = null;
    private static String[] spinnerArray;
    private static HashMap<Integer, int[]> luxThresholds;

    public static DataHolder getInstance() {

        if (instance == null) {
            instance = new DataHolder();
            spinnerArray = getSpinnerValues();
            luxThresholds = new HashMap<>();
            populateHashmap();
        }
        return instance;
    }

    public HashMap<Integer, int[]> getLuxThresholds() {
        return luxThresholds;
    }

    public String[] getSpinnerArray() {
        return spinnerArray;
    }

    private static void populateHashmap() {
        luxThresholds.put(0, new int[]{100, 300});
        luxThresholds.put(1, new int[]{300, 1000});
        luxThresholds.put(2, new int[]{1000, 2000});
        luxThresholds.put(3, new int[]{2000, 3000});
        luxThresholds.put(4, new int[]{3000, 5000});
    }

    private static String[] getSpinnerValues() {
        return new String[]{"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};
    }

}
