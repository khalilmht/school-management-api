package com.khalilmht.khalil.ui;

import com.khalilmht.khalil.ui.model.EleveDto;
import com.khalilmht.khalil.ui.model.NoteDto;
import com.khalilmht.khalil.ui.service.EleveApiService;
import com.khalilmht.khalil.ui.service.NoteApiService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MainApp extends Application {

    private final EleveApiService eleveApiService = new EleveApiService();
    private final NoteApiService noteApiService = new NoteApiService();

    private Stage primaryStage;
    private BorderPane mainLayout;

    // --- Composants Vue Élèves ---
    private TableView<EleveDto> tableEleves;
    private ObservableList<EleveDto> listeElevesData;
    private FilteredList<EleveDto> filteredElevesData;
    private TextField txtMatricule, txtNom, txtPrenom, txtLieuNaissance, txtEmail, txtRechercheEleve;
    private ComboBox<String> comboSexe;
    private DatePicker datePickerNaissance;
    private Long selectedEleveId = null;
    private Button btnEnregistrerEleve, btnSupprimerEleve;

    // --- Composants Vue Notes ---
    private TableView<NoteDto> tableNotes;
    private ObservableList<NoteDto> listeNotesData;
    private ComboBox<EleveDto> comboEleveNote;
    private TextField txtEvaluationId, txtValeurNote;
    private TextArea txtAppreciation;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("School Management System - Tchad");

        mainLayout = new BorderPane();

        // Afficher la page d'accueil au démarrage
        afficherPageAccueil();

        Scene scene = new Scene(mainLayout, 1200, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 1. PAGE D'ACCUEIL / DASHBOARD
     */
    private void afficherPageAccueil() {
        VBox accueilPane = new VBox(25);
        accueilPane.setAlignment(Pos.CENTER);
        accueilPane.setPadding(new Insets(40));
        accueilPane.setStyle("-fx-background-color: #f0f4f8;");

        Label lblTitre = new Label("Bienvenue sur le School Management System");
        lblTitre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1a365d;");

        Label lblSousTitre = new Label("Plateforme centralisée de gestion administrative et académique");
        lblSousTitre.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568;");

        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);

        VBox cardEleves = creerCarteStatistique("Gestion des Élèves", "Inscriptions, profils & dossiers", "#3182ce");
        VBox cardNotes = creerCarteStatistique("Notes & Bulletins", "Saisie et suivi des performances", "#d69e2e");
        VBox cardParametres = creerCarteStatistique("Paramètres", "Configuration du système", "#718096");

        statsBox.getChildren().addAll(cardEleves, cardNotes, cardParametres);

        HBox actionsBox = new HBox(15);
        actionsBox.setAlignment(Pos.CENTER);

        Button btnAccederEleves = new Button("Accéder à la Gestion des Élèves ->");
        btnAccederEleves.setStyle("-fx-background-color: #2b6cb0; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand;");
        btnAccederEleves.setOnAction(e -> afficherVueEleves());

        Button btnAccederNotes = new Button("Accéder au Module de Notes ->");
        btnAccederNotes.setStyle("-fx-background-color: #d69e2e; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand;");
        btnAccederNotes.setOnAction(e -> afficherVueNotes());

        actionsBox.getChildren().addAll(btnAccederEleves, btnAccederNotes);

        accueilPane.getChildren().addAll(lblTitre, lblSousTitre, new Separator(), statsBox, actionsBox);
        mainLayout.setCenter(accueilPane);
        mainLayout.setTop(creerBarreNavigation());
    }

    private VBox creerCarteStatistique(String titre, String description, String couleurHex) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(260);
        card.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label lblTitle = new Label(titre);
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + couleurHex + ";");

        Label lblDesc = new Label(description);
        lblDesc.setWrapText(true);
        lblDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

        card.getChildren().addAll(lblTitle, lblDesc);
        return card;
    }

    /**
     * BARRE DE NAVIGATION COMMUNE
     */
    private HBox creerBarreNavigation() {
        HBox navBar = new HBox(15);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(12, 20, 12, 20));
        navBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e0; -fx-border-width: 0 0 1 0;");

        Button btnHome = new Button("🏠 Accueil");
        btnHome.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnHome.setOnAction(e -> afficherPageAccueil());

        Button btnElevesMenu = new Button("👥 Élèves");
        btnElevesMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnElevesMenu.setOnAction(e -> afficherVueEleves());

        Button btnNotesMenu = new Button("📝 Notes");
        btnNotesMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnNotesMenu.setOnAction(e -> afficherVueNotes());

        Label lblAppInfo = new Label("SMS v1.0 | Spring Boot & JavaFX");
        lblAppInfo.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 11px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(btnHome, btnElevesMenu, btnNotesMenu, spacer, lblAppInfo);
        return navBar;
    }

    /**
     * 2. VUE GESTION DES ÉLÈVES
     */
    private void afficherVueEleves() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("GESTION DES ÉLÈVES");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2b6cb0;");

        txtRechercheEleve = new TextField();
        txtRechercheEleve.setPromptText("Rechercher (Nom, Prénom, Matricule)...");
        txtRechercheEleve.setPrefWidth(250);
        txtRechercheEleve.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredElevesData.setPredicate(eleve -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String filter = newValue.toLowerCase();
                if (eleve.getNom() != null && eleve.getNom().toLowerCase().contains(filter)) return true;
                if (eleve.getPrenom() != null && eleve.getPrenom().toLowerCase().contains(filter)) return true;
                if (eleve.getMatricule() != null && eleve.getMatricule().toLowerCase().contains(filter)) return true;
                return false;
            });
        });

        Button actualiserBtn = new Button("Actualiser");
        actualiserBtn.setOnAction(e -> chargerDonneesEleves());

        HBox topBar = new HBox(15, titreLabel, txtRechercheEleve, actualiserBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0;");

        tableEleves = new TableView<>();
        listeElevesData = FXCollections.observableArrayList();
        filteredElevesData = new FilteredList<>(listeElevesData, b -> true);
        tableEleves.setItems(filteredElevesData);

        TableColumn<EleveDto, String> colMatricule = new TableColumn<>("Matricule");
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        colMatricule.setPrefWidth(100);

        TableColumn<EleveDto, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(110);

        TableColumn<EleveDto, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPrenom.setPrefWidth(110);

        TableColumn<EleveDto, String> colSexe = new TableColumn<>("Sexe");
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colSexe.setPrefWidth(55);

        TableColumn<EleveDto, String> colLieu = new TableColumn<>("Lieu Naissance");
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));
        colLieu.setPrefWidth(110);

        TableColumn<EleveDto, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(140);

        TableColumn<EleveDto, LocalDate> colDate = new TableColumn<>("Date Naissance");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        colDate.setPrefWidth(110);

        tableEleves.getColumns().addAll(colMatricule, colNom, colPrenom, colSexe, colLieu, colEmail, colDate);
        tableEleves.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) remplirFormulaireEleve(newSel);
        });

        VBox formPane = creerFormulaireEleve();

        BorderPane elevesLayout = new BorderPane();
        elevesLayout.setTop(topBar);
        elevesLayout.setCenter(tableEleves);
        elevesLayout.setRight(formPane);

        mainLayout.setCenter(elevesLayout);
        chargerDonneesEleves();
    }

    private VBox creerFormulaireEleve() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(310);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Détails / Gestion Élève");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(10);

        txtMatricule = new TextField();
        txtNom = new TextField();
        txtPrenom = new TextField();
        txtLieuNaissance = new TextField();

        comboSexe = new ComboBox<>();
        comboSexe.getItems().addAll("M", "F");
        comboSexe.setValue("M");
        comboSexe.setMaxWidth(Double.MAX_VALUE);

        txtEmail = new TextField();
        datePickerNaissance = new DatePicker();
        datePickerNaissance.setValue(LocalDate.of(2010, 1, 1));
        datePickerNaissance.setMaxWidth(Double.MAX_VALUE);

        grid.add(new Label("Matricule:"), 0, 0);
        grid.add(txtMatricule, 0, 1);
        grid.add(new Label("Nom:"), 0, 2);
        grid.add(txtNom, 0, 3);
        grid.add(new Label("Prénom:"), 0, 4);
        grid.add(txtPrenom, 0, 5);
        grid.add(new Label("Sexe:"), 0, 6);
        grid.add(comboSexe, 0, 7);
        grid.add(new Label("Lieu de Naissance:"), 0, 8);
        grid.add(txtLieuNaissance, 0, 9);
        grid.add(new Label("Email:"), 0, 10);
        grid.add(txtEmail, 0, 11);
        grid.add(new Label("Date de Naissance:"), 0, 12);
        grid.add(datePickerNaissance, 0, 13);

        btnEnregistrerEleve = new Button("Enregistrer (Ajout)");
        btnEnregistrerEleve.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrerEleve.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrerEleve.setOnAction(e -> soumettreFormulaireEleve());

        btnSupprimerEleve = new Button("Supprimer la sélection");
        btnSupprimerEleve.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSupprimerEleve.setMaxWidth(Double.MAX_VALUE);
        btnSupprimerEleve.setDisable(true);
        btnSupprimerEleve.setOnAction(e -> supprimerSelectionEleve());

        Button btnAnnuler = new Button("Mode Ajout / Annuler");
        btnAnnuler.setMaxWidth(Double.MAX_VALUE);
        btnAnnuler.setOnAction(e -> reinitialiserFormulaireEleve());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrerEleve, btnSupprimerEleve, btnAnnuler);
        return vbox;
    }

    private void remplirFormulaireEleve(EleveDto eleve) {
        selectedEleveId = eleve.getId();
        txtMatricule.setText(eleve.getMatricule());
        txtNom.setText(eleve.getNom());
        txtPrenom.setText(eleve.getPrenom());
        comboSexe.setValue(eleve.getSexe());
        txtLieuNaissance.setText(eleve.getLieuNaissance());
        txtEmail.setText(eleve.getEmail());
        if (eleve.getDateNaissance() != null) {
            datePickerNaissance.setValue(eleve.getDateNaissance());
        }
        btnEnregistrerEleve.setText("Modifier l'élève");
        btnEnregistrerEleve.setStyle("-fx-background-color: #d69e2e; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSupprimerEleve.setDisable(false);
    }

    private void reinitialiserFormulaireEleve() {
        selectedEleveId = null;
        txtMatricule.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtLieuNaissance.clear();
        comboSexe.setValue("M");
        txtEmail.clear();
        datePickerNaissance.setValue(LocalDate.of(2010, 1, 1));
        if (tableEleves != null) tableEleves.getSelectionModel().clearSelection();
        if (btnEnregistrerEleve != null) {
            btnEnregistrerEleve.setText("Enregistrer (Ajout)");
            btnEnregistrerEleve.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");
        }
        if (btnSupprimerEleve != null) btnSupprimerEleve.setDisable(true);
    }

    private void soumettreFormulaireEleve() {
        if (txtMatricule.getText().isEmpty() || txtNom.getText().isEmpty() ||
                txtPrenom.getText().isEmpty() || txtLieuNaissance.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Veuillez remplir les champs obligatoires.");
            return;
        }

        EleveDto dto = new EleveDto();
        dto.setMatricule(txtMatricule.getText().trim());
        dto.setNom(txtNom.getText().trim());
        dto.setPrenom(txtPrenom.getText().trim());
        dto.setSexe(comboSexe.getValue());
        dto.setLieuNaissance(txtLieuNaissance.getText().trim());
        dto.setEmail(txtEmail.getText().trim());
        dto.setDateNaissance(datePickerNaissance.getValue());
        dto.setStatut("ACTIF");

        String resultat;
        if (selectedEleveId == null) {
            resultat = eleveApiService.creerEleve(dto);
        } else {
            dto.setId(selectedEleveId);
            resultat = eleveApiService.modifierEleve(selectedEleveId, dto);
        }

        if ("SUCCES".equals(resultat)) {
            reinitialiserFormulaireEleve();
            chargerDonneesEleves();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Opération effectuée avec succès !");
        } else {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", resultat);
        }
    }

    private void supprimerSelectionEleve() {
        if (selectedEleveId == null) return;

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cet élève ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            boolean succes = eleveApiService.supprimerEleve(selectedEleveId);
            if (succes) {
                reinitialiserFormulaireEleve();
                chargerDonneesEleves();
                afficherAlerte(Alert.AlertType.INFORMATION, "Supprimé", "L'élève a été supprimé.");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer l'élève.");
            }
        }
    }

    private void chargerDonneesEleves() {
        if (listeElevesData != null) {
            listeElevesData.clear();
            listeElevesData.addAll(eleveApiService.recupererEleves());
        }
    }

    /**
     * 3. VUE GESTION DES NOTES
     */
    private void afficherVueNotes() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("GESTION DES NOTES & ÉVALUATIONS");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d69e2e;");

        Button btnActualiserNotes = new Button("Actualiser les Notes");
        btnActualiserNotes.setOnAction(e -> chargerDonneesNotes());

        HBox topBar = new HBox(15, titreLabel, btnActualiserNotes);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0;");

        tableNotes = new TableView<>();
        listeNotesData = FXCollections.observableArrayList();
        tableNotes.setItems(listeNotesData);

        TableColumn<NoteDto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<NoteDto, Long> colEval = new TableColumn<>("Évaluation ID");
        colEval.setCellValueFactory(new PropertyValueFactory<>("evaluationId"));
        colEval.setPrefWidth(100);

        TableColumn<NoteDto, Long> colEleveId = new TableColumn<>("Élève ID");
        colEleveId.setCellValueFactory(new PropertyValueFactory<>("eleveId"));
        colEleveId.setPrefWidth(90);

        TableColumn<NoteDto, BigDecimal> colValeur = new TableColumn<>("Note (/20)");
        colValeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        colValeur.setPrefWidth(90);

        TableColumn<NoteDto, String> colAppreciation = new TableColumn<>("Appréciation");
        colAppreciation.setCellValueFactory(new PropertyValueFactory<>("appreciation"));
        colAppreciation.setPrefWidth(220);

        tableNotes.getColumns().addAll(colId, colEval, colEleveId, colValeur, colAppreciation);

        VBox formPane = creerFormulaireNote();

        BorderPane notesLayout = new BorderPane();
        notesLayout.setTop(topBar);
        notesLayout.setCenter(tableNotes);
        notesLayout.setRight(formPane);

        mainLayout.setCenter(notesLayout);
        chargerDonneesNotes();
    }

    private VBox creerFormulaireNote() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(320);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Saisie d'une Note");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);

        comboEleveNote = new ComboBox<>();
        comboEleveNote.setPromptText("Sélectionner un élève...");
        comboEleveNote.setMaxWidth(Double.MAX_VALUE);

        // Affichage propre du Nom et Prénom dans la ComboBox
        comboEleveNote.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EleveDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getMatricule() + " - " + item.getNom() + " " + item.getPrenom());
            }
        });
        comboEleveNote.setButtonCell(comboEleveNote.getCellFactory().call(null));

        txtEvaluationId = new TextField();
        txtEvaluationId.setPromptText("ID de l'évaluation");

        txtValeurNote = new TextField();
        txtValeurNote.setPromptText("Ex: 15.5");

        txtAppreciation = new TextArea();
        txtAppreciation.setPromptText("Commentaire / Appréciation...");
        txtAppreciation.setPrefRowCount(3);

        grid.add(new Label("Élève:"), 0, 0);
        grid.add(comboEleveNote, 0, 1);
        grid.add(new Label("Évaluation ID:"), 0, 2);
        grid.add(txtEvaluationId, 0, 3);
        grid.add(new Label("Note (/20):"), 0, 4);
        grid.add(txtValeurNote, 0, 5);
        grid.add(new Label("Appréciation:"), 0, 6);
        grid.add(txtAppreciation, 0, 7);

        Button btnEnregistrerNote = new Button("Enregistrer la Note");
        btnEnregistrerNote.setStyle("-fx-background-color: #d69e2e; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrerNote.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrerNote.setOnAction(e -> soumettreFormulaireNote());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrerNote);
        return vbox;
    }

    private void soumettreFormulaireNote() {
        EleveDto selectedEleve = comboEleveNote.getValue();
        if (selectedEleve == null || txtEvaluationId.getText().isEmpty() || txtValeurNote.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Veuillez sélectionner un élève, saisir l'ID d'évaluation et la note.");
            return;
        }

        try {
            BigDecimal noteVal = new BigDecimal(txtValeurNote.getText().trim());
            Long evalId = Long.parseLong(txtEvaluationId.getText().trim());

            NoteDto noteDto = new NoteDto();
            noteDto.setEleveId(selectedEleve.getId());
            noteDto.setEvaluationId(evalId);
            noteDto.setValeur(noteVal);
            noteDto.setAppreciation(txtAppreciation.getText().trim());

            String resultat = noteApiService.enregistrerNote(noteDto);

            if ("SUCCES".equals(resultat)) {
                txtValeurNote.clear();
                txtAppreciation.clear();
                chargerDonneesNotes();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "La note a été enregistrée avec succès !");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", resultat);
            }
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Format incorrect", "La note et l'ID d'évaluation doivent être des nombres valides.");
        }
    }

    private void chargerDonneesNotes() {
        if (listeNotesData != null) {
            listeNotesData.clear();
            listeNotesData.addAll(noteApiService.recupererToutesLesNotes());
        }
        if (comboEleveNote != null) {
            comboEleveNote.setItems(FXCollections.observableArrayList(eleveApiService.recupererEleves()));
        }
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}