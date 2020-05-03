Vous trouverez avec ce fichier :
- le dossier du projet JMonkey Engine
- un zip avec un executable Windows 64bits
- un zip avec un executable Linux 64bits
- un executable java que vous pouvez retrouver dans TheDuckPond/dist
- un dossier avec quelques screenshots
Si vous rencontrez un problème vous pouvez me contacter :
leo.auvray@supinfo.com

Tous les modèles 3D, sons et textures on été empruntés 
sur des bibliothèques libres.

Mes classes :

- Duck pour la gestion des canards et canards boss
- Lilypad pour la gestion des nénuphars
- Sound pour la gestion du son
- Controls pour la gestion des inputs


Fonctionnalités absentes et causes :

- Rocher dans l'eau (oubli)

- Canards qui suivent les "head duck" (essais non concluants)

- Canards qui meurent et naissent (choix pour des raisons 
d'équilibrage pour qu'il n'y ai ni trop ni trop peu de canards 
mais possible de l'implémenter facilement)


Description du jeu :

Simulation d'un étang avec des canards qui mangent des nénuphars.
Un total de 12 canards sont présents et 30 nénuphars au début de 
la simulation. Lorsqu'un canard mange un nénuphar il gagne 1 à 
sa variable de nourriture, il faut que celle-ci soit égale à 5 
pour que le canard devienne un boss.
Tous les 2000 ticks, entre 5 et 25 nénuphars apparaissent et 
les canards perde 1 à leur variable de nourriture, 
si celle-ci passe en dessous de 5, le boss redevient un 
canard normal. 
Les collisions entres les canards sont gérées, ceux-ci 
ne peuvent se passer au travers et il ne peuvent pas quitter 
l'étang.


Manuel d'utilisation :

Déplacez et rotationnez la caméra libre avec Z,Q,S,D et montez 
et décendez-la avec A et W.
Echap pour quitter.



