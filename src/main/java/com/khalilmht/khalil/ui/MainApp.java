package com.khalilmht.khalil.ui;

import com.khalilmht.khalil.ui.model.*;
import com.khalilmht.khalil.ui.service.*;
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
    private final EvaluationApiService evaluationApiService = new EvaluationApiService();
    private final NoteApiService noteApiService = new NoteApiService();
    private final ClasseMatiereApiService classeMatiereApiService = new ClasseMatiereApiService();
    private final ClasseApiService classeApiService = new ClasseApiService();
    private final MatiereApiService matiereApiService = new MatiereApiService();
    private final NiveauApiService niveauApiService = new NiveauApiService();

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

    // --- Composants Vue Niveaux ---
    private TableView<NiveauDto> tableNiveaux;
    private ObservableList<NiveauDto> listeNiveauxData;
    private TextField txtCodeNiveau, txtNomNiveau;

    // --- Composants Vue Classes ---
    private TableView<ClasseDto> tableClasses;
    private ObservableList<ClasseDto> listeClassesData;
    private TextField txtCodeClasse, txtNomClasse, txtCapaciteClasse;
    private ComboBox<NiveauDto> comboNiveauClasse;

    // --- Composants Vue Matières ---
    private TableView<MatiereDto> tableMatieres;
    private ObservableList<MatiereDto> listeMatieresData;
    private TextField txtCodeMatiere, txtIntituleMatiere, txtCoeffMatiere;

    // --- Composants Vue Évaluations ---
    private TableView<EvaluationDto> tableEvaluations;
    private ObservableList<EvaluationDto> listeEvaluationsData;
    private TextField txtIntituleEval, txtNoteMaxEval, txtCoeffEval;
    private ComboBox<String> comboTypeEval, comboPeriodeEval;
    private DatePicker datePickerEval;
    private ComboBox<ClasseMatiereDto> comboClasseMatiereEval;

    // --- Composants Vue Notes ---
    private TableView<NoteDto> tableNotes;
    private ObservableList<NoteDto> listeNotesData;
    private ComboBox<EleveDto> comboEleveNote;
    private ComboBox<EvaluationDto> comboEvaluationNote;
    private TextField txtValeurNote;
    private TextArea txtAppreciation;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("School Management System - Tchad");

        mainLayout = new BorderPane();
        afficherPageAccueil();

        Scene scene = new Scene(mainLayout, 1300, 700);
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

        HBox statsBox = new HBox(15);
        statsBox.setAlignment(Pos.CENTER);

        statsBox.getChildren().addAll(
                creerCarteStatistique("Niveaux", "Cycles d'études", "#d69e2e"),
                creerCarteStatistique("Classes", "Structure pédagogique", "#2b6cb0"),
                creerCarteStatistique("Matières", "Enseignements & coeffs", "#805ad5"),
                creerCarteStatistique("Élèves", "Inscriptions & profils", "#3182ce"),
                creerCarteStatistique("Évaluations", "Planification examens", "#319795")
        );

        HBox actionsBox = new HBox(12);
        actionsBox.setAlignment(Pos.CENTER);

        Button btnAccNiveaux = creerBoutonMenuRapide("📊 Niveaux", "#d69e2e", e -> afficherVueNiveaux());
        Button btnAccClasses = creerBoutonMenuRapide("🏫 Classes", "#2b6cb0", e -> afficherVueClasses());
        Button btnAccMatieres = creerBoutonMenuRapide("📚 Matières", "#805ad5", e -> afficherVueMatieres());
        Button btnAccEleves = creerBoutonMenuRapide("👥 Élèves", "#3182ce", e -> afficherVueEleves());
        Button btnAccEvals = creerBoutonMenuRapide("📅 Évaluations", "#319795", e -> afficherVueEvaluations());
        Button btnAccNotes = creerBoutonMenuRapide("📝 Notes", "#c53030", e -> afficherVueNotes());

        actionsBox.getChildren().addAll(btnAccNiveaux, btnAccClasses, btnAccMatieres, btnAccEleves, btnAccEvals, btnAccNotes);

        accueilPane.getChildren().addAll(lblTitre, lblSousTitre, new Separator(), statsBox, actionsBox);
        mainLayout.setCenter(accueilPane);
        mainLayout.setTop(creerBarreNavigation());
    }

    private VBox creerCarteStatistique(String titre, String description, String couleurHex) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(190);
        card.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label lblTitle = new Label(titre);
        lblTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + couleurHex + ";");

        Label lblDesc = new Label(description);
        lblDesc.setWrapText(true);
        lblDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #718096;");

        card.getChildren().addAll(lblTitle, lblDesc);
        return card;
    }

    private Button creerBoutonMenuRapide(String texte, String couleur, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button btn = new Button(texte);
        btn.setStyle("-fx-background-color: " + couleur + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12; -fx-cursor: hand; -fx-background-radius: 5;");
        btn.setOnAction(action);
        return btn;
    }

    /**
     * BARRE DE NAVIGATION COMMUNE
     */
    private HBox creerBarreNavigation() {
        HBox navBar = new HBox(10);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10, 20, 10, 20));
        navBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e0; -fx-border-width: 0 0 1 0;");

        Button btnHome = new Button("🏠 Accueil");
        btnHome.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnHome.setOnAction(e -> afficherPageAccueil());

        Button btnNiveauxMenu = new Button("📊 Niveaux");
        btnNiveauxMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnNiveauxMenu.setOnAction(e -> afficherVueNiveaux());

        Button btnClassesMenu = new Button("🏫 Classes");
        btnClassesMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnClassesMenu.setOnAction(e -> afficherVueClasses());

        Button btnMatieresMenu = new Button("📚 Matières");
        btnMatieresMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnMatieresMenu.setOnAction(e -> afficherVueMatieres());

        Button btnElevesMenu = new Button("👥 Élèves");
        btnElevesMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnElevesMenu.setOnAction(e -> afficherVueEleves());

        Button btnEvalsMenu = new Button("📅 Évaluations");
        btnEvalsMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnEvalsMenu.setOnAction(e -> afficherVueEvaluations());

        Button btnNotesMenu = new Button("📝 Notes");
        btnNotesMenu.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        btnNotesMenu.setOnAction(e -> afficherVueNotes());

        Label lblAppInfo = new Label("SMS v1.0 | Tchad");
        lblAppInfo.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 11px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(btnHome, btnNiveauxMenu, btnClassesMenu, btnMatieresMenu, btnElevesMenu, btnEvalsMenu, btnNotesMenu, spacer, lblAppInfo);
        return navBar;
    }

    /**
     * 2. VUE GESTION DES NIVEAUX
     */
    private void afficherVueNiveaux() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("GESTION DES NIVEAUX SCOLAIRES");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d69e2e;");

        Button btnActualiser = new Button("Actualiser");
        btnActualiser.setOnAction(e -> chargerDonneesNiveaux());

        HBox topBar = new HBox(15, titreLabel, btnActualiser);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0;");

        tableNiveaux = new TableView<>();
        listeNiveauxData = FXCollections.observableArrayList();
        tableNiveaux.setItems(listeNiveauxData);

        TableColumn<NiveauDto, String> colCode = new TableColumn<>("Code");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(120);

        TableColumn<NiveauDto, String> colNom = new TableColumn<>("Nom du Niveau");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(250);

        tableNiveaux.getColumns().addAll(colCode, colNom);

        BorderPane niveauxLayout = new BorderPane();
        niveauxLayout.setTop(topBar);
        niveauxLayout.setCenter(tableNiveaux);
        niveauxLayout.setRight(creerFormulaireNiveau());

        mainLayout.setCenter(niveauxLayout);
        chargerDonneesNiveaux();
    }

    private VBox creerFormulaireNiveau() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(310);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Nouveau Niveau");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(6);
        grid.setHgap(10);

        txtCodeNiveau = new TextField();
        txtCodeNiveau.setPromptText("Ex: 6EME");
        txtNomNiveau = new TextField();
        txtNomNiveau.setPromptText("Ex: Sixième");

        grid.add(new Label("Code:"), 0, 0); grid.add(txtCodeNiveau, 0, 1);
        grid.add(new Label("Nom:"), 0, 2); grid.add(txtNomNiveau, 0, 3);

        Button btnEnregistrer = new Button("Enregistrer le Niveau");
        btnEnregistrer.setStyle("-fx-background-color: #d69e2e; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrer.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrer.setOnAction(e -> soumettreFormulaireNiveau());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrer);
        return vbox;
    }

    private void soumettreFormulaireNiveau() {
        if (txtCodeNiveau.getText().isEmpty() || txtNomNiveau.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Remplissez tous les champs du niveau.");
            return;
        }

        NiveauDto dto = new NiveauDto();
        dto.setCode(txtCodeNiveau.getText().trim());
        dto.setNom(txtNomNiveau.getText().trim());

        String res = niveauApiService.creerNiveau(dto);
        if ("SUCCES".equals(res)) {
            txtCodeNiveau.clear();
            txtNomNiveau.clear();
            chargerDonneesNiveaux();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Niveau créé avec succès !");
        } else {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", res);
        }
    }

    private void chargerDonneesNiveaux() {
        if (listeNiveauxData != null) {
            listeNiveauxData.clear();
            listeNiveauxData.addAll(niveauApiService.recupererNiveaux());
        }
    }

    /**
     * 3. VUE GESTION DES CLASSES
     */
    private void afficherVueClasses() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("GESTION DES CLASSES");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2b6cb0;");

        Button btnActualiser = new Button("Actualiser");
        btnActualiser.setOnAction(e -> chargerDonneesClasses());

        HBox topBar = new HBox(15, titreLabel, btnActualiser);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0;");

        tableClasses = new TableView<>();
        listeClassesData = FXCollections.observableArrayList();
        tableClasses.setItems(listeClassesData);

        TableColumn<ClasseDto, String> colCode = new TableColumn<>("Code");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<ClasseDto, String> colNom = new TableColumn<>("Nom Classe");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<ClasseDto, Integer> colCap = new TableColumn<>("Capacité Max");
        colCap.setCellValueFactory(new PropertyValueFactory<>("capaciteMaximale"));

        tableClasses.getColumns().addAll(colCode, colNom, colCap);

        BorderPane classesLayout = new BorderPane();
        classesLayout.setTop(topBar);
        classesLayout.setCenter(tableClasses);
        classesLayout.setRight(creerFormulaireClasse());

        mainLayout.setCenter(classesLayout);
        chargerDonneesClasses();
    }

    private VBox creerFormulaireClasse() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(310);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Nouvelle Classe");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(6);
        grid.setHgap(10);

        txtCodeClasse = new TextField();
        txtCodeClasse.setPromptText("Ex: 6EME-A");
        txtNomClasse = new TextField();
        txtNomClasse.setPromptText("Ex: Sixième A");
        txtCapaciteClasse = new TextField("40");

        comboNiveauClasse = new ComboBox<>();
        comboNiveauClasse.setPromptText("Sélectionner un niveau...");
        comboNiveauClasse.setMaxWidth(Double.MAX_VALUE);
        comboNiveauClasse.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(NiveauDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNom() + " (" + item.getCode() + ")");
            }
        });
        comboNiveauClasse.setButtonCell(comboNiveauClasse.getCellFactory().call(null));
        comboNiveauClasse.setItems(FXCollections.observableArrayList(niveauApiService.recupererNiveaux()));

        grid.add(new Label("Code:"), 0, 0); grid.add(txtCodeClasse, 0, 1);
        grid.add(new Label("Nom:"), 0, 2); grid.add(txtNomClasse, 0, 3);
        grid.add(new Label("Niveau:"), 0, 4); grid.add(comboNiveauClasse, 0, 5);
        grid.add(new Label("Capacité Max:"), 0, 6); grid.add(txtCapaciteClasse, 0, 7);

        Button btnEnregistrer = new Button("Enregistrer la Classe");
        btnEnregistrer.setStyle("-fx-background-color: #2b6cb0; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrer.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrer.setOnAction(e -> soumettreFormulaireClasse());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrer);
        return vbox;
    }

    private void soumettreFormulaireClasse() {
        NiveauDto niv = comboNiveauClasse.getValue();
        if (txtCodeClasse.getText().isEmpty() || txtNomClasse.getText().isEmpty() || niv == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Remplissez tous les champs et choisissez un niveau.");
            return;
        }
        try {
            String res = classeApiService.creerClasse(
                    txtCodeClasse.getText().trim(),
                    txtNomClasse.getText().trim(),
                    niv.getId(),
                    Integer.parseInt(txtCapaciteClasse.getText().trim())
            );
            if ("SUCCES".equals(res)) {
                txtCodeClasse.clear(); txtNomClasse.clear();
                chargerDonneesClasses();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Classe créée avec succès !");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", res);
            }
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Format", "Capacité invalide.");
        }
    }

    private void chargerDonneesClasses() {
        if (listeClassesData != null) {
            listeClassesData.clear();
            listeClassesData.addAll(classeApiService.recupererClasses());
        }
    }

    /**
     * 4. VUE GESTION DES MATIÈRES
     */
    private void afficherVueMatieres() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("GESTION DES MATIÈRES");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #805ad5;");

        Button btnActualiser = new Button("Actualiser");
        btnActualiser.setOnAction(e -> chargerDonneesMatieres());

        HBox topBar = new HBox(15, titreLabel, btnActualiser);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0;");

        tableMatieres = new TableView<>();
        listeMatieresData = FXCollections.observableArrayList();
        tableMatieres.setItems(listeMatieresData);

        TableColumn<MatiereDto, String> colCode = new TableColumn<>("Code");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<MatiereDto, String> colIntitule = new TableColumn<>("Intitulé");
        colIntitule.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        TableColumn<MatiereDto, BigDecimal> colCoeff = new TableColumn<>("Coefficient");
        colCoeff.setCellValueFactory(new PropertyValueFactory<>("coefficient"));

        tableMatieres.getColumns().addAll(colCode, colIntitule, colCoeff);

        BorderPane matieresLayout = new BorderPane();
        matieresLayout.setTop(topBar);
        matieresLayout.setCenter(tableMatieres);
        matieresLayout.setRight(creerFormulaireMatiere());

        mainLayout.setCenter(matieresLayout);
        chargerDonneesMatieres();
    }

    private VBox creerFormulaireMatiere() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(310);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Nouvelle Matière");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(6);
        grid.setHgap(10);

        txtCodeMatiere = new TextField();
        txtCodeMatiere.setPromptText("Ex: MATH");
        txtIntituleMatiere = new TextField();
        txtIntituleMatiere.setPromptText("Ex: Mathématiques");
        txtCoeffMatiere = new TextField("2.0");

        grid.add(new Label("Code:"), 0, 0); grid.add(txtCodeMatiere, 0, 1);
        grid.add(new Label("Intitulé:"), 0, 2); grid.add(txtIntituleMatiere, 0, 3);
        grid.add(new Label("Coefficient:"), 0, 4); grid.add(txtCoeffMatiere, 0, 5);

        Button btnEnregistrer = new Button("Enregistrer la Matière");
        btnEnregistrer.setStyle("-fx-background-color: #805ad5; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrer.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrer.setOnAction(e -> soumettreFormulaireMatiere());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrer);
        return vbox;
    }

    private void soumettreFormulaireMatiere() {
        if (txtCodeMatiere.getText().isEmpty() || txtIntituleMatiere.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Remplissez tous les champs.");
            return;
        }
        try {
            MatiereDto dto = new MatiereDto();
            dto.setCode(txtCodeMatiere.getText().trim());
            dto.setIntitule(txtIntituleMatiere.getText().trim());
            dto.setCoefficient(new BigDecimal(txtCoeffMatiere.getText().trim()));

            String res = matiereApiService.creerMatiere(dto);
            if ("SUCCES".equals(res)) {
                txtCodeMatiere.clear(); txtIntituleMatiere.clear();
                chargerDonneesMatieres();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Matière créée !");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", res);
            }
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Format", "Coefficient invalide.");
        }
    }

    private void chargerDonneesMatieres() {
        if (listeMatieresData != null) {
            listeMatieresData.clear();
            listeMatieresData.addAll(matiereApiService.recupererMatieres());
        }
    }

    /**
     * 5. VUE GESTION DES ÉLÈVES
     */
    private void afficherVueEleves() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("GESTION DES ÉLÈVES");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #3182ce;");

        txtRechercheEleve = new TextField();
        txtRechercheEleve.setPromptText("Rechercher (Nom, Prénom, Matricule)...");
        txtRechercheEleve.setPrefWidth(230);
        txtRechercheEleve.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredElevesData.setPredicate(eleve -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String filter = newVal.toLowerCase();
                return (eleve.getNom() != null && eleve.getNom().toLowerCase().contains(filter)) ||
                        (eleve.getPrenom() != null && eleve.getPrenom().toLowerCase().contains(filter)) ||
                        (eleve.getMatricule() != null && eleve.getMatricule().toLowerCase().contains(filter));
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
        TableColumn<EleveDto, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<EleveDto, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        TableColumn<EleveDto, String> colSexe = new TableColumn<>("Sexe");
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colSexe.setPrefWidth(50);
        TableColumn<EleveDto, String> colLieu = new TableColumn<>("Lieu Naissance");
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));
        TableColumn<EleveDto, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<EleveDto, LocalDate> colDate = new TableColumn<>("Date Naissance");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        tableEleves.getColumns().addAll(colMatricule, colNom, colPrenom, colSexe, colLieu, colEmail, colDate);
        tableEleves.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) remplirFormulaireEleve(newSel);
        });

        BorderPane elevesLayout = new BorderPane();
        elevesLayout.setTop(topBar);
        elevesLayout.setCenter(tableEleves);
        elevesLayout.setRight(creerFormulaireEleve());

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
        comboSexe = new ComboBox<>(FXCollections.observableArrayList("M", "F"));
        comboSexe.setValue("M");
        comboSexe.setMaxWidth(Double.MAX_VALUE);
        txtEmail = new TextField();
        datePickerNaissance = new DatePicker(LocalDate.of(2010, 1, 1));
        datePickerNaissance.setMaxWidth(Double.MAX_VALUE);

        grid.add(new Label("Matricule:"), 0, 0); grid.add(txtMatricule, 0, 1);
        grid.add(new Label("Nom:"), 0, 2); grid.add(txtNom, 0, 3);
        grid.add(new Label("Prénom:"), 0, 4); grid.add(txtPrenom, 0, 5);
        grid.add(new Label("Sexe:"), 0, 6); grid.add(comboSexe, 0, 7);
        grid.add(new Label("Lieu Naissance:"), 0, 8); grid.add(txtLieuNaissance, 0, 9);
        grid.add(new Label("Email:"), 0, 10); grid.add(txtEmail, 0, 11);
        grid.add(new Label("Date Naissance:"), 0, 12); grid.add(datePickerNaissance, 0, 13);

        btnEnregistrerEleve = new Button("Enregistrer (Ajout)");
        btnEnregistrerEleve.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrerEleve.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrerEleve.setOnAction(e -> soumettreFormulaireEleve());

        btnSupprimerEleve = new Button("Supprimer");
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
        if (eleve.getDateNaissance() != null) datePickerNaissance.setValue(eleve.getDateNaissance());
        btnEnregistrerEleve.setText("Modifier l'élève");
        btnEnregistrerEleve.setStyle("-fx-background-color: #d69e2e; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSupprimerEleve.setDisable(false);
    }

    private void reinitialiserFormulaireEleve() {
        selectedEleveId = null;
        txtMatricule.clear(); txtNom.clear(); txtPrenom.clear(); txtLieuNaissance.clear(); txtEmail.clear();
        comboSexe.setValue("M");
        datePickerNaissance.setValue(LocalDate.of(2010, 1, 1));
        if (tableEleves != null) tableEleves.getSelectionModel().clearSelection();
        btnEnregistrerEleve.setText("Enregistrer (Ajout)");
        btnEnregistrerEleve.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSupprimerEleve.setDisable(true);
    }

    private void soumettreFormulaireEleve() {
        if (txtMatricule.getText().isEmpty() || txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Remplissez les champs obligatoires.");
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

        String res = selectedEleveId == null ? eleveApiService.creerEleve(dto) : eleveApiService.modifierEleve(selectedEleveId, dto);
        if ("SUCCES".equals(res)) {
            reinitialiserFormulaireEleve();
            chargerDonneesEleves();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Opération réussie !");
        } else {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", res);
        }
    }

    private void supprimerSelectionEleve() {
        if (selectedEleveId == null) return;
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer cet élève ?", ButtonType.YES, ButtonType.NO);
        conf.showAndWait();
        if (conf.getResult() == ButtonType.YES && eleveApiService.supprimerEleve(selectedEleveId)) {
            reinitialiserFormulaireEleve();
            chargerDonneesEleves();
        }
    }

    private void chargerDonneesEleves() {
        if (listeElevesData != null) {
            listeElevesData.clear();
            listeElevesData.addAll(eleveApiService.recupererEleves());
        }
    }

    /**
     * 6. VUE GESTION DES ÉVALUATIONS
     */
    private void afficherVueEvaluations() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("PLANIFICATION DES ÉVALUATIONS");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #319795;");

        Button btnActualiser = new Button("Actualiser");
        btnActualiser.setOnAction(e -> chargerDonneesEvaluations());

        HBox topBar = new HBox(15, titreLabel, btnActualiser);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0;");

        tableEvaluations = new TableView<>();
        listeEvaluationsData = FXCollections.observableArrayList();
        tableEvaluations.setItems(listeEvaluationsData);

        TableColumn<EvaluationDto, String> colIntitule = new TableColumn<>("Intitulé");
        colIntitule.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        TableColumn<EvaluationDto, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("typeEvaluation"));
        TableColumn<EvaluationDto, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateEvaluation"));
        TableColumn<EvaluationDto, BigDecimal> colNoteMax = new TableColumn<>("Note Max");
        colNoteMax.setCellValueFactory(new PropertyValueFactory<>("noteMaximale"));
        TableColumn<EvaluationDto, BigDecimal> colCoeff = new TableColumn<>("Coeff");
        colCoeff.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        TableColumn<EvaluationDto, String> colPeriode = new TableColumn<>("Période");
        colPeriode.setCellValueFactory(new PropertyValueFactory<>("periode"));

        tableEvaluations.getColumns().addAll(colIntitule, colType, colDate, colNoteMax, colCoeff, colPeriode);

        BorderPane evalLayout = new BorderPane();
        evalLayout.setTop(topBar);
        evalLayout.setCenter(tableEvaluations);
        evalLayout.setRight(creerFormulaireEvaluation());

        mainLayout.setCenter(evalLayout);
        chargerDonneesEvaluations();
    }

    private VBox creerFormulaireEvaluation() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(310);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Nouvelle Évaluation");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(6);
        grid.setHgap(10);

        txtIntituleEval = new TextField();
        txtIntituleEval.setPromptText("Ex: Devoir N°1");
        comboTypeEval = new ComboBox<>(FXCollections.observableArrayList("DEVOIR_SURVEILLE", "INTERROGATION", "COMPOSITION", "EXAMEN", "PRATIQUE", "AUTRE"));
        comboTypeEval.setValue("DEVOIR_SURVEILLE");
        comboTypeEval.setMaxWidth(Double.MAX_VALUE);

        datePickerEval = new DatePicker(LocalDate.now());
        datePickerEval.setMaxWidth(Double.MAX_VALUE);

        txtNoteMaxEval = new TextField("20.00");
        txtCoeffEval = new TextField("1.0");

        comboPeriodeEval = new ComboBox<>(FXCollections.observableArrayList("TRIMESTRE_1", "TRIMESTRE_2", "TRIMESTRE_3"));
        comboPeriodeEval.setValue("TRIMESTRE_1");
        comboPeriodeEval.setMaxWidth(Double.MAX_VALUE);

        comboClasseMatiereEval = new ComboBox<>();
        comboClasseMatiereEval.setPromptText("Sélectionner Classe & Matière...");
        comboClasseMatiereEval.setMaxWidth(Double.MAX_VALUE);
        comboClasseMatiereEval.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ClasseMatiereDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNomClasse() + " - " + item.getNomMatiere());
            }
        });
        comboClasseMatiereEval.setButtonCell(comboClasseMatiereEval.getCellFactory().call(null));
        comboClasseMatiereEval.setItems(FXCollections.observableArrayList(classeMatiereApiService.recupererClassesMatieres()));

        grid.add(new Label("Intitulé:"), 0, 0); grid.add(txtIntituleEval, 0, 1);
        grid.add(new Label("Type:"), 0, 2); grid.add(comboTypeEval, 0, 3);
        grid.add(new Label("Date:"), 0, 4); grid.add(datePickerEval, 0, 5);
        grid.add(new Label("Note Max:"), 0, 6); grid.add(txtNoteMaxEval, 0, 7);
        grid.add(new Label("Coefficient:"), 0, 8); grid.add(txtCoeffEval, 0, 9);
        grid.add(new Label("Période:"), 0, 10); grid.add(comboPeriodeEval, 0, 11);
        grid.add(new Label("Classe & Matière:"), 0, 12); grid.add(comboClasseMatiereEval, 0, 13);

        Button btnEnregistrerEval = new Button("Enregistrer l'Évaluation");
        btnEnregistrerEval.setStyle("-fx-background-color: #319795; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrerEval.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrerEval.setOnAction(e -> soumettreFormulaireEvaluation());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrerEval);
        return vbox;
    }

    private void soumettreFormulaireEvaluation() {
        ClasseMatiereDto selectedClasseMatiere = comboClasseMatiereEval.getValue();
        if (txtIntituleEval.getText().isEmpty() || selectedClasseMatiere == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Remplissez l'intitulé et choisissez la classe-matière.");
            return;
        }

        try {
            EvaluationDto dto = new EvaluationDto();
            dto.setIntitule(txtIntituleEval.getText().trim());
            dto.setTypeEvaluation(comboTypeEval.getValue());
            dto.setDateEvaluation(datePickerEval.getValue());
            dto.setNoteMaximale(new BigDecimal(txtNoteMaxEval.getText().trim()));
            dto.setCoefficient(new BigDecimal(txtCoeffEval.getText().trim()));
            dto.setPeriode(comboPeriodeEval.getValue());
            dto.setClasseMatiereId(selectedClasseMatiere.getId());
            dto.setEstValidee(false);

            String resultat = evaluationApiService.creerEvaluation(dto);
            if ("SUCCES".equals(resultat)) {
                txtIntituleEval.clear();
                chargerDonneesEvaluations();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Évaluation planifiée !");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", resultat);
            }
        } catch (Exception e) {
            afficherAlerte(Alert.AlertType.ERROR, "Format", "Vérifiez les valeurs numériques.");
        }
    }

    private void chargerDonneesEvaluations() {
        if (listeEvaluationsData != null) {
            listeEvaluationsData.clear();
            listeEvaluationsData.addAll(evaluationApiService.recupererEvaluations());
        }
    }

    /**
     * 7. VUE GESTION DES NOTES
     */
    private void afficherVueNotes() {
        mainLayout.setTop(creerBarreNavigation());

        Label titreLabel = new Label("SAISIE ET SUIVI DES NOTES");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #c53030;");

        Button btnActualiser = new Button("Actualiser");
        btnActualiser.setOnAction(e -> chargerDonneesNotes());

        HBox topBar = new HBox(15, titreLabel, btnActualiser);
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
        TableColumn<NoteDto, Long> colEleveId = new TableColumn<>("Élève ID");
        colEleveId.setCellValueFactory(new PropertyValueFactory<>("eleveId"));
        TableColumn<NoteDto, BigDecimal> colValeur = new TableColumn<>("Note");
        colValeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        TableColumn<NoteDto, String> colAppreciation = new TableColumn<>("Appréciation");
        colAppreciation.setCellValueFactory(new PropertyValueFactory<>("appreciation"));

        tableNotes.getColumns().addAll(colId, colEval, colEleveId, colValeur, colAppreciation);

        BorderPane notesLayout = new BorderPane();
        notesLayout.setTop(topBar);
        notesLayout.setCenter(tableNotes);
        notesLayout.setRight(creerFormulaireNote());

        mainLayout.setCenter(notesLayout);
        chargerDonneesNotes();
    }

    private VBox creerFormulaireNote() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(320);
        vbox.setStyle("-fx-background-color: #edf2f7; -fx-border-color: #cbd5e0;");

        Label formTitle = new Label("Attribution d'une Note");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);

        comboEleveNote = new ComboBox<>();
        comboEleveNote.setPromptText("Sélectionner un élève...");
        comboEleveNote.setMaxWidth(Double.MAX_VALUE);
        comboEleveNote.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EleveDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getMatricule() + " - " + item.getNom() + " " + item.getPrenom());
            }
        });
        comboEleveNote.setButtonCell(comboEleveNote.getCellFactory().call(null));

        comboEvaluationNote = new ComboBox<>();
        comboEvaluationNote.setPromptText("Sélectionner une évaluation...");
        comboEvaluationNote.setMaxWidth(Double.MAX_VALUE);
        comboEvaluationNote.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EvaluationDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getIntitule() + " (" + item.getPeriode() + ")");
            }
        });
        comboEvaluationNote.setButtonCell(comboEvaluationNote.getCellFactory().call(null));

        txtValeurNote = new TextField();
        txtValeurNote.setPromptText("Ex: 15.5");

        txtAppreciation = new TextArea();
        txtAppreciation.setPromptText("Commentaire...");
        txtAppreciation.setPrefRowCount(3);

        grid.add(new Label("Élève:"), 0, 0); grid.add(comboEleveNote, 0, 1);
        grid.add(new Label("Évaluation:"), 0, 2); grid.add(comboEvaluationNote, 0, 3);
        grid.add(new Label("Note:"), 0, 4); grid.add(txtValeurNote, 0, 5);
        grid.add(new Label("Appréciation:"), 0, 6); grid.add(txtAppreciation, 0, 7);

        Button btnEnregistrerNote = new Button("Enregistrer la Note");
        btnEnregistrerNote.setStyle("-fx-background-color: #c53030; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEnregistrerNote.setMaxWidth(Double.MAX_VALUE);
        btnEnregistrerNote.setOnAction(e -> soumettreFormulaireNote());

        vbox.getChildren().addAll(formTitle, grid, btnEnregistrerNote);
        return vbox;
    }

    private void soumettreFormulaireNote() {
        EleveDto selectedEleve = comboEleveNote.getValue();
        EvaluationDto selectedEval = comboEvaluationNote.getValue();

        if (selectedEleve == null || selectedEval == null || txtValeurNote.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.WARNING, "Champs requis", "Sélectionnez un élève, une évaluation et la note.");
            return;
        }

        try {
            NoteDto noteDto = new NoteDto();
            noteDto.setEleveId(selectedEleve.getId());
            noteDto.setEvaluationId(selectedEval.getId());
            noteDto.setValeur(new BigDecimal(txtValeurNote.getText().trim()));
            noteDto.setAppreciation(txtAppreciation.getText().trim());

            String resultat = noteApiService.enregistrerNote(noteDto);
            if ("SUCCES".equals(resultat)) {
                txtValeurNote.clear();
                txtAppreciation.clear();
                chargerDonneesNotes();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Note enregistrée !");
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", resultat);
            }
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Format", "La note doit être un nombre valide.");
        }
    }

    private void chargerDonneesNotes() {
        if (listeNotesData != null) {
            listeNotesData.clear();
            listeNotesData.addAll(noteApiService.recupererToutesLesNotes());
        }
        if (comboEleveNote != null) comboEleveNote.setItems(FXCollections.observableArrayList(eleveApiService.recupererEleves()));
        if (comboEvaluationNote != null) comboEvaluationNote.setItems(FXCollections.observableArrayList(evaluationApiService.recupererEvaluations()));
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