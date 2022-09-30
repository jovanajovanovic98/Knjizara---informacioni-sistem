/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import constant.Constants;
import cordinator.MainCordinator;
import domain.User;
import form.LoginForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Korisnik
 */
public class LoginController {
     private final LoginForm frmLogin;

    public LoginController(LoginForm frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {
        frmLogin.setVisible(true);
    }
    
    public void closeForm() {
        frmLogin.setVisible(false);
    }
    
     private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser(e);
            }

            private void loginUser(ActionEvent e) {
                resetForm();
                try {
                    String username = frmLogin.getTxtUsername().getText().trim();
                    String password = String.valueOf(frmLogin.getTxtPassword().getPassword());
                    
                    validateForm(username, password);
                    User sendUser = new User();
                    sendUser.setUsername(username);
                    sendUser.setPassword(password);
                    User user = Communication.getInstance().login(username, password);
                    MainCordinator.getInstance().addParam(Constants.PARAM_CURRENT_USER, user);
                    JOptionPane.showMessageDialog(frmLogin, "Uspešno ste se ulogovali, " + user.getFirstname() + " " + user.getLastname(), "Login", JOptionPane.INFORMATION_MESSAGE);
   
                    frmLogin.dispose();
                    MainCordinator.getInstance().openMainForm();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmLogin, ex.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void resetForm() {
                frmLogin.getLblPasswordError().setText("");
                frmLogin.getLblUsernameError().setText("");
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";
                if (username.isEmpty()) {
                    frmLogin.getLblUsernameError().setText("Korisničko ime ne sme biti prazno!");
                    errorMessage += "Korisničko ime ne sme biti prazno!\n";

                }
                if (password.trim().isEmpty()) {
                    frmLogin.getLblPasswordError().setText("Lozinka ne sme biti prazna!");
                    errorMessage += "Lozinka ne sme biti prazna!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });
    }
}
