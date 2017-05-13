/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Doink
 */
@Named(value = "loginBean")
@ManagedBean
@SessionScoped
public class LoginBean {
    @EJB
    private UserFacade userFacade;
            
    private String email;
    private String password;
    
    private User currentUser;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    public String login(){
        User user = userFacade.findUserByCredentiels(this.email, this.password);
        
        if (user == null) {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rossz email vagy jelszó!",""));
            return null;
        } else {
            currentUser = user;
            return "index";
        }
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/faces/pages/login";
    }
    
    public Boolean isAdmin(){
        return (this.currentUser != null && User.Role.ADMIN.equals(this.currentUser.getRole()));
    }
    
    public Boolean isUser(){
        return (this.currentUser != null && User.Role.USER.equals(this.currentUser.getRole()));
    }
    
    public Boolean isLoggedIn(){
        return this.currentUser != null;
    }
    public User getCurrentUser(){
        return this.currentUser;
    }
}
