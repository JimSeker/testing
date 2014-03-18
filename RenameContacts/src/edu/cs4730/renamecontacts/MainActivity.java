package edu.cs4730.renamecontacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public TextView outputText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		outputText = (TextView) findViewById(R.id.output);
		fetchandupdate();


	}


	void fetchandupdate() {

		String name, newname, contact_id;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;


		StringBuffer output = new StringBuffer();

		ContentResolver contentResolver = getContentResolver();
		//first request all the contacts.
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				output.append("\n ID:"+contact_id);
				name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

				output.append("\n Org Name:" + name);
				newname = getHarry(Integer.parseInt(contact_id));
				if (newname != "") {
					output.append("\n New Name:" + newname);
					ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

					ops.add( ContentProviderOperation.newUpdate( Data.CONTENT_URI )
							.withSelection(ContactsContract.CommonDataKinds.StructuredName._ID + "=? AND " +
									Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'",
									new String[]{contact_id})
							.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newname)
							.build());     
					//now make the change.
					try {
						ContentProviderResult[] result = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
						if (result == null) {
							output.append("\n failed");
						} else {
							output.append("\n success");
						}
						outputText.setText(output);
					} catch (Exception e) {
						e.printStackTrace();
						//Toast.makeText(getBaseContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
					} 


				} else {
					output.append("\n new Name: Not changed.");
				}
				output.append("\n");
			}

		}

	}




	String getHarry(int num) {

		List<String> names = Arrays.asList("Adrian Pucey", "Alastor Mad Eye Moody", "Albus Dumbledore", "Alguff Awful", "Alice Longbottom", "Alicia Spinnet", "Ambrosius Flume", "Amelia Susan Bones", 
				"Amos Diggory", "Andrew Kirke", "Andromeda Blackv", "Andros the Invincible", "Angelina Johnson", "Arabella Figg", "Argus Filch", "Arthur Weasley", "Augusta Longbottom", 
				"Barny the Fruitbat", "Bartemius Crouch Jr.", "Bartemius Crouch Senior", "Belatrix Black", "Bellatrix Lestrange", "Bertha Jorkins", "Bertie Bott", "Bill Weasly", 
				"Birtha Jorkins", "Blaise Zabini", "Bloody Barron", "Bob Ogden", "Cassius  Warrington", "Cedric Diggory", "Charlie Weasley", "Cho Chang", "Ciceron Harkis", "Colin Creevy", 
				"Cormac McLaggen", "Cornelious Fudge", "Crookshanks", "Daphne Greengrass", "Dean Thomas", "Dedulus Diggle", "Dennis Creevey", "Dobby House Elf", "Dolores Umbrige", "Draco Malfoy",
				"Duddly Dersly", "Eddie Carmichael", "Edgar Bones", "Egor Karkeroff", "Eleanor Branstone", "Eloise Midgeon", "Emma Dobbs", "Emmaline Vance", "Ernie Mcmillan", "Ernie Prang", "Fat Fryer", 
				"Fat Lady", "Fenrir Greyback", "Filius Flitwick", "Finius Nigelus", "Fleur Delacour", "Florean Fortesque", "Frank Bryce", "Fred Weasley", "Gabrielle Delacour", "George Weasley", 
				"Gildaroy Lockheart", "Ginny Weasley", "Godric Gryffindor", "Graham Pritchard", "Gregory Goyle", "Gwenog Jones", "Hannah Abbott", "Harry Potter", "Hecter Dagworth Granger", 
				"Helga Hufflepuff", "Herbert Chorley", "Hermione Jane Granger", "Herold Dingle", "Humphrey Belcher", "Igor Karkaroff", "James Potter", "Justin Finch Flechly", "Katie Bell", 
				"Kingsley Shacklebolt", "Laura Madley", "Lavender Brown", "Lee Jordan", "Lily Potter", "Lord Voldemort", "Lucius Malfoy", "Ludovic Ludo Bagman", 
				"Luna Lovegood", "Madam Hooch", "Madam Malkin", "Madam Pince", "Madam Pomfrey", "Madam Rosmerta", "Maddam Maxime", "Maddam Pomfrey", "Maddam Rosemerter", "Malcolm Baddock", 
				"Marcus Belby", "Marcus Flint", "Marge Dursley", "Marietta Edgecome", "Mark Evans", "Marvalo Guant", "Merope Gaunt", "Micheal Corner", "Millicent Bagnold", "Millicent Bulstrode", 
				"Minerva McGonagall", "Moaning Myrtle", "Molly Weasley", "Montague", "Morfin Gaunt", "Mundungus Fletcher", "Narcissa Malfoy", "Neville Longbottom", "Nicholas Flamel", 
				"Nimphodorah Tonks", "Theodore Nott", "Nymphadora Tonks", "Olivander", "Oliver Wood", "Olympe Maxime", "Orla Quirke", "Otto Bagman", "Owen Cauldwell", "Padma Patil", 
				"Pansy Parkinson", "Parvatie Patil", "Penelope Clearwater", "Percy Weasly", "Pertunia Dursly", "Peter Petigrue", "Petunia Dursley", "Piers Polkis", "Pigwigeon", "Severus Snape", 
				"Remus Lupin", "Rits Skeeter", "Roger Davies", "Romilda Vane", "Ron Weasley", "Rose Zeller", "Rowena Ravenclaw", "Rubeus Hagrid", "Rufus Scrimgeour", "Salazar Slytherin", 
				"Sally-Anne Perks", "Seamus Finnigan", "Nicholas De Mimsey Porpington", "Sirius Black", "Susan Bones", "The Bloody Barron", "Grey Lady", "Theodore Nott", "Tom Riddle", "Vernon Dursley", 
				"Victoria Frobisher", "Viktor Krum", "Vincent Crabbe");
		if (num <names.size()) {
			return names.get(num);	
		} else {
			return "";
		}

	}


}
