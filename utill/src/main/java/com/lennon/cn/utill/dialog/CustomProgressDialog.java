package com.lennon.cn.utill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Loading;

import lennon.com.utill.R;


/**
 * @author luoying
 * 
 */
public class CustomProgressDialog extends Dialog {

	private Context mContext;

	public CustomProgressDialog(Context context) {
		super(context, R.style.dialog_progress);
		this.mContext = context;
		setParams();
	}

	public CustomProgressDialog(Context context, String title, String msg) {
		super(context, R.style.dialog_progress);
		this.mContext = context;
		setParams();
		setTitile(title);
		setMessage(msg);
	}

	public CustomProgressDialog(Context context, int msg_resid) {
		super(context, R.style.dialog_progress);
		this.mContext = context;
		setParams();
		setMessage(msg_resid);
	}

	public CustomProgressDialog(Context context, int title_resid, int msg_resid) {
		super(context, R.style.dialog_progress);
		this.mContext = context;
		setParams();
		setTitile(title_resid);
		setMessage(msg_resid);
	}

	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
		Loading loading = (Loading) findViewById(R.id.progressdialog_anim_progress);
		loading.start();
	}

	/**
	 * 
	 * setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public void setTitile(String strTitle) {

	}

	/**
	 * 
	 * setTitile 标题
	 * 
	 * @param title_resid
	 * @return
	 * 
	 */
	public void setTitile(int title_resid) {

	}

	/**
	 * 
	 * setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public void setMessage(String strMessage) {
		TextView tvMsg = (TextView) findViewById(R.id.progressdialog_ms_tv);
		if (tvMsg != null) {
			tvMsg.setVisibility(View.VISIBLE);
			tvMsg.setText(strMessage);
		}else{
			tvMsg.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * setMessage 提示内容
	 * 
	 * @param resid
	 * @return
	 * 
	 */
	public void setMessage(int resid) {
		TextView tvMsg = (TextView) findViewById(R.id.progressdialog_ms_tv);
		if (tvMsg != null) {
			tvMsg.setText(resid);
		}
	}

	private void setParams() {
		setContentView(R.layout.customprogressdialog);
		// 设置全屏
		getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		// 设置显示dialog后自动弹出输入法

	}

}
