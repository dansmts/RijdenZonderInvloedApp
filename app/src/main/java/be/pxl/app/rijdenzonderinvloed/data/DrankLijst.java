package be.pxl.app.rijdenzonderinvloed.data;

import java.util.ArrayList;

public class DrankLijst {
    private ArrayList<Drank> drankLijst;

    public DrankLijst() {
        drankLijst = new ArrayList<>();
        drankLijst.add(new Drank(1, "Glas bier", 25, 5, 1, "ic_drink_pint_small"));
        drankLijst.add(new Drank(2, "Glas bier", 50, 5, 1, "ic_drink_pint_large"));
        drankLijst.add(new Drank(3, "Blik bier", 33, 5, 1, "ic_drink_beer_can"));
        drankLijst.add(new Drank(4, "Glas wijn", 10, 12, 1, "ic_drink_wine_glass"));
        drankLijst.add(new Drank(5, "Cocktail", 25, 5, 1, "ic_drink_cocktail"));
        drankLijst.add(new Drank(6, "Glas champagne", 25, 5, 1, "ic_drink_champagne"));
        drankLijst.add(new Drank(7, "Glas aperitief", 25, 5, 1, "ic_drink_sherry"));
        drankLijst.add(new Drank(8, "Glas sterke drank", 25, 5, 1, "ic_drink_strong_alcohol"));
    }

    public ArrayList<Drank> getDrankLijst() { return drankLijst; }
    public void setDrankLijst(ArrayList<Drank> drankLijst) { this.drankLijst = drankLijst; }
}
