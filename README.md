# An Efficient Fixed Set Search for the Covering Location with Interconnected Facilities Problem

This paper studies the Coverage Location Problem with Interconnected Facilities (CPIF). It belongs to the family of Facility Location Problems, but being more realistic to nowadays situations as surveillance, or natural disaster control. This problem aims at locating a set of interconnected facilities to minimize the number of demand points that are not covered by the selected facilities. Two facilities are considered as interconnected if the distance between them is smaller than or equal to a predefined distance, while a facility covers a demand point if the distance to it is smaller than a certain threshold. The wide variety of real-world applications that fit into this model makes them attractive for designing an algorithm able to solve the problem efficiently. To this end, a metaheuristic algorithm based on the Fixed Set Search framework is implemented. The proposed algorithm will be able to provide high-quality solutions in short computational times, being competitive with the state-of-the-art.

Paper link: [DOI](https://doi.org/10.1007/978-3-031-26504-4_37) <br>
Conference: [14th Metaheuristics International Conference, 11-14 July 2022, Ortigia-Syracuse, Italy.](https://www.ants-lab.it/mic2022/)

## Datasets

* [555 pmed instances](./instances)


* All txt format instances can be found in instances folder.
* **solution.xlsx** is an excel with the results.
* **results.xlsx**  is an excel with all instances and the facilities selected.
* **code** folder contains the source code.

## Executable

You can just run the CPIF-MIC.jar as follows.

```
java -jar CPIF-MIC.jar
```

If you want new instances just replace folder instances.


## Cite

Please cite our paper if you use it in your own work:

Bibtext
```
@InProceedings{10.1007/978-3-031-26504-4_37,
author="Lozano-Osorio, Isaac
and S{\'a}nchez-Oro, Jes{\'u}s
and Mart{\'i}nez-Gavara, Anna
and L{\'o}pez-S{\'a}nchez, Ana D.
and Duarte, Abraham",
editor="Di Gaspero, Luca
and Festa, Paola
and Nakib, Amir
and Pavone, Mario",
title="An Efficient Fixed Set Search for the Covering Location with Interconnected Facilities Problem",
booktitle="Metaheuristics",
year="2023",
publisher="Springer International Publishing",
address="Cham",
pages="485--490",
isbn="978-3-031-26504-4"
}
```

MDPI and ACS Style
```
Lozano-Osorio, I.; Sánchez-Oro, J.; Martínez-Gavara, A.; López-Sánchez, A. D.; Duarte, A. An Efficient Fixed Set Search for the Covering Location with Interconnected Facilities Problem. Metaheuristics 2023, 485–490. https://doi.org/10.1007/978-3-031-26504-4_37.
```

AMA Style
```
Lozano-Osorio I, Sánchez-Oro J, Martínez-Gavara A, López-Sánchez AD, Duarte A. An Efficient Fixed Set Search for the Covering Location with Interconnected Facilities Problem. In: Di Gaspero L, Festa P, Nakib A, Pavone M, eds. Metaheuristics. Springer International Publishing; 2023:485-490. https://doi.org/10.1007/978-3-031-26504-4_37.
```

Chicago/Turabian Style
```
Lozano-Osorio, Isaac, Jesús Sánchez-Oro, Anna Martínez-Gavara, Ana D. López-Sánchez, and Abraham Duarte. ‘An Efficient Fixed Set Search for the Covering Location with Interconnected Facilities Problem’. In Metaheuristics, edited by Luca Di Gaspero, Paola Festa, Amir Nakib, and Mario Pavone, 485–90. Cham: Springer International Publishing, 2023. https://doi.org/10.1007/978-3-031-26504-4_37.
```
