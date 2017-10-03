package com.hch.qrcodetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateCodeActivity extends Activity {

	private Button generateButton;
	private EditText contentText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createcode);
		setTitle("生成二维码");

		generateButton = (Button) findViewById(R.id.generateButton);
		contentText = (EditText) findViewById(R.id.contentText);
		// 生成二维码
		generateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CreateCodeActivity.this,
						QrCodeActivity.class);
				String content = contentText.getText().toString();
				if (TextUtils.isEmpty(content)) {
					Toast.makeText(CreateCodeActivity.this, "请输入文字",
							Toast.LENGTH_SHORT).show();
					return;
				}
				intent.putExtra("content", content);
				startActivity(intent);
			}
		});

	}
}
