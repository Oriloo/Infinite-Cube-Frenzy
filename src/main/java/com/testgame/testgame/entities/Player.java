package com.testgame.testgame.entities;

import javafx.scene.paint.Color; // Import pour gérer la couleur du joueur

/**
 * Classe Player représentant le joueur dans le jeu.
 * Hérite de la classe Entity et dispose de fonctionnalités pour gérer la santé, les munitions,
 * le tir, le rechargement et les déplacements.
 */
public class Player extends Entity {
    private int health = 100; // Santé initiale du joueur
    private int ammo; // Munitions actuelles du joueur
    private int maxAmmo = 20; // Capacité maximale de munitions
    private double baseSpeed = 2.0; // Vitesse de base du joueur

    /**
     * Constructeur de la classe Player.
     * Initialise le joueur à une position donnée avec un chargeur plein et une couleur bleue.
     * @param x Coordonnée x initiale du joueur
     * @param y Coordonnée y initiale du joueur
     */
    public Player(double x, double y) {
        super(x, y, 30, 30, Color.BLUE); // Définit le joueur comme un carré bleu de 30x30 pixels
        this.ammo = maxAmmo; // Chargeur plein au début
    }

    /**
     * Méthode de mise à jour du joueur.
     * Cette méthode est actuellement vide mais pourrait être utilisée pour gérer
     * des comportements spécifiques à chaque mise à jour du jeu.
     */
    @Override
    public void update() {
        // Logique de mise à jour du joueur (vide pour l'instant)
    }

    /**
     * Permet au joueur de tirer un projectile, en réduisant les munitions de 1.
     * @return true si le joueur a tiré (munitions suffisantes), false sinon
     */
    public boolean shoot() {
        if (ammo > 0) {
            ammo--;
            return true;
        } else {
            System.out.println("Plus de munitions !");
            return false;
        }
    }

    /**
     * Recharge le chargeur du joueur au maximum.
     */
    public void reload() {
        this.ammo = maxAmmo;
    }

    /**
     * Getter pour obtenir le nombre actuel de munitions.
     * @return Le nombre actuel de munitions du joueur
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Getter pour obtenir le nombre maximal de munitions.
     * @return Le nombre maximal de munitions
     */
    public int getMaxAmmo() {
        return maxAmmo;
    }

    /**
     * Déplace le joueur dans la direction spécifiée, en tenant compte des limites de la scène.
     * La vitesse de déplacement est influencée par le facteur de vitesse du jeu.
     * @param dx Direction du mouvement en x (-1, 0 ou 1)
     * @param dy Direction du mouvement en y (-1, 0 ou 1)
     * @param sceneWidth Largeur de la scène pour limiter les mouvements
     * @param sceneHeight Hauteur de la scène pour limiter les mouvements
     * @param gameSpeed Facteur de vitesse du jeu, influençant la vitesse de déplacement
     */
    public void move(double dx, double dy, double sceneWidth, double sceneHeight, double gameSpeed) {
        double speed = baseSpeed * gameSpeed; // Calcule la vitesse avec le facteur de jeu

        double newX = x + dx * speed;
        double newY = y + dy * speed;

        // Vérifie les limites de l'écran pour s'assurer que le joueur reste visible
        if (newX >= 0 && newX <= sceneWidth - shape.getWidth()) {
            x = newX;
            shape.setTranslateX(x);
        }
        if (newY >= 0 && newY <= sceneHeight - shape.getHeight()) {
            y = newY;
            shape.setTranslateY(y);
        }
    }

    /**
     * Réduit la santé du joueur en fonction de la quantité de dégâts reçus.
     * @param amount Quantité de dégâts infligés au joueur
     */
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println("Player health: " + health);
    }

    /**
     * Vérifie si le joueur a besoin d'un pack de munitions (si les munitions sont faibles).
     * @return true si les munitions sont de 5 ou moins, false sinon
     */
    public boolean needsAmmoPack() {
        return ammo <= 5;
    }
}
