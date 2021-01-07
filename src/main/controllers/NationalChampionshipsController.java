package main.controllers;

import javafx.fxml.FXML;
import main.dao.DBChampionshipDAO;
import main.services.ChampionshipService;

public class NationalChampionshipsController {

    @FXML
    void initialize() {

        var champService = new ChampionshipService<>(DBChampionshipDAO::new);
        var championships = champService.findAll();
        System.out.println(championships.get(2).getUsers());
    }
}
