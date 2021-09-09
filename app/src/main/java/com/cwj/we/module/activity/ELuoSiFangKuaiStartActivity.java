package com.cwj.we.module.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.game.eluosifangkuai.BlockAdapter;
import com.cwj.we.game.eluosifangkuai.CacheUtils;
import com.cwj.we.game.eluosifangkuai.StateFang;
import com.gyf.immersionbar.ImmersionBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 俄罗斯方块开始
 */
public class ELuoSiFangKuaiStartActivity extends BaseActivity {

    Button pausebtn;
    TextView levelTextView;
    int timeInterval = 800;
    BlockAdapter nextTetrisAdapter;
    int highestScore = 0;
    CacheUtils cacheUtils;
    int[][] blockColor = new int[15][10];
    int score = 0;
    public String TAG = "MainActivity";
    Random random;
    int randColor;
    Button leftMove, rightMove, rotateMove, downMove;
    int rand;
    int[] position = new int[]{-4, 4};//positin[0]为y方向位置
    int[] qu = new int[4];

    Timer timer;
    int stop = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            boolean isNewTimer = true;

            // 将正在下落的方块与停止下落的方块区别对待
            for (int i = 0; i < ySize; i++) {
                if (allBlock[i] == 0) {
                    for (int j = 0; j < xSize; j++) {
                        blockList.set(i * xSize + j, 0);
                    }
                } else {
                    for (int j = 0; j < xSize; j++) {
                        blockList.set(i * xSize + j, blockColor[i][j]);
                    }
                }
            }

