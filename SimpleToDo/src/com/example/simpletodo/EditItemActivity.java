package com.example.simpletodo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class EditItemActivity extends Activity {
	int itemPos = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String itemText = getIntent().getStringExtra("item_text");
		itemPos = getIntent().getIntExtra("item_pos", 0);
		
		EditText editItem = (EditText) findViewById(R.id.etEditItem);
		editItem.setText(itemText);
		editItem.setSelection(editItem.getText().length());
		
		
		
		// Show the Up button in the action bar.
		setupActionBar();

	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void saveTodoItem(View v) {
		EditText etEditedItem = (EditText) findViewById(R.id.etEditItem);

		Intent editedItemData = new Intent();
		String newItemtext = etEditedItem.getText().toString(); 
		editedItemData.putExtra("item_text", newItemtext);
		editedItemData.putExtra("item_pos", itemPos);
		setResult(RESULT_OK, editedItemData);
		finish();
	}

	
	
}
