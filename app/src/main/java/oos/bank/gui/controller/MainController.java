/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oos.bank.gui.cellfactory.AccountCellFactory;
import oos.bank.gui.dialog.AddAccountDialog;
import oos.bank.gui.dialog.ErrorAlert;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController extends Controller {
    @FXML ListView<String> listView;
    @FXML Label label;
    @FXML Label customers;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        label.setText(bankModel.getName());
        updateBalance();
        showAccounts();
        setCustomers();
    }

    /**
     * Adds all accounts of PrivateBank to ListView listView
     */
    public void showAccounts(){
        listView.setItems(FXCollections.observableArrayList(bankModel.getAllAccounts()));
        listView.setCellFactory(factory -> new AccountCellFactory(this));
    }

    public void addAccount(){
        AddAccountDialog dialog = new AddAccountDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(account -> {
            try {
                bankModel.createAccount(account);
            } catch(Exception e){
                ErrorAlert alert = new ErrorAlert(e.getLocalizedMessage());
            }
        });
        listView.setItems(FXCollections.observableArrayList(bankModel.getAllAccounts()));
        setCustomers();
    }

    public void deleteAccount(String account) throws Exception {
        bankModel.deleteAccount(account);
        listView.getItems().remove(account);
        updateBalance();
        setCustomers();
    }

    public void deleteAll() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting all accounts ?");
        alert.setTitle("Delete all");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            try {
                for (String account : bankModel.getAllAccounts())
                    bankModel.deleteAccount(account);
            } catch (Exception e) {
                ErrorAlert errorAlert = new ErrorAlert(e.getLocalizedMessage());
            }
            listView.getItems().clear();
        }
        updateBalance();
        setCustomers();
    }

    public void setCustomers(){
        customers.setText(Integer.toString(bankModel.countCustomers()));
    }
}