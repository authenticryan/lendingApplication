package com.example.sharingapp;

import android.content.Context;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME = "contactList.sav";

    ContactList() {
        this.contacts = new ArrayList<Contact>();
    }

    public void setContacts(ArrayList<Contact> contact_list) {
        this.contacts = contact_list;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<String>();
        for (final Contact contact : contacts) {
            usernames.add(contact.getUsername());
        }
        return usernames;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void deleteContact(Contact contact) {
        if(contacts.contains(contact)) {
            contacts.remove(contact);
        }
    }

    public Contact getContact(int index){
        return contacts.get(index);
    }

    public int getSize(){
        return contacts.size();
    }

    public int getIndex(Contact contact) {
        int index = 0;
        for( final Contact ittrContact : contacts) {
            if (ittrContact.getUsername().equalsIgnoreCase(contact.getUsername())){
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean hasContact(Contact contact){
        for (final Contact ittrContact : contacts) {
            if (ittrContact.getUsername().equalsIgnoreCase(contact.getUsername()))
                return true;
        }
        return false;
    }

    public Contact getContactByUsername(String username) {
        for(final Contact contact : contacts) {
            if(contact.getUsername() == username)
                return contact;
        }
        return null;
    }

    public void loadContacts(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }

    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username){
        ArrayList<String> usernames = this.getAllUsernames();
            for (final String contactsUsername : usernames) {
                if (contactsUsername.equalsIgnoreCase(username))
                    return false;
            }
        return true;
    }
}
