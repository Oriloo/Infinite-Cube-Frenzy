package com.testgame.testgame;

import com.testgame.testgame.entities.Player;
import com.testgame.testgame.entities.Projectile;
import javafx.scene.input.MouseButton; // Import pour gérer les boutons de la souris
import javafx.scene.input.MouseEvent; // Import pour gérer les événements de souris
import javafx.scene.layout.Pane; // Import pour gérer la disposition des éléments

/**
 * Classe InputHandler pour gérer les interactions de la souris.
 * Cette classe permet de gérer les clics gauche et droit de la souris pour contrôler les actions du joueur,
 * telles que tirer et effectuer des attaques spéciales, tout en tenant compte de la vitesse du jeu.
 */
public class InputHandler {
    private final Player player; // Référence au joueur pour contrôler ses actions
    private final Pane root; // Référence à la racine de la scène pour ajouter des éléments visuels si nécessaire
    private final double gameSpeed; // Vitesse actuelle du jeu
    private final GameApp gameApp; // Référence à GameApp pour accéder aux méthodes de gestion des projectiles et de l'affichage des munitions

    /**
     * Constructeur de la classe InputHandler.
     * Initialise les contrôles en fonction du joueur, de la scène principale et de l'application de jeu.
     * @param player Référence au joueur
     * @param root Référence à la racine de la scène
     * @param gameApp Référence à l'application principale pour accéder aux méthodes de mise à jour et de gestion
     */
    public InputHandler(Player player, Pane root, GameApp gameApp) {
        this.player = player;
        this.root = root;
        this.gameApp = gameApp;
        this.gameSpeed = gameApp.getGameSpeed(); // Initialise la vitesse actuelle du jeu
    }

    /**
     * Gère les clics de la souris, en distinguant le clic gauche et le clic droit.
     * Le clic gauche effectue un tir simple, tandis que le clic droit déclenche une attaque spéciale.
     * @param event L'événement de clic de la souris
     */
    public void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {  // Si clic gauche
            handlePrimaryClick(event);
        } else if (event.getButton() == MouseButton.SECONDARY) {  // Si clic droit
            handleSecondaryClick(event);
        }
    }

    /**
     * Gère le clic gauche de la souris, créant un projectile qui se dirige vers la position cliquée.
     * Tire uniquement si le joueur a des munitions disponibles.
     * @param event L'événement de clic gauche de la souris
     */
    private void handlePrimaryClick(MouseEvent event) {
        if (player.shoot()) {  // Vérifie si le joueur a des munitions
            double clickX = event.getX();
            double clickY = event.getY();
            // Création d'un projectile qui se dirige vers la position cliquée
            Projectile projectile = new Projectile(player.getX(), player.getY(), clickX, clickY, gameSpeed);
            gameApp.addProjectile(projectile);  // Ajoute le projectile à la scène via GameApp
            gameApp.updateAmmoDisplay();        // Met à jour l'affichage des munitions
        }
    }

    /**
     * Gère le clic droit de la souris, déclenchant une attaque spéciale en éventail si le joueur a au moins 5 munitions.
     * L'attaque spéciale consiste en cinq projectiles lancés avec des angles de dispersion autour de la cible.
     * @param event L'événement de clic droit de la souris
     */
    private void handleSecondaryClick(MouseEvent event) {
        if (player.getAmmo() >= 5) {  // Vérifie si le joueur a assez de munitions pour une attaque spéciale
            // Déduit 5 munitions pour l'attaque spéciale
            for (int i = 0; i < 5; i++) {
                player.shoot();
            }
            gameApp.updateAmmoDisplay();  // Met à jour l'affichage des munitions

            // Calcul de l'angle de base vers la cible
            double baseAngle = Math.atan2(event.getY() - player.getY(), event.getX() - player.getX());

            // Angles de dispersion autour de l'angle de base pour créer un effet d'éventail
            double[] angles = {-20, -10, 0, 10, 20};

            // Création de cinq projectiles dans différentes directions
            for (double angleOffset : angles) {
                double angle = baseAngle + Math.toRadians(angleOffset);
                double targetX = player.getX() + Math.cos(angle) * 100;  // Point cible éloigné pour déterminer la direction
                double targetY = player.getY() + Math.sin(angle) * 100;

                Projectile projectile = new Projectile(player.getX(), player.getY(), targetX, targetY, gameSpeed);
                gameApp.addProjectile(projectile);  // Ajoute chaque projectile à la scène via GameApp
            }
        } else {
            System.out.println("Pas assez de munitions pour l'attaque spéciale !");
        }
    }
}
