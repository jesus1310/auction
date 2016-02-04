import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        if(getLot(lotNumber) != null) {
            boolean successful = getLot(lotNumber).bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    getLot(lotNumber).getHighestBid().getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            Lot selectedLot = lots.get(lotNumber - 1);
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                    selectedLot.getNumber() +
                    " was returned instead of " +
                    lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }

    /**
     * Método que muestra por pantalla los detalles de los items que están siendo subastados
     * Si hay alguno que no ha recibido pujas se indicará con un mensaje de error
     */
    public void close(){
        int indice = 0;
        while (indice < lots.size()){
            if (lots.get(indice).getHighestBid() != null){
                System.out.println("Nombre de usuario--> " + lots.get(indice).getHighestBid().getBidder().getName() +
                    "\nPuja--> " + lots.get(indice).getHighestBid().getValue() + 
                    "\nDescripción--> " + lots.get(indice).getDescription() + "\n");
            }
            else {
                System.out.println("El objeto " + lots.get(indice).getDescription() + " no ha recibido ninguna puja todavía \n");
            }
            indice = indice + 1;
        }
    }

    /**
     * Método que devuelve una colección con todos los objetos que no han sido subastados
     */
    public ArrayList getUnsold(){
        ArrayList<Lot> unsold = new ArrayList<>();
        int indice = 0;
        while (indice < lots.size()){
            if (lots.get(indice).getHighestBid() == null){
                unsold.add(lots.get(indice));
            }
            indice = indice + 1;
        }
        return unsold;
    }
}
