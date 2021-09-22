package com.cwj.we.module.chenghu;

public class Stack<T> {
    private static final int MAXSIZE = 14;  /* 存储空间初始分配量 */
    private int size;   /* 栈的最大空间 */
    public int top;    /* 栈顶指针 */
    private Object[] stack; /* 顺序栈 */

    //创建栈
    public Stack() {
        size = MAXSIZE;
        stack = new Object[size];
        top = -1;
    }

    //创建自定义空间大小的栈
    public Stack(int sz) {
        size = sz;
        stack = new Object[size];
        top = -1;
    }

    public Stack(Stack item) {
        stack = new Object[item.size];
        top = item.top;
        for (int i = 0; i < item.StackLength(); ++i) {
            stack[i] = item.stack[i];
        }
        size = item.size;
    }

    //清空栈
    public void clearStack() {
        top = -1;
    }

    //判断栈空
    public boolean isEmpty() {
        return top == -1;
    }

    //栈长
    public int StackLength() {
        return top + 1;
    }

    /**
     * 获取栈顶元素
     *
     * @return 栈顶元素
     */
    public T getTop() {
        if (top == -1)
            return null;
        return (T) stack[top];
    }

    /**
     * 入栈操作
     *
     * @param newElem 等待入栈的元素
     */
    public void push(T newElem) {
        if (top == MAXSIZE) {
            Object[] temp = stack;
            // 如果栈满，则创建空间为当前栈空间两倍的栈
            stack = new Object[stack.length * 2];
            System.arraycopy(temp, 0, stack, 0, temp.length);
        }
        stack[++top] = newElem;
    }

    /**
     * 出栈操作
     *
     * @return 栈顶元素
     */
    public T pop() {
        if (isEmpty())
            return null;
        T elem = getTop();
        stack[top--] = null;
        return elem;
    }

    //栈的遍历
    public void StackTraverse() {
        while (top != -1) {
            System.out.print(stack[top]);
            --top;
        }
    }
}