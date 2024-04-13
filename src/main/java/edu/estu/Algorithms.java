package edu.estu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Algorithms {

    public void initializeAlgorithms(int initial) {
        arbitraryInsertion(initial);
        cheapestInsertion(initial);
        farthestInsertion(initial);
        nearestInsertion(initial);
        nearestNeighbor(initial);
    }

    private void arbitraryInsertion(int initial) {
        LinkedList<Integer> visited = new LinkedList<>();
        visited.add(initial);
        visited.add(initial);
        List<Integer> list = IntStream.range(0, 81).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        LinkedList<Integer> indexes = new LinkedList<>();
        IntStream.range(0, 81).forEach(indexes::add);
        Collections.shuffle(indexes);
        IntStream.range(0, TurkishNetwork.distances.length).forEach(i -> {
            LinkedList<Integer> elementList = new LinkedList<>();
            elementList.add(indexes.get(i));
            add(visited, elementList);
        });
        int cost = getCost(visited);
        printAlgorithms("ArbitraryInsertion", visited, cost);
    }

    private void cheapestInsertion(int initial) {
        LinkedList<Integer> visited = new LinkedList<>();
        visited.add(initial);
        visited.add(initial);
        LinkedList<Integer> indexes = new LinkedList<>();
        IntStream.range(0, 81).forEach(indexes::add);
        for (int i = 0; i < TurkishNetwork.distances.length; i++) {
            add(visited, indexes);
        }
        int cost = getCost(visited);
        printAlgorithms("CheapestInsertion", visited, cost);
    }

    private void farthestInsertion(int initial) {
        LinkedList<Integer> visited = new LinkedList<>();
        visited.add(initial);
        visited.add(initial);
        int i = 1;
        while (true) {
            if (i >= TurkishNetwork.distances.length) break;
            LinkedList<Integer> maximumDistances = new LinkedList<>();
            int maximumDistance = Integer.MIN_VALUE;
            for (int j = visited.size() - 1; j >= 0; j--) {
                int[] distances = TurkishNetwork.distances[visited.get(j)];
                for (int k = 0; k < TurkishNetwork.distances.length; k++)
                    if (!visited.contains(k) && distances[k] >= maximumDistance) {
                        if (distances[k] > maximumDistance) {
                            maximumDistance = distances[k];
                            maximumDistances.clear();
                        }
                        maximumDistances.add(k);
                    }
            }
            add(visited, maximumDistances);
            i++;
        }
        int cost = getCost(visited);
        printAlgorithms("FarthestInsertion", visited, cost);
    }
    private void nearestInsertion(int initial) {
        LinkedList<Integer> visited = new LinkedList<>();
        visited.add(initial);
        visited.add(initial);

        int i = 1;
        while (true) {
            if (i >= TurkishNetwork.distances.length) break;
            LinkedList<Integer> minimumDistances = new LinkedList<>();
            int minimumDistance = Integer.MAX_VALUE;
            int j = 0;
            while (j < visited.size() - 1) {
                int[] distances = TurkishNetwork.distances[visited.get(j)];
                int k = 0;
                if (!visited.contains(k) && distances[k] <= minimumDistance) {
                    if (distances[k] < minimumDistance) {
                        minimumDistance = distances[k];
                        minimumDistances.clear();
                    }
                    minimumDistances.add(k);
                }
                k++;
                while (k < TurkishNetwork.distances.length) {
                    if (!visited.contains(k) && distances[k] <= minimumDistance) {
                        if (distances[k] < minimumDistance) {
                            minimumDistance = distances[k];
                            minimumDistances.clear();
                        }
                        minimumDistances.add(k);
                    }
                    k++;
                }
                j++;
            }
            add(visited, minimumDistances);
            i++;
        }
        int cost = getCost(visited);
        printAlgorithms("NearestInsertion", visited, cost);
    }

    private void nearestNeighbor(int initial) {
        LinkedList<Integer> visited = new LinkedList<>();
        visited.add(initial);
        int nextNode = initial;
        for (int i = 1; i < TurkishNetwork.distances.length; i++) {
            int[] distances = TurkishNetwork.distances[nextNode];
            int minimumDistance = Integer.MAX_VALUE;
            int indexOfMinimumDistance = -1;
            for (int j = 0; j < TurkishNetwork.distances.length; j++)
                if (!visited.contains(j) && distances[j] != 0 && distances[j] < minimumDistance) {
                    minimumDistance = distances[j];
                    indexOfMinimumDistance = j;
                }
            if (indexOfMinimumDistance != -1) {
                visited.add(indexOfMinimumDistance);
            }
            nextNode = indexOfMinimumDistance;
        }
        visited.add(initial);
        int cost = getCost(visited);
        printAlgorithms("NearestNeighbor", visited, cost);
    }

    private void add(LinkedList<Integer> visited, LinkedList<Integer> minimumDistances) {
        int minimumCost = Integer.MAX_VALUE;
        int addIndex = -1;
        int indexOfAddedElement = -1;
        for (Integer i : minimumDistances)
            for (int j = 0; j < visited.size() - 1; j++) {
                if (visited.contains(i)) continue;
                int currentCost =
                        TurkishNetwork.distances[i][visited.get(j)] +
                                TurkishNetwork.distances[i][visited.get(j + 1)] -
                                TurkishNetwork.distances[visited.get(j)][visited.get(j + 1)];
                if (currentCost < minimumCost) {
                    minimumCost = currentCost;
                    addIndex = j;
                    indexOfAddedElement = i;
                }
            }
        if (indexOfAddedElement != -1)
            visited.add(addIndex + 1, indexOfAddedElement);
    }

    private int getCost(LinkedList<Integer> visited) {
        int cost = 0;
        int i = 0;
        if (i < visited.size() - 1) {
            cost += TurkishNetwork.distances[visited.get(i)][visited.get(i + 1)];
            i++;
            while (i < visited.size() - 1) {
                cost += TurkishNetwork.distances[visited.get(i)][visited.get(i + 1)];
                i++;
            }
        }
        return cost;
    }

    private void printAlgorithms(String algorithmName, LinkedList<Integer> visited, int cost) {
        System.out.print(algorithmName + " " + cost + " [");
        int i = 0;
        if (i < visited.size() - 1) {
            System.out.print(visited.get(i) + ", ");
            i++;
            while (i < visited.size() - 1) {
                System.out.print(visited.get(i) + ", ");
                i++;
            }
        }
        System.out.print(visited.getLast() + "]");
        System.out.println();
    }
}
