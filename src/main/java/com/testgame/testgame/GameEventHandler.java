package com.testgame.testgame;

import com.testgame.testgame.entities.Player;
import javafx.event.EventHandler; // Import pour gérer les événements
import javafx.scene.input.KeyCode; // Import pour les codes des touches
import javafx.scene.input.KeyEvent; // Import pour gérer les événements de touche

import java.util.HashSet;
import java.util.Set; // Import pour utiliser des ensembles pour le suivi des touches actives

/**
 * Classe GameEventHandler pour gérer les événements de touches du clavier.
 * Cette classe détecte les touches pressées et relâchées et met à jour la position du joueur
 * en fonction des touches actives et de la vitesse du jeu.
 */
public class GameEventHandler {
    private Player player; // Référence au joueur pour mettre à jour sa position
    private Set<KeyCode> activeKeys; // Ensemble des touches actuellement enfoncées
    private double sceneWidth; // Largeur de la scène pour limiter les déplacements du joueur
    private double sceneHeight; // Hauteur de la scène pour limiter les déplacements du joueur
    private double gameSpeed; // Vitesse du jeu, influence la vitesse de déplacement du joueur

    /**
     * Constructeur de la classe GameEventHandler.
     * Initialise le gestionnaire d'événements pour contrôler le joueur et limite ses mouvements aux dimensions de la scène.
     * @param player Référence au joueur
     * @param sceneWidth Largeur de la scène
     * @param sceneHeight Hauteur de la scène
     * @param gameSpeed Vitesse initiale du jeu
     */
    public GameEventHandler(Player player, double sceneWidth, double sceneHeight, double gameSpeed) {
        this.player = player;
        this.activeKeys = new HashSet<>(); // Initialise l'ensemble des touches actives
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.gameSpeed = gameSpeed;
    }

    /**
     * Retourne un EventHandler pour détecter les touches pressées.
     * @return EventHandler qui ajoute une touche à l'ensemble des touches actives lors de la pression
     */
    public EventHandler<KeyEvent> getKeyPressedHandler() {
        return event -> activeKeys.add(event.getCode());
    }

    /**
     * Retourne un EventHandler pour détecter les touches relâchées.
     * @return EventHandler qui retire une touche de l'ensemble des touches actives lors du relâchement
     */
    public EventHandler<KeyEvent> getKeyReleasedHandler() {
        return event -> activeKeys.remove(event.getCode());
    }

    /**
     * Met à jour l'état du joueur en fonction des touches actives.
     * La vitesse de déplacement est influencée par la vitesse de jeu actuelle.
     * @param gameSpeed Nouvelle vitesse de jeu, influençant la vitesse de déplacement
     */
    public void update(double gameSpeed) {
        // Met à jour la vitesse du jeu pour ajuster la vitesse de déplacement
        this.gameSpeed = gameSpeed;

        // Déplace le joueur vers le haut si la touche Z est enfoncée
        if (activeKeys.contains(KeyCode.Z)) {
            player.move(0, -1, sceneWidth, sceneHeight, gameSpeed);
        }
        // Déplace le joueur vers le bas si la touche S est enfoncée
        if (activeKeys.contains(KeyCode.S)) {
            player.move(0, 1, sceneWidth, sceneHeight, gameSpeed);
        }
        // Déplace le joueur vers la gauche si la touche Q est enfoncée
        if (activeKeys.contains(KeyCode.Q)) {
            player.move(-1, 0, sceneWidth, sceneHeight, gameSpeed);
        }
        // Déplace le joueur vers la droite si la touche D est enfoncée
        if (activeKeys.contains(KeyCode.D)) {
            player.move(1, 0, sceneWidth, sceneHeight, gameSpeed);
        }
    }
}
