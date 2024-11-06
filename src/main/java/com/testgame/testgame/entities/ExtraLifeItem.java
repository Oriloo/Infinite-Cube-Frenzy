package com.testgame.testgame.entities;

import javafx.scene.paint.Color; // Import pour gérer les couleurs
import javafx.scene.shape.Circle; // Import pour créer des formes circulaires

/**
 * Classe ExtraLifeItem représentant un objet qui ajoute une vie supplémentaire dans le jeu.
 * Cet objet est représenté par un cercle de couleur rose et possède une position définie.
 */
public class ExtraLifeItem {
    private Circle shape; // Forme circulaire représentant l'objet de vie supplémentaire
    private double x, y;  // Position de l'objet

    /**
     * Constructeur de la classe ExtraLifeItem.
     * Initialise la position et la représentation graphique de l'objet de vie supplémentaire.
     * @param x Coordonnée x initiale de l'objet
     * @param y Coordonnée y initiale de l'objet
     */
    public ExtraLifeItem(double x, double y) {
        this.x = x;
        this.y = y;
        shape = new Circle(10, Color.PINK);  // Crée un cercle rose avec un rayon de 10 pixels
        shape.setTranslateX(x); // Positionne le cercle en x
        shape.setTranslateY(y); // Positionne le cercle en y
    }

    /**
     * Getter pour obtenir la forme de l'objet de vie supplémentaire.
     * @return La forme circulaire de l'objet
     */
    public Circle getShape() {
        return shape;
    }

    /**
     * Getter pour obtenir la coordonnée x de l'objet.
     * @return La coordonnée x de l'objet
     */
    public double getX() {
        return x;
    }

    /**
     * Getter pour obtenir la coordonnée y de l'objet.
     * @return La coordonnée y de l'objet
     */
    public double getY() {
        return y;
    }
}
