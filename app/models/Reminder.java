
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;
import java.util.Date;

/**
 *
 * @author mukul
 */
@Entity
public class Reminder extends Model {
    
    @Required
    @OneToOne
    public Contact contact;
    
    
    @Required
    public Date reminderDate;
    
  public Reminder(Contact contact, Date date) {
        this.contact = contact;
        this.reminderDate = date;
    }
    
}