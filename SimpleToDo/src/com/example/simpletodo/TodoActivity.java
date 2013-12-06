package com.example.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
	private final int EDIT_ITEM_REQUEST_CODE = 1;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public void addTodoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}

	private void updateTodoItem(String itemText, int itemPos) {
	    itemsAdapter.remove(itemsAdapter.getItem(itemPos));
	    itemsAdapter.insert(itemText, itemPos);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		readItems();
		
		lvItems = (ListView) findViewById(R.id.lvItems);
//		items = new ArrayList<String>();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
//		items.add("First Item");
//		items.add("Second Item");
		setupListViewListener();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {

	     String itemText = data.getStringExtra("item_text");
	     int itemPos = data.getExtras().getInt("item_pos");
	     updateTodoItem(itemText, itemPos);
		 itemsAdapter.notifyDataSetInvalidated();
		 saveItems();
	  }
	} 
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> aView, 
					View item, int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetInvalidated();
				saveItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> aView, 
					View item, int pos, long id) {
				Intent intentEditItem = new Intent(TodoActivity.this, 
									  EditItemActivity.class);
				String itemText = (String) lvItems.getItemAtPosition(pos);
				intentEditItem.putExtra("item_text", itemText);
				intentEditItem.putExtra("item_pos", pos);
				startActivityForResult(intentEditItem, EDIT_ITEM_REQUEST_CODE);

			}
		});
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
