package com.testgame.testgame.entities;

import javafx.scene.paint.Color; // Import pour la gestion des couleurs
import java.util.List; // Import pour l'utilisation des listes de projectiles

/**
 * Classe EnemyOrange représentant un ennemi orange dans le jeu.
 * Cet ennemi possède une capacité d'esquive des projectiles et se dirige vers le joueur
 * lorsqu'il n'a aucun projectile à éviter.
 */
public class EnemyOrange extends Enemy {

    private double evadeSpeed = 3; // Vitesse d'esquive, plus rapide que la vitesse de déplacement de base

    /**
     * Constructeur de la classe EnemyOrange.
     * Initialise l'ennemi à une position donnée et lui attribue une couleur orange.
     * @param x Coordonnée x initiale de l'ennemi
     * @param y Coordonnée y initiale de l'ennemi
     */
    public EnemyOrange(double x, double y) {
        super(x, y);
        this.shape.setFill(Color.ORANGE); // Définit la couleur de l'ennemi en orange
    }

    /**
     * Méthode de mise à jour de l'état de l'ennemi.
     * Si un projectile est détecté dans un rayon de 50 pixels, l'ennemi esquive le projectile.
     * Sinon, il se déplace en direction du joueur.
     * @param projectiles Liste des projectiles à esquiver
     * @param player Le joueur vers lequel l'ennemi se dirige
     * @param gameSpeed Vitesse du jeu qui influence la vitesse de l'ennemi
     */
    public void update(List<Projectile> projectiles, Player player, double gameSpeed) {
        boolean evaded = false; // Indicateur pour savoir si une esquive a été effectuée

        // Parcourt les projectiles pour vérifier s'il y en a un à esquiver
        for (Projectile projectile : projectiles) {
            double distanceX = projectile.getX() - x;
            double distanceY = projectile.getY() - y;
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            // Si un projectile est proche (rayon de 50 pixels), l'ennemi esquive
            if (distance < 50) {
                double evadeDirectionX = -distanceX / distance;
                double evadeDirectionY = -distanceY / distance;

                // Déplacement dans la direction opposée au projectile
                x += evadeDirectionX * evadeSpeed * gameSpeed;
                y += evadeDirectionY * evadeSpeed * gameSpeed;
                shape.setTranslateX(x);
                shape.setTranslateY(y);

                evaded = true; // L'ennemi a esquivé, on peut sortir de la boucle
                break; // Esquive seulement le premier projectile détecté
            }
        }

        // Si aucun projectile n'a été esquivé, se dirige vers le joueur
        if (!evaded) {
            moveTowardsPlayer(player, gameSpeed);
        }
    }

    /**
     * Déplace l'ennemi vers le joueur si aucun projectile n'est à esquiver.
     * @param player Le joueur cible
     * @param gameSpeed Facteur de vitesse du jeu qui influence la vitesse de l'ennemi
     */
    private void moveTowardsPlayer(Player player, double gameSpeed) {
        // Calcul des différences de position entre l'ennemi et le joueur
        double deltaX = player.getX() - x;
        double deltaY = player.getY() - y;
        double distance = Math.hypot(deltaX, deltaY);

        // Calcul de la direction unitaire et ajustement de la vitesse
        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        // Mise à jour de la position de l'ennemi
        x += directionX * baseSpeed * gameSpeed;
        y += directionY * baseSpeed * gameSpeed;

        // Mise à jour de la position graphique
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }
}
