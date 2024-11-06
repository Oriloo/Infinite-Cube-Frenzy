package com.testgame.testgame.entities;

import javafx.scene.paint.Color; // Import pour gérer la couleur du projectile
import javafx.scene.shape.Circle; // Import pour créer une forme circulaire pour le projectile

/**
 * Classe Projectile représentant un projectile lancé dans le jeu.
 * Chaque projectile a une position, une direction, une vitesse et peut détecter des collisions avec des ennemis.
 */
public class Projectile {
    private Circle shape; // Forme circulaire du projectile
    private double x, y; // Position actuelle du projectile
    private double directionX, directionY; // Direction normalisée du projectile
    private double baseSpeed = 10; // Vitesse de base du projectile

    /**
     * Constructeur de la classe Projectile.
     * Initialise un projectile à une position de départ et le dirige vers une cible.
     * La vitesse est influencée par le facteur de vitesse du jeu.
     * @param startX Coordonnée x de départ
     * @param startY Coordonnée y de départ
     * @param targetX Coordonnée x de la cible
     * @param targetY Coordonnée y de la cible
     * @param gameSpeed Vitesse du jeu pour ajuster la vitesse du projectile
     */
    public Projectile(double startX, double startY, double targetX, double targetY, double gameSpeed) {
        this.shape = new Circle(5, Color.ORANGE); // Crée un cercle orange de rayon 5 pour représenter le projectile
        this.x = startX;
        this.y = startY;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);

        // Calcul de la direction normalisée
        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        this.directionX = deltaX / length;
        this.directionY = deltaY / length;

        // Applique la vitesse de jeu pour ajuster la vitesse du projectile
        this.baseSpeed *= gameSpeed;
    }

    /**
     * Met à jour la position du projectile en fonction de sa direction et de sa vitesse.
     */
    public void update() {
        x += directionX * baseSpeed;
        y += directionY * baseSpeed;
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    /**
     * Getter pour obtenir la forme du projectile.
     * @return La forme circulaire du projectile
     */
    public Circle getShape() {
        return shape;
    }

    /**
     * Vérifie si le projectile est en dehors des limites de la scène.
     * @param sceneWidth Largeur de la scène
     * @param sceneHeight Hauteur de la scène
     * @return true si le projectile est en dehors des limites, false sinon
     */
    public boolean isOutOfBounds(double sceneWidth, double sceneHeight) {
        return x < 0 || x > sceneWidth || y < 0 || y > sceneHeight;
    }

    /**
     * Vérifie si le projectile entre en collision avec un ennemi.
     * La collision est détectée si la distance entre le centre du projectile et celui de l'ennemi est inférieure à la somme de leurs rayons.
     * @param enemy L'ennemi avec lequel vérifier la collision
     * @return true si le projectile est en collision avec l'ennemi, false sinon
     */
    public boolean collidesWith(Enemy enemy) {
        double distanceX = x - (enemy.getX() + enemy.getShape().getWidth() / 2);
        double distanceY = y - (enemy.getY() + enemy.getShape().getHeight() / 2);
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Collision si la distance est inférieure à la somme des rayons
        return distance < (shape.getRadius() + enemy.getShape().getWidth() / 2);
    }

    /**
     * Getter pour obtenir la coordonnée x du projectile.
     * @return La coordonnée x du projectile
     */
    public double getX() {
        return x;
    }

    /**
     * Getter pour obtenir la coordonnée y du projectile.
     * @return La coordonnée y du projectile
     */
    public double getY() {
        return y;
    }
}
