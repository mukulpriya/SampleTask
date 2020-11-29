/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;
import javax.persistence.*;

/**
 *
 * @author mukul
 */
public class Contacts extends Application {
    @Before
    static void checkUser() {
        if(connected() == null) {
            flash.error("Please log in first");
            Application.index();
        }
    }
    
    public static void index() {
        List<Contact> contacts = Contact.find("byUser", connected()).fetch();
        List<Reminder> rems = Reminder.findAll();
        render(contacts,rems);
    }
    
    
    public static void create(Long id) {
        if(id == null) {
            render();
        }
        Contact contact = Contact.findById(id);
        render(contact);
    }
    public static void save(Contact contact) {
        
        contact.user = connected();
        validation.valid(contact);
        if(validation.hasErrors()) {
            if(request.isAjax()) error("Invalid value");
            render("@create", contact);
        }
        contact.save();
        int x = newContactAdded(contact);
        index();
    }
    
    public static void delete(Long id) {
        Contact contact = Contact.findById(id);
        int x = newContactDeleted(contact);
        contact.delete();
        flash.success("contact deleted for contact id %s", contact.id);
        index();
    }
    
    // ideally the following should be a PostPersist method after creation
    public static int newContactAdded(Contact contact) {
        Reminder rem = createReminder(contact);
        if (rem != null) rem.save();
        return 1;
        
    }
    
    // ideally this should be PrePersist method before seletion
    public static int newContactDeleted(Contact contact) {
        
        List<Reminder> list = Reminder.find("byContact", contact).fetch();
        for (Reminder rem : list) {
            rem.delete();
        }
        return 1;
    }
    
    
    public static Reminder createReminder(Contact contact) {
        List<Reminder> list = Reminder.find("byContact", contact).fetch();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(contact.birthdate);
        calendar.add(Calendar.HOUR_OF_DAY, -contact.reminder);
        
        Reminder rem = null;
        if (list.size() > 0) {
            rem = list.get(0);
            
            if (contact.reminder == 0){
                rem.delete();
                return null;
            } else {
                rem.reminderDate = calendar.getTime();
                return rem;
            }   
        } else {
            if (contact.reminder > 0) {
                rem = new Reminder(contact, calendar.getTime());
                return rem;
            }    
        }
        return rem;
}
    
}
