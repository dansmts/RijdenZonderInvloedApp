package be.pxl.app.rijdenzonderinvloed.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Drank implements Parcelable {
    private int id;
    private String soortDrank;
    private double aantalCl;
    private double alcoholPercentage;
    private double aantalStandaardGlazen;
    private String icoonNaam;


    public Drank(int id, String soortDrank, double aantalCl, double alcoholPercentage, double aantalStandaardGlazen, String icoonNaam) {
        this.id = id;
        this.soortDrank = soortDrank;
        this.aantalCl = aantalCl;
        this.alcoholPercentage = alcoholPercentage;
        this.aantalStandaardGlazen = aantalStandaardGlazen;
        this.icoonNaam = icoonNaam;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSoortDrank() { return soortDrank; }
    public void setSoortDrank(String soortDrank) { this.soortDrank = soortDrank; }

    public double getAantalCl() { return aantalCl; }
    public void setAantalCl(double aantalCl) { this.aantalCl = aantalCl; }

    public double getAlcoholPercentage() { return alcoholPercentage; }
    public void setAlcoholPercentage(double alcoholPercentage) { this.alcoholPercentage = alcoholPercentage; }

    public double getAantalStandaardGlazen() { return aantalStandaardGlazen; }
    public void setAantalStandaardGlazen(double aantalStandaardGlazen) { this.aantalStandaardGlazen = aantalStandaardGlazen; }

    public String getIcoonNaam() { return icoonNaam; }
    public void setIcoonNaam(String icoonNaam) { this.icoonNaam = icoonNaam; }

    @Override
    public String toString() {
        return getSoortDrank() + " " + getAantalCl() + " cl - " + getAlcoholPercentage() + "%";
    }

    public String standaardGlazenToString() {
        if ( getAantalStandaardGlazen() == 1) {
            return getAantalStandaardGlazen() + " standaard glas";
        } else {
            return getAantalStandaardGlazen() + " standaard glazen";
        }
    }

// Parcelling part
    public static final Creator CREATOR = new Creator() {
        public Drank createFromParcel(Parcel in) { return new Drank(in); }

        @Override
        public Drank[] newArray(int size) { return new Drank[0]; }
    };
    public Drank(Parcel in){
        this.id = in.readInt();
        this.soortDrank = in.readString();
        this.aantalCl = in.readDouble();
        this.alcoholPercentage = in.readDouble();
        this.aantalStandaardGlazen = in.readDouble();
        this.icoonNaam = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.soortDrank);
        dest.writeDouble(this.aantalCl);
        dest.writeDouble(this.alcoholPercentage);
        dest.writeDouble(this.aantalStandaardGlazen);
        dest.writeString(this.icoonNaam);
    }
}
