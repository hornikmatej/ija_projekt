package ija;

import ija.store.Shelf;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda, ktorá sa zaoberá poziadavkami
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
public class Request {

    private List<Shelf> shelves;
    private List<String> tovar;
    private List<Integer> pocet;

    /**
     * Konstruktor pre triedu request (poziadavka)
     */
    public Request() {
        this.shelves = new ArrayList<>();
        this.tovar = new ArrayList<>();
        this.pocet = new ArrayList<>();
    }

    /**
     * Funkcia vrati zoznam regalov na ktorych je umiestneny tovar
     * @return List<Shelf> - vrati list regalov na ktorych je umiestneny tovar
     */
    public List<Shelf> getShelves() {
        return shelves;
    }

    /**
     * Funkcia vrati zoznam tovarov
     * @return List<String> - vrati zoznam tovarov
     */
    public List<String> getTovar() {
        return tovar;
    }

    /**
     * Funkcia vrati zoznam poctu kusov tovaru
     * @return List<Integer> - vrati zoznam poctu kusov tovaru
     */
    public List<Integer> getPocet() {
        return pocet;
    }

    /**
     * Funkcia prilozi tovar, umiestnenie tovaru, pocet kusov do poziadavky
     * @param polica regal poziadavky
     * @param nazov_tovaru nazov
     * @param pocet pocet kusov tovaru
     */
    public void prilozitTovar(Shelf polica, String nazov_tovaru, Integer pocet){
        this.shelves.add(polica);
        this.tovar.add(nazov_tovaru);
        this.pocet.add((pocet));
    }
}
