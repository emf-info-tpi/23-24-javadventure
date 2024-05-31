# Documentation Utilisateur

## installation

Pour commencer déroulez l'onglet code puis cliquez sur "Download ZIP"

![image](https://github.com/riedo-emf/javadventure/assets/123940171/39e1d5e8-074a-4fc3-a1db-bd42072a54c5)

ensuite à l'emplacement ou vous avez téléchargé le fichier faites un click droit et extrayez les donnes du zip


![image](https://github.com/riedo-emf/javadventure/assets/123940171/946a0c7f-b009-4824-a002-6b4c531485c4)


Ensuite avec un editeur de votre choix ouvrez le projet, Dans cet exemple j'utilise netbeans ide (telechargement : [Apache NetBeans Releases](https://netbeans.apache.org/front/main/download/))

Pour ouvrir un fichier dans netbeans utilisez l'icone suivante : 

![image](https://github.com/riedo-emf/javadventure/assets/123940171/3c0ab611-982a-4fda-aec0-5995b9b881b6)

ou utilisez la combinaison de touches (Ctrl + Shift + O)

ensuite naviguez j'usquau projet et descendez dans l'arborescence de fichier j'usquau projet maven et cliquez sur open project.

![image](https://github.com/riedo-emf/javadventure/assets/123940171/be817de6-3f16-4ec0-85ce-b5d944fa7013)


une fois que ceci est fait vous aurez une arboressence qui resemble a ceci : 

![image](https://github.com/riedo-emf/javadventure/assets/123940171/15793626-2c6d-49f7-b6d4-e6448feab0db)


## Comment jouer

## Comment jouer

faites un click droit sur JavAdventure et selectionnez run

![image](https://github.com/riedo-emf/javadventure/assets/123940171/395e722b-012e-41ff-a1bc-6ce16ab8bfe1)

Maintenant que la fenêtre de jeu est ouverte vous pouvez deplacer votre personnage "C" avec les touches W,A,S,D. 

![image](https://github.com/riedo-emf/javadventure/assets/123940171/3d027b51-de2c-4feb-9676-57b773580131)


il y a aussi une ligne de commande pour interragir. la liste des commandes atuelle est la suivante : 

- Prendre + "nom de l'objet trouvé". ex: prendre epee

- Regarder + "nom de l'element a regarder". ex: regarder murs

- Regarder + inventaire

- Poser + "nom de l'objet a poser"

### combat

en premier ouvre cette classe java :

![image](https://github.com/riedo-emf/javadventure/assets/123940171/fa894831-1e3a-4a5e-8b34-f04a5604e444)

dans cette classe il y a deux méthodes. chaque methode correspond a un enemi dans une salle. par exemple la methode combatEnemiSalleSud correspond a l'enemi dans la salle sud.

les intruction pour coder chaque methode sont indiquées en javadoc.

si tu as correctement codé ces méthodes et que tu t'approches d'un monstre il mourra. par contre si tu n'as pas codé ces methodes il ne mourra pas.

S'il ne meurt pas il te suffit de corriger ta méthode puis sauver le fichier (ctrl+s) et dans le jeu rapproches-toi du même monstre. pas besoin de relancer.
