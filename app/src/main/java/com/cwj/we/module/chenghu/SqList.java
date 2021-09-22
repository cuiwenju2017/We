package com.cwj.we.module.chenghu;

public class SqList {
    private String[] data = null;
    private int length = 0;
    private int size = 0;

    public SqList(int n) {
        size = n;
        data = new String[n];
        for (int i = 0; i < size; i++) {
            data[i] = "";
        }
        add("");
        add("");
    }

    public SqList(SqList temp) {
        data = new String[temp.size];
        for (int i = 0; i < temp.length(); i++) {
            data[i] = temp.getItem(i);
        }
        length = temp.length();
        size = temp.size;
    }

    public boolean isExist(String item) {
        for (int i = 1; i < length; i++) {
            if (data[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExist(int position, String item) {
        for (int i = position + 1; i < length; i++) {
            if (data[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void add(String item) {
        if (!isExist(item)) {
            data[length++] = item;
        }
    }

    public int findData(String item) {
        for (int i = 0; i < length; i++) {
            if (data[i].equals(item))
                return i;
        }
        return -1;
    }

    public int length() {
        return length;
    }

    public void changeValue(int position, String item) {
        if (!isExist(position, item)) {
            data[position] = item;
        } else {
            deleteItem(position);
        }
    }

    public void deleteItem(int position) {
        for (int i = position; i < length - 1; i++) {
            data[i] = data[i + 1];
        }
        data[length - 1] = "";
        length--;
    }

    public void clear() {
        for (int i = 0; i < length; i++) {
            data[i] = null;
        }
        length = 0;
        // 加两个空元素 第一个存放原始的用户输入，第二个存放初始的
        add("");
        add("");
    }

    public String getItem(int position) {
        if (position < length)
            return data[position];
        else
            return "";
    }

    public void allItemAppend(String tail) {
        for (int i = 0; i < length; i++) {
            if (data[i].length() == 0) {
                data[i] = tail;
            } else {
                data[i] = data[i] + "," + tail;
            }
        }
    }

    public void combine(SqList temp) {
        for (int i = 1; i < temp.length(); i++) {
            add(temp.getItem(i));
        }
    }
}
