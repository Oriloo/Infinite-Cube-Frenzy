package com.testgame.testgame.entities;

import javafx.scene.shape.Rectangle; // Import pour utiliser des formes rectangulaires
import javafx.scene.paint.Color; // Import pour gérer les couleurs

/**
 * Classe abstraite Entity qui représente une entité générique dans le jeu.
 * Elle fournit des attributs et des méthodes de base pour toutes les entités visibles,
 * comme leur forme, position et couleur.
 */
public abstract class Entity {
    protected Rectangle shape; // Forme de l'entité
    protected double x, y; // Position de l'entité

    /**
     * Constructeur de la classe Entity.
     * Initialise une entité avec une position, une taille et une couleur données.
     * @param x Coordonnée x initiale de l'entité
     * @param y Coordonnée y initiale de l'entité
     * @param width Largeur de l'entité
     * @param height Hauteur de l'entité
     * @param color Couleur de l'entité
     */
    public Entity(double x, double y, double width, double height, Color color) {
        this.shape = new Rectangle(width, height); // Crée un rectangle pour représenter visuellement l'entité
        this.shape.setFill(color); // Définit la couleur de l'entité
        this.x = x;
        this.y = y;
        shape.setTranslateX(x); // Positionne l'entité en x
        shape.setTranslateY(y); // Positionne l'entité en y
    }

    /**
     * Méthode abstraite update.
     * Cette méthode doit être implémentée par les sous-classes pour mettre à jour
     * l'état de l'entité, selon les besoins spécifiques.
     */
    public abstract void update();

    /**
     * Getter pour obtenir la forme de l'entité.
     * @return La forme Rectangle de l'entité
     */
    public Rectangle getShape() {
        return shape;
    }

    /**
     * Getter pour obtenir la coordonnée x de l'entité.
     * @return La coordonnée x de l'entité
     */
    public double getX() {
        return x;
    }

    /**
     * Getter pour obtenir la coordonnée y de l'entité.
     * @return La coordonnée y de l'entité
     */
    public double getY() {
        return y;
    }
}