            boolean canMove = true;
            if (msg.what == 0) {
                //如果处于等时间间隔下落状态
                //检测是否可以按要求移动
                position[0]++;
                for (int i = 3; i >= 0; i--) {
                    int line = i + position[0];
                    if (line >= 0 && StateFang.shape[rand][i] != 0) {
                        //如果到底了，或者下面有方块
                        if (line >= ySize ||
                                ((allBlock[line] & (leftMath(StateFang.shape[rand][i], position[1]))) != 0)
                        ) {
                            canMove = false;
                            break;
                        }
                    }
                }
                if (!canMove) {
                    position[0]--;
                    for (int i = 3; i >= 0; i--) {
                        int line = i + position[0];
                        if (line >= 0 && StateFang.shape[rand][i] != 0) {
                            for (int j = 0; j < xSize; j++) {
                                if (((1 << j) & (leftMath(StateFang.shape[rand][i], position[1]))) != 0) {
                                    blockList.set(line * xSize + j, randColor);
                                }
                            }
                        }
                    }
                    stopDown();
                } else {
                    for (int i = 3; i >= 0; i--) {
                        int line = i + position[0];
                        if (line >= 0 && StateFang.shape[rand][i] != 0) {
                            for (int j = 0; j < xSize; j++) {
                                if (((1 << j) & (leftMath(StateFang.shape[rand][i], position[1]))) != 0) {
                                    blockList.set(line * xSize + j, randColor);
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 3; i >= 0; i--) {
                    int line = i + position[0];
                    if (line >= 0 && StateFang.shape[rand][i] != 0) {
                        for (int j = 0; j < xSize; j++) {
                            if (((1 << j) & (leftMath(StateFang.shape[rand][i], position[1]))) != 0) {
                                blockList.set(line * xSize + j, randColor);
                            }
                        }
                    }
                }
            }

            blockAdapter.setmDatas(blockList);
            blockAdapter.notifyDataSetChanged();
        }
    };
    private String gradeStr;

    private void nextTetrisShow() {
        nextTetrisList.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((1 << j) & StateFang.shape[nextRand][i]) != 0) {
                    nextTetrisList.add(nextRandColor);
                } else {
                    nextTetrisList.add(0);
                }
            }
        }
        nextTetrisAdapter.setmDatas(nextTetrisList);
        nextTetrisAdapter.notifyDataSetChanged();
    }

    private void gameOver() {
        cacheUtils.putValue("highestScore" + grade, String.valueOf(highestScore));
        stopTimer();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("游戏结束");
        dialog.setMessage("本局您获得" + score + "分");
        dialog.setPositiveButton("再来一局", (dialog1, which) -> {
            stop = 0;
            position[0] = -4;
            position[1] = 4;
            for (int i = 0; i < ySize; i++) {
                allBlock[i] = 0;
                for (int j = 0; j < xSize; j++) {
                    blockColor[i][j] = 0;
                }
            }
            rand = random.nextInt(19);
            ;
            position[0] = StateFang.initPosition[rand][1];
            position[1] = StateFang.initPosition[rand][0];
            randColor = random.nextInt(5) + 1;
            ;

            nextRand = random.nextInt(19);
            nextRandColor = random.nextInt(5) + 1;

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, timeInterval);
        });
        dialog.setNegativeButton("退出", (dialog12, which) -> finish()).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 方块数为10*15
     */
    int ySize = 15;
    int xSize = 10;
    /**
     * 所有方块用一个数组表示
     */
    int[] allBlock = new int[ySize];
    GridView tetrisView;
    List<Integer> blockList = new ArrayList<>();
    List<Integer> nextTetrisList = new ArrayList<>();
    BlockAdapter blockAdapter;

    TextView scoreTextView, maxScoreTextView, speedTextView;
    GridView nextTetrisView;
    int nextRand, nextRandColor;
    int grade;

    /**
     * 保留小数点后2位
     *
     * @return
     */
    public static String keepTwo(double b) {
        DecimalFormat format = new DecimalFormat("#0.00");
        String str = format.format(b);
        return str;
    }

    /**
     * 去掉多余的零
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_e_luo_si_fang_kuai_start;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade", 3);
        switch (grade) {
            case 1:
                timeInterval = 1200;
                break;
            case 2:
                timeInterval = 1000;
                break;
            case 3:
                timeInterval = 800;
                break;
            case 4:
                timeInterval = 600;
                break;
            case 5:
                timeInterval = 400;
                break;
            default:
                break;
        }

        cacheUtils = new CacheUtils(this, "UserInfo");
        String maxString = "";
        try {
            maxString = cacheUtils.getValue("highestScore" + grade, String.valueOf(0));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        try {
            highestScore = Integer.parseInt(maxString.toString());
        } catch (NumberFormatException e) {
            highestScore = 0;
        }

        leftMove = (Button) findViewById(R.id.left_move);
        rightMove = (Button) findViewById(R.id.right_move);
        rotateMove = (Button) findViewById(R.id.rotate_move);
        downMove = (Button) findViewById(R.id.down_move);
        scoreTextView = (TextView) findViewById(R.id.score);
        maxScoreTextView = (TextView) findViewById(R.id.maxScore);
        speedTextView = (TextView) findViewById(R.id.speed);
        levelTextView = (TextView) findViewById(R.id.level);
        maxScoreTextView.setText("最高分：" + highestScore);
        scoreTextView.setText("分数：" + score);
        if (grade == 1) {
            gradeStr = "简单";
        } else if (grade == 2) {
            gradeStr = "普通";
        } else if (grade == 3) {
            gradeStr = "中等";
        } else if (grade == 4) {
            gradeStr = "困难";
        } else if (grade == 5) {
            gradeStr = "超难";
        }
        levelTextView.setText("等级：" + gradeStr);
        speedTextView.setText("速度：" + subZeroAndDot(keepTwo(1000.0 / timeInterval)));

        leftMove.setOnClickListener(v -> {
            handler.removeCallbacks(null);
            handler.removeCallbacksAndMessages(null);

            leftMoveMethod();
        });

        leftMove.setOnLongClickListener(v -> {
            handler.postDelayed(taskleftMove, 200);//延迟调用
            handler.post(taskleftMove);//立即调用
            return false;
        });

        rotateMove.setOnClickListener(v -> {
            int nextRotate = StateFang.nextShape[rand];
            for (int i = 3; i >= 0; i--) {
                int line = i + position[0];
                //检查是否越界
                if (leftMath(StateFang.shape[nextRotate][i], position[1]) > 0x3ff) {
                    //右边界
                    return;
                } else if (StateFang.shape[nextRotate][i] > 0 && line >= ySize) {
                    //下边界
                    return;
                } else if (leftMath(leftMath(StateFang.shape[nextRotate][i], position[1]),
                        -position[1]) != StateFang.shape[nextRotate][i]) {
                    return;
                }
                //检查是否与其他方块重合
                else if (line > 0 && line < ySize &&
                        (leftMath(StateFang.shape[nextRotate][i], position[1]) & allBlock[line]) != 0) {
                    return;
                }
            }
            rand = nextRotate;
            handler.sendEmptyMessage(1);
        });

        rightMove.setOnClickListener(v -> {
            handler.removeCallbacks(null);
            handler.removeCallbacksAndMessages(null);

            rightMoveMethod();
        });

        rightMove.setOnLongClickListener(v -> {
            handler.postDelayed(taskrightMove, 200);//延迟调用
            handler.post(taskrightMove);//立即调用
            return false;
        });

        downMove.setOnClickListener(v -> {
            int c;
            int down = 1 << 10;
            for (int i = 3; i >= 0; i--) {
                int line = i + position[0];
                if (line >= 0 && StateFang.shape[rand][i] != 0) {
                    down = Math.min(down, ySize - line - 1);
                    for (int j = 0; j < xSize; j++) {
                        if (((1 << j) & (leftMath(StateFang.shape[rand][i], position[1]))) != 0) {
                            for (int k = 0; k + line < ySize; k++) {
                                if (blockColor[k + line][j] > 0) {
                                    down = Math.min(down, k - 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (down <= 0 || down == (1 << 10)) {
                return;
            } else {
                position[0] += down;
                handler.sendEmptyMessage(0);
            }
        });

        tetrisView = (GridView) findViewById(R.id.tetrisView);
        nextTetrisView = (GridView) findViewById(R.id.nextTetrisView);
        pausebtn = (Button) findViewById(R.id.pause);
        pausebtn.setOnClickListener(v -> pause());

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                blockList.add(0);
            }
        }
        blockAdapter = new BlockAdapter(this, blockList, R.layout.item_adapter);
        tetrisView.setAdapter(blockAdapter);

        random = new Random();
        rand = random.nextInt(19);
        position[0] = StateFang.initPosition[rand][1];
        position[1] = StateFang.initPosition[rand][0];
        randColor = random.nextInt(5) + 1;
        nextRand = random.nextInt(19);
        nextRandColor = random.nextInt(5) + 1;
        nextTetrisList.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((1 << j) & StateFang.shape[nextRand][i]) != 0) {
                    nextTetrisList.add(nextRandColor);
                } else {
                    nextTetrisList.add(0);
                }
            }
        }
        nextTetrisAdapter = new BlockAdapter(this, nextTetrisList, R.layout.item_adapter);
        nextTetrisView.setAdapter(nextTetrisAdapter);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, timeInterval);
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
                .init();
    }

    private Runnable taskleftMove = new Runnable() {
        public void run() {
            handler.postDelayed(this, 200);//设置循环时间，此处是5秒
            leftMoveMethod();
        }
    };

    private Runnable taskrightMove = new Runnable() {
        public void run() {
            handler.postDelayed(this, 200);//设置循环时间，此处是5秒
            rightMoveMethod();
        }
    };

    private void rightMoveMethod() {
        for (int i = 3; i >= 0; i--) {
            if (((leftMath(StateFang.shape[rand][i], position[1])) << 1) > 0x3ff
            ) {
                //如果越界了
                return;
            }
        }
        for (int i = 3; i >= 0; i--) {
            int line = i + position[0];
            if (line >= 0 && StateFang.shape[rand][i] != 0) {
                if ((allBlock[line] & (leftMath(StateFang.shape[rand][i], position[1]) << 1)) != 0) {

                    return;
                }
            }

        }
        position[1]++;
        handler.sendEmptyMessage(1);
    }

    private void leftMoveMethod() {
        //左移实际是数字的右移
        //左移需要判断是否能够左移
        for (int i = 3; i >= 0; i--) {
            if ((((leftMath(StateFang.shape[rand][i], position[1])) >> 1) << 1)
                    != (leftMath(StateFang.shape[rand][i], position[1]))) {
                //如果越界了
                return;
            }
        }
        for (int i = 3; i >= 0; i--) {
            int line = i + position[0];
            if (line >= 0 && StateFang.shape[rand][i] != 0) {
                if ((allBlock[line] & (leftMath(StateFang.shape[rand][i], position[1]) >> 1)) != 0) {
                    return;
                }
            }

        }
        position[1]--;
        handler.sendEmptyMessage(1);
    }

    boolean isPause = false;

    private void pause() {
        isPause = !isPause;
        if (isPause) {
            stopTimer();
            pausebtn.setText("继续");
            leftMove.setEnabled(false);
            rightMove.setEnabled(false);
            rotateMove.setEnabled(false);
            downMove.setEnabled(false);
        } else {
            startTimer();
            pausebtn.setText("暂停");
            leftMove.setEnabled(true);
            rightMove.setEnabled(true);
            rotateMove.setEnabled(true);
            downMove.setEnabled(true);
        }
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, timeInterval);
    }


    int leftMath(int a, int b) {
        //之前一直不显示是因为这里搞反了，所出来的都是0,所以每次改一点代码都需要测试一下才能保存
        if (b < 0) {
            return a >> -b;
        } else {
            return a << b;
        }
    }

    // 停止定时器
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    void stopDown() {
        // 写入、消除、重置
        for (int i = 3; i >= 0; i--) {

            int line = i + position[0];
            if (line >= 0 && StateFang.shape[rand][i] != 0) {
                allBlock[line] += (leftMath(StateFang.shape[rand][i], position[1]));
                for (int j = 0; j < xSize; j++) {
                    if (((1 << j) & (leftMath(StateFang.shape[rand][i], position[1]))) != 0) {
                        blockColor[line][j] = randColor;
                    }
                }
            }

        }

        for (int i = ySize - 1; i >= 0; ) {
            if (allBlock[i] == 0x3ff) {
                score++;
                scoreTextView.setText("分数：" + score);
                for (int j = i - 1; j >= 0; j--) {
                    allBlock[j + 1] = allBlock[j];
                    for (int k = 0; k < xSize; k++) {
                        blockColor[j + 1][k] = blockColor[j][k];
                    }
                }
                allBlock[0] = 0;
                for (int j = 0; j < xSize; j++) {
                    blockColor[0][j] = 0;
                }
            } else {
                i--;
            }
        }

        if (allBlock[0] != 0) {
            if (score > highestScore) {
                cacheUtils.getValue("highestScore" + grade, score + "");
                highestScore = score;
                maxScoreTextView.setText("最高分：" + highestScore);
                scoreTextView.setText("分数：" + score);

            }
            gameOver();
        }

        rand = nextRand;
        position[0] = StateFang.initPosition[rand][1];
        position[1] = StateFang.initPosition[rand][0];
        randColor = nextRandColor;

        nextRand = random.nextInt(19);
        nextRandColor = random.nextInt(5) + 1;
        nextTetrisShow();
    }

    private boolean mBackKeyPressed = false;//记录是否有首次按键

    //点击两次退出程序
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则清除第一次记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            //结束Activity
            finish();
        }
    }
}