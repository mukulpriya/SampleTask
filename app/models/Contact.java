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

/**
 *
 * @author mukul
 */
@Entity
public class Contact extends Model {
    
    @Required
    @ManyToOne
    public User user;
    
    @Required
    public String name;
    
    @Required
    public Date birthdate;
    
    @Required
    @Email
    public String email;
    
    @Required
    @Min(0)
    public Integer reminder;
    
}

