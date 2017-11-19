package com.juns.wechat.activity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juns.wechat.bean.FriendBean;
import com.juns.wechat.bean.UserBean;
import com.juns.wechat.greendao.mydao.GreenDaoManager;
import com.juns.wechat.manager.AccountManager;
import com.juns.wechat.net.response.LoginBean;
import com.same.city.love.R;
import com.style.base.BaseActivity;
import com.style.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * create by 王者 on 2016/7/12
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    @Bind(R.id.etInputName)
    EditText etInputName;
    @Bind(R.id.etPassWord)
    EditText etPassWord;
    @Bind(R.id.tv_password)
    TextView tvPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_wenti)
    TextView tvWenti;
    @Bind(R.id.btn_register)
    Button btnRegister;
    private String userName, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLayoutResID = R.layout.activity_login;
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        userName = AccountManager.getInstance().getUserName();
        password = AccountManager.getInstance().getUserPassWord();
        setText(etInputName, userName);
        setText(etPassWord, password);
        btnLogin.setEnabled(true);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvWenti.setOnClickListener(this);
        etInputName.addTextChangedListener(new TextChange());
        etPassWord.addTextChangedListener(new TextChange());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wenti:
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                break;
            case R.id.btn_login:
                startLogin();
                break;
            default:
                break;
        }
    }

    private void startLogin() {
        userName = etInputName.getText().toString().trim().replace(" ", "");
        password = etPassWord.getText().toString().trim().replace(" ", "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            login(userName, password);
        } else {
            showToast("请填写账号或密码！");
        }
    }

    private void login(String userName, final String password) {
        if (!CommonUtil.isNetWorkConnected(this)) {
            showToast(R.string.toast_network_unavailable);
            return;
        }
        showProgressDialog("正在登录。。。");
        LoginBean data = new LoginBean();
        UserBean u = new UserBean();
        u.setUserId("8");
        u.setUserName(userName);
        u.setNickName("Star");
        data.setUserBean(u);
        data.setToken("dfhfoqfhqiuhfreuierhngjh");
        if ("8".equals(u.userId) && "17364814713".equals(userName) && "123456".equals(password)) {
            AccountManager.getInstance().setUser(data.userBean);
            AccountManager.getInstance().setToken(data.token);
            AccountManager.getInstance().setUserPassWord(password);
            /*UserBean ff = GreenDaoManager.getInstance().findByUserId("23");
            u.setHeadUrl(ff.headUrl);
            GreenDaoManager.getInstance().save(u);*/
            skip(MainActivity.class);
            finish();
        } else {
            dismissProgressDialog();
            showToast("账号或密码错误");
        }

       /* List<UserBean> list = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            UserBean u2 = new UserBean();
            u2.setUserId(i);
            u2.setUserName(1820282300 + i + "");
            u2.setNickName("用户=" + i);
        }
        GreenDaoManager.getInstance().saveUserList(list);

        List<FriendBean> friendBeanList = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            FriendBean f = new FriendBean();
            f.setOwnerId(1);
            f.setContactId(i);
            f.setRemark("好友" + i);
        }
        GreenDaoManager.getInstance().saveFriends(friendBeanList);*/

        /*HttpActionImpl.getInstance().login(TAG, userName, password, new NetDataBeanCallback<LoginBean>(LoginBean.class) {
            @Override
            protected void onCodeSuccess(LoginBean data) {
                dismissProgressDialog();
                AccountManager.getInstance().setUser(data.userBean);
                AccountManager.getInstance().setToken(data.token);
                AccountManager.getInstance().setUserPassWord(password);
                showProgressDialog("正在同步数据");
                SyncDataUtil.getInstance().syncData(TAG, new SyncDataUtil.Callback() {
                    @Override
                    public void onSuccess() {
                        skip(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        dismissProgressDialog();
                        showToast("数据同步错误");
                    }
                });
            }

            @Override
            protected void onCodeFailure(String msg) {
                dismissProgressDialog();
                showToast(msg);
            }
        });*/
    }

    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign2 = etInputName.getText().length() > 0;
            boolean Sign3 = etPassWord.getText().length() > 4;
            if (Sign2 & Sign3) {
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setEnabled(false);
            }
        }
    }

}
