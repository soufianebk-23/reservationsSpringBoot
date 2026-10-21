# Projet Réservations – Spring Boot 3

Application web de réservation de spectacles réalisée dans le cadre de l'UE
**Projet d'intégration de développement** (ICC Bruxelles).

Cette version correspond au **Starter Kit** (chapitres 1 à 4 du roadmap).

## Fonctionnalités

- Gestion des artistes : liste, fiche, création, modification, suppression
- Messages de notification (succès / erreur)
- Authentification avec Spring Security (mots de passe chiffrés avec BCrypt)
- Connexion et déconnexion
- Mot de passe oublié avec envoi d'un email de réinitialisation
- Inscription avec validation du mot de passe
- Page de profil : consultation, modification, suppression du compte

## Technologies

- Java 21
- Spring Boot 3.5.6 (Web, Data JPA, Security, Validation, Mail)
- Thymeleaf et Thymeleaf Layout Dialect
- MariaDB 10.4 et Flyway
- Maven

## Installation

1. Créer une base de données MariaDB/MySQL nommée `reservations` (utf8mb4).
2. Copier `.env.example` en `.env` et compléter les valeurs :
```
   DB_USERNAME=root
   DB_PASSWORD=
   MAIL_USERNAME=...
   MAIL_PASSWORD=...
```
3. Lancer l'application :
```
   mvn spring-boot:run
```
4. Ouvrir http://localhost:8080

Les tables et les données de test sont créées automatiquement par Flyway au démarrage.

## Comptes de test

| Login  | Mot de passe | Rôle   |
|--------|--------------|--------|
| `bob`  | `12345678`   | ADMIN  |
| `anna` | `12345678`   | MEMBER |

## Auteur

Soufiane Benktib – Bachelier en Informatique, ICC Bruxelles