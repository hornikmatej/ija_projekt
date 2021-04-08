package ija;

import ija.store.Shelf;

import java.util.ArrayList;
import java.util.List;

public class Request {

    private List<Shelf> shelves;
    private List<String> tovar;
    private List<Integer> pocet;

    public Request() {
        this.shelves = new ArrayList<>();
        this.tovar = new ArrayList<>();
        this.pocet = new ArrayList<>();
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public List<String> getTovar() {
        return tovar;
    }

    public List<Integer> getPocet() {
        return pocet;
    }

    public void prilozitTovar(Shelf polica, String nazov_tovaru, Integer pocet){
        this.shelves.add(polica);
        this.tovar.add(nazov_tovaru);
        this.pocet.add((pocet));
    }
}
