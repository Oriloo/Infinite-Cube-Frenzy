package com.testgame.testgame.entities;

import javafx.scene.paint.Color; // Import pour gérer la couleur de l'ennemi


/**
 * Classe Enemy représentant un ennemi dans le jeu.
 * Hérite de la classe Entity et dispose d'une vitesse de base et de méthodes pour se déplacer vers une cible et détecter les collisions.
 */
public class Enemy extends Entity {
    protected double baseSpeed = 2; // Vitesse de base de l'ennemi

    /**
     * Constructeur de la classe Enemy.
     * Initialise un ennemi sous forme d'un carré rouge de 30x30 pixels à une position donnée.
     * @param x Coordonnée x initiale de l'ennemi
     * @param y Coordonnée y initiale de l'ennemi
     */
    public Enemy(double x, double y) {
        super(x, y, 30, 30, Color.RED); // L'ennemi est un carré rouge de taille 30x30
    }

    /**
     * Méthode de mise à jour de l'ennemi.
     * Actuellement, cette méthode est vide mais pourrait être utilisée pour définir des comportements spécifiques à chaque mise à jour du jeu.
     */
    @Override
    public void update() {
        // Logique de mise à jour de l'ennemi (aucune action pour l'instant)
    }

    /**
     * Déplace l'ennemi vers une position cible, en fonction de la vitesse de jeu.
     * @param targetX Coordonnée x de la cible
     * @param targetY Coordonnée y de la cible
     * @param gameSpeed Facteur de vitesse du jeu qui influence la vitesse de déplacement de l'ennemi
     */
    public void moveTowards(double targetX, double targetY, double gameSpeed) {
        // Calcul des différences de position entre l'ennemi et la cible
        double deltaX = targetX - x;
        double deltaY = targetY - y;
        double distance = Math.hypot(deltaX, deltaY); // Calcul de la distance jusqu'à la cible

        // Calcul de la direction unitaire et ajustement de la vitesse avec gameSpeed
        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        // Mise à jour de la position de l'ennemi en fonction de la direction et de la vitesse
        x += directionX * baseSpeed * gameSpeed;
        y += directionY * baseSpeed * gameSpeed;

        // Mise à jour de la position graphique
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    /**
     * Vérifie si l'ennemi entre en collision avec le joueur.
     * @param player L'instance du joueur à vérifier pour la collision
     * @return true si l'ennemi est en collision avec le joueur, sinon false
     */
    public boolean collidesWithPlayer(Player player) {
        // Calcul des distances entre l'ennemi et le joueur
        double distanceX = x - (player.getX() + player.getShape().getWidth() / 2);
        double distanceY = y - (player.getY() + player.getShape().getHeight() / 2);
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Retourne true si la distance est inférieure à la somme des rayons (collision)
        return distance < (shape.getWidth() / 2 + player.getShape().getWidth() / 2);
    }

    /**
     * Getter pour la coordonnée x de l'ennemi.
     * @return La coordonnée x de l'ennemi
     */
    public double getX() {
        return x;
    }

    /**
     * Getter pour la coordonnée y de l'ennemi.
     * @return La coordonnée y de l'ennemi
     */
    public double getY() {
        return y;
    }
}
