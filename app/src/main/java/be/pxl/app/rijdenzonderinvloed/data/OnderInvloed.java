package be.pxl.app.rijdenzonderinvloed.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.preference.PreferenceManager;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.pxl.app.rijdenzonderinvloed.R;

import static java.time.temporal.ChronoUnit.MINUTES;

public class OnderInvloed implements Parcelable {
    private String id;
    private LocalDate datumDrinkAvond;
    private LocalTime tijdEersteConsumptie;
    private ArrayList<Consumptie> lijstConsumpties;
    private double maxPromille;
    private String geslacht;
    private int gewicht;

    public OnderInvloed() {
        this.datumDrinkAvond = LocalDate.now();
        this.lijstConsumpties = new ArrayList<>();
    }

    public OnderInvloed(Context context) {
        this.datumDrinkAvond = LocalDate.now();
        this.lijstConsumpties = new ArrayList<>();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        this.gewicht = Integer.parseInt(sp.getString(context.getString(R.string.pref_gewicht_key),
                context.getString(R.string.pref_gewicht_default)));
        this.geslacht = sp.getString(context.getString(R.string.pref_geslacht_key),
                context.getString(R.string.pref_geslacht_default_value));
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDatumDrinkAvond() { return datumDrinkAvond; }
    public void setDatumDrinkAvond(LocalDate datumDrinkAvond) { this.datumDrinkAvond = datumDrinkAvond; }

    public LocalTime getTijdEersteConsumptie() { return tijdEersteConsumptie; }
    public void setTijdEersteConsumptie(LocalTime tijdEersteConsumptie) { this.tijdEersteConsumptie = tijdEersteConsumptie; }

    public ArrayList<Consumptie> getLijstConsumpties() { return lijstConsumpties; }
    public void setLijstConsumpties(ArrayList<Consumptie> lijstConsumpties) { this.lijstConsumpties = lijstConsumpties; }

    public double getMaxPromille() { return maxPromille; }
    public void setMaxPromille(double maxPromille) { this.maxPromille = maxPromille; }

    public String getGeslacht() { return geslacht; }
    public void setGeslacht(String geslacht) { this.geslacht = geslacht; }

    public int getGewicht() { return gewicht; }
    public void setGewicht(int gewicht) { this.gewicht = gewicht; }

    public void voegConsumptieToe(Consumptie consumptie) {
        if (this.tijdEersteConsumptie == null) {
            this.tijdEersteConsumptie = consumptie.getTijdConsumptie();
        }
        this.lijstConsumpties.add(0, consumptie);
    }

    public int getAantalConsumpties() {
        return this.lijstConsumpties.size();
    }

    public double berekenBloedAlcoholGehalte() {
        // Formule BAG
        // BAG = (a x 10)/(g x r) - (u - 0,5) x (g x 0,002)
        // Waarbij:
        // a = aantal glazen
        // g = lichaamsgewicht (kg)
        // r = bij mannen 0,7 en bij vrouwen 0,5
        // u = het aantal uren vanaf het eerste glas
        if (getAantalConsumpties() != 0) {
            float vochtPerKilo = (float) (geslacht.equals("man") ? 0.7 : 0.5);

            long minutenTussenEersteEnLaatsteConsumptie = MINUTES.between(this.getTijdEersteConsumptie(), LocalTime.now());
            double urenVanafEersteGlas = Math.ceil(minutenTussenEersteEnLaatsteConsumptie / 60);
            double BAG = (getAantalStandaardGlazen() * 10) / (this.gewicht * vochtPerKilo) - (urenVanafEersteGlas - 0.5) * (this.gewicht * 0.002);
            if (BAG > 0) {
                if (BAG > maxPromille) { maxPromille = BAG; }
                return Double.parseDouble(new DecimalFormat("##.###").format(BAG)); }
        }
        return 0;
    }

    public String bloedAlcoholGehalteToString() {
        return berekenBloedAlcoholGehalte() + " ‰";
    }

    public String maxPromilleToString() {
        return Double.parseDouble(new DecimalFormat("##.###").format(this.maxPromille)) + " ‰";
    }

    public double berekenAlcoholAfbraakSnelheid() {
        // afbraaksnelheid = 0,002 x g x g x r
        // Waarbij:
        // g = lichaamsgewicht (kg)
        // f = bij mannen 0,71 en bij vrouwen 0,62

        float factorGeslacht = (float) (geslacht.equals("man") ? 0.71 : 0.62);
        double AAS = 0.002 * this.gewicht * this.gewicht * factorGeslacht;
        return Double.parseDouble(new DecimalFormat("##.###").format(AAS));
    }

    public LocalTime berekenTijdTerugNuchter() {
        double gramAlcohol = getAantalStandaardGlazen() * 10;
        double AAS = berekenAlcoholAfbraakSnelheid();

        long afbraakTijdInMinuten = Math.round(gramAlcohol / AAS * 60);
        return getTijdEersteConsumptie().plusMinutes(afbraakTijdInMinuten);
    }

    public double getAantalStandaardGlazen() {
        double totaalAantalStandaardGlazen = 0;
        for (Consumptie c : lijstConsumpties) {
            totaalAantalStandaardGlazen += c.getDrank().getAantalStandaardGlazen();
        }
        return totaalAantalStandaardGlazen;
    }

    public String aantalStandaardGlazenToString() {
        double totaalAantalStandaardGlazen = getAantalStandaardGlazen();
        if (totaalAantalStandaardGlazen == 1) {
            return totaalAantalStandaardGlazen + " standaardglas";
        }
        return totaalAantalStandaardGlazen + " standaardglazen";
    }

    public String getDrankIcoon() {
        Map<Integer, Integer> map = new HashMap<>();

        for (Consumptie c : getLijstConsumpties()) {
            Integer val = map.get(c.getDrankId());
            map.put(c.getDrankId(), val == null ? 1 : val + 1);
        }

        Map.Entry<Integer, Integer> max = null;

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        int drankId =  max.getKey();
        return new DrankLijst().getDrankLijst().get(drankId).getIcoonNaam();
    }


// Parcelling part
    public static final Creator CREATOR = new Creator() {
        public OnderInvloed createFromParcel(Parcel in) { return new OnderInvloed(in); }

        @Override
        public OnderInvloed[] newArray(int size) { return new OnderInvloed[0]; }
    };

    public OnderInvloed(Parcel in){
        this.id = in.readString();
        this.datumDrinkAvond = LocalDate.parse(in.readString());
        this.tijdEersteConsumptie = LocalTime.parse(in.readString());
        this.lijstConsumpties = in.readArrayList(Consumptie.class.getClassLoader());
        this.maxPromille = in.readDouble();
        this.geslacht = in.readString();
        this.gewicht = in.readInt();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.datumDrinkAvond.toString());
        dest.writeString(this.tijdEersteConsumptie.toString());
        dest.writeList(this.lijstConsumpties);
        dest.writeDouble(this.maxPromille);
        dest.writeString(this.geslacht);
        dest.writeInt(this.gewicht);
    }
}
