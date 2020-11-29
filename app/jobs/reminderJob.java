/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobs;
import models.*;
import play.jobs.*;
import play.libs.*;
import org.apache.commons.mail.*;
import java.util.*;
/**
 *
 * @author mukul
 */

@Every("1h")
public class reminderJob extends Job {
    
    public void doJob() {
        
        // bad way to do it . but was not able to find a proper querry to do this
        // ideally one need to just querry on the date every one hour 
        List<Reminder> rems = Reminder.findAll();
        
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int hour = calendar.get(Calendar.HOUR);
       
        for (Reminder rem : rems) {
            Date reminderDate = rem.reminderDate;
            calendar.setTime(reminderDate);
            
            int rDay = calendar.get(Calendar.DAY_OF_MONTH);
            int rMonth = calendar.get(Calendar.MONTH);
            int rHour = calendar.get(Calendar.HOUR);
            
            if (day == rDay && month == rMonth && hour == rHour) {
                 try {
                      Contact contact = rem.contact;
                      SimpleEmail email = new SimpleEmail();
                      email.setFrom("sender@test.fr");
                      email.addTo(contact.user.username);
                      email.setSubject("Birthday Reminder");
                      email.setMsg("Birthday reminder for " + contact.name);
                        Mail.send(email);
                    } catch (Exception ex) {
                        System.out.println("Unable to send email");
                        System.out.println(ex);
                    }
                
            }
            
        }       
    }
    
}