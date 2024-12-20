package Oyun;
import javafx.application.Application;
import sample.Arayuz;
import java.io.*;
public class Main{
    public static void main (String[] args) throws IOException {
        Arayuz.launch(Arayuz.class,args);
        // Arayüzü başlat
        /*Arayuz arayuz = new Arayuz();
        arayuz.Main(new Main()); // Main referansını geç
        Application.launch(Arayuz.class, args);*/
        /*Arayuz arayuz = new Arayuz();
        arayuz.setMain(this);
        arayuz.launch(Arayuz.class,args);*/
        /*Arayuz arayuz = new Arayuz();
        Oyuncu oyuncu = arayuz.GetOyuncu();
        Oyuncu bilgisayar = new Oyuncu();
        System.out.println("fdasfasSeviye :" + oyuncu.GetSeviye() + "\nAd:" + oyuncu.getOyuncuAdi());
        int adim=1;
        do{
            adim=Savas(adim,oyuncu,bilgisayar);
        }while(!(oyuncu.Kartlar_bitti_mi() || bilgisayar.Kartlar_bitti_mi()) && !(adim==6 || Oyuncu.son_tur_mu));
        Kazanan_kontrol(oyuncu,bilgisayar);*/
    }
    public static int Savas(int adim,Oyuncu oyuncu,Oyuncu bilgisayar) throws IOException {
        WriteFile(adim,"\n----------" + adim + ".Adim----------\n" + oyuncu.KartlariniYazdir() + "\nOyuncu(Vuracağı Hasar) -- Bilgisayar(Vuracağı Hasar)\n");
        oyuncu.kartSec(adim);
        bilgisayar.kartSec(0);
        for(int i = 0; i < 3; i++){
            SavasAraclari insankart = oyuncu.GetSecilenKartlar().get(i);
            SavasAraclari pckart = bilgisayar.GetSecilenKartlar().get(i);
            insankart.DurumGuncelle(pckart.SaldiriHesapla(insankart)); // bilgisayar insana vuruyor
            pckart.DurumGuncelle(insankart.SaldiriHesapla(pckart)); // insan bilgisayara vuruyor
            oyuncu.SeviyeAyarlama(pckart); //Bilgisayar kartinin dayanikliligini kontrol eder ona gore insanin seviyesini ayarlar
            bilgisayar.SeviyeAyarlama(insankart); //Insan kartinin dayanikliligini kontrol eder ona gore bilgisayarin seviyesini ayarlar
            WriteFile(0,i+1 + ".Eslesme:    " + oyuncu.GetSecilenKartlar().get(i).getAltSinif() + "(" + insankart.SaldiriHesapla(pckart) + ")" + "  <-->  " +  bilgisayar.GetSecilenKartlar().get(i).getAltSinif() + "(" + pckart.SaldiriHesapla(insankart) + ")" + "\n");
        }
        WriteFile(0,"\nOyuncu seviye: " + oyuncu.GetSeviye() + "\nBilgisayar seviye: " + bilgisayar.GetSeviye() + "\n\nDagitimdaki Kartlarin:" + oyuncu.DagitimdakiKartlariYazdir() + "\n");
        oyuncu.KartKontrol(adim);
        bilgisayar.KartKontrol(adim);
        return adim+1;
    }
    public static void Kazanan_kontrol(Oyuncu oyuncu, Oyuncu bilgisayar) throws IOException {
        if(oyuncu.GetSeviye()> bilgisayar.GetSeviye())
            WriteFile(0,"İnsanlar kazandı , bilgisayar kaybetti");
        else if (oyuncu.GetSeviye()== bilgisayar.GetSeviye()) {
            int insandayaniklilik=0,pcdayaniklilik=0;
            for(SavasAraclari x:oyuncu.GetKartlarin())
                insandayaniklilik+=x.getDayaniklilik();
            for (SavasAraclari x:bilgisayar.GetKartlarin())
                pcdayaniklilik+=x.getDayaniklilik();
            if(insandayaniklilik>pcdayaniklilik)
                WriteFile(0,"İnsanlar kazandı , bilgisayar kaybetti");
            else
                WriteFile(0,"Bilgisayar kazandı , insanlar kaybetti");
        } else
            WriteFile(0,"Bilgisayar kazandı , insanlar kaybetti");
    }
    static void WriteFile(int adim,String metin) throws IOException {
        if(adim==1) {
            BufferedWriter clear = new BufferedWriter(new FileWriter("adimlar.txt"));
            clear.close();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("adimlar.txt",true));
        writer.write(metin);
        writer.close();
    }
}