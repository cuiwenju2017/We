package com.cwj.we.module.chenghu;

import android.widget.Button;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 称呼计算器
 */
public class ChengHuJiSuanQiActivity extends BaseActivity {

    private TextView mInputTextView;
    private TextView mResultTextView;
    private Button mACButton, mBackButton, mEqualButton, mEachButton;
    private Button mHusbandButton, mWifeButton;
    private Button mFatherButton, mMotherButton;
    private Button mOldBrotherButton, mLittleBrotherButton;
    private Button mOldSisterButton, mLittleSisterButton;
    private Button mSonButton, mDaughterButton;
    private StringBuffer currentRelative = new StringBuffer();// 存储输入数据
    private SqList tempCurrentRelative = null;//存储用户当前输入且化简后的亲戚关系
    private Stack<SqList> backRelative = new Stack<SqList>();
    private String eachRelative = "";
    private String sex;
    private HashMap<String, String> correspondingAppellation = new HashMap<String, String>(1200);
    private HashMap<String, String> eachAppellation = new HashMap<String, String>(1200);
    private final static int MAXSIZE = 22;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cheng_hu_ji_suan_qi;
    }

    @Override
    public void initData() {
        currentRelative.delete(0, currentRelative.length());
        currentRelative.append("我");
        /*----------------------------最简亲戚关系----------------------------*/
        correspondingAppellation.put("", "自己");
        correspondingAppellation.put("f", "爸爸");
        correspondingAppellation.put("f,f", "爷爷");
        correspondingAppellation.put("f,f,f", "曾祖父");
        correspondingAppellation.put("f,f,f,f", "高祖父");
        correspondingAppellation.put("f,f,f,f,f", "天祖父");
        correspondingAppellation.put("f,f,f,f,f,f", "烈祖父");
        correspondingAppellation.put("f,f,f,f,f,m", "烈祖母");
        correspondingAppellation.put("f,f,f,f,f,f,f", "太祖父");
        correspondingAppellation.put("f,f,f,f,f,f,m", "太祖母");
        correspondingAppellation.put("f,f,f,f,m", "天祖母");
        correspondingAppellation.put("f,f,f,f,ob", "伯高祖父");
        correspondingAppellation.put("f,f,f,f,ob,w", "伯高祖母");
        correspondingAppellation.put("f,f,f,f,lb", "叔高祖父");
        correspondingAppellation.put("f,f,f,f,lb,w", "叔高祖母");
        correspondingAppellation.put("f,f,f,f,os", "姑高祖母");
        correspondingAppellation.put("f,f,f,f,ls", "姑高祖母");
        correspondingAppellation.put("f,f,f,f,os,h", "姑高祖父");
        correspondingAppellation.put("f,f,f,f,ls,h", "姑高祖父");
        correspondingAppellation.put("f,f,f,m", "高祖母");
        correspondingAppellation.put("f,f,f,m,os", "姨高祖母");
        correspondingAppellation.put("f,f,f,m,ls", "姨高祖母");
        correspondingAppellation.put("f,f,f,m,os,h", "姨高祖父");
        correspondingAppellation.put("f,f,f,m,ls,h", "姨高祖父");
        correspondingAppellation.put("f,f,f,ob", "曾伯祖父");
        correspondingAppellation.put("f,f,f,ob,w", "曾伯祖母");
        correspondingAppellation.put("f,f,f,lb", "曾叔祖父");
        correspondingAppellation.put("f,f,f,lb,w", "曾叔祖母");
        correspondingAppellation.put("f,f,f,ob,s", "堂伯祖父/堂叔祖父");
        correspondingAppellation.put("f,f,f,lb,s", "堂伯祖父/堂叔祖父");
        correspondingAppellation.put("f,f,f,ob,s,w", "堂伯祖母/堂叔祖母");
        correspondingAppellation.put("f,f,f,lb,s,w", "堂伯祖母/堂叔祖母");
        correspondingAppellation.put("f,f,f,ob,s,s", "从伯父/从叔父");
        correspondingAppellation.put("f,f,f,lb,s,s", "从伯父/从叔父");
        correspondingAppellation.put("f,f,f,ob,s,s,w", "从伯母/从叔母");
        correspondingAppellation.put("f,f,f,lb,s,s,w", "从伯母/从叔母");
        correspondingAppellation.put("f,f,f,ob,s,s,s", "族兄/族弟");
        correspondingAppellation.put("f,f,f,lb,s,s,s", "族兄/族弟");
        correspondingAppellation.put("f,f,f,ob,d", "堂姑祖母");
        correspondingAppellation.put("f,f,f,lb,d", "堂姑祖母");
        correspondingAppellation.put("f,f,f,ob,d,h", "堂姑祖父");
        correspondingAppellation.put("f,f,f,lb,d,h", "堂姑祖父");
        correspondingAppellation.put("f,f,f,os", "曾祖姑母");
        correspondingAppellation.put("f,f,f,ls", "曾祖姑母");
        correspondingAppellation.put("f,f,f,os,h", "曾祖姑丈");
        correspondingAppellation.put("f,f,f,ls,h", "曾祖姑丈");
        correspondingAppellation.put("f,f,f,os,s", "表伯祖父/表叔祖父");
        correspondingAppellation.put("f,f,f,ls,s", "表伯祖父/表叔祖父");
        correspondingAppellation.put("f,f,f,os,s,w", "表伯祖母/表叔祖母");
        correspondingAppellation.put("f,f,f,ls,s,w", "表伯祖母/表叔祖母");
        correspondingAppellation.put("f,f,f,os,d", "表姑祖母");
        correspondingAppellation.put("f,f,f,ls,d", "表姑祖母");
        correspondingAppellation.put("f,f,f,os,d,h", "表姑祖父");
        correspondingAppellation.put("f,f,f,ls,d,h", "表姑祖父");
        correspondingAppellation.put("f,f,m", "曾祖母");
        correspondingAppellation.put("f,f,m,f", "高外祖父");
        correspondingAppellation.put("f,f,m,m", "高外祖母");
        correspondingAppellation.put("f,f,m,ob", "舅曾祖父");
        correspondingAppellation.put("f,f,m,lb", "舅曾祖父");
        correspondingAppellation.put("f,f,m,ob,w", "舅曾祖母");
        correspondingAppellation.put("f,f,m,lb,w", "舅曾祖母");
        correspondingAppellation.put("f,f,m,ob,s", "表伯祖父/表叔祖父");
        correspondingAppellation.put("f,f,m,ob,s,w", "表伯祖母/表叔祖母");
        correspondingAppellation.put("f,f,m,lb,s", "表伯祖父/表叔祖父");
        correspondingAppellation.put("f,f,m,lb,s,w", "表伯祖母/表叔祖母");
        correspondingAppellation.put("f,f,m,os,s", "表伯祖父/表叔祖父");
        correspondingAppellation.put("f,f,m,os,s,w", "表伯祖母/表叔祖母");
        correspondingAppellation.put("f,f,m,ls,s", "表伯祖父/表叔祖父");
        correspondingAppellation.put("f,f,m,ls,s,w", "表伯祖母/表叔祖母");
        correspondingAppellation.put("f,f,m,ob,d", "表姑祖母");
        correspondingAppellation.put("f,f,m,lb,d", "表姑祖母");
        correspondingAppellation.put("f,f,m,ob,d,h", "表姑祖父");
        correspondingAppellation.put("f,f,m,lb,d,h", "表姑祖父");
        correspondingAppellation.put("f,f,m,os", "姨曾祖母");
        correspondingAppellation.put("f,f,m,ls", "姨曾祖母");
        correspondingAppellation.put("f,f,m,os,h", "姨曾祖父");
        correspondingAppellation.put("f,f,m,ls,h", "姨曾祖父");
        correspondingAppellation.put("f,f,m,os,d", "表姑祖母");
        correspondingAppellation.put("f,f,m,ls,d", "表姑祖母");
        correspondingAppellation.put("f,f,m,os,d,h", "表姑祖父");
        correspondingAppellation.put("f,f,m,ls,d,h", "表姑祖父");
        correspondingAppellation.put("f,f,ob,s", "堂伯/堂叔");
        correspondingAppellation.put("f,f,ob,s,w", "堂伯母/堂叔母");
        correspondingAppellation.put("f,f,lb,ls", "堂伯/堂叔");
        correspondingAppellation.put("f,f,lb,s,w", "堂伯母/堂叔母");
        correspondingAppellation.put("f,f,ob,s,s", "从兄/从弟");
        correspondingAppellation.put("f,f,ob,s,s,w", "从嫂/从弟妹");
        correspondingAppellation.put("f,f,lb,s,s", "从兄/从弟");
        correspondingAppellation.put("f,f,lb,s,s,w", "从嫂/从弟妹");
        correspondingAppellation.put("f,f,ob", "堂祖父");
        correspondingAppellation.put("f,f,lb", "堂祖父");
        correspondingAppellation.put("f,f,ob,w", "堂祖母");
        correspondingAppellation.put("f,f,lb,w", "堂祖母");
        correspondingAppellation.put("f,f,ob,s,w", "堂婶");
        correspondingAppellation.put("f,f,lb,s,w", "堂婶");
        correspondingAppellation.put("f,f,ob,s,s,s", "从侄");
        correspondingAppellation.put("f,f,lb,s,s,s", "从侄");
        correspondingAppellation.put("f,f,ob,s,s,s,w", "从侄媳妇");
        correspondingAppellation.put("f,f,lb,s,s,s,w", "从侄媳妇");
        correspondingAppellation.put("f,f,ob,s,s,s,s", "从侄孙");
        correspondingAppellation.put("f,f,lb,s,s,s,s", "从侄孙");
        correspondingAppellation.put("f,f,ob,s,s,s,d", "从侄孙女");
        correspondingAppellation.put("f,f,lb,s,s,s,d", "从侄孙女");
        correspondingAppellation.put("f,f,ob,s,s,d", "从侄女");
        correspondingAppellation.put("f,f,lb,s,s,d", "从侄女");
        correspondingAppellation.put("f,f,ob,s,s,d,h", "从侄女婿");
        correspondingAppellation.put("f,f,lb,s,s,d,h", "从侄女婿");
        correspondingAppellation.put("f,f,ob,s,d", "从姐/从妹");
        correspondingAppellation.put("f,f,ob,s,d,h", "从姐夫/从妹夫");
        correspondingAppellation.put("f,f,lb,s,d", "从姐/从妹");
        correspondingAppellation.put("f,f,lb,s,d,h", "从姐夫/从妹夫");
        correspondingAppellation.put("f,f,ob,d", "堂姑");
        correspondingAppellation.put("f,f,lb,d", "堂姑");
        correspondingAppellation.put("f,f,ob,d,h", "堂姑丈");
        correspondingAppellation.put("f,f,lb,d,h", "堂姑丈");
        correspondingAppellation.put("f,f,ob,d,s", "堂姑表兄/堂姑表弟");
        correspondingAppellation.put("f,f,lb,d,s", "堂姑表兄/堂姑表弟");
        correspondingAppellation.put("f,f,ob,d,d", "堂姑表姐/堂姑表妹");
        correspondingAppellation.put("f,f,lb,d,d", "堂姑表姐/堂姑表妹");
        correspondingAppellation.put("f,f,ob", "伯祖父");
        correspondingAppellation.put("f,f,ob,w", "伯祖母");
        correspondingAppellation.put("f,f,lb", "叔祖父");
        correspondingAppellation.put("f,f,lb,w", "叔祖母");
        correspondingAppellation.put("f,f,os", "姑奶奶");
        correspondingAppellation.put("f,f,ls", "姑奶奶");
        correspondingAppellation.put("f,f,os,h", "姑爷爷");
        correspondingAppellation.put("f,f,ls,h", "姑爷爷");
        correspondingAppellation.put("f,f,os,s", "姑表伯父/姑表叔父");
        correspondingAppellation.put("f,f,os,s,w", "姑表伯母/姑表叔母");
        correspondingAppellation.put("f,f,ls,s", "姑表伯父/姑表叔父");
        correspondingAppellation.put("f,f,ls,s,w", "姑表伯母/姑表叔母");
        correspondingAppellation.put("f,f,os,s,s", "从表兄弟");
        correspondingAppellation.put("f,f,ls,s,s", "从表兄弟");
        correspondingAppellation.put("f,f,os,s,d", "从表姐妹");
        correspondingAppellation.put("f,f,ls,s,d", "从表姐妹");
        correspondingAppellation.put("f,f,os,d", "姑表姑母");
        correspondingAppellation.put("f,f,ls,d", "姑表姑母");
        correspondingAppellation.put("f,f,os,d,h", "姑表姑父");
        correspondingAppellation.put("f,f,ls,d,h", "姑表姑父");
        correspondingAppellation.put("f,f,os,d,s", "从表兄弟");
        correspondingAppellation.put("f,f,ls,d,s", "从表兄弟");
        correspondingAppellation.put("f,f,os,d,d", "从表姐妹");
        correspondingAppellation.put("f,f,ls,d,d", "从表姐妹");
        correspondingAppellation.put("f,m", "奶奶");
        correspondingAppellation.put("f,m,f", "曾外祖父");
        correspondingAppellation.put("f,m,f,f", "曾外曾祖父");
        correspondingAppellation.put("f,m,f,m", "曾外曾祖母");
        correspondingAppellation.put("f,m,f,ob,s", "堂舅祖父");
        correspondingAppellation.put("f,m,f,lb,s", "堂舅祖父");
        correspondingAppellation.put("f,m,f,ob,s,w", "堂舅祖母");
        correspondingAppellation.put("f,m,f,lb,s,w", "堂舅祖母");
        correspondingAppellation.put("f,m,f,ob,d", "堂姨祖母");
        correspondingAppellation.put("f,m,f,lb,d", "堂姨祖母");
        correspondingAppellation.put("f,m,f,ob,d,h", "堂姨祖父");
        correspondingAppellation.put("f,m,f,lb,d,h", "堂姨祖父");
        correspondingAppellation.put("f,m,f,ob", "伯曾外祖父");
        correspondingAppellation.put("f,m,f,ob,w", "伯曾外祖母");
        correspondingAppellation.put("f,m,f,lb", "叔曾外祖父");
        correspondingAppellation.put("f,m,f,lb,w", "叔曾外祖母");
        correspondingAppellation.put("f,m,f,os", "姑曾外祖母");
        correspondingAppellation.put("f,m,f,ls", "姑曾外祖母");
        correspondingAppellation.put("f,m,f,os,h", "姑曾外祖父");
        correspondingAppellation.put("f,m,f,ls,h", "姑曾外祖父");
        correspondingAppellation.put("f,m,f,os,s", "表舅祖父");
        correspondingAppellation.put("f,m,f,ls,s", "表舅祖父");
        correspondingAppellation.put("f,m,f,os,s,w", "表舅祖母");
        correspondingAppellation.put("f,m,f,ls,s,w", "表舅祖母");
        correspondingAppellation.put("f,m,m", "曾外祖母");
        correspondingAppellation.put("f,m,m,f", "曾外曾外祖父");
        correspondingAppellation.put("f,m,m,m", "曾外曾外祖母");
        correspondingAppellation.put("f,m,m,ob", "舅曾外祖父");
        correspondingAppellation.put("f,m,m,lb", "舅曾外祖父");
        correspondingAppellation.put("f,m,m,ob,w", "舅曾外祖母");
        correspondingAppellation.put("f,m,m,lb,w", "舅曾外祖母");
        correspondingAppellation.put("f,m,m,ob,s", "表舅祖父");
        correspondingAppellation.put("f,m,m,lb,s", "表舅祖父");
        correspondingAppellation.put("f,m,m,ob,s,w", "表舅祖母");
        correspondingAppellation.put("f,m,m,lb,s,w", "表舅祖母");
        correspondingAppellation.put("f,m,m,ob,d", "表姨祖母");
        correspondingAppellation.put("f,m,m,lb,d", "表姨祖母");
        correspondingAppellation.put("f,m,m,ob,d,h", "表姨祖父");
        correspondingAppellation.put("f,m,m,lb,d,h", "表姨祖父");
        correspondingAppellation.put("f,m,m,os", "姨曾外祖母");
        correspondingAppellation.put("f,m,m,ls", "姨曾外祖母");
        correspondingAppellation.put("f,m,m,os,h", "姨曾外祖父");
        correspondingAppellation.put("f,m,m,ls,h", "姨曾外祖父");
        correspondingAppellation.put("f,m,m,os,d", "表姨祖母");
        correspondingAppellation.put("f,m,m,ls,d", "表姨祖母");
        correspondingAppellation.put("f,m,m,os,d,h", "表姨祖父");
        correspondingAppellation.put("f,m,m,ls,d,h", "表姨祖父");
        correspondingAppellation.put("f,m,m,os,s", "表舅祖父");
        correspondingAppellation.put("f,m,m,ls,s", "表舅祖父");
        correspondingAppellation.put("f,m,m,os,s,w", "表舅祖母");
        correspondingAppellation.put("f,m,m,ls,s,w", "表舅祖母");
        correspondingAppellation.put("f,m,m,os,d", "表姨祖母");
        correspondingAppellation.put("f,m,m,ls,d", "表姨祖母");
        correspondingAppellation.put("f,m,m,os,d,h", "表姨祖父");
        correspondingAppellation.put("f,m,m,ls,d,h", "表姨祖父");
        correspondingAppellation.put("f,m,ob", "舅公");
        correspondingAppellation.put("f,m,ob,w", "舅婆");
        correspondingAppellation.put("f,m,ob,s", "舅表伯父/舅表叔父");
        correspondingAppellation.put("f,m,ob,s,w", "舅表伯母/舅表叔母");
        correspondingAppellation.put("f,m,lb,s", "舅表伯父/舅表叔父");
        correspondingAppellation.put("f,m,lb,s,w", "舅表伯母/舅表叔母");
        correspondingAppellation.put("f,m,ob,s,s", "从表兄弟");
        correspondingAppellation.put("f,m,ob,s,d", "从表姐妹");
        correspondingAppellation.put("f,m,ob,d", "舅表姑母");
        correspondingAppellation.put("f,m,ob,d,h", "舅表姑父");
        correspondingAppellation.put("f,m,ob,d,s", "从表兄弟");
        correspondingAppellation.put("f,m,ob,d,d", "从表姐妹");
        correspondingAppellation.put("f,m,os", "姨奶奶");
        correspondingAppellation.put("f,m,os,h", "姨爷爷");
        correspondingAppellation.put("f,m,os,s", "姨表伯父/姨表叔父");
        correspondingAppellation.put("f,m,os,s,w", "姨表伯母/姨表叔母");
        correspondingAppellation.put("f,m,ls,s", "姨表伯父/姨表叔父");
        correspondingAppellation.put("f,m,ls,,w", "姨表伯母/姨表叔母");
        correspondingAppellation.put("f,m,os,s,s", "从表兄弟");
        correspondingAppellation.put("f,m,os,s,d", "从表姐妹");
        correspondingAppellation.put("f,m,os,d", "姨表姑母");
        correspondingAppellation.put("f,m,os,d,h", "姨表姑父");
        correspondingAppellation.put("f,m,os,d,s", "从表兄弟");
        correspondingAppellation.put("f,m,os,d,d", "从表姐妹");
        correspondingAppellation.put("f,m,lb", "舅公");
        correspondingAppellation.put("f,m,lb,w", "舅婆");
        correspondingAppellation.put("f,m,lb,s,s", "从表兄弟");
        correspondingAppellation.put("f,m,lb,s,d", "从表姐妹");
        correspondingAppellation.put("f,m,lb,d", "舅表姑母");
        correspondingAppellation.put("f,m,lb,d,h", "舅表姑父");
        correspondingAppellation.put("f,m,lb,d,s", "从表兄弟");
        correspondingAppellation.put("f,m,lb,d,d", "从表姐妹");
        correspondingAppellation.put("f,m,ls", "姨奶奶");
        correspondingAppellation.put("f,m,ls,h", "姨爷爷");
        correspondingAppellation.put("f,m,ls,s,s", "从表兄弟");
        correspondingAppellation.put("f,m,ls,s,d", "从表姐妹");
        correspondingAppellation.put("f,m,ls,d", "姨表姑母");
        correspondingAppellation.put("f,m,ls,d,h", "姨表姑父");
        correspondingAppellation.put("f,m,ls,d,s", "从表兄弟");
        correspondingAppellation.put("f,m,ls,d,d", "从表姐妹");
        correspondingAppellation.put("f,ob,w,f", "姻伯公");
        correspondingAppellation.put("f,ob,w,m", "姻伯婆");
        correspondingAppellation.put("f,ob,w,ob", "姻世伯");
        correspondingAppellation.put("f,ob,w,ob,w", "姻伯母");
        correspondingAppellation.put("f,ob,w,os", "姻伯母");
        correspondingAppellation.put("f,ob,w,os,h", "姻世伯");
        correspondingAppellation.put("f,ob,w,lb", "姻世伯");
        correspondingAppellation.put("f,ob,w,lb,w", "姻伯母");
        correspondingAppellation.put("f,ob,w,ls", "姻伯母");
        correspondingAppellation.put("f,ob,w,ls,h", "姻世伯");
        correspondingAppellation.put("f,os,s", "姑表哥/姑表弟");
        correspondingAppellation.put("f,os,s,w", "姑表嫂/姑表弟媳");
        correspondingAppellation.put("f,ls,s", "姑表哥/姑表弟");
        correspondingAppellation.put("f,ls,s,w", "姑表嫂/姑表弟媳");
        correspondingAppellation.put("f,ob,s,s", "堂侄");
        correspondingAppellation.put("f,ob,s,s,w", "堂侄媳妇");
        correspondingAppellation.put("f,ob,s,s,s", "堂侄孙");
        correspondingAppellation.put("f,ob,s,s,s,w", "堂侄孙媳妇");
        correspondingAppellation.put("f,ob,s,s,d", "堂侄孙女");
        correspondingAppellation.put("f,ob,s,s,d,h", "堂侄孙女婿");
        correspondingAppellation.put("f,ob,s,d", "堂侄女");
        correspondingAppellation.put("f,ob,s,d,h", "堂侄女婿");
        correspondingAppellation.put("f,ob,d,s", "堂外甥");
        correspondingAppellation.put("f,ob,d,d", "堂外甥女");
        correspondingAppellation.put("f,lb,w,f", "姻伯公");
        correspondingAppellation.put("f,lb,w,m", "姻伯婆");
        correspondingAppellation.put("f,lb,w,ob", "姻世伯");
        correspondingAppellation.put("f,lb,w,ob,w", "姻伯母");
        correspondingAppellation.put("f,lb,w,os", "姻伯母");
        correspondingAppellation.put("f,lb,w,os,h", "姻世伯");
        correspondingAppellation.put("f,lb,w,lb", "姻世伯");
        correspondingAppellation.put("f,lb,w,lb,w", "姻伯母");
        correspondingAppellation.put("f,lb,w,ls", "姻伯母");
        correspondingAppellation.put("f,lb,w,ls,h", "姻世伯");
        correspondingAppellation.put("f,lb,s,s", "堂侄");
        correspondingAppellation.put("f,lb,s,s,w", "堂侄媳妇");
        correspondingAppellation.put("f,lb,s,s,s", "堂侄孙");
        correspondingAppellation.put("f,lb,s,s,s,w", "堂侄孙媳妇");
        correspondingAppellation.put("f,lb,s,s,d", "堂侄孙女");
        correspondingAppellation.put("f,lb,s,s,d,h", "堂侄孙女婿");
        correspondingAppellation.put("f,lb,s,d", "堂侄女");
        correspondingAppellation.put("f,lb,s,d,h", "堂侄女婿");
        correspondingAppellation.put("f,lb,d", "堂姐/堂妹");
        correspondingAppellation.put("f,lb,s", "堂兄/堂弟");
        correspondingAppellation.put("f,lb,d,s", "堂外甥");
        correspondingAppellation.put("f,lb,d,d", "堂外甥女");
        correspondingAppellation.put("f,ob", "伯父");
        correspondingAppellation.put("f,ob,w", "伯母");
        correspondingAppellation.put("f,ob,d", "堂姐/堂妹");
        correspondingAppellation.put("f,ob,s", "堂兄/堂弟");
        correspondingAppellation.put("f,lb", "叔叔");
        correspondingAppellation.put("f,lb,w", "婶婶");
        correspondingAppellation.put("f,os", "姑妈");
        correspondingAppellation.put("f,os,h", "姑丈");
        correspondingAppellation.put("f,os,h,f", "姻伯公");
        correspondingAppellation.put("f,os,h,m", "姻伯婆");
        correspondingAppellation.put("f,os,h,ob", "姻世伯");
        correspondingAppellation.put("f,os,h,ob,w", "姻伯母");
        correspondingAppellation.put("f,os,h,os", "姻伯母");
        correspondingAppellation.put("f,os,h,os,h", "姻世伯");
        correspondingAppellation.put("f,os,h,lb", "姻世伯");
        correspondingAppellation.put("f,os,h,lb,w", "姻伯母");
        correspondingAppellation.put("f,os,h,ls", "姻伯母");
        correspondingAppellation.put("f,os,h,ls,h", "姻世伯");
        correspondingAppellation.put("f,os,s,s", "表侄");
        correspondingAppellation.put("f,os,s,s,s", "表侄孙");
        correspondingAppellation.put("f,os,s,s,s,w", "表侄孙媳妇");
        correspondingAppellation.put("f,os,s,s,d", "表侄孙女");
        correspondingAppellation.put("f,os,s,s,d,h", "表侄孙女婿");
        correspondingAppellation.put("f,os,s,d", "表侄女");
        correspondingAppellation.put("f,os,s,d,s", "外表侄孙");
        correspondingAppellation.put("f,os,s,d,s,w", "外表侄孙媳妇");
        correspondingAppellation.put("f,os,s,d,d", "外表侄孙女");
        correspondingAppellation.put("f,os,s,d,d,h", "外表侄孙女婿");
        correspondingAppellation.put("f,os,d", "姑表姐/姑表妹");
        correspondingAppellation.put("f,os,d,h", "姑表姐夫/姑表妹夫");
        correspondingAppellation.put("f,ls,d", "姑表姐/姑表妹");
        correspondingAppellation.put("f,ls,d,h", "姑表姐夫/姑表妹夫");
        correspondingAppellation.put("f,os,d,s", "表外甥");
        correspondingAppellation.put("f,os,d,d", "表外甥女");
        correspondingAppellation.put("f,ls", "姑妈");
        correspondingAppellation.put("f,ls,h", "姑丈");
        correspondingAppellation.put("f,ls,h,f", "姻伯公");
        correspondingAppellation.put("f,ls,h,m", "姻伯婆");
        correspondingAppellation.put("f,ls,h,ob", "姻世伯");
        correspondingAppellation.put("f,ls,h,ob,w", "姻伯母");
        correspondingAppellation.put("f,ls,h,os", "姻伯母");
        correspondingAppellation.put("f,ls,h,os,h", "姻世伯");
        correspondingAppellation.put("f,ls,h,lb", "姻世伯");
        correspondingAppellation.put("f,ls,h,lb,w", "姻伯母");
        correspondingAppellation.put("f,ls,h,ls", "姻伯母");
        correspondingAppellation.put("f,ls,h,ls,h", "姻世伯");
        correspondingAppellation.put("f,ls,s,s", "表侄");
        correspondingAppellation.put("f,ls,s,s,s", "表侄孙");
        correspondingAppellation.put("f,ls,s,s,s,w", "表侄孙媳妇");
        correspondingAppellation.put("f,ls,s,s,d", "表侄孙女");
        correspondingAppellation.put("f,ls,s,s,d,h", "表侄孙女婿");
        correspondingAppellation.put("f,ls,s,d", "表侄女");
        correspondingAppellation.put("f,ls,s,d,s", "外表侄孙");
        correspondingAppellation.put("f,ls,s,d,s,w", "外表侄孙媳妇");
        correspondingAppellation.put("f,ls,s,d,d", "外表侄孙女");
        correspondingAppellation.put("f,ls,s,d,d,h", "外表侄孙女婿");
        correspondingAppellation.put("f,ls,d,s", "表外甥");
        correspondingAppellation.put("f,ls,d,d", "表外甥女");
        correspondingAppellation.put("f,os", "姑妈");
        correspondingAppellation.put("f,ls", "姑妈");
        correspondingAppellation.put("m", "妈妈");
        correspondingAppellation.put("m,f", "外公");
        correspondingAppellation.put("m,f,f", "外曾祖父");
        correspondingAppellation.put("m,f,f,f", "外高祖父");
        correspondingAppellation.put("m,f,f,m", "外高祖母");
        correspondingAppellation.put("m,f,f,ob,s", "堂伯外祖父/堂叔外祖父");
        correspondingAppellation.put("m,f,f,ob,s,w", "堂伯外祖母/堂叔外祖母");
        correspondingAppellation.put("m,f,f,lb,s", "堂伯外祖父/堂叔外祖父");
        correspondingAppellation.put("m,f,f,lb,s,w", "堂伯外祖母/堂叔外祖母");
        correspondingAppellation.put("m,f,f,ob,d", "堂姑外祖母");
        correspondingAppellation.put("m,f,f,ob,d,h", "堂姑外祖父");
        correspondingAppellation.put("m,f,f,lb,d", "堂姑外祖母");
        correspondingAppellation.put("m,f,f,lb,d,h", "堂姑外祖父");
        correspondingAppellation.put("m,f,f,ob", "伯外曾祖父");
        correspondingAppellation.put("m,f,f,ob,w", "伯外曾祖母");
        correspondingAppellation.put("m,f,f,lb", "叔外曾祖父");
        correspondingAppellation.put("m,f,f,lb,w", "叔外曾祖母");
        correspondingAppellation.put("m,f,f,os", "姑外曾祖母");
        correspondingAppellation.put("m,f,f,os,h", "姑外曾祖父");
        correspondingAppellation.put("m,f,f,os,s", "表伯外祖父/表叔外祖父");
        correspondingAppellation.put("m,f,f,os,s,w", "表伯外祖母/表叔外祖母");
        correspondingAppellation.put("m,f,f,ls,s", "表伯外祖父/表叔外祖父");
        correspondingAppellation.put("m,f,f,ls,s,w", "表伯外祖母/表叔外祖母");
        correspondingAppellation.put("m,f,f,os,d", "表姑外祖母");
        correspondingAppellation.put("m,f,f,os,d,h", "表姑外祖父");
        correspondingAppellation.put("m,f,f,ls", "姑外曾祖母");
        correspondingAppellation.put("m,f,f,ls,h", "姑外曾祖父");
        correspondingAppellation.put("m,f,f,ls,d", "表姑外祖母");
        correspondingAppellation.put("m,f,f,ls,d,h", "表姑外祖父");
        correspondingAppellation.put("m,f,m", "外曾祖母");
        correspondingAppellation.put("m,f,m,f", "外高外祖父");
        correspondingAppellation.put("m,f,m,m", "外高外祖母");
        correspondingAppellation.put("m,f,m,ob", "舅外曾祖父");
        correspondingAppellation.put("m,f,m,ob,w", "舅外曾祖母");
        correspondingAppellation.put("m,f,m,ob,d", "表姑外祖母");
        correspondingAppellation.put("m,f,m,ob,d,h", "表姑外祖父");
        correspondingAppellation.put("m,f,m,os", "姨外曾祖母");
        correspondingAppellation.put("m,f,m,os,h", "姨外曾祖父");
        correspondingAppellation.put("m,f,m,os,d", "表姑外祖母");
        correspondingAppellation.put("m,f,m,os,d,h", "表姑外祖父");
        correspondingAppellation.put("m,f,ob", "大姥爷/小姥爷");
        correspondingAppellation.put("m,f,ob,s", "堂舅");
        correspondingAppellation.put("m,f,ob,s,w", "堂舅妈");
        correspondingAppellation.put("m,f,ob,s,s", "堂舅表兄/堂舅表弟");
        correspondingAppellation.put("m,f,lb,s,s", "堂舅表兄/堂舅表弟");
        correspondingAppellation.put("m,f,ob,s,d", "堂舅表姐/堂舅表妹");
        correspondingAppellation.put("m,f,lb,s,d", "堂舅表姐/堂舅表妹");
        correspondingAppellation.put("m,f,ob,d", "堂姨");
        correspondingAppellation.put("m,f,ob,d,h", "堂姨丈");
        correspondingAppellation.put("m,f,ob,d,s", "堂姨表兄/堂姨表弟");
        correspondingAppellation.put("m,f,ob,d,d", "堂姨表姐/堂姨表妹");
        correspondingAppellation.put("m,f,lb,d,s", "堂姨表兄/堂姨表弟");
        correspondingAppellation.put("m,f,lb,d,d", "堂姨表姐/堂姨表妹");
        correspondingAppellation.put("m,f,m,lb", "舅外曾祖父");
        correspondingAppellation.put("m,f,m,lb,w", "舅外曾祖母");
        correspondingAppellation.put("m,f,m,lb,d", "表姑外祖母");
        correspondingAppellation.put("m,f,m,lb,d,h", "表姑外祖父");
        correspondingAppellation.put("m,f,m,ls", "姨外曾祖母");
        correspondingAppellation.put("m,f,m,ls,h", "姨外曾祖父");
        correspondingAppellation.put("m,f,m,ls,d", "表姑外祖母");
        correspondingAppellation.put("m,f,m,ls,d,h", "表姑外祖父");
        correspondingAppellation.put("m,f,lb", "大姥爷/小姥爷");
        correspondingAppellation.put("m,f,lb,s", "堂舅");
        correspondingAppellation.put("m,f,lb,s,w", "堂舅妈");
        correspondingAppellation.put("m,f,lb,d", "堂姨");
        correspondingAppellation.put("m,f,lb,d,h", "堂姨丈");
        correspondingAppellation.put("m,f,ob", "伯外祖父");
        correspondingAppellation.put("m,f,ob,w", "伯外祖母");
        correspondingAppellation.put("m,f,lb", "叔外祖父");
        correspondingAppellation.put("m,f,lb,w", "叔外祖母");
        correspondingAppellation.put("m,f,os", "姑姥姥");
        correspondingAppellation.put("m,f,os,h", "姑姥爷");
        correspondingAppellation.put("m,f,os,s", "姑表舅父");
        correspondingAppellation.put("m,f,os,s,w", "姑表舅母");
        correspondingAppellation.put("m,f,os,s,s", "从表兄弟");
        correspondingAppellation.put("m,f,os,s,d", "从表姐妹");
        correspondingAppellation.put("m,f,os,d", "姑表姨母");
        correspondingAppellation.put("m,f,os,d,h", "姑表姨父");
        correspondingAppellation.put("m,f,os,d,s", "从表兄弟");
        correspondingAppellation.put("m,f,os,d,d", "从表姐妹");
        correspondingAppellation.put("m,f,ls", "姑姥姥");
        correspondingAppellation.put("m,f,ls,h", "姑姥爷");
        correspondingAppellation.put("m,f,ls,s", "姑表舅父");
        correspondingAppellation.put("m,f,ls,s,w", "姑表舅母");
        correspondingAppellation.put("m,f,ls,s,s", "从表兄弟");
        correspondingAppellation.put("m,f,ls,s,d", "从表姐妹");
        correspondingAppellation.put("m,f,ls,d", "姑表姨母");
        correspondingAppellation.put("m,f,ls,d,h", "姑表姨父");
        correspondingAppellation.put("m,f,ls,d,s", "从表兄弟");
        correspondingAppellation.put("m,f,ls,d,d", "从表姐妹");
        correspondingAppellation.put("m,m", "外婆");
        correspondingAppellation.put("m,m,f", "外曾外祖父");
        correspondingAppellation.put("m,m,f,f", "外曾外曾祖父");
        correspondingAppellation.put("m,m,f,m", "外曾外曾祖母");
        correspondingAppellation.put("m,m,f,ob,s", "堂舅外祖父");
        correspondingAppellation.put("m,m,f,ob,s,w", "堂舅外祖母");
        correspondingAppellation.put("m,m,f,ob,d", "堂姨外祖母");
        correspondingAppellation.put("m,m,f,ob,d,h", "堂姨外祖父");
        correspondingAppellation.put("m,m,f,lb,s", "堂舅外祖父");
        correspondingAppellation.put("m,m,f,lb,s,w", "堂舅外祖母");
        correspondingAppellation.put("m,m,f,lb,d", "堂姨外祖母");
        correspondingAppellation.put("m,m,f,lb,d,h", "堂姨外祖父");
        correspondingAppellation.put("m,m,f,ob", "伯外曾外祖父");
        correspondingAppellation.put("m,m,f,ob,w", "伯外曾外祖母");
        correspondingAppellation.put("m,m,f,lb", "叔外曾外祖父");
        correspondingAppellation.put("m,m,f,lb,w", "叔外曾外祖母");
        correspondingAppellation.put("m,m,f,os", "姑外曾外祖母");
        correspondingAppellation.put("m,m,f,os,h", "姑外曾外祖父");
        correspondingAppellation.put("m,m,f,os,s", "表舅外祖父");
        correspondingAppellation.put("m,m,f,os,s,w", "表舅外祖母");
        correspondingAppellation.put("m,m,f,os,d", "表姨外祖母");
        correspondingAppellation.put("m,m,f,os,d,h", "表姨外祖父");
        correspondingAppellation.put("m,m,f,ls", "姑外曾外祖母");
        correspondingAppellation.put("m,m,f,ls,h", "姑外曾外祖父");
        correspondingAppellation.put("m,m,f,ls,s", "表舅外祖父");
        correspondingAppellation.put("m,m,f,ls,s,w", "表舅外祖母");
        correspondingAppellation.put("m,m,f,ls,d", "表姨外祖母");
        correspondingAppellation.put("m,m,f,ls,d,h", "表姨外祖父");
        correspondingAppellation.put("m,m,m", "外曾外祖母");
        correspondingAppellation.put("m,m,m,f", "外曾外曾外祖父");
        correspondingAppellation.put("m,m,m,m", "外曾外曾外祖母");
        correspondingAppellation.put("m,m,m,ob", "舅外曾外祖父");
        correspondingAppellation.put("m,m,m,ob,w", "舅外曾外祖母");
        correspondingAppellation.put("m,m,m,ob,s", "表舅外祖父");
        correspondingAppellation.put("m,m,m,ob,s,w", "表舅外祖母");
        correspondingAppellation.put("m,m,m,ob,d", "表姨外祖母");
        correspondingAppellation.put("m,m,m,ob,d,h", "表姨外祖父");
        correspondingAppellation.put("m,m,m,os", "姨外曾外祖母");
        correspondingAppellation.put("m,m,m,os,h", "姨外曾外祖父");
        correspondingAppellation.put("m,m,m,os,s", "表舅外祖父");
        correspondingAppellation.put("m,m,m,os,s,w", "表舅外祖母");
        correspondingAppellation.put("m,m,m,os,d", "表姨外祖母");
        correspondingAppellation.put("m,m,m,os,d,h", "表姨外祖父");
        correspondingAppellation.put("m,m,ob", "外舅公");
        correspondingAppellation.put("m,m,ob,w", "外舅婆");
        correspondingAppellation.put("m,m,ob,s", "舅表舅父");
        correspondingAppellation.put("m,m,ob,s,w", "舅表舅母");
        correspondingAppellation.put("m,m,ob,s,s", "从表兄弟");
        correspondingAppellation.put("m,m,ob,s,d", "从表姐妹");
        correspondingAppellation.put("m,m,ob,d", "舅表姨母");
        correspondingAppellation.put("m,m,ob,d,h", "舅表姨父");
        correspondingAppellation.put("m,m,ob,d,s", "从表兄弟");
        correspondingAppellation.put("m,m,ob,d,d", "从表姐妹");
        correspondingAppellation.put("m,m,os", "姨姥姥");
        correspondingAppellation.put("m,m,os,h", "姨姥爷");
        correspondingAppellation.put("m,m,os,s", "姨表舅父");
        correspondingAppellation.put("m,m,os,s,w", "姨表舅母");
        correspondingAppellation.put("m,m,os,s,s", "从表兄弟");
        correspondingAppellation.put("m,m,os,s,d", "从表姐妹");
        correspondingAppellation.put("m,m,os,d", "姨表姨母");
        correspondingAppellation.put("m,m,os,d,h", "姨表姨父");
        correspondingAppellation.put("m,m,os,d,s", "从表兄弟");
        correspondingAppellation.put("m,m,os,d,d", "从表姐妹");
        correspondingAppellation.put("m,ob", "舅舅");
        correspondingAppellation.put("m,ob,w", "舅妈");
        correspondingAppellation.put("m,ob,w,f", "姻伯公");
        correspondingAppellation.put("m,ob,w,m", "姻伯婆");
        correspondingAppellation.put("m,ob,w,ob", "姻世伯");
        correspondingAppellation.put("m,ob,w,ob,w", "姻伯母");
        correspondingAppellation.put("m,ob,w,os", "姻伯母");
        correspondingAppellation.put("m,ob,w,os,h", "姻世伯");
        correspondingAppellation.put("m,ob,w,lb", "姻世伯");
        correspondingAppellation.put("m,ob,w,lb,w", "姻伯母");
        correspondingAppellation.put("m,ob,w,ls", "姻伯母");
        correspondingAppellation.put("m,ob,w,ls,h", "姻世伯");
        correspondingAppellation.put("m,ob,s", "舅表哥/舅表弟");
        correspondingAppellation.put("m,ob,s,w", "舅表嫂/舅表弟媳");
        correspondingAppellation.put("m,lb,s", "舅表哥/舅表弟");
        correspondingAppellation.put("m,lb,s,w", "舅表嫂/舅表弟媳");
        correspondingAppellation.put("m,ob,s,s", "表侄");
        correspondingAppellation.put("m,ob,s,s,s", "表侄孙");
        correspondingAppellation.put("m,ob,s,s,s,w", "表侄孙媳妇");
        correspondingAppellation.put("m,ob,s,s,d", "表侄孙女");
        correspondingAppellation.put("m,ob,s,s,d,h", "表侄孙女婿");
        correspondingAppellation.put("m,ob,s,d", "表侄女");
        correspondingAppellation.put("m,ob,s,d,s", "外表侄孙");
        correspondingAppellation.put("m,ob,s,d,s,w", "外表侄孙媳妇");
        correspondingAppellation.put("m,ob,s,d,d", "外表侄孙女");
        correspondingAppellation.put("m,ob,s,d,d,h", "外表侄孙女婿");
        correspondingAppellation.put("m,ob,d,s", "表外甥");
        correspondingAppellation.put("m,ob,d,d", "表外甥女");
        correspondingAppellation.put("m,m,m,lb", "舅外曾外祖父");
        correspondingAppellation.put("m,m,m,lb,w", "舅外曾外祖母");
        correspondingAppellation.put("m,m,m,lb,s", "表舅外祖父");
        correspondingAppellation.put("m,m,m,lb,s,w", "表舅外祖母");
        correspondingAppellation.put("m,m,m,lb,d", "表姨外祖母");
        correspondingAppellation.put("m,m,m,lb,d,h", "表姨外祖父");
        correspondingAppellation.put("m,m,m,ls", "姨外曾外祖母");
        correspondingAppellation.put("m,m,m,ls,h", "姨外曾外祖父");
        correspondingAppellation.put("m,m,m,ls,s", "表舅外祖父");
        correspondingAppellation.put("m,m,m,ls,s,w", "表舅外祖母");
        correspondingAppellation.put("m,m,m,ls,d", "表姨外祖母");
        correspondingAppellation.put("m,m,m,ls,d,h", "表姨外祖父");
        correspondingAppellation.put("m,m,lb", "外舅公");
        correspondingAppellation.put("m,m,lb,w", "外舅婆");
        correspondingAppellation.put("m,m,lb,s", "舅表舅父");
        correspondingAppellation.put("m,m,lb,s,w", "舅表舅母");
        correspondingAppellation.put("m,m,lb,s,s", "从表兄弟");
        correspondingAppellation.put("m,m,lb,s,d", "从表姐妹");
        correspondingAppellation.put("m,m,lb,d", "舅表姨母");
        correspondingAppellation.put("m,m,lb,d,h", "舅表姨父");
        correspondingAppellation.put("m,m,lb,d,s", "从表兄弟");
        correspondingAppellation.put("m,m,lb,d,d", "从表姐妹");
        correspondingAppellation.put("m,m,ls", "姨姥姥");
        correspondingAppellation.put("m,m,ls,h", "姨姥爷");
        correspondingAppellation.put("m,m,ls,s", "姨表舅父");
        correspondingAppellation.put("m,m,ls,s,w", "姨表舅母");
        correspondingAppellation.put("m,m,ls,s,s", "从表兄弟");
        correspondingAppellation.put("m,m,ls,s,d", "从表姐妹");
        correspondingAppellation.put("m,m,ls,d", "姨表姨母");
        correspondingAppellation.put("m,m,ls,d,h", "姨表姨父");
        correspondingAppellation.put("m,m,ls,d,s", "从表兄弟");
        correspondingAppellation.put("m,m,ls,d,d", "从表姐妹");
        correspondingAppellation.put("m,lb", "舅舅");
        correspondingAppellation.put("m,lb,w", "舅妈");
        correspondingAppellation.put("m,lb,w,f", "姻伯公");
        correspondingAppellation.put("m,lb,w,m", "姻伯婆");
        correspondingAppellation.put("m,lb,w,ob", "姻世伯");
        correspondingAppellation.put("m,lb,w,ob,w", "姻伯母");
        correspondingAppellation.put("m,lb,w,os", "姻伯母");
        correspondingAppellation.put("m,lb,w,os,h", "姻世伯");
        correspondingAppellation.put("m,lb,w,lb", "姻世伯");
        correspondingAppellation.put("m,lb,w,lb,w", "姻伯母");
        correspondingAppellation.put("m,lb,w,ls", "姻伯母");
        correspondingAppellation.put("m,lb,w,ls,h", "姻世伯");
        correspondingAppellation.put("m,lb,s,s", "表侄");
        correspondingAppellation.put("m,lb,s,s,s", "表侄孙");
        correspondingAppellation.put("m,lb,s,s,s,w", "表侄孙媳妇");
        correspondingAppellation.put("m,lb,s,s,d", "表侄孙女");
        correspondingAppellation.put("m,lb,s,s,d,h", "表侄孙女婿");
        correspondingAppellation.put("m,lb,s,d", "表侄女");
        correspondingAppellation.put("m,lb,s,d,s", "外表侄孙");
        correspondingAppellation.put("m,lb,s,d,s,w", "外表侄孙媳妇");
        correspondingAppellation.put("m,lb,s,d,d", "外表侄孙女");
        correspondingAppellation.put("m,lb,s,d,d,h", "外表侄孙女婿");
        correspondingAppellation.put("m,ob,d", "舅表姐/舅表妹");
        correspondingAppellation.put("m,ob,d,h", "舅表姐夫/舅表妹夫");
        correspondingAppellation.put("m,lb,d", "舅表姐/舅表妹");
        correspondingAppellation.put("m,lb,d,h", "舅表姐夫/舅表妹夫");
        correspondingAppellation.put("m,lb,d,s", "表外甥");
        correspondingAppellation.put("m,lb,d,d", "表外甥女");
        correspondingAppellation.put("m,ob", "大舅");
        correspondingAppellation.put("m,ob,w", "大舅妈");
        correspondingAppellation.put("m,lb", "小舅");
        correspondingAppellation.put("m,lb,w", "小舅妈");
        correspondingAppellation.put("m,os", "姨妈");
        correspondingAppellation.put("m,os,h", "姨丈");
        correspondingAppellation.put("m,os,h,f", "姻伯公");
        correspondingAppellation.put("m,os,h,m", "姻伯婆");
        correspondingAppellation.put("m,os,h,ob", "姻世伯");
        correspondingAppellation.put("m,os,h,ob,w", "姻伯母");
        correspondingAppellation.put("m,os,h,os", "姻伯母");
        correspondingAppellation.put("m,os,h,os,h", "姻世伯");
        correspondingAppellation.put("m,os,h,lb", "姻世伯");
        correspondingAppellation.put("m,os,h,lb,w", "姻伯母");
        correspondingAppellation.put("m,os,h,ls", "姻伯母");
        correspondingAppellation.put("m,os,h,ls,h", "姻世伯");
        correspondingAppellation.put("m,os,s", "姨表哥/姨表弟");
        correspondingAppellation.put("m,os,s,w", "姨表嫂/姨表弟媳");
        correspondingAppellation.put("m,ls,s", "姨表哥/姨表弟");
        correspondingAppellation.put("m,ls,s,w", "姨表嫂/姨表弟媳");
        correspondingAppellation.put("m,os,s,s", "表侄");
        correspondingAppellation.put("m,os,s,s,s", "表侄孙");
        correspondingAppellation.put("m,os,s,s,s,w", "表侄孙媳妇");
        correspondingAppellation.put("m,os,s,s,d", "表侄孙女");
        correspondingAppellation.put("m,os,s,s,d,h", "表侄孙女婿");
        correspondingAppellation.put("m,os,s,d", "表侄女");
        correspondingAppellation.put("m,os,s,d,s", "外表侄孙");
        correspondingAppellation.put("m,os,s,d,s,w", "外表侄孙媳妇");
        correspondingAppellation.put("m,os,s,d,d", "外表侄孙女");
        correspondingAppellation.put("m,os,s,d,d,h", "外表侄孙女婿");
        correspondingAppellation.put("m,os,d,s", "表外甥");
        correspondingAppellation.put("m,os,d,d", "表外甥女");
        correspondingAppellation.put("m,ls", "姨妈");
        correspondingAppellation.put("m,ls,h", "姨丈");
        correspondingAppellation.put("m,ls,h,f", "姻伯公");
        correspondingAppellation.put("m,ls,h,m", "姻伯婆");
        correspondingAppellation.put("m,ls,h,ob", "姻世伯");
        correspondingAppellation.put("m,ls,h,ob,w", "姻伯母");
        correspondingAppellation.put("m,ls,h,os", "姻伯母");
        correspondingAppellation.put("m,ls,h,os,h", "姻世伯");
        correspondingAppellation.put("m,ls,h,lb", "姻世伯");
        correspondingAppellation.put("m,ls,h,lb,w", "姻伯母");
        correspondingAppellation.put("m,ls,h,ls", "姻伯母");
        correspondingAppellation.put("m,ls,h,ls,h", "姻世伯");
        correspondingAppellation.put("m,ls,s,s", "表侄");
        correspondingAppellation.put("m,ls,s,s,s", "表侄孙");
        correspondingAppellation.put("m,ls,s,s,s,w", "表侄孙媳妇");
        correspondingAppellation.put("m,ls,s,s,d", "表侄孙女");
        correspondingAppellation.put("m,ls,s,s,d,h", "表侄孙女婿");
        correspondingAppellation.put("m,ls,s,d", "表侄女");
        correspondingAppellation.put("m,ls,s,d,s", "外表侄孙");
        correspondingAppellation.put("m,ls,s,d,s,w", "外表侄孙媳妇");
        correspondingAppellation.put("m,ls,s,d,d", "外表侄孙女");
        correspondingAppellation.put("m,ls,s,d,d,h", "外表侄孙女婿");
        correspondingAppellation.put("m,os,d", "姨表姐/姨表妹");
        correspondingAppellation.put("m,os,d,h", "姨表姐夫/姨表妹夫");
        correspondingAppellation.put("m,ls,d", "姨表姐/姨表妹");
        correspondingAppellation.put("m,ls,d,h", "姨表姐夫/姨表妹夫");
        correspondingAppellation.put("m,ls,d,s", "表外甥");
        correspondingAppellation.put("m,ls,d,d", "表外甥女");
        correspondingAppellation.put("m,os", "大姨");
        correspondingAppellation.put("m,os,h", "大姨父");
        correspondingAppellation.put("m,ls", "小姨");
        correspondingAppellation.put("m,ls,h", "小姨父");
        correspondingAppellation.put("h", "老公");
        correspondingAppellation.put("h,f", "公公");
        correspondingAppellation.put("h,f,f", "祖翁");
        correspondingAppellation.put("h,f,f,ob", "伯祖翁");
        correspondingAppellation.put("h,f,f,ob,w", "伯祖婆");
        correspondingAppellation.put("h,f,f,lb", "叔祖翁");
        correspondingAppellation.put("h,f,f,lb,w", "叔祖婆");
        correspondingAppellation.put("h,f,f,f", "太公翁");
        correspondingAppellation.put("h,f,f,f,ob", "太伯翁");
        correspondingAppellation.put("h,f,f,f,ob,w", "太姆婆");
        correspondingAppellation.put("h,f,f,f,lb", "太叔翁");
        correspondingAppellation.put("h,f,f,f,lb,w", "太婶婆");
        correspondingAppellation.put("h,f,f,m", "太奶亲");
        correspondingAppellation.put("h,f,m", "祖婆");
        correspondingAppellation.put("h,f,ob", "伯翁");
        correspondingAppellation.put("h,f,ob,w", "伯婆");
        correspondingAppellation.put("h,f,lb", "叔公");
        correspondingAppellation.put("h,f,lb,w", "叔婆");
        correspondingAppellation.put("h,f,ob,s", "堂大伯/堂叔仔");
        correspondingAppellation.put("h,f,ob,s,w", "堂嫂/堂小弟");
        correspondingAppellation.put("h,f,lb,s", "堂大伯/堂叔仔");
        correspondingAppellation.put("h,f,lb,s,w", "堂嫂/堂小弟");
        correspondingAppellation.put("h,f,ob,s,s", "堂夫侄男");
        correspondingAppellation.put("h,f,ob,s,d", "堂夫侄女");
        correspondingAppellation.put("h,f,ob,d", "堂大姑姐/堂小姑妹");
        correspondingAppellation.put("h,f,ob,d,h", "堂大姑姐夫/堂小姑妹夫");
        correspondingAppellation.put("h,f,lb,d", "堂大姑姐/堂小姑妹");
        correspondingAppellation.put("h,f,lb,d,h", "堂大姑姐夫/堂小姑妹夫");
        correspondingAppellation.put("h,f,ob,d,s", "堂夫甥男");
        correspondingAppellation.put("h,f,ob,d,d", "堂夫甥女");
        correspondingAppellation.put("h,f,os", "姑婆");
        correspondingAppellation.put("h,f,os,h", "姑公");
        correspondingAppellation.put("h,f,os,s", "姑表大伯子/姑表小叔弟");
        correspondingAppellation.put("h,f,os,s,w", "姑表大伯嫂/姑表小叔弟妇");
        correspondingAppellation.put("h,f,ls,s", "姑表大伯子/姑表小叔弟");
        correspondingAppellation.put("h,f,ls,s,w", "姑表大伯嫂/姑表小叔弟妇");
        correspondingAppellation.put("h,f,os,s,s", "姑表夫侄男");
        correspondingAppellation.put("h,f,os,s,d", "姑表夫侄女");
        correspondingAppellation.put("h,f,os,d", "姑表大姑姐/姑表小姑妹");
        correspondingAppellation.put("h,f,os,d,h", "姑表大姑姐夫/姑表小姑妹夫");
        correspondingAppellation.put("h,f,ls,d", "姑表大姑姐/姑表小姑妹");
        correspondingAppellation.put("h,f,ls,d,h", "姑表大姑姐夫/姑表小姑妹夫");
        correspondingAppellation.put("h,f,os,d,s", "姑表夫甥男");
        correspondingAppellation.put("h,f,os,d,d", "姑表夫甥女");
        correspondingAppellation.put("h,f,lb,s,s", "堂夫侄男");
        correspondingAppellation.put("h,f,lb,s,d", "堂夫侄女");
        correspondingAppellation.put("h,f,lb,d,s", "堂夫甥男");
        correspondingAppellation.put("h,f,lb,d,d", "堂夫甥女");
        correspondingAppellation.put("h,f,ls", "姑婆");
        correspondingAppellation.put("h,f,ls,h", "姑公");
        correspondingAppellation.put("h,f,ls,s,s", "姑表夫侄男");
        correspondingAppellation.put("h,f,ls,s,d", "姑表夫侄女");
        correspondingAppellation.put("h,f,ls,d,s", "姑表夫甥男");
        correspondingAppellation.put("h,f,ls,d,d", "姑表夫甥女");
        correspondingAppellation.put("h,m", "婆婆");
        correspondingAppellation.put("h,m,ob", "舅公");
        correspondingAppellation.put("h,m,ob,w", "舅婆");
        correspondingAppellation.put("h,m,ob,s", "舅表大伯子/舅表小叔弟");
        correspondingAppellation.put("h,m,ob,s,w", "舅表大伯嫂/舅表小叔弟妇");
        correspondingAppellation.put("h,m,lb,s", "舅表大伯子/舅表小叔弟");
        correspondingAppellation.put("h,m,lb,s,w", "舅表大伯嫂/舅表小叔弟妇");
        correspondingAppellation.put("h,m,ob,s,s", "舅表夫侄男");
        correspondingAppellation.put("h,m,ob,s,d", "舅表夫侄女");
        correspondingAppellation.put("h,m,ob,d", "舅表大姑姐/舅表小姑妹");
        correspondingAppellation.put("h,m,ob,d,h", "舅表大姑姐夫/舅表小姑妹夫");
        correspondingAppellation.put("h,m,lb,d", "舅表大姑姐/舅表小姑妹");
        correspondingAppellation.put("h,m,lb,d,h", "舅表大姑姐夫/舅表小姑妹夫");
        correspondingAppellation.put("h,m,ob,d,s", "舅表夫甥男");
        correspondingAppellation.put("h,m,ob,d,d", "舅表夫甥女");
        correspondingAppellation.put("h,m,os", "姨婆");
        correspondingAppellation.put("h,m,os,h", "姨公");
        correspondingAppellation.put("h,m,os,s", "姨表大伯子/姨表小叔弟");
        correspondingAppellation.put("h,m,os,s,w", "姨表大伯嫂/姨表小叔弟妇");
        correspondingAppellation.put("h,m,ls,s", "姨表大伯子/姨表小叔弟");
        correspondingAppellation.put("h,m,ls,s,w", "姨表大伯嫂/姨表小叔弟妇");
        correspondingAppellation.put("h,m,os,s,s", "姨表夫侄男");
        correspondingAppellation.put("h,m,os,s,d", "姨表夫侄女");
        correspondingAppellation.put("h,m,os,d", "姨表大姑姐/姨表小姑妹");
        correspondingAppellation.put("h,m,os,d,h", "姨表大姑姐夫/姨表小姑妹夫");
        correspondingAppellation.put("h,m,ls,d", "姨表大姑姐/姨表小姑妹");
        correspondingAppellation.put("h,m,ls,d,h", "姨表大姑姐夫/姨表小姑妹夫");
        correspondingAppellation.put("h,m,os,d,s", "姨表夫甥男");
        correspondingAppellation.put("h,m,os,d,d", "姨表夫甥女");
        correspondingAppellation.put("h,m,lb", "舅公");
        correspondingAppellation.put("h,m,lb,w", "舅婆");
        correspondingAppellation.put("h,m,lb,s,s", "舅表夫侄男");
        correspondingAppellation.put("h,m,lb,s,d", "舅表夫侄女");
        correspondingAppellation.put("h,m,lb,d,s", "舅表夫甥男");
        correspondingAppellation.put("h,m,lb,d,d", "舅表夫甥女");
        correspondingAppellation.put("h,m,ls", "姨婆");
        correspondingAppellation.put("h,m,ls,h", "姨公");
        correspondingAppellation.put("h,m,ls,s,s", "姨表夫侄男");
        correspondingAppellation.put("h,m,ls,s,d", "姨表夫侄女");
        correspondingAppellation.put("h,m,ls,d,s", "姨表夫甥男");
        correspondingAppellation.put("h,m,ls,d,d", "姨表夫甥女");
        correspondingAppellation.put("h,ob", "大伯子");
        correspondingAppellation.put("h,ob,w", "大婶子");
        correspondingAppellation.put("h,lb", "小叔子");
        correspondingAppellation.put("h,lb,w", "小婶子");
        correspondingAppellation.put("h,ob,s", "叔侄");
        correspondingAppellation.put("h,lb,s", "叔侄");
        correspondingAppellation.put("h,os", "大姑子");
        correspondingAppellation.put("h,os,h", "大姑夫");
        correspondingAppellation.put("h,ls", "小姑子");
        correspondingAppellation.put("h,ls,h", "小姑夫");
        correspondingAppellation.put("h,os,s", "姑甥");
        correspondingAppellation.put("h,ls,s", "姑甥");
        correspondingAppellation.put("w", "老婆");
        correspondingAppellation.put("w,f", "岳父");
        correspondingAppellation.put("w,f,f", "太岳父");
        correspondingAppellation.put("w,f,f,ob", "太伯岳");
        correspondingAppellation.put("w,f,f,ob,w", "太伯岳母");
        correspondingAppellation.put("w,f,f,lb,", "太叔岳");
        correspondingAppellation.put("w,f,f,lb,w", "太叔岳母");
        correspondingAppellation.put("w,f,f,ob,s", "姻伯/姻叔");
        correspondingAppellation.put("w,f,f,ob,s,w", "姻姆/姻婶");
        correspondingAppellation.put("w,f,f,lb,s", "姻伯/姻叔");
        correspondingAppellation.put("w,f,f,lb,s,w", "姻姆/姻婶");
        correspondingAppellation.put("w,f,f,os", "太姑岳母");
        correspondingAppellation.put("w,f,f,os,h", "太姑岳父");
        correspondingAppellation.put("w,f,f,ls", "太姑岳母");
        correspondingAppellation.put("w,f,f,ls,h", "太姑岳父");
        correspondingAppellation.put("w,f,m", "太岳母");
        correspondingAppellation.put("w,f,m,ob", "太舅岳父");
        correspondingAppellation.put("w,f,m,ob,w", "太舅岳母");
        correspondingAppellation.put("w,f,m,os", "太姨岳母");
        correspondingAppellation.put("w,f,m,os,h", "太姨岳父");
        correspondingAppellation.put("w,f,ob,s", "堂大舅/堂舅仔");
        correspondingAppellation.put("w,f,ob,d", "堂大姨/堂姨仔");
        correspondingAppellation.put("w,f,lb,s", "堂大舅/堂舅仔");
        correspondingAppellation.put("w,f,lb,d", "堂大姨/堂姨仔");
        correspondingAppellation.put("w,f,m,lb", "太舅岳父");
        correspondingAppellation.put("w,f,m,lb,w", "太舅岳母");
        correspondingAppellation.put("w,f,m,ls", "太姨岳母");
        correspondingAppellation.put("w,f,m,ls,h", "太姨岳父");
        correspondingAppellation.put("w,f,ob", "伯岳");
        correspondingAppellation.put("w,f,ob,w", "伯岳母");
        correspondingAppellation.put("w,f,lb", "叔岳");
        correspondingAppellation.put("w,f,lb,w", "叔岳母");
        correspondingAppellation.put("w,f,os", "姑岳母");
        correspondingAppellation.put("w,f,os,h", "姑岳父");
        correspondingAppellation.put("w,f,os,s", "表大舅/表舅仔");
        correspondingAppellation.put("w,f,os,s,w", "表内嫂/表内弟妇");
        correspondingAppellation.put("w,f,ls,s", "表大舅/表舅仔");
        correspondingAppellation.put("w,f,ls,s,w", "表内嫂/表内弟妇");
        correspondingAppellation.put("w,f,os,d", "表大姨/表姨仔");
        correspondingAppellation.put("w,f,os,d,h", "表襟兄/表襟弟");
        correspondingAppellation.put("w,f,ls,d", "表大姨/表姨仔");
        correspondingAppellation.put("w,f,ls,d,h", "表襟兄/表襟弟");
        correspondingAppellation.put("w,f,ls", "姑岳母");
        correspondingAppellation.put("w,f,ls,h", "姑岳父");
        correspondingAppellation.put("w,m", "岳母");
        correspondingAppellation.put("w,m,f", "外太岳父");
        correspondingAppellation.put("w,m,m", "外太岳母");
        correspondingAppellation.put("w,m,ob", "舅岳父");
        correspondingAppellation.put("w,m,ob,w", "舅岳母");
        correspondingAppellation.put("w,m,ob,s", "表大舅/表舅仔");
        correspondingAppellation.put("w,m,ob,d", "表大姨/表姨仔");
        correspondingAppellation.put("w,m,lb,s", "表大舅/表舅仔");
        correspondingAppellation.put("w,m,lb,d", "表大姨/表姨仔");
        correspondingAppellation.put("w,m,os", "姨岳母");
        correspondingAppellation.put("w,m,os,h", "姨岳父");
        correspondingAppellation.put("w,m,os,s", "表大舅/表舅仔");
        correspondingAppellation.put("w,m,os,d", "表大姨/表姨仔");
        correspondingAppellation.put("w,m,ls,s", "表大舅/表舅仔");
        correspondingAppellation.put("w,m,ls,d", "表大姨/表姨仔");
        correspondingAppellation.put("w,ob,s", "内侄");
        correspondingAppellation.put("w,ob,s,w", "内侄媳妇");
        correspondingAppellation.put("w,ob,s,s", "内侄孙");
        correspondingAppellation.put("w,ob,s,s,w", "内侄孙媳妇");
        correspondingAppellation.put("w,ob,s,d", "内侄孙女");
        correspondingAppellation.put("w,ob,s,d,h", "内侄孙女婿");
        correspondingAppellation.put("w,ob,d", "内侄女");
        correspondingAppellation.put("w,ob,d,h", "内侄女婿");
        correspondingAppellation.put("w,ob,d,s", "外侄孙");
        correspondingAppellation.put("w,ob,d,s,w", "外侄孙媳妇");
        correspondingAppellation.put("w,ob,d,d", "外侄孙女");
        correspondingAppellation.put("w,ob,d,d,h", "外侄孙女婿");
        correspondingAppellation.put("w,m,lb", "舅岳父");
        correspondingAppellation.put("w,m,lb,w", "舅岳母");
        correspondingAppellation.put("w,m,ls", "姨岳母");
        correspondingAppellation.put("w,m,ls,h", "姨岳父");
        correspondingAppellation.put("w,lb,s", "内侄");
        correspondingAppellation.put("w,lb,s,w", "内侄媳妇");
        correspondingAppellation.put("w,lb,s,s", "内侄孙");
        correspondingAppellation.put("w,lb,s,s,w", "内侄孙媳妇");
        correspondingAppellation.put("w,lb,s,d", "内侄孙女");
        correspondingAppellation.put("w,lb,s,d,h", "内侄孙女婿");
        correspondingAppellation.put("w,lb,d", "内侄女");
        correspondingAppellation.put("w,lb,d,h", "内侄女婿");
        correspondingAppellation.put("w,lb,d,s", "外侄孙");
        correspondingAppellation.put("w,lb,d,s,w", "外侄孙媳妇");
        correspondingAppellation.put("w,lb,d,d", "外侄孙女");
        correspondingAppellation.put("w,lb,d,d,h", "外侄孙女婿");
        correspondingAppellation.put("w,ob", "大舅子");
        correspondingAppellation.put("w,ob,w", "舅嫂");
        correspondingAppellation.put("w,lb", "小舅子");
        correspondingAppellation.put("w,lb,w", "舅弟媳");
        correspondingAppellation.put("w,os,s", "内甥");
        correspondingAppellation.put("w,os,s,w", "姨甥媳妇");
        correspondingAppellation.put("w,os,s,s", "姨甥孙");
        correspondingAppellation.put("w,os,s,s,w", "姨甥孙媳妇");
        correspondingAppellation.put("w,os,s,d", "姨甥孙女");
        correspondingAppellation.put("w,os,s,d,h", "姨甥孙女婿");
        correspondingAppellation.put("w,os,d", "姨甥女");
        correspondingAppellation.put("w,os,d,h", "姨甥女婿");
        correspondingAppellation.put("w,os,d,s", "姨甥孙");
        correspondingAppellation.put("w,os,d,s,w", "姨甥孙媳妇");
        correspondingAppellation.put("w,os,d,d", "姨甥孙女");
        correspondingAppellation.put("w,os,d,d,h", "姨甥孙女婿");
        correspondingAppellation.put("w,ls,s", "内甥");
        correspondingAppellation.put("w,ls,s,w", "姨甥媳妇");
        correspondingAppellation.put("w,ls,s,s", "姨甥孙");
        correspondingAppellation.put("w,ls,s,s,w", "姨甥孙媳妇");
        correspondingAppellation.put("w,ls,s,d", "姨甥孙女");
        correspondingAppellation.put("w,ls,s,d,h", "姨甥孙女婿");
        correspondingAppellation.put("w,ls,d", "姨甥女");
        correspondingAppellation.put("w,ls,d,h", "姨甥女婿");
        correspondingAppellation.put("w,ls,d,s", "姨甥孙");
        correspondingAppellation.put("w,ls,d,s,w", "姨甥孙媳妇");
        correspondingAppellation.put("w,ls,d,d", "姨甥孙女");
        correspondingAppellation.put("w,ls,d,d,h", "姨甥孙女婿");
        correspondingAppellation.put("w,os", "大姨子");
        correspondingAppellation.put("w,os,h", "大姨夫");
        correspondingAppellation.put("w,ls", "小姨子");
        correspondingAppellation.put("w,ls,h", "小姨夫");
        correspondingAppellation.put("ob", "兄弟");
        correspondingAppellation.put("ob,w,f", "姻世伯");
        correspondingAppellation.put("ob,w,m", "姻伯母");
        correspondingAppellation.put("ob,w,ob", "姻兄/姻弟");
        correspondingAppellation.put("ob,w,lb", "姻兄/姻弟");
        correspondingAppellation.put("ob,s", "侄子");
        correspondingAppellation.put("ob,s,w", "侄媳");
        correspondingAppellation.put("ob,s,s", "侄孙");
        correspondingAppellation.put("ob,s,s,w", "侄孙媳");
        correspondingAppellation.put("ob,s,s,s", "侄曾孙");
        correspondingAppellation.put("ob,s,s,d", "侄曾孙女");
        correspondingAppellation.put("ob,s,d", "侄孙女");
        correspondingAppellation.put("ob,s,d,h", "侄孙女婿");
        correspondingAppellation.put("ob,d", "侄女");
        correspondingAppellation.put("ob,d,h", "侄女婿");
        correspondingAppellation.put("ob,d,s", "外侄孙");
        correspondingAppellation.put("ob,d,s,w", "外侄孙媳妇");
        correspondingAppellation.put("ob,d,d", "外侄孙女");
        correspondingAppellation.put("ob,d,d,h", "外侄孙女婿");
        correspondingAppellation.put("lb", "兄弟");
        correspondingAppellation.put("lb,w,f", "姻世伯");
        correspondingAppellation.put("lb,w,m", "姻伯母");
        correspondingAppellation.put("lb,w,ob", "姻兄/姻弟");
        correspondingAppellation.put("lb,w,lb", "姻兄/姻弟");
        correspondingAppellation.put("lb,s", "侄子");
        correspondingAppellation.put("lb,s,w", "侄媳");
        correspondingAppellation.put("lb,s,s", "侄孙");
        correspondingAppellation.put("lb,s,s,w", "侄孙媳");
        correspondingAppellation.put("lb,s,s,s", "侄曾孙");
        correspondingAppellation.put("lb,s,s,d", "侄曾孙女");
        correspondingAppellation.put("lb,s,d", "侄孙女");
        correspondingAppellation.put("lb,s,d,h", "侄孙女婿");
        correspondingAppellation.put("lb,d", "侄女");
        correspondingAppellation.put("lb,d,h", "侄女婿");
        correspondingAppellation.put("lb,d,s", "外侄孙");
        correspondingAppellation.put("lb,d,s,w", "外侄孙媳妇");
        correspondingAppellation.put("lb,d,d", "外侄孙女");
        correspondingAppellation.put("lb,d,d,h", "外侄孙女婿");
        correspondingAppellation.put("ob", "哥哥");
        correspondingAppellation.put("ob,w", "嫂子");
        correspondingAppellation.put("ob,w,f", "姻伯父");
        correspondingAppellation.put("ob,w,m", "姻伯母");
        correspondingAppellation.put("lb", "弟弟");
        correspondingAppellation.put("lb,w", "弟妹");
        correspondingAppellation.put("lb,w,f", "姻叔父");
        correspondingAppellation.put("lb,w,m", "姻叔母");
        correspondingAppellation.put("os", "姐妹");
        correspondingAppellation.put("os,h,f", "姻世伯");
        correspondingAppellation.put("os,h,m", "姻伯母");
        correspondingAppellation.put("os,h,ob", "姻兄/姻弟");
        correspondingAppellation.put("os,h,lb", "姻兄/姻弟");
        correspondingAppellation.put("os,s", "外甥");
        correspondingAppellation.put("os,s,w", "外甥媳妇");
        correspondingAppellation.put("os,s,s", "外甥孙");
        correspondingAppellation.put("os,s,s,w", "外甥孙媳妇");
        correspondingAppellation.put("os,s,s,s", "外曾甥孙");
        correspondingAppellation.put("os,s,s,d", "外曾甥孙女");
        correspondingAppellation.put("os,s,d", "外甥孙女");
        correspondingAppellation.put("os,s,d,h", "外甥孙女婿");
        correspondingAppellation.put("os,s,d,s", "外曾甥孙");
        correspondingAppellation.put("os,s,d,d", "外曾甥孙女");
        correspondingAppellation.put("os,d", "外甥女");
        correspondingAppellation.put("os,d,h", "外甥女婿");
        correspondingAppellation.put("os,d,s", "外甥孙");
        correspondingAppellation.put("os,d,s,w", "外甥孙媳妇");
        correspondingAppellation.put("os,d,s,s", "外曾甥孙");
        correspondingAppellation.put("os,d,s,d", "外曾甥孙女");
        correspondingAppellation.put("os,d,d", "外甥孙女");
        correspondingAppellation.put("os,d,d,h", "外甥孙女婿");
        correspondingAppellation.put("os,d,d,s", "外曾甥孙");
        correspondingAppellation.put("os,d,d,d", "外曾甥孙女");
        correspondingAppellation.put("ls", "姐妹");
        correspondingAppellation.put("ls,h,f", "姻世伯");
        correspondingAppellation.put("ls,h,m", "姻伯母");
        correspondingAppellation.put("ls,h,ob", "姻兄/姻弟");
        correspondingAppellation.put("ls,h,lb", "姻兄/姻弟");
        correspondingAppellation.put("ls,s", "外甥");
        correspondingAppellation.put("ls,s,w", "外甥媳妇");
        correspondingAppellation.put("ls,s,s", "外甥孙");
        correspondingAppellation.put("ls,s,s,w", "外甥孙媳妇");
        correspondingAppellation.put("ls,s,s,s", "外曾甥孙");
        correspondingAppellation.put("ls,s,s,d", "外曾甥孙女");
        correspondingAppellation.put("ls,s,d", "外甥孙女");
        correspondingAppellation.put("ls,s,d,h", "外甥孙女婿");
        correspondingAppellation.put("ls,s,d,s", "外曾甥孙");
        correspondingAppellation.put("ls,s,d,d", "外曾甥孙女");
        correspondingAppellation.put("ls,d", "外甥女");
        correspondingAppellation.put("ls,d,h", "外甥女婿");
        correspondingAppellation.put("ls,d,s", "外甥孙");
        correspondingAppellation.put("ls,d,s,w", "外甥孙媳妇");
        correspondingAppellation.put("ls,d,s,s", "外曾甥孙");
        correspondingAppellation.put("ls,d,s,d", "外曾甥孙女");
        correspondingAppellation.put("ls,d,d", "外甥孙女");
        correspondingAppellation.put("ls,d,d,h", "外甥孙女婿");
        correspondingAppellation.put("ls,d,d,s", "外曾甥孙");
        correspondingAppellation.put("ls,d,d,d", "外曾甥孙女");
        correspondingAppellation.put("os", "姐姐");
        correspondingAppellation.put("os,h", "姐夫");
        correspondingAppellation.put("ls", "妹妹");
        correspondingAppellation.put("ls,h", "妹夫");
        correspondingAppellation.put("s", "儿子");
        correspondingAppellation.put("s,w", "儿媳妇");
        correspondingAppellation.put("s,w,ob", "姻侄");
        correspondingAppellation.put("s,w,ob,s", "姻侄孙");
        correspondingAppellation.put("s,w,ob,d", "姻侄孙女");
        correspondingAppellation.put("s,w,os", "姻侄女");
        correspondingAppellation.put("s,w,os,s", "姻侄孙");
        correspondingAppellation.put("s,w,os,d", "姻侄孙女");
        correspondingAppellation.put("s,w,lb", "姻侄");
        correspondingAppellation.put("s,w,lb,s", "姻侄孙");
        correspondingAppellation.put("s,w,lb,d", "姻侄孙女");
        correspondingAppellation.put("s,w,ls", "姻侄女");
        correspondingAppellation.put("s,w,ls,s", "姻侄孙");
        correspondingAppellation.put("s,w,ls,d", "姻侄孙女");
        correspondingAppellation.put("s,s", "孙子");
        correspondingAppellation.put("s,s,w", "孙媳妇");
        correspondingAppellation.put("s,s,s", "曾孙");
        correspondingAppellation.put("s,s,s,w", "曾孙媳妇");
        correspondingAppellation.put("s,s,s,s", "玄孙");
        correspondingAppellation.put("s,s,s,d", "玄孙女");
        correspondingAppellation.put("s,s,s,s,s", "来孙");
        correspondingAppellation.put("s,s,s,s,d", "来孙女");
        correspondingAppellation.put("s,s,s,s,s,s", "晜孙");
        correspondingAppellation.put("s,s,s,s,s,d", "晜孙女");
        correspondingAppellation.put("s,s,s,s,s,s,s", "仍孙");
        correspondingAppellation.put("s,s,s,s,s,s,d", "仍孙女");
        correspondingAppellation.put("s,s,d", "曾孙女");
        correspondingAppellation.put("s,s,d,h", "曾孙女婿");
        correspondingAppellation.put("s,s,d,s", "外玄孙");
        correspondingAppellation.put("s,s,d,d", "外玄孙女");
        correspondingAppellation.put("s,d", "孙女");
        correspondingAppellation.put("s,d,h", "孙女婿");
        correspondingAppellation.put("s,d,s", "曾外孙");
        correspondingAppellation.put("s,d,d", "曾外孙女");
        correspondingAppellation.put("d", "女儿");
        correspondingAppellation.put("d,h", "女婿");
        correspondingAppellation.put("d,h,ob", "姻侄");
        correspondingAppellation.put("d,h,ob,s", "姻侄孙");
        correspondingAppellation.put("d,h,ob,d", "姻侄孙女");
        correspondingAppellation.put("d,h,os", "姻侄女");
        correspondingAppellation.put("d,h,os,s", "姻侄孙");
        correspondingAppellation.put("d,h,os,d", "姻侄孙女");
        correspondingAppellation.put("d,h,lb", "姻侄");
        correspondingAppellation.put("d,h,lb,s", "姻侄孙");
        correspondingAppellation.put("d,h,lb,d", "姻侄孙女");
        correspondingAppellation.put("d,h,ls", "姻侄女");
        correspondingAppellation.put("d,h,ls,s", "姻侄孙");
        correspondingAppellation.put("d,h,ls,d", "姻侄孙女");
        correspondingAppellation.put("d,s", "外孙");
        correspondingAppellation.put("d,s,w", "外孙媳");
        correspondingAppellation.put("d,s,s", "外曾孙");
        correspondingAppellation.put("d,s,d", "外曾孙女");
        correspondingAppellation.put("d,d", "外孙女");
        correspondingAppellation.put("d,d,h", "外孙女婿");
        correspondingAppellation.put("d,d,s", "外曾外孙");
        correspondingAppellation.put("d,d,d", "外曾外孙女");
        correspondingAppellation.put("s,w,m", "亲家母");
        correspondingAppellation.put("s,w,f", "亲家公");
        correspondingAppellation.put("s,w,f,f", "太姻翁");
        correspondingAppellation.put("s,w,f,m", "太姻姆");
        correspondingAppellation.put("s,w,f,ob", "姻兄");
        correspondingAppellation.put("s,w,f,lb", "姻弟");
        correspondingAppellation.put("d,h,m", "亲家母");
        correspondingAppellation.put("d,h,f", "亲家公");
        correspondingAppellation.put("d,h,f,f", "太姻翁");
        correspondingAppellation.put("d,h,f,m", "太姻姆");
        correspondingAppellation.put("d,h,f,ob", "姻兄");
        correspondingAppellation.put("d,h,f,lb", "姻弟");
        /*----------------------------亲戚互称------------------------------*/
        eachAppellation.put("", "");     //自己-自己
        eachAppellation.put("h", "w");   //老公-老婆
        eachAppellation.put("w", "h");   //老婆-老公
        eachAppellation.put("fM", "s");  //爸爸（后代男孩）-儿子
        eachAppellation.put("fF", "d");  //爸爸（后代女孩）-女儿
        eachAppellation.put("mM", "s");  //妈妈（后代男孩）-儿子
        eachAppellation.put("mF", "d");  //妈妈（后代女孩）-女儿
        eachAppellation.put("obM", "lb"); //哥哥（同辈男性）-弟弟
        eachAppellation.put("obF", "ls"); //哥哥（同辈女性）-妹妹
        eachAppellation.put("lbM", "ob"); //弟弟（同辈男性）-哥哥
        eachAppellation.put("lbF", "os"); //弟弟（同辈女性）-姐姐
        eachAppellation.put("osM", "lb"); //姐姐（同辈男性）-弟弟
        eachAppellation.put("osF", "ls"); //姐姐（同辈女性）-妹妹
        eachAppellation.put("lsM", "ob"); //妹妹（同辈男性）-哥哥
        eachAppellation.put("lsF", "os"); //妹妹（同辈女性）-姐姐
        eachAppellation.put("sM", "f");  //儿子（长辈男性）-爸爸
        eachAppellation.put("sF", "m");  //儿子（长辈女性）-妈妈
        eachAppellation.put("dM", "f");  //女儿（长辈男性）-爸爸
        eachAppellation.put("dF", "m");  //女儿（长辈女性）-妈妈
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
        tempCurrentRelative = new SqList(12);
        tempCurrentRelative.add("");
        mInputTextView = (TextView) findViewById(R.id.input);
        mResultTextView = (TextView) findViewById(R.id.result);
        mACButton = (Button) findViewById(R.id.ac_button);
        mBackButton = (Button) findViewById(R.id.back_button);
        mEqualButton = (Button) findViewById(R.id.equal);
        mEachButton = (Button) findViewById(R.id.mutual_view);
        mFatherButton = (Button) findViewById(R.id.father);
        mMotherButton = (Button) findViewById(R.id.mother);
        mHusbandButton = (Button) findViewById(R.id.husband);
        mWifeButton = (Button) findViewById(R.id.wife);
        mOldBrotherButton = (Button) findViewById(R.id.old_bother);
        mLittleBrotherButton = (Button) findViewById(R.id.little_bother);
        mOldSisterButton = (Button) findViewById(R.id.old_sister);
        mLittleSisterButton = (Button) findViewById(R.id.little_sister);
        mSonButton = (Button) findViewById(R.id.son);
        mDaughterButton = (Button) findViewById(R.id.daughter);

        onEqualClick();
        onClearClick();
        onBackClick();
        onEachViewClick();
        onRelativeClick();

        buttonUnable(mBackButton);
        buttonUnable(mEachButton);
    }

    //禁用按钮
    private void buttonUnable(Button button) {
        button.setTextColor(getResources().getColor(R.color.darkgray));
        button.setEnabled(false);
    }

    //启用按钮
    private void buttonEnable(Button button) {
        button.setTextColor(getResources().getColor(R.color.button_text));
        button.setEnabled(true);
    }

    // 等于按钮点击事件
    private void onEqualClick() {
        mEqualButton.setOnClickListener(v -> {
            buttonUnable(mBackButton);
            String results = searchRelative();
            if (results.length() == 0) {
                mResultTextView.setText("关系有点远，年长的就叫老祖宗，同龄人就叫帅哥美女吧~");
            } else {
                mResultTextView.setText(results);
            }
            currentRelative.delete(0, currentRelative.length());
            currentRelative.append("我");
            eachRelative = tempCurrentRelative.getItem(0);//将原始输入亲戚关系存入字符串
            tempCurrentRelative.clear();
            backRelative.clearStack();
            buttonEnable(mWifeButton);
            buttonEnable(mHusbandButton);
            buttonEnable(mEachButton);
        });
    }

    // 清除按钮处理事件
    private void onClearClick() {
        mACButton.setOnClickListener(v -> {
            buttonEnable(mWifeButton);
            buttonEnable(mHusbandButton);
            buttonEnable(mBackButton);
            buttonUnable(mEachButton);
            mEqualButton.setEnabled(true);
            currentRelative.delete(0, currentRelative.length());
            currentRelative.append("我");
            mInputTextView.setText("我");
            mResultTextView.setText("");
            tempCurrentRelative.clear();
        });
    }

    // 回退按钮处理事件
    private void onBackClick() {
        mBackButton.setOnClickListener(v -> {
            mEqualButton.setEnabled(true);
            if (tempCurrentRelative.getItem(0).length() != 0 && !backRelative.isEmpty()) {//用户当前输入不为空且上一步亲戚关系不为空
                tempCurrentRelative = backRelative.pop();
            } else {
                tempCurrentRelative.clear();
            }
            if (currentRelative.length() > 1) {//屏幕字符串回退“的xx”
                currentRelative.delete(currentRelative.length() - 3, currentRelative.length());
            }
            mInputTextView.setText(currentRelative);
            if (tempCurrentRelative.length() != 0) {//回退后亲戚关系不为空，则需要判断当前最后一位亲戚的性别
                String temptemp = tempCurrentRelative.getItem(0);
                sex = (temptemp.indexOf(',') == -1) ? temptemp
                        : temptemp.substring(temptemp.lastIndexOf(',') + 1, temptemp.length());
                if (isMale()) {//男性，禁用老公按钮，启用老婆按钮
                    buttonEnable(mWifeButton);
                    buttonUnable(mHusbandButton);
                } else {//女性，禁用老婆按钮，启用老公按钮
                    buttonEnable(mHusbandButton);
                    buttonUnable(mWifeButton);
                }
            }
        });
    }

    // 互查按钮处理事件，例：我的爸爸的哥哥-大伯的弟弟的儿子，“f,ob”-“lb,s”
    private void onEachViewClick() {
        mEachButton.setOnClickListener(v -> {
            String results = "";
            mInputTextView.setText("TA称呼我");
            Stack<String> tempStack = new Stack<String>();
            eachRelative += ",";
            int position = 0;
            while (eachRelative.length() != 0) {
                position = eachRelative.indexOf(',');
                tempStack.push(eachRelative.substring(0, position));
                eachRelative = eachRelative.substring(position + 1, eachRelative.length());
            }
            String tempKey = "";
            while (!tempStack.isEmpty()) {//用弹栈模拟用户反向输入并进行实时互称转换以及化简
                tempKey = tempStack.pop();
                sex = tempStack.getTop();
                if (sex != null) {// 下一个元素非空，即可以判断性别
                    if (tempKey.equals("h") || tempKey.equals("w") || tempKey.equals("")) {
                        tempCurrentRelative.allItemAppend(eachAppellation.get(tempKey));
                    } else if (isMale()) {
                        tempCurrentRelative.allItemAppend(eachAppellation.get(tempKey + "M"));
                    } else {
                        tempCurrentRelative.allItemAppend(eachAppellation.get(tempKey + "F"));
                    }
                    simplyRelative();
                } else//栈空无法判断性别
                {
                    if (tempKey.equals("h") || tempKey.equals("w") || tempKey.equals("")) {
                        tempCurrentRelative.allItemAppend(eachAppellation.get(tempKey));
                        simplyRelative();
                        results = searchRelative();
                    } else {//则两种情况M/F
                        SqList temptemp = new SqList(tempCurrentRelative);
                        tempCurrentRelative.allItemAppend(eachAppellation.get(tempKey + "M"));
                        temptemp.allItemAppend(eachAppellation.get(tempKey + "F"));
                        tempCurrentRelative.combine(temptemp);
                        simplyRelative();
                        results = searchRelative();
                    }
                }
            }
            if (results.length() != 0)
                mResultTextView.setText(results);
            else
                mResultTextView.setText("关系有点远，年长的就叫老祖宗，同龄人就叫帅哥美女吧~");
            mEqualButton.setEnabled(false);
            mEachButton.setEnabled(false);
            tempCurrentRelative.clear();
        });
    }

    // 查找亲戚称谓
    private String searchRelative() {
        SqList results = new SqList(20);
        String relativeResults = "";
        for (int i = 1; i < tempCurrentRelative.length(); i++) {
            if (correspondingAppellation.get(tempCurrentRelative.getItem(i)) != null) {
                results.add(correspondingAppellation.get(tempCurrentRelative.getItem(i)));
            }
        }
        for (int i = 2; i < results.length() - 1; i++) {
            relativeResults = relativeResults + results.getItem(i) + "/";
        }
        relativeResults += results.getItem(results.length() - 1);
        return relativeResults;
    }

    /*
     * 亲戚按钮点击事件
     *
     * 互查按钮不可用；等号按钮点击时，启用互查按钮。
     * 男性按钮点击时，老公按钮不可用；女性按钮点击时，老婆按钮不可用。
     *
     */
    private void onRelativeClick() {
        mHusbandButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的丈夫");
                showCurrentRelative();
                buttonUnable(mHusbandButton);
                buttonEnable(mWifeButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("h");//线性表所有亲戚关系+“h”
                simplyRelative();//实时化简
            }
        });
        mWifeButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的妻子");
                showCurrentRelative();
                buttonUnable(mWifeButton);
                buttonEnable(mHusbandButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("w");
                simplyRelative();
            }
        });
        mFatherButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的爸爸");
                showCurrentRelative();
                buttonUnable(mHusbandButton);
                buttonEnable(mWifeButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("f");
                simplyRelative();
            }
        });
        mMotherButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的妈妈");
                showCurrentRelative();
                buttonUnable(mWifeButton);
                buttonEnable(mHusbandButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("m");
                simplyRelative();
            }
        });
        mOldBrotherButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的哥哥");
                showCurrentRelative();
                buttonUnable(mHusbandButton);
                buttonEnable(mWifeButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("ob");
                simplyRelative();
            }
        });
        mLittleBrotherButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的弟弟");
                showCurrentRelative();
                buttonUnable(mHusbandButton);
                buttonEnable(mWifeButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("lb");
                simplyRelative();
            }
        });
        mOldSisterButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的姐姐");
                showCurrentRelative();
                buttonUnable(mWifeButton);
                buttonEnable(mHusbandButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("os");
                simplyRelative();
            }
        });
        mLittleSisterButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的妹妹");
                showCurrentRelative();
                buttonUnable(mWifeButton);
                buttonEnable(mHusbandButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("ls");
                simplyRelative();
            }
        });
        mSonButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的儿子");
                showCurrentRelative();
                buttonUnable(mHusbandButton);
                buttonEnable(mWifeButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("s");
                simplyRelative();
            }
        });
        mDaughterButton.setOnClickListener(v -> {
            if (currentRelative.length() < MAXSIZE) {
                backRelative.push(new SqList(tempCurrentRelative));
                currentRelative.append("的女儿");
                showCurrentRelative();
                buttonUnable(mWifeButton);
                buttonEnable(mHusbandButton);
                buttonEnable(mBackButton);
                buttonUnable(mEachButton);
                mEqualButton.setEnabled(true);
                tempCurrentRelative.allItemAppend("d");
                simplyRelative();
            }
        });
    }

    // 显示输入数据
    private void showCurrentRelative() {
        mInputTextView.setText(currentRelative.toString());
    }

    // 判断亲戚性别
    private boolean isMale() {
        // 倒数第三个字符串sex
        boolean temp = true;
        switch (sex) {
            case "h":
            case "f":
            case "ob":
            case "lb":
            case "s": {//丈夫、爸爸、哥哥、弟弟、儿子-男性
                temp = true;
                break;
            }
            case "w":
            case "m":
            case "os":
            case "ls":
            case "d": {//妻子、妈妈、姐姐、妹妹、女儿-女性
                temp = false;
                break;
            }
        }
        return temp;
    }

    // 化简称谓关系链
    private void simplyRelative() {
        for (int i = tempCurrentRelative.length() - 1; i > 0; i--)//循环结束控制i>0:i=0存放用户原始输入亲戚关系
        {
            String pattern = "((\\w{1,3},)*)(\\w{1,3},\\w{1,3})";//用户当前输入且化简后的亲戚关系的匹配模式：(…x，)(x，x)
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(tempCurrentRelative.getItem(i));
            if (m.find()) {
                switch (m.group(3))//…x，x，x取最后两位亲戚
                {
                    case "m,h":
                    case "os,f":
                    case "ob,f":
                    case "ls,f":
                    case "lb,f": {// 妈妈的老公、兄弟姐妹的爸爸-爸爸
                        tempCurrentRelative.changeValue(i, m.group(1) + "f");
                        break;
                    }
                    case "f,w":
                    case "os,m":
                    case "ob,m":
                    case "ls,m":
                    case "lb,m": {// 爸爸的老婆、兄弟姐妹的妈妈-妈妈
                        tempCurrentRelative.changeValue(i, m.group(1) + "m");
                        break;
                    }
                    case "d,ob":
                    case "d,lb":
                    case "s,ob":
                    case "s,lb": {// 孩子的兄弟是自己的儿子
                        tempCurrentRelative.changeValue(i, m.group(1) + "s");
                        break;
                    }
                    case "d,os":
                    case "d,ls":
                    case "s,os":
                    case "s,ls": {// 孩子的姐妹是自己的女儿
                        tempCurrentRelative.changeValue(i, m.group(1) + "d");
                        break;
                    }

                    case "os,ob":
                    case "ob,ob": {// 哥哥
                        tempCurrentRelative.changeValue(i, m.group(1) + "ob");
                        break;
                    }
                    case "ls,lb":
                    case "lb,lb": {// 弟弟
                        tempCurrentRelative.changeValue(i, m.group(1) + "lb");
                        break;
                    }
                    case "f,s":
                    case "m,s":
                    case "os,lb":
                    case "ob,lb":
                    case "ls,ob":
                    case "lb,ob": {// 可能是哥哥，弟弟，自己
                        tempCurrentRelative.changeValue(i, m.group(1) + "ob");
                        tempCurrentRelative.add(m.group(1) + "lb");
                        if (m.group(1).equals("")) {//如果用户仅输入了两位亲戚，则添加自己
                            tempCurrentRelative.add("");
                        } else {//获取倒数第三位性别，男性有可能是自己可化简为“”，女性只可能是哥哥或弟弟，无需化简为“”
                            sex = m.group(1).substring(0, m.group(1).length() - 1);
                            if (sex.indexOf(',') != -1) {
                                sex = sex.substring(sex.lastIndexOf(',') + 1, sex.length());
                            }
                            if (isMale()) {
                                tempCurrentRelative.add(m.group(1).substring(0, m.group(1).lastIndexOf(',')));
                            }
                        }
                        break;
                    }

                    case "os,os":
                    case "ob,os": {// 姐姐
                        tempCurrentRelative.changeValue(i, m.group(1) + "os");
                        break;
                    }
                    case "ls,ls":
                    case "lb,ls": {// 妹妹
                        tempCurrentRelative.changeValue(i, m.group(1) + "ls");
                        break;
                    }
                    case "f,d":
                    case "m,d":
                    case "ob,ls":
                    case "os,ls":
                    case "lb,os":
                    case "ls,os": {// 可能是姐姐，妹妹，自己
                        tempCurrentRelative.changeValue(i, m.group(1) + "os");
                        tempCurrentRelative.add(m.group(1) + "ls");
                        if (m.group(1).equals("")) {//如果用户仅输入了两位亲戚，则添加自己
                            tempCurrentRelative.add("");
                        } else {//获取倒数第三位性别，女性有可能是自己可化简为“”，男性只可能是哥哥或弟弟，无需化简为“”
                            sex = m.group(1).substring(0, m.group(1).length() - 1);
                            if (sex.indexOf(',') != -1) {
                                sex = sex.substring(sex.lastIndexOf(',') + 1, sex.length());
                            }
                            if (!isMale()) {
                                tempCurrentRelative.add(m.group(1).substring(0, m.group(1).lastIndexOf(',')));
                            }
                        }
                        break;
                    }
                    case "d,f":
                    case "s,f": {// 女儿或儿子的爸爸，需判断倒数第三位性别：男性-爸爸，女性-妈妈的老公（+“h”）
                        // 例：妹妹的女儿的爸爸-妹妹的老公；弟弟的女儿的爸爸-弟弟
                        if (m.group(1).length() != 0) {
                            sex = m.group(1).substring(0, m.group(1).length() - 1);
                            if (sex.indexOf(',') != -1) {
                                sex = sex.substring(sex.lastIndexOf(',') + 1, sex.length());
                            }
                            if (isMale()) {
                                tempCurrentRelative.changeValue(i, m.group(1).substring(0, m.group(1).lastIndexOf(',')));
                            } else {
                                tempCurrentRelative.changeValue(i, m.group(1) + "h");
                            }
                            simplyRelative();
                        } else {//如果用户仅输入两位亲戚，则可能是老公或自己
                            tempCurrentRelative.changeValue(i, "h");
                            tempCurrentRelative.add("");
                        }
                        break;
                    }

                    case "d,m":
                    case "s,m": {// 女儿或儿子的妈妈，需判断倒数第三位性别：女性-妈妈，男性-爸爸的老婆
                        // 例：妹妹的女儿的妈妈-妹妹；弟弟的女儿的妈妈-弟弟的妻子
                        if (m.group(1).length() != 0) {
                            sex = m.group(1).substring(0, m.group(1).length() - 1);
                            if (sex.indexOf(',') != -1) {
                                sex = sex.substring(sex.lastIndexOf(',') + 1, sex.length());
                            }
                            if (isMale()) {
                                tempCurrentRelative.changeValue(i, m.group(1) + "w");
                            } else {
                                tempCurrentRelative.changeValue(i, m.group(1).substring(0, m.group(1).lastIndexOf(',')));
                            }

                            simplyRelative();
                        } else {//如果用户仅输入两位亲戚，则可能是老婆或自己
                            tempCurrentRelative.changeValue(i, "w");
                            tempCurrentRelative.add("");
                        }
                        break;
                    }
                    case "h,s":
                    case "w,s": {// 夫妻的儿子是自己的儿子
                        tempCurrentRelative.changeValue(i, m.group(1) + "s");
                        break;
                    }

                    case "h,d":
                    case "w,d": {// 夫妻的女儿是自己的女儿
                        tempCurrentRelative.changeValue(i, m.group(1) + "d");
                        break;
                    }

                    case "w,h":
                    case "h,w": {// 老公的老婆
                        tempCurrentRelative.changeValue(i, m.group(1) + "");
                        if (tempCurrentRelative.getItem(i).length() != 0) {
                            tempCurrentRelative.changeValue(i, tempCurrentRelative.getItem(i).substring(0,
                                    tempCurrentRelative.getItem(i).length() - 1));
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }
}