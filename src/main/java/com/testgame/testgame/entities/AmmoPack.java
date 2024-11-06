package com.testgame.testgame.entities;

import javafx.scene.paint.Color; // Import de la classe Color pour gérer les couleurs
import javafx.scene.shape.Rectangle; // Import de la classe Rectangle pour représenter graphiquement le pack de munitions

/**
 * Classe représentant un pack de munitions dans le jeu.
 * Ce pack de munitions est représenté par un rectangle de couleur jaune
 * à une position donnée dans l'espace du jeu.
 */
public class AmmoPack {
    // Attributs privés pour la forme graphique (rectangle) et les coordonnées x et y du pack de munitions
    private Rectangle shape;
    private double x, y;

    /**
     * Constructeur de la classe AmmoPack.
     * Initialise le rectangle qui représente le pack de munitions
     * et définit sa position dans l'espace du jeu.
     * @param x Coordonnée x de la position du pack de munitions
     * @param y Coordonnée y de la position du pack de munitions
     */
    public AmmoPack(double x, double y) {
        // Création d'un rectangle de 20x20 de couleur jaune pour symboliser le pack de munitions
        this.shape = new Rectangle(20, 20, Color.YELLOW);
        this.x = x;
        this.y = y;
        // Positionnement du rectangle aux coordonnées spécifiées
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    /**
     * Getter pour la forme du pack de munitions (Rectangle).
     * @return Le rectangle représentant graphiquement le pack de munitions
     */
    public Rectangle getShape() {
        return shape;
    }

    /**
     * Getter pour la coordonnée x du pack de munitions.
     * @return La coordonnée x du pack de munitions
     */
    public double getX() {
        return x;
    }

    /**
     * Getter pour la coordonnée y du pack de munitions.
     * @return La coordonnée y du pack de munitions
     */
    public double getY() {
        return y;
    }
}
