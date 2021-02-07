package com.cwj.we.module.jiqiren;

import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.Msg;
import com.cwj.we.bean.MsgBean;
import com.cwj.we.module.adapter.MsgAdapter;
import com.cwj.we.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能机器人
 */
public class JiqirenActivity extends BaseActivity<JiqirenPrensenter> implements JiqirenView {

    private List<Msg> msgList = new ArrayList<>();
    private EditText editText;
    private Button sendButton;
    private RecyclerView recyclerView;
    private MsgAdapter msgAdapter;

    @Override
    protected JiqirenPrensenter createPresenter() {
        return new JiqirenPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jiqiren;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        initMsgs();
        editText = findViewById(R.id.enter);
        sendButton = findViewById(R.id.send);
        recyclerView = findViewById(R.id.chatroomRecyclerView);
        //布局排列方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);
        sendButton.setOnClickListener(v -> {
            //得到输入框中的内容
            String content = editText.getText().toString();
            //判断内容不是空的
            if (!"".equals(content)) {
                presenter.msg(content);//智能机器人
                //将内容添加到单例中
                Msg msg = new Msg(content, Msg.TYPE_SENT);
                msgList.add(msg);
                //要求适配器重新刷新
                msgAdapter.notifyItemInserted(msgList.size() - 1);
                //要求recyclerView布局将消息刷新
                recyclerView.scrollToPosition(msgList.size() - 1);
                editText.setText("");
//                YUtils.showLoading(this, "回复中，请稍等...");
            } else {
                ToastUtil.showTextToast(this, "输入内容不能为空");
            }
        });
    }

    public void initMsgs() {
        Msg msg1 = new Msg("你好，我是智能机器人菲菲！", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("我会的可多了：天气、翻译、藏头诗、笑话、歌词、计算、域名信息/备案/收录查询、IP查询、手机号码归属、人工智能聊天我都会，快来和我聊天吧！", Msg.TYPE_RECEIVED);
        msgList.add(msg2);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void msgData(MsgBean bean) {
//        YUtils.hideLoading();
        if (bean != null) {
            //将内容添加到单例中
            Msg msg = new Msg(bean.getContent().replace("{br}", "\n"), Msg.TYPE_RECEIVED);
            msgList.add(msg);
            //要求适配器重新刷新
            msgAdapter.notifyItemInserted(msgList.size() - 1);
            //要求recyclerView布局将消息刷新
            recyclerView.scrollToPosition(msgList.size() - 1);
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showTextToast(this, msg);
    }
}