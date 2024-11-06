package com.testgame.testgame;

import com.testgame.testgame.entities.*;
import javafx.animation.AnimationTimer; // Import pour la boucle d'animation
import javafx.application.Application; // Import pour créer une application JavaFX
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Classe principale GameApp qui lance le jeu. Gère l'initialisation, la logique de jeu, et l'affichage des scores.
 */
public class GameApp extends Application {
    // Déclaration des variables principales de jeu
    private Player player;
    private GameEventHandler gameEventHandler;
    private List<Projectile> projectiles; // Liste des projectiles actifs
    private List<Enemy> enemies; // Liste des ennemis actifs
    private List<AmmoPack> ammoPacks; // Liste des packs de munitions
    private Random random;
    private int killCount = 0; // Compteur de kills
    private double gameSpeed = 0.5; // Vitesse initiale du jeu
    private Text killCountText; // Affichage du compteur de kills
    private Text ammoText; // Affichage des munitions
    private boolean isAmmoPackPresent = false; // Indicateur de présence d'un pack de munitions
    private InputHandler inputHandler;
    private Pane root; // Pane racine pour les éléments de jeu

    private ExtraLifeItem extraLifeItem;
    private boolean hasExtraLife = false; // Indicateur de vie supplémentaire
    private Rectangle extraLifeIndicator; // Indicateur visuel de vie supplémentaire

    private List<Integer> highScores = new ArrayList<>(); // Liste des 5 meilleurs scores
    private AnimationTimer gameLoop;  // Boucle de jeu pour mettre à jour les éléments

    /**
     * Méthode de démarrage de l'application. Initialise le stage et affiche le menu.
     * @param primaryStage Le stage principal de l'application
     */
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        primaryStage.setTitle("Infinite Cube Frenzy");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon64.png")));

        showMenu(primaryStage);  // Affiche le menu au démarrage

        primaryStage.show();
    }

    /**
     * Affiche le menu principal avec un bouton "Start" et la liste des scores
     * @param stage Le stage où afficher le menu
     */
    private void showMenu(Stage stage) {
        Pane menuRoot = new Pane();
        menuRoot.setStyle("-fx-background-color: black;");

        VBox menuBox = new VBox(10);
        menuBox.setTranslateX(300);
        menuBox.setTranslateY(200);

        // Bouton Start pour commencer le jeu
        Button startButton = new Button("Start");
        startButton.setFont(new Font(20));
        startButton.setOnAction(event -> startGame(stage));  // Lance le jeu au clic

        // Affiche la liste des meilleurs scores (si disponible)
        Text highScoresTitle = new Text("Top 5 Scores");
        highScoresTitle.setFont(new Font(20));
        highScoresTitle.setFill(Color.WHITE);

        VBox scoresBox = new VBox(5);
        scoresBox.getChildren().add(highScoresTitle);
        for (int i = 0; i < Math.min(5, highScores.size()); i++) {
            Text scoreText = new Text("Top" + (i + 1) + " : " + highScores.get(i) + " kills");
            scoreText.setFont(new Font(16));
            scoreText.setFill(Color.WHITE);
            scoresBox.getChildren().add(scoreText);
        }

        menuBox.getChildren().addAll(startButton, scoresBox);
        menuRoot.getChildren().add(menuBox);

        stage.setScene(new Scene(menuRoot, 800, 600));
    }

    /**
     * Initialise une nouvelle partie en configurant le joueur, la scène, et les éléments de jeu.
     * @param stage Le stage où le jeu est affiché
     */
    private void startGame(Stage stage) {
        root = new Pane();  // Réinitialise la scène pour une nouvelle partie
        Scene scene = new Scene(root, 800, 600);

        // Initialisation des objets de jeu
        projectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        ammoPacks = new ArrayList<>();
        random = new Random();
        killCount = 0;
        gameSpeed = 0.5;
        hasExtraLife = false;
        isAmmoPackPresent = false;
        player = new Player(100, 100);
        root.getChildren().add(player.getShape());

        gameEventHandler = new GameEventHandler(player, scene.getWidth(), scene.getHeight(), gameSpeed);
        scene.setOnKeyPressed(gameEventHandler.getKeyPressedHandler());
        scene.setOnKeyReleased(gameEventHandler.getKeyReleasedHandler());

        inputHandler = new InputHandler(player, root, this);
        scene.setOnMouseClicked(event -> inputHandler.handleMouseClick(event));

        // Affichage du compteur de kills
        killCountText = new Text("Kills: " + killCount);
        killCountText.setFont(new Font(20));
        killCountText.setFill(Color.BLACK);
        killCountText.setX(10);
        killCountText.setY(20);
        root.getChildren().add(killCountText);

        // Affichage des munitions
        ammoText = new Text("Ammo: " + player.getAmmo() + "/" + player.getMaxAmmo());
        ammoText.setFont(new Font(20));
        ammoText.setFill(Color.BLACK);
        ammoText.setX(10);
        ammoText.setY(40);
        root.getChildren().add(ammoText);

        spawnEnemy(root); // Ajoute le premier ennemi

        // Démarre la boucle de jeu pour mettre à jour l'état du jeu en continu
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameEventHandler.update(gameSpeed);
                player.update();

                updateProjectiles(scene);
                updateEnemies(root);
                checkForAmmoSpawn();
                updateExtraLifeIndicatorPosition();
            }
        };
        gameLoop.start();

        stage.setScene(scene);
    }

    /**
     * Termine la partie, ajoute le score à la liste des meilleurs scores, et affiche le menu.
     * @param stage Le stage où afficher le menu de fin de jeu
     */
    private void endGame(Stage stage) {
        gameLoop.stop();  // Arrête la boucle de jeu
        if (killCount > 0) {
            highScores.add(killCount); // Ajoute le score final à la liste des meilleurs scores
            highScores.sort((a, b) -> b - a); // Trie les scores en ordre décroissant
            if (highScores.size() > 5) {
                highScores = highScores.subList(0, 5); // Conserve les 5 meilleurs scores uniquement
            }
        }
        showMenu(stage);  // Retour au menu principal
    }

    /**
     * Met à jour les projectiles, gère les collisions, et les supprime s'ils sortent des limites de la scène.
     * @param scene La scène de jeu
     */
    private void updateProjectiles(Scene scene) {
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            projectile.update();

            if (projectile.isOutOfBounds(scene.getWidth(), scene.getHeight())) {
                projectileIterator.remove();
                root.getChildren().remove(projectile.getShape());
                continue;
            }

            // Gère les collisions entre projectiles et ennemis
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy currentEnemy = enemyIterator.next();
                if (projectile.collidesWith(currentEnemy)) {
                    enemyIterator.remove();
                    root.getChildren().remove(currentEnemy.getShape());
                    projectileIterator.remove();
                    root.getChildren().remove(projectile.getShape());

                    killCount++;
                    updateKillCount();
                    increaseGameSpeed();

                    spawnEnemy(root);
                    break;
                }
            }
        }
    }

    /**
     * Met à jour les ennemis et vérifie les collisions avec le joueur.
     * @param root Le conteneur principal de la scène
     */
    private void updateEnemies(Pane root) {
        for (Enemy enemy : enemies) {
            if (enemy instanceof EnemyOrange) {
                ((EnemyOrange) enemy).update(projectiles, player, gameSpeed);
            } else {
                enemy.moveTowards(player.getX(), player.getY(), gameSpeed);
            }

            // Vérifie la collision avec le joueur
            if (enemy.collidesWithPlayer(player)) {
                if (hasExtraLife) {
                    hasExtraLife = false;
                    root.getChildren().remove(extraLifeIndicator);
                } else {
                    endGame((Stage) root.getScene().getWindow()); // Fin de jeu
                    return;
                }
                enemies.remove(enemy);
                root.getChildren().remove(enemy.getShape());
                spawnEnemy(root);
                break;
            }
        }

        // Vérifie la collecte des packs de munitions
        Iterator<AmmoPack> ammoIterator = ammoPacks.iterator();
        while (ammoIterator.hasNext()) {
            AmmoPack ammoPack = ammoIterator.next();
            if (player.getShape().getBoundsInParent().intersects(ammoPack.getShape().getBoundsInParent())) {
                player.reload();
                updateAmmoDisplay();
                ammoIterator.remove();
                root.getChildren().remove(ammoPack.getShape());
                isAmmoPackPresent = false;
            }
        }

        // Vérifie la collecte de la vie supplémentaire
        if (extraLifeItem != null && player.getShape().getBoundsInParent().intersects(extraLifeItem.getShape().getBoundsInParent())) {
            hasExtraLife = true;
            root.getChildren().remove(extraLifeItem.getShape());
            extraLifeItem = null;
            displayExtraLifeIndicator();
        }
    }

    /**
     * Vérifie s'il est nécessaire de faire apparaître un pack de munitions pour le joueur.
     */
    private void checkForAmmoSpawn() {
        if (player.needsAmmoPack() && !isAmmoPackPresent) {
            spawnAmmoPack();
            isAmmoPackPresent = true;
        }
    }

    /**
     * Ajoute un projectile à la scène.
     * @param projectile Le projectile à ajouter
     */
    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
        root.getChildren().add(projectile.getShape());
    }

    /**
     * Fait apparaître un nouvel ennemi à une distance minimale du joueur.
     * @param root Le conteneur principal de la scène
     */
    private void spawnEnemy(Pane root) {
        double minDistance = 400;
        double enemyX, enemyY;
        do {
            enemyX = random.nextDouble() * 800;
            enemyY = random.nextDouble() * 600;
        } while (Math.hypot(enemyX - player.getX(), enemyY - player.getY()) < minDistance);

        Enemy enemy;
        if (random.nextDouble() < 0.05 && !hasExtraLife && extraLifeItem == null) {
            spawnExtraLifeItem();
        }

        if (random.nextDouble() < 0.05) {
            enemy = new EnemyOrange(enemyX, enemyY);
        } else {
            enemy = new Enemy(enemyX, enemyY);
        }

        enemies.add(enemy);
        root.getChildren().add(enemy.getShape());
    }

    /**
     * Fait apparaître un objet de vie supplémentaire à une distance minimale du joueur.
     */
    private void spawnExtraLifeItem() {
        double minDistance = 400;
        double itemX, itemY;
        do {
            itemX = random.nextDouble() * 800;
            itemY = random.nextDouble() * 600;
        } while (Math.hypot(itemX - player.getX(), itemY - player.getY()) < minDistance);

        extraLifeItem = new ExtraLifeItem(itemX, itemY);
        root.getChildren().add(extraLifeItem.getShape());
    }

    /**
     * Fait apparaître un pack de munitions à une position aléatoire.
     */
    private void spawnAmmoPack() {
        double ammoX = random.nextDouble() * 800;
        double ammoY = random.nextDouble() * 600;
        AmmoPack ammoPack = new AmmoPack(ammoX, ammoY);
        ammoPacks.add(ammoPack);
        root.getChildren().add(ammoPack.getShape());
    }

    /**
     * Met à jour le texte du compteur de kills.
     */
    private void updateKillCount() {
        killCountText.setText("Kills: " + killCount);
    }

    /**
     * Met à jour l'affichage des munitions.
     */
    public void updateAmmoDisplay() {
        ammoText.setText("Ammo: " + player.getAmmo() + "/" + player.getMaxAmmo());
    }

    /**
     * Augmente progressivement la vitesse du jeu.
     */
    private void increaseGameSpeed() {
        gameSpeed += 0.02;
    }

    /**
     * Affiche un indicateur visuel pour la vie supplémentaire.
     */
    private void displayExtraLifeIndicator() {
        extraLifeIndicator = new Rectangle(10, 10, Color.PINK);
        root.getChildren().add(extraLifeIndicator);
        updateExtraLifeIndicatorPosition();
    }

    /**
     * Met à jour la position de l'indicateur de vie supplémentaire à côté du joueur.
     */
    private void updateExtraLifeIndicatorPosition() {
        if (extraLifeIndicator != null) {
            extraLifeIndicator.setTranslateX(player.getX() + player.getShape().getWidth() / 2 - 5);
            extraLifeIndicator.setTranslateY(player.getY() - 15);
        }
    }

    /**
     * Retourne la vitesse actuelle du jeu.
     * @return Vitesse du jeu
     */
    public double getGameSpeed() {
        return gameSpeed;
    }

    /**
     * Point d'entrée principal de l'application.
     * @param args Arguments de ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}
