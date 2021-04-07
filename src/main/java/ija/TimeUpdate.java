package ija;

import java.time.LocalTime;


/**
 * Rozhranie volane vzdy po tiku hodin
 *
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
public interface TimeUpdate {

    /**
     * Funkcia volana hlavnym ovladacom zxa ucelom aktualizovania polohy objektov na mape
     * @param time sucasny cas
     */
    void update(LocalTime time);
}
