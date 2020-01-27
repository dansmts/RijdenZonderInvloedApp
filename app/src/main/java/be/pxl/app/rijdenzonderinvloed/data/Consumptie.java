package be.pxl.app.rijdenzonderinvloed.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Consumptie implements Parcelable {
    private int drankId;
    private LocalDate datumConsumptie;
    private LocalTime tijdConsumptie;

    public Consumptie(int drankId, LocalDate datumConsumptie, LocalTime tijdConsumptie) {
        this.drankId = drankId;
        this.datumConsumptie = datumConsumptie;
        this.tijdConsumptie = tijdConsumptie;
    }

    public int getDrankId() { return drankId; }
    public void setDrankId(int drankId) { this.drankId = drankId; }

    public LocalDate getDatumConsumptie() { return datumConsumptie; }
    public void setDatumConsumptie(LocalDate datumConsumptie) { this.datumConsumptie = datumConsumptie; }

    public LocalTime getTijdConsumptie() { return tijdConsumptie; }
    public void setTijdConsumptie(LocalTime tijdConsumptie) { this.tijdConsumptie = tijdConsumptie; }

    public Drank getDrank() {
        DrankLijst drankLijst = new DrankLijst();
        return drankLijst.getDrankLijst().get(this.drankId);
    }

// Parcelling part
    public static final Creator CREATOR = new Creator() {
        public Consumptie createFromParcel(Parcel in) { return new Consumptie(in); }

        @Override
        public Consumptie[] newArray(int size) { return new Consumptie[0]; }
    };

    public Consumptie(Parcel in){
        this.drankId = in.readInt();
        this.datumConsumptie = LocalDate.parse(in.readString());
        this.tijdConsumptie = LocalTime.parse(in.readString());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.drankId);
        dest.writeString(this.datumConsumptie.toString());
        dest.writeString(this.tijdConsumptie.toString());
    }
}
