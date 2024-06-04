# Documentation Utilisateur

## **Installation**

Pour commencer, déroulez l'onglet "Code" puis cliquez sur "Download ZIP".

![image](https://github.com/riedo-emf/javadventure/assets/123940171/39e1d5e8-074a-4fc3-a1db-bd42072a54c5)

Ensuite, à l'emplacement où vous avez téléchargé le fichier, faites un clic droit et extrayez les données du zip.

![image](https://github.com/riedo-emf/javadventure/assets/123940171/946a0c7f-b009-4824-a002-6b4c531485c4)

Ensuite, avec un éditeur de votre choix, ouvrez le projet. Dans cet exemple, j'utilise NetBeans IDE (téléchargement : [Apache NetBeans Releases](https://netbeans.apache.org/front/main/download/)).

Pour ouvrir un fichier dans NetBeans, utilisez l'icône suivante :

![image](https://github.com/riedo-emf/javadventure/assets/123940171/3c0ab611-982a-4fda-aec0-5995b9b881b6)

ou utilisez la combinaison de touches (Ctrl + Shift + O).

Ensuite, naviguez jusqu'au projet et descendez dans l'arborescence de fichiers jusqu'au projet Maven et cliquez sur "Open Project".

![image](https://github.com/riedo-emf/javadventure/assets/123940171/be817de6-3f16-4ec0-85ce-b5d944fa7013)

Une fois que cela est fait, vous aurez une arborescence qui ressemble à ceci :

![image](https://github.com/riedo-emf/javadventure/assets/123940171/15793626-2c6d-49f7-b6d4-e6448feab0db)

## **Comment jouer**

Faites un clic droit sur JavAdventure et sélectionnez "Run".

![image](https://github.com/riedo-emf/javadventure/assets/123940171/395e722b-012e-41ff-a1bc-6ce16ab8bfe1)

Maintenant que la fenêtre de jeu est ouverte, vous pouvez déplacer votre personnage "C" avec les touches W, A, S, D.

![image](https://github.com/riedo-emf/javadventure/assets/123940171/3d027b51-de2c-4feb-9676-57b773580131)

Il y a aussi une ligne de commande pour interagir. La liste des commandes actuelles est la suivante :

- Prendre + "nom de l'objet trouvé". ex: prendre épée
- Regarder + "nom de l'élément à regarder". ex: regarder murs
- Regarder + inventaire
- Poser + "nom de l'objet à poser"

### **Combat**

Premièrement, ouvrez cette classe Java :

![image](https://github.com/riedo-emf/javadventure/assets/123940171/fa894831-1e3a-4a5e-8b34-f04a5604e444)

Dans cette classe, il y a deux méthodes. Chaque méthode correspond à un ennemi dans une salle. Par exemple, la méthode combatEnnemiSalleSud correspond à l'ennemi dans la salle sud.

Les instructions pour coder chaque méthode sont indiquées en Javadoc.

Si vous avez correctement codé ces méthodes et que vous vous approchez d'un monstre, il mourra. Par contre, si vous n'avez pas codé ces méthodes, il ne mourra pas.

S'il ne meurt pas, il vous suffit de corriger votre méthode, puis de sauvegarder le fichier (Ctrl + S) et dans le jeu, rapprochez-vous du même monstre. Pas besoin de relancer le jeu.
